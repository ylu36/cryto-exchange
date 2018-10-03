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
                message = "max_rate_too_low";   
                recordTransaction(db, message, 0, 0);     
                return;
            }            
            String query = "SELECT * FROM orderbook ORDER BY rate ASC;";
            try {
                conn = db.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                
                while(rs.next() && buyAmount > 0) {
                    offerId = rs.getString("offerID");
                    amount = rs.getInt("amount");
                    rate = rs.getInt("rate");
                    if(rate > maxrate) {
                        message = "max_rate_too_low";
                        recordTransaction(db, message, 0, 0);     
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
                    message = "not_enough_BTC_storage_in_orderbook";
                    recordTransaction(db, message, 0, 0);     
                    return;
                }    
                conn.close();          
            } catch (Exception e) {                
                e.printStackTrace();
            } finally {           
                try { conn.close(); } catch (Exception e) { /* ignored */ }
            }
            
            System.out.println(balanceUSD);
            System.out.println(cashNeed);
            if(cashNeed > balanceUSD) {
                message = "no_enough_balance";
                recordTransaction(db, message, 0, 0);     
                return;
            }

            boolean canHold;
            String id = "";
            Map<String, Integer> rateChart = new HashMap<>();
            rateChart.put("1e06381d", 50);
            rateChart.put("16b961ed", 80);
            rateChart.put("431671cb", 100);

            // send HOLD request to marketActor
            for (Map.Entry<String, List<Integer>> entry : orders.entrySet())
            {
                id = entry.getKey();
                int amount = entry.getValue().get(0);
                canHold = sendHoldRequest(db, marketActor, id, amount);
                message = "send_HOLD_request";
                rate = rateChart.get(id);
                if(canHold == false) {
                    // can't hold, return error
                    message = "HOLD_times_out";
                    recordTransaction(db, message, amount, rate);     
                    return;
                }
                recordTransaction(db, message, amount, rate);     
            }
            // send CONFIRM request to marketActor
            for (Map.Entry<String, List<Integer>> entry : orders.entrySet())
            {
                id = entry.getKey();
                int amount = entry.getValue().get(0);
                message = "send_CONFIRM_request"; 
                sendConfirmRequest(db, marketActor, id, amount);
                recordTransaction(db, message, amount, rate);     
            }
            int transaction_id = getLatestTranactionId(db);
            message = Integer.toString(transaction_id);
        }

        public boolean sendHoldRequest(Database db, ActorRef marketActor, String offerId, int amount) {
            try {
                String res = FutureConverters.toJava(Patterns.ask(marketActor, new Hold(db, offerId, amount), 1000))
                .thenApply(response -> (String) response).toCompletableFuture().get();
                
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
                .thenApply(response -> (String) response).toCompletableFuture();
            }
             catch (Exception e) {
                e.printStackTrace();
            }            
        }

        public void recordTransaction(Database db, String message, int amount, int rate) {
            Connection conn = null;
            try {
                conn = db.getConnection();
                String insertIntoTransaction = "INSERT INTO transactions (message, amount, rate) values(?,?,?);";

                PreparedStatement pstmt = conn.prepareStatement(insertIntoTransaction);
                pstmt.setString(1, message);
                pstmt.setInt(2, amount);
                pstmt.setInt(3, rate);
                pstmt.executeUpdate();
            } catch(Exception e) {
                e.printStackTrace();
            } finally {
                try { conn.close(); } catch (Exception e) { /* ignored */ }
            }
        }

        private int getLatestTranactionId(Database db) {
            Connection conn = null;
            int id = -1;
            try {
                conn = db.getConnection();
                String query = "SELECT * FROM transactions;";
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                while(rs.next()) {       
                    id = rs.getInt("id");
                } 
            } catch(Exception e) {
                e.printStackTrace();
            } finally {
                try { conn.close(); } catch (Exception e) { /* ignored */ }
            }
            return id;
        }
    }
}