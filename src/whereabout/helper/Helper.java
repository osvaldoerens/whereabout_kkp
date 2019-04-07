/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whereabout.helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author khoerulAbu
 */
public class Helper {
    
    public static Connection koneksi;
    public static ResultSet rs;
    public static Statement st;
    public static PreparedStatement ps;
    public static SimpleDateFormat sdf;
    
    
    public static Connection ambilKoneksi(){
        try{
            String url  = "jdbc:mysql://localhost/whereabout_kkp?zeroDateTimeBehavior=convertToNull";
            String user = "root";
            String pass = "root";
            koneksi = DriverManager.getConnection(url, user, pass);
        } catch (SQLException ex) {
            Logger.getLogger(Helper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return koneksi;
    }
    
    public static String formatTanggal(Date tanggal){
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(tanggal);
    }
    
}
