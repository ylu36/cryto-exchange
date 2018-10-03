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
                e.printStackTrace();
            } finally {
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }    
    
    public static class Confirm {
        public final String offerId;
        public final int amount;
        
        public int getCurrentAmount(Database db, String offerId) {
            Connection conn = null;
            int total = -1;
            String query = "SELECT * FROM orderbook WHERE offerID = '" + offerId + "';";
            try {
                conn = db.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                while(rs.next()) {       
                    total = rs.getInt("amount");
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            return total;
        }

        public Confirm(Database db, String offerId, int amount, boolean debugFlag, boolean noResponseFlag) {
            int balance;
            this.offerId = offerId;
            this.amount = amount;
            
            if(debugFlag || noResponseFlag) 
                return;            
            int total = getCurrentAmount(db, offerId);
            balance = total - amount;
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
        }

        // public void print(Database db) {
        //     Connection conn = null;
        //     try {
        //         String query = "select * from orderbook;";
        //         conn = db.getConnection();
        //         Statement stmt = conn.createStatement();
        //         ResultSet rs = stmt.executeQuery(query);
        //         while(rs.next()) {       
        //             String s = rs.getString("offerID");
        //             System.out.println(s + '\t' + rs.getInt("amount"));
        //         }   
        //     } catch (Exception e) {
        //         e.printStackTrace();
        //     } finally {           
        //         try { conn.close(); } catch (Exception e) { /* ignored */ }
        //     }
        // }
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
        Connection conn = null;
        public GetTransactions(Database db) {
            transactions = new ArrayList<>();
            String query = "SELECT * FROM transactions;";
            try {
                Connection conn = db.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                while(rs.next()) {
                    transactions.add(rs.getInt("id"));
                    System.out.println(rs.getString("message"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {           
                try { conn.close(); } catch (Exception e) { /* ignored */ }
            }
        }
    }

    
    public static class GetTransactionById {
        String message;
        int rate, amount;
        public GetTransactionById(Database db, int id) {
            String query = "SELECT * FROM transactions where id=" + id + ";";
            Connection conn = null;
            try {
                conn = db.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                while(rs.next()) {
                    message = rs.getString("message");
                    rate = rs.getInt("rate");
                    amount = rs.getInt("amount");
                }
               
            } catch (Exception e) {
                e.printStackTrace();
                message = "error";
            } finally {           
                try { conn.close(); } catch (Exception e) { /* ignored */ }
            }
        }
    }
}