package actors;
import akka.actor.ActorRef;
import akka.pattern.Patterns;
import play.db.*;
import java.util.*;
import java.sql.*;
import java.io.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import scala.compat.java8.FutureConverters;
import java.util.concurrent.CompletionStage;
import actors.MarketActor;
import actors.MarketActorProtocol.*;
import java.util.Timer;
import java.util.TimerTask;
public class UserActorProtocol {
    public static class PlaceOffer {
        public final int maxrate;
        public int buyAmount, balanceUSD;
        public Map<String, List<Integer>> orders = new HashMap<>();
        String offerId;
        int amount, rate;
        String message;
        boolean debugFlag, noResponseFlag;
        public PlaceOffer(Database db, ActorRef marketActor, int maxrate, int buyAmount, int balanceUSD, boolean debugFlag) {
            this.buyAmount = buyAmount;
            this.maxrate = maxrate;
            this.balanceUSD = balanceUSD;
            this.debugFlag = debugFlag;
            int cashNeed = 0;
            Connection conn = null;
            // get lowest sell offer
            if(maxrate < 50) {
                // max rate too low; return error
                message = "max rate too low";
                return;
            }            
            String query = "SELECT * FROM orderbook ORDER BY rate ASC;";
            try {
                conn = db.getConnection();
                String insertIntoTransaction = "INSERT INTO transactions (message) values('in user place offer');";

                // PreparedStatement pstmt = conn.prepareStatement(query);
                
                // pstmt.executeUpdate();
                // // stmt.executeQuery(insertIntoTransaction);  //NOT WORKING
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                
                while(rs.next() && buyAmount > 0) {
                    offerId = rs.getString("offerID");
                    amount = rs.getInt("amount");
                    rate = rs.getInt("rate");
                    if(rate > maxrate) {
                        message = "max rate too low";
                        return;
                    }
                    if(amount > 0) {
                        int num = Math.min(buyAmount, amount);
                        List<Integer> list = Arrays.asList(num, rate);
                        orders.put(offerId, list);
                        cashNeed += num * rate;
                        buyAmount -= num;
                    }
                }
                if(buyAmount > 0) {
                    message = "not enough BTC storage in orderbook";
                    return;
                }    
                conn.close();          
            } catch (Exception e) {
                StringWriter sw = new StringWriter();
                e.printStackTrace(new PrintWriter(sw));
            } finally {           
                try { conn.close(); } catch (Exception e) { /* ignored */ }
            }
            message = orders.toString();
            System.out.println(balanceUSD);
            System.out.println(cashNeed);
            if(cashNeed > balanceUSD) {
                message = "user has not enough balance";
                return;
            }

            boolean canHold;
            String id = "";
            // send HOLD request to marketActor
            for (Map.Entry<String, List<Integer>> entry : orders.entrySet())
            {
                id = entry.getKey();
                int amount = entry.getValue().get(0);
                canHold = sendHoldRequest(db, marketActor, id, amount);
                if(canHold == false) {
                    // can't hold, return error
                    System.out.println("HOLD times out");
                    return;
                }
            }
            // send CONFIRM request to marketActor
            for (Map.Entry<String, List<Integer>> entry : orders.entrySet())
            {
                id = entry.getKey();
                int amount = entry.getValue().get(0);
                sendConfirmRequest(db, marketActor, id, amount);
            }
        }

        public boolean sendHoldRequest(Database db, ActorRef marketActor, String offerId, int amount) {
            try {
                String res = FutureConverters.toJava(Patterns.ask(marketActor, new Hold(db, offerId, amount), 1000))
                .thenApply(response -> (String) response).toCompletableFuture().get();
                System.out.println("back to Hold request");           
                System.out.println("after 3 sec...");
                System.out.println(res);
                return true;
            }
             catch (Exception e) {
                e.printStackTrace();
                return false;
            }            
        }

        public void sendConfirmRequest(Database db, ActorRef marketActor, String offerId, int amount) {
            try {
                FutureConverters.toJava(Patterns.ask(marketActor, new Confirm(db, offerId, amount, debugFlag, noResponseFlag), 1000))
                .thenApply(response -> (String) response).toCompletableFuture();//.get();
                System.out.println("confirmRequest called");
            }
             catch (Exception e) {
                e.printStackTrace();
            }            
        }
    }
}