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
        Statement stmt;
        public Hold(Database db, String offerId, int amount) {
            this.offerId = offerId;
            this.amount = amount;
            String query = "SELECT * FROM orderbook WHERE offerID = '" + offerId + "';";
            try {
                Connection conn = db.getConnection();
                stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                while(rs.next()) {       
                    total = rs.getInt("amount");
                }       
                conn.close();
            } catch (Exception e) {
                StringWriter sw = new StringWriter();
                e.printStackTrace(new PrintWriter(sw));
            }
            System.out.println("called he34re"); 
            // update(db, offerId, amount);  
            // print(db, offerId, amount);
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
                StringWriter sw = new StringWriter();
                e.printStackTrace(new PrintWriter(sw));
            }
        }
    }
    
    // System.out.println(query);
    // stmt.executeQuery(query);
    // // rs = stmt.executeQuery("select * from orderbook;");
    // // while(rs.next()) {
    // //     System.out.println("after hold:"+rs.getInt("amount"));
    // // }   
    // System.out.println("balance is "+balance);     
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
                StringWriter sw = new StringWriter();
                e.printStackTrace(new PrintWriter(sw));
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
                message = "";
            } catch (Exception e) {
                StringWriter sw = new StringWriter();
                e.printStackTrace(new PrintWriter(sw));
                message = sw.toString();
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
            } catch (Exception e) {
                StringWriter sw = new StringWriter();
                e.printStackTrace(new PrintWriter(sw));
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
            } catch (Exception e) {
                StringWriter sw = new StringWriter();
                e.printStackTrace(new PrintWriter(sw));
                message = sw.toString();
            }
        }
    }
}