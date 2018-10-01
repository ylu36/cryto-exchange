package actors;
import java.util.*;
import play.db.*;
import java.sql.*;
import java.io.*;
public class MarketActorProtocol {
    public static class Hold {
        public final String offerId;
        public int amount, total;
        int balance;
        String message;
        public Hold(Database db, String offerId, int amount) {
            this.offerId = offerId;
            this.amount = amount;
            Connection conn = null;
            String query = "SELECT * FROM orderbook WHERE offerID = '" + offerId + "';";
            try {
                conn = db.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                while(rs.next()) {       
                    total = rs.getInt("amount");
                }       
                
                message = "success";
            } catch (Exception e) {
                message = "error";
                StringWriter sw = new StringWriter();
                e.printStackTrace(new PrintWriter(sw));
            } finally {
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            // update(db, offerId, amount);  
            // print(db, offerId, amount);
            balance = total - amount;

            // get BTC/USD rate
            int rate;
            if(offerId == "431671cb")
                rate = 100;
            else if(offerId == "16b961ed")
                rate = 80;
            else 
                rate = 50;

            // insert into transactions table
            insertIntoTransaction(db, offerId, amount, rate);
        }

        public void update(Database db, String offerId, int amount) {
            String query1 = "UPDATE orderbook SET amount = " + balance + " WHERE offerID = '" + offerId + "';";

            try {                        
                balance = total-amount;
                Connection conn = db.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query1);                                              
                pstmt.executeUpdate();
                System.out.println("yodated"); 
                conn.close();
            } catch (Exception e) {
                StringWriter sw = new StringWriter();
                e.printStackTrace(new PrintWriter(sw));
            }
        }


        public void print(Database db, String offerId, int amount) {
            try {
                String query = "select * from orderbook;";
                Connection conn = db.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                while(rs.next()) {       
                    String s = rs.getString("offerID");
                    System.out.println(s + rs.getInt("amount"));
                }   
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void insertIntoTransaction(Database db, String offerId, int amount, int rate) {
            try {
                String query = "INSERT INTO transactions (offerID, amount, rate) values (?,?,?);";
                Connection conn = db.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query);
                pstmt.setString(1, offerId);
                pstmt.setInt(2, amount);
                pstmt.setInt(3, rate);
                pstmt.executeUpdate();
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
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
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public static class GetSellOfferById {
        int amount;
        int rate;
        String message;
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
                message = "success";
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
                message = "error";
            }
        }
    }

    public static class GetTransactions {
        List<Integer> transactions;
        public GetTransactions(Database db) {
            transactions = new ArrayList<>();
            String query = "SELECT id FROM transactions;";

            try {
                Connection conn = db.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                while(rs.next()) {
                    transactions.add(rs.getInt("id"));
                }
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static class GetTransactionById {
        int amount;
        int rate;
        String message;
        public GetTransactionById(Database db, int id) {
            String query = "SELECT * FROM transactions where id=" + id + ";";

            try {
                Connection conn = db.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                while(rs.next()) {
                    amount = rs.getInt("amount");
                    rate = rs.getInt("rate");
                }
                message = "success";
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
                message = "error";
            }
        }
    }
}