/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whereabout.helper;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author khoerulAbu
 */
public class Uploader {
    //method static untuk baca csv yang diupload
    public static void readCsvUsingLoad(Statement st, String query){
        try {
                System.out.println(query);
                st = Helper.ambilKoneksi().createStatement();
                st.execute(query);
        } catch (SQLException ex) {
            Logger.getLogger(Uploader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
