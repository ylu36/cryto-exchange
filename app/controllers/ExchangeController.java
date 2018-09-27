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
import play.Logger;
import play.Logger.ALogger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import actors.UserActor;
import actors.UserActorProtocol.*;
public class ExchangeController extends Controller {
    final ActorSystem system;
    final ActorRef userActor;
    private static final ALogger logger = Logger.of(ExchangeController.class);
    private static int balanceUSD, balanceBTC;

    @Inject
	public ExchangeController(ActorSystem system) {
        logger.info("initializing exchange controller...");
        System.out.println("init exchangeController...");
        this.system = system;
        this.balanceUSD = 0;
        this.balanceBTC = 0;
        this.userActor = system.actorOf(UserActor.getProps());
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

    // public Result gettranactions() {
    //     ObjectNode result = Json.newObject();
    //     result.put("status", "success");
    //     return ok(result);
    // }

    public CompletionStage<Result> gettranactions() {
        return FutureConverters.toJava(Patterns.ask(userActor, new PlaceOffer("name", 10), 1000))
                .thenApply(response -> ok((String) response));
    }
}
