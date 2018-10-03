package controllers;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import javax.inject.Inject;
import javax.inject.Singleton;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import scala.compat.java8.FutureConverters;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.pattern.Patterns;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.db.*;
import java.sql.*;
import actors.UserActor;
import actors.UserActorProtocol.*;
import actors.MarketActor;
import actors.MarketActorProtocol.*;
public class ExchangeController extends Controller {
    final ActorSystem system;
    final Database db;
    final ActorRef userActor, marketActor;
    private static int balanceUSD, balanceBTC;
    boolean debugFlag, noResponseFlag;
    @Inject
	public ExchangeController(ActorSystem system, Database db) {
        System.out.println("init exchangeController...");
        this.system = system;
        this.db = db;
        debugFlag = false;
        noResponseFlag = false;
        balanceBTC = 0;
        balanceUSD = 0;
        this.userActor = system.actorOf(UserActor.getProps());
        this.marketActor = system.actorOf(MarketActor.getProps());
        String dropTable = "DROP TABLE IF exists orderbook;";
        String createOrderbook = "CREATE TABLE IF NOT EXISTS orderbook (rate integer, amount integer, offerID text PRIMARY KEY);";
        String createTransactions = "CREATE TABLE IF NOT EXISTS transactions (id integer PRIMARY KEY AUTOINCREMENT, message text, amount integer, rate integer);";
        String initialize = "INSERT INTO orderbook (rate, amount, offerID) VALUES (100, 5, '431671cb'), (80, 2, '16b961ed'), (50, 12, '1e06381d');";
        try {
            Connection conn = db.getConnection();
            Statement stmt = conn.createStatement();
            stmt.execute(dropTable);
            stmt.execute(createOrderbook);
            stmt.execute(createTransactions);
            stmt.execute(initialize);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /* debug APIs */
    public Result setdebugconfirmfail() {
        debugFlag = true;
        ObjectNode result = Json.newObject();
        result.put("status", "success");
        return ok(result);
    }
    
    public Result setdebugconfirmno_response() {
        noResponseFlag = true;
        ObjectNode result = Json.newObject();
        result.put("status", "success");
        return ok(result);
    }

    public Result reset() {
        debugFlag = false;
        noResponseFlag = false;
        ObjectNode result = Json.newObject();
        result.put("status", "success");
        return ok(result);
    }

    public Result addbalance(Integer amount) {
        balanceUSD += amount;
        ObjectNode result = Json.newObject();
        result.put("status", "success");
        return ok(result);
    }

    public Result getbalance() {
        ObjectNode result = Json.newObject();
        result.put("status", "success");
        result.put("USD", balanceUSD);
        result.put("BTC", balanceBTC);
        return ok(result);
    }


    public CompletionStage<Result> gettranactions() {  
        return FutureConverters.toJava(Patterns.ask(marketActor, new GetTransactions(db), 1000))
                .thenApply(response -> ok((String) response));
    }

    public CompletionStage<Result> gettranactionbyid(Integer id) {   
        return FutureConverters.toJava(Patterns.ask(marketActor, new GetTransactionById(db, id), 1000))
                .thenApply(response -> ok(parseGetTransactionById((String) response)));
    }

    public CompletionStage<Result> getselloffers() {
        return FutureConverters.toJava(Patterns.ask(marketActor, new GetSellOffers(db), 1000))
        .thenApply(response -> ok((ObjectNode)parseSellOffers((String)response)));
    }

    public CompletionStage<Result> getsellofferbyid(String offerId) {
        return FutureConverters.toJava(Patterns.ask(marketActor, new GetSellOfferById(db, offerId), 1000))
        .thenApply(response -> ok((ObjectNode)parseSellOfferById((String)response)));
    }

    public CompletionStage<Result> buy(int maxrate, int amount) {
        return FutureConverters.toJava(Patterns.ask(userActor, new PlaceOffer(db, marketActor, maxrate, amount, balanceUSD, debugFlag), 1000))
        .thenApply(response -> ok(parseBuyResult((String)response)));
    } 

    private ObjectNode parseSellOffers(String response) {
        ObjectNode result = Json.newObject();        
        result.put("status", "success");
        result.put("offers", response);
        return result;
    }

    private ObjectNode parseSellOfferById(String response) {
        ObjectNode result = Json.newObject();  
        System.out.println(response);
        if(response.split(" ")[2].equals("success")) {      
            result.put("status", "success");
            result.put("rate", response.split(" ")[0]);
            result.put("amount", response.split(" ")[1]);
        }
        else {
            result.put("status", "error");
            result.put("message", response.split(" ")[2]);
        }
        return result;
    }

    private ObjectNode parseGetTransactionById(String response) {
        ObjectNode result = Json.newObject();  
    
        result.put("status", "success");
        result.put("rate", response.split(" ")[1]);
        result.put("amount", response.split(" ")[2]);
        result.put("message", response.split(" ")[0]);
        return result;
    }

    private ObjectNode parseBuyResult(String response) {
        ObjectNode result = Json.newObject(); 
        if(isNumeric(response)) {    
            result.put("status", "success");
            result.put("transactionID", response);
        }
        else {
            result.put("status", "error");
            result.put("message", response);
        }
        return result;
    }

    public static boolean isNumeric(String strNum) {
        try {
            double d = Integer.parseInt(strNum);
        } catch (NumberFormatException | NullPointerException nfe) {
            return false;
        }
        return true;
    }
}
