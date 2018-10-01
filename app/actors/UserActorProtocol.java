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
        public PlaceOffer(Database db, ActorRef marketActor, int maxrate, int buyAmount, int balanceUSD) {
            this.buyAmount = buyAmount;
            this.maxrate = maxrate;
            this.balanceUSD = balanceUSD;
            int cashNeed = 0;
            // get lowest sell offer
            if(maxrate < 50) {
                // max rate too low; return error
                message = "max rate too low";
                return;
            }            
            String query = "SELECT * FROM orderbook ORDER BY rate ASC;";
            try {
                Connection conn = db.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                
                while(rs.next() && buyAmount > 0) {
                    offerId = rs.getString("offerID");
                    amount = rs.getInt("amount");
                    rate = rs.getInt("rate");
                    // System.out.println(offerId+'\t'+amount +'\t'+rate);
                    if(amount > 0) {
                        int num = Math.min(buyAmount, amount);
                        List<Integer> list = Arrays.asList(num, rate);
                        orders.put(offerId, list);
                        cashNeed += num * rate;
                        buyAmount -= num;
                    }
                }    
                conn.close();          
            } catch (Exception e) {
                StringWriter sw = new StringWriter();
                e.printStackTrace(new PrintWriter(sw));
            }
            message = orders.toString();
            System.out.println(balanceUSD);
            System.out.println(cashNeed);
            if(cashNeed > balanceUSD) {
                message = "not enough balance";
                return;
            }

            // send HOLD request to marketActor
            for (Map.Entry<String, List<Integer>> entry : orders.entrySet())
            {
                String id = entry.getKey();
                int amount = entry.getValue().get(0);
                sendHoldRequest(db, marketActor, id, amount);
            }
                        
        }

        public boolean sendHoldRequest(Database db, ActorRef marketActor, String offerId, int amount) {
            try {
                String res = FutureConverters.toJava(Patterns.ask(marketActor, new Hold(db, offerId, amount), 1000))
                .thenApply(response -> (String) response).toCompletableFuture().get();
                System.out.println("back to Hold request");           
                System.out.println("after 3 sec...");
                System.out.println(res);
            }
             catch (Exception e) {
                StringWriter sw = new StringWriter();
                e.printStackTrace(new PrintWriter(sw));
            }
            return true;
        }
    }
}