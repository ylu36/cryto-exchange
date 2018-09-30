package actors;
import java.util.*;
import play.db.*;
import java.sql.*;
import java.io.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import actors.MarketActor;
import actors.MarketActorProtocol.*;
public class UserActorProtocol {
    public static class PlaceOffer {
        public final int maxrate;
        public int buyAmount, balanceUSD;
        public Map<String, List<Integer>> orders = new HashMap<>();
        String offerId;
        int amount, rate;
        String message;
        public PlaceOffer(Database db, int maxrate, int buyAmount, int balanceUSD) {
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
                    if(amount > 0) {
                        int num = Math.min(buyAmount, amount);
                        List<Integer> list = Arrays.asList(num, rate);
                        orders.put(offerId, list);
                        cashNeed += num * rate;
                        buyAmount -= num;
                    }
                }              
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
            // FutureConverters.toJava(Patterns.ask(marketActor, new GetTransactions(db), 1000))
            //             .thenApply(response -> ok((String) response));
            
        }
    }
}