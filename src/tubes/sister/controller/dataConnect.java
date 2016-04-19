/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tubes.sister.controller;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Mu'ti C Putro
 */
public class dataConnect {
    protected Connection conn = null;

    public Connection getConn() {
        return conn;
    }
        
    public void connect(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
//            conn = DriverManager.getConnection("jdbc:mysql://localhost/chatting_tcp_low", "root", "");
            conn = DriverManager.getConnection("jdbc:mysql://www.db4free.net:3306/tubessister","tubessister","tubessister");
            System.out.println("konek");
        }
        catch(ClassNotFoundException | SQLException ex){
            ex.printStackTrace();
        }
    }
    
    public ResultSet retrieveQuery(String query){
        try {
            PreparedStatement p = conn.prepareStatement(query);
            return p.executeQuery();   
        } 
        catch (SQLException ex) {
            ex.printStackTrace();
            return null;
			
        }
    }
    
    public int doQuery(String query){//masuk
        try {
            PreparedStatement p = conn.prepareStatement(query);
            return p.executeUpdate();   
        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }
    }
    
    public void close(){
        try {
            this.conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(dataConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
