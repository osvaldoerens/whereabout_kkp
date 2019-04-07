/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whereabout.daoImpl.transaksi;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import whereabout.dao.trsansaksi.WhereaboutDao;
import whereabout.daoImpl.KaryawanDaoImpl;
import whereabout.helper.Helper;
import whereabout.model.karyawan.Karyawan;
import whereabout.model.transaksi.Whereabout;
import whereabout.view.PanelWhereabout;

/**
 *
 * @author khoerulAbu
 */
public class WhereaboutDaoImpl implements WhereaboutDao{
    
    private final KaryawanDaoImpl karyawanDaoImpl = new KaryawanDaoImpl();

    @Override
    public void requestLeave(PanelWhereabout panelWhereabout) {
        try {
            String query = "INSERT INTO Whereabout values(null,?,?,?,?,?,?,?)";
            Helper.ps = Helper.ambilKoneksi().prepareStatement(query);
            Helper.ps.setString(1, panelWhereabout.field_tujuan.getText());
            Helper.ps.setString(2, panelWhereabout.field_deskripsi.getText());
            Helper.ps.setString(3, panelWhereabout.nikKaryawan);
            Helper.ps.setString(4, Helper.formatTanggal(new Date()));
            Helper.ps.setString(5, Helper.formatTanggal(new Date()));
            Helper.ps.setString(6, panelWhereabout.field_jam_pergi.getText());
            Helper.ps.setString(7, panelWhereabout.field_jam_kembali.getText());
            
            Helper.ps.executeUpdate();
            JOptionPane.showMessageDialog(panelWhereabout, "Whereabout Berhasil dibuat !");
            Tabel(panelWhereabout);
        } catch (SQLException ex) {
            Logger.getLogger(WhereaboutDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //load default tabel
    public void Tabel(PanelWhereabout panel) {
        try {
            List<Whereabout> list = new ArrayList<>();

            String[] header = {"ID","KARYAWAN", "TUJUAN", "DESKRIPSI", "JAM PERGI","JAM KEMBALI"};
            DefaultTableModel tabelModel = new DefaultTableModel(null, header);
            panel.tabel_whereabout.setModel(tabelModel);
            String query =  "SELECT * FROM Whereabout order by id ASC";
            if(!panel.field_cari.getText().isEmpty()){
                query = "SELECT * FROM Whereabout where nikKaryawan like '%"+panel.field_cari.getText()+"%' ";
            }
           
            Helper.st = Helper.ambilKoneksi().createStatement();
            Helper.rs = Helper.st.executeQuery(query);

            while (Helper.rs.next()) {
                Whereabout w = new Whereabout();
                w.setId(Helper.rs.getInt(1));
                w.setTujuan(Helper.rs.getString(2));
                w.setDeskripsi(Helper.rs.getString(3));
                w.setKodeKaryawan(Helper.rs.getString(4));
                w.setCreatedDate(Helper.rs.getDate(5));
                w.setUpdatedDate(Helper.rs.getDate(6));
                w.setJamPergi(Helper.rs.getString(7));
                w.setJamKembali(Helper.rs.getString(8));
            
                list.add(w);
            }

            for (Whereabout wr : list) {
                tabelModel.addRow(new Object[]{
                    wr.getId(),
                    wr.getKodeKaryawan(),
                    wr.getTujuan(),
                    wr.getDeskripsi(),
                    wr.getJamPergi(),
                    wr.getJamKembali()
                });
            }
            panel.sub_panel_.setVisible(false);
        } catch (SQLException ex) {
            Logger.getLogger(WhereaboutDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * 
     * @param panel 
     */
    public void tableKlik(PanelWhereabout panel){
        int baris = panel.tabel_whereabout.getSelectedRow();
        String kode = panel.tabel_whereabout.getValueAt(baris, 1).toString();
        Karyawan k  = karyawanDaoImpl.findByKode(kode);
        Whereabout w = findById(Integer.valueOf(panel.tabel_whereabout.getValueAt(baris, 0).toString()));
        panel.lbl_nama.setText(k.getNama());
        panel.lbl_nik.setText(k.getKode());
        panel.lbl_tujuan.setText(w.getTujuan());
        panel.field_isi_deskripsi.setText(w.getDeskripsi());
        panel.lbl_jam_pergi.setText(w.getJamPergi());
        panel.lbl_jam_kembali.setText(w.getJamKembali());
        panel.lbl_tanggal_buat.setText(Helper.formatTanggal(w.getCreatedDate())); 
    }

    @Override
    public Whereabout findById(int id) {
        Whereabout whereabout = new Whereabout();
        try {
            String query = "SELECT * FROM Whereabout where id = '"+id+"' ";
            Helper.st = Helper.ambilKoneksi().createStatement();
            Helper.rs = Helper.st.executeQuery(query);
            if(Helper.rs.next()){
                whereabout.setId(Helper.rs.getInt("id"));
                whereabout.setTujuan(Helper.rs.getString("tujuan"));
                whereabout.setDeskripsi(Helper.rs.getString("deskripsi"));
                whereabout.setJamPergi(Helper.rs.getString("jamPergi"));
                whereabout.setJamKembali(Helper.rs.getString("jamKembali"));
                whereabout.setCreatedDate(Helper.rs.getDate("createdDate"));
                whereabout.setUpdatedDate(Helper.rs.getDate("updatedDate")); 
            }
        } catch (SQLException ex) {
            Logger.getLogger(WhereaboutDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return whereabout;
    }
}
