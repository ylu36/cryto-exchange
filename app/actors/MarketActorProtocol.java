package actors;
import java.util.*;
import play.db.*;
import java.sql.*;
import java.io.*;
public class MarketActorProtocol {
    public void insertIntoTransaction(Database db, String message) {
        try {
            String query = "INSERT INTO transactions (message) values (?);";
            Connection conn = db.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, message);
            pstmt.executeUpdate();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static class Hold {
        public final String offerId;
        public int amount, total;
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

            // get BTC/USD rate
            int rate;
            if(offerId == "431671cb")
                rate = 100;
            else if(offerId == "16b961ed")
                rate = 80;
            else 
                rate = 50;
        }
    }
    
    
    public static class Confirm {
        public final String offerId;
        public final int amount;
        int balance, total;

        public Confirm(Database db, String offerId, int amount, boolean debugFlag, boolean noResponseFlag) {
            
            this.offerId = offerId;
            this.amount = amount;
            if(debugFlag || noResponseFlag) 
                return;
            if(offerId == "431671cb")
                total = 5;
            else if(offerId == "16b961ed")
                total = 2;
            else 
                total = 12;
            balance = total - amount;
            System.out.println("total is " + total + " amount is " + amount);
            System.out.println("set amount to " + balance + " for offerID=" + offerId);
            String query = "UPDATE orderbook SET amount = ? WHERE offerID = ?;";

            try {                        
                balance = total-amount;
                Connection conn = db.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query);    
                pstmt.setInt(1, balance);  
                pstmt.setString(2, offerId);                                       
                pstmt.executeUpdate();               
                conn.close();
            } catch (Exception e) {                
                e.printStackTrace();
            }             
            print(db);
        }

        public void print(Database db) {
            try {
                String query = "select * from orderbook;";
                Connection conn = db.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                while(rs.next()) {       
                    String s = rs.getString("offerID");
                    System.out.println(s + '\t' + rs.getInt("amount"));
                }   
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static class GetSellOffers {
        List<String> offerIDs;
        public GetSellOffers(Database db) {
            offerIDs = new ArrayList<>();
            String query = "insert into transactions(message) values('here I am'); SELECT offerID FROM orderbook;";
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
            // MarketActorProtocol.GetSellOffers hello = new MarketActorProtocol.GetSellOffers(db);
            // hello.insertIntoTransaction(db, "here I am ");
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
            String query = "SELECT * FROM transactions;";
            System.out.println("here");
            try {
                Connection conn = db.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                while(rs.next()) {
                    transactions.add(rs.getInt("id"));
                    System.out.println(rs.getString("message"));
                }
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    
    // public static class GetTransactionById {
    //     int amount;
    //     int rate;
    //     String message;
    //     public GetTransactionById(Database db, int id) {
    //         String query = "SELECT * FROM transactions where id=" + id + ";";

    //         try {
    //             Connection conn = db.getConnection();
    //             Statement stmt = conn.createStatement();
    //             ResultSet rs = stmt.executeQuery(query);
    //             while(rs.next()) {
    //                 amount = rs.getInt("amount");
    //                 rate = rs.getInt("rate");
    //             }
    //             message = "success";
    //             conn.close();
    //         } catch (Exception e) {
    //             e.printStackTrace();
    //             message = "error";
    //         }
    //     }
    // }

    // public static class ConfirmFail {
    //     String message;
    //     public class ConfirmFail() {

    //     }
    // }
}