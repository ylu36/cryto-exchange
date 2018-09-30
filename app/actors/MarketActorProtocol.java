package actors;
import java.util.*;
import play.db.*;
import java.sql.*;
public class MarketActorProtocol {
    public static class Hold {
        public final String offerId;
        public final int amount;
        public Hold(String offerId, int amount) {
            this.offerId = offerId;
            this.amount = amount;
        }
    }

    public static class Confirm {
        public final String offerId;
        public final int amount;
        public Confirm(String offerId, int amount) {
            this.offerId = offerId;
            this.amount = amount;
        }
    }
    public static class GetSellOffers {
        List<String> offerIDs;
        public GetSellOffers(Database db) {
            offerIDs = new ArrayList<>();
            String query = "SELECT offerID FROM orderbook;";
            try {
                Connection conn = db.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                while(rs.next()) {
                    offerIDs.add(rs.getString("offerID"));
                }              
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
    public static class GetSellOfferById {
        int amount;
        int rate;
        public GetSellOfferById(Database db, String offerID) {
            String query = "SELECT * FROM orderbook WHERE offerID = '" + offerID + "';";
            try {
                Connection conn = db.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                while(rs.next()) {
                    amount = rs.getInt("amount");
                    rate = rs.getInt("rate");
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}