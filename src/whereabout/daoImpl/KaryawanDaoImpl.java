/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whereabout.daoImpl;

import com.opencsv.CSVReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import whereabout.dao.KaryawanDao;
import whereabout.helper.Helper;
import whereabout.helper.Uploader;
import whereabout.model.karyawan.Karyawan;
import whereabout.model.karyawan.Kelamin;
import whereabout.model.karyawan.Role;
import whereabout.model.karyawan.Status;
import whereabout.model.karyawan.StatusPekerjaan;
import whereabout.view.FormLogin;
import whereabout.view.FormUtama;
import whereabout.view.PanelHc;

/**
 *
 * @author khoerulAbu
 */
public class KaryawanDaoImpl implements KaryawanDao {

    @Override
    public void loadRecord(PanelHc panel) {
        String filePath = panel.field_nama_path_file.getText();

        String loadQuery = "LOAD DATA LOCAL INFILE '" + filePath + "' INTO TABLE Karyawan FIELDS TERMINATED BY ';'" + " LINES TERMINATED BY '\n' "
                + "(id,kode,"
                + " nama, tanggalLahir,tempatLahir,kelamin,tanggalBergabung,tanggalKeluar,"
                + " alamat, email,nomorTelepon,statusMenikah,statusPekerjaan, password, createdDate, updatedDate) ";
        Uploader.readCsvUsingLoad(Helper.st, loadQuery);

        // method upload
        uploadKaryawan(filePath);
        Tabel(panel);
    }

    @Override
    public void uploadKaryawan(String namaFile) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try (CSVReader reader = new CSVReader(new FileReader(namaFile), ';')) {
            String insertQuery = "Insert into Karyawan "
                    + " values (null,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            Helper.ps = Helper.ambilKoneksi().prepareStatement(insertQuery);
            String[] rowData = null;
            int i = 0;
            while ((rowData = reader.readNext()) != null) {
                for (String data : rowData) {
                    System.out.println("Strin : " + data);
                    Helper.ps.setString((i % 15) + 1, data);
                    
                    if (++i % 15 == 0) {
                        Helper.ps.addBatch();// add batch
                    }
                    if (i % 150 == 0)// insert when the batch size is 10
                    {
                        Helper.ps.executeBatch();
                    }
                }
            }
            JOptionPane.showMessageDialog(null, "Data Successfully Uploaded");
          
        } catch (IOException | SQLException ex) {
            System.out.println("Error : " + ex.getMessage());
            Logger.getLogger(KaryawanDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //load default tabel
    public void Tabel(PanelHc panel) {
        try {
            List<Karyawan> list = new ArrayList<>();

            String[] header = {"NIK", "NAMA", "JENIS KELAMIN", "TANGGAL LAHIR"};
            DefaultTableModel tabelModel = new DefaultTableModel(null, header);
            panel.tabel_panel_hc.setModel(tabelModel);
            String query =  "SELECT * FROM Karyawan order by nama ASC";
            if(!panel.field_cari_panel_hc.getText().isEmpty()){
                query = "SELECT * FROM Karyawan where nama like '%"+panel.field_cari_panel_hc.getText()+"%' "
                        + "or kode like '%"+panel.field_cari_panel_hc.getText()+"%' ";
            }
           
            Helper.st = Helper.ambilKoneksi().createStatement();
            Helper.rs = Helper.st.executeQuery(query);

            while (Helper.rs.next()) {
                Karyawan k = new Karyawan();
                k.setId(Helper.rs.getLong(1));
                k.setKode(Helper.rs.getString(2));
                k.setNama(Helper.rs.getString(3));
                k.setTanggalLahir(Helper.rs.getDate(4));
                k.setTempatLahir(Helper.rs.getString(5));
                if (Helper.rs.getInt(6) == Kelamin.PRIA.ordinal()) {
                    k.setKelamin(Kelamin.PRIA);
                } else {
                    k.setKelamin(Kelamin.WANITA);
                }
                k.setTanggalBergabung(Helper.rs.getDate(7));
                //if(!"0000-00-00".equals(String.valueOf(Helper.rs.getDate(8)))){
                k.setTanggalKeluar(Helper.rs.getDate(8));  
                
                
                k.setAlamat(Helper.rs.getString(9));
                k.setEmail(Helper.rs.getString(10));
                k.setNomorTelepon(Helper.rs.getString(11));
                if (Helper.rs.getInt(12) == Status.MENIKAH.ordinal()) {
                    k.setStatusMenikah(Status.CERAI);
                } else if (Helper.rs.getInt(12) == Status.LAJANG.ordinal()) {
                    k.setStatusMenikah(Status.LAJANG);
                } else if (Helper.rs.getInt(12) == Status.MENIKAH.ordinal()) {
                    k.setStatusMenikah(Status.MENIKAH);
                }

                if (Helper.rs.getInt(13) == StatusPekerjaan.KONTRAK.ordinal()) {
                    k.setStatusPekerjaan(StatusPekerjaan.KONTRAK);
                } else if (Helper.rs.getInt(13) == StatusPekerjaan.TETAP.ordinal()) {
                    k.setStatusPekerjaan(StatusPekerjaan.TETAP);
                }
                k.setPassword(Helper.rs.getString(14));
                k.setCreatedDate(Helper.rs.getDate(15));
                //if(!"0000-00-00".equals(String.valueOf(Helper.rs.getDate(16)))){
                     k.setUpdatedDate(Helper.rs.getDate(16));
                //}
               
                list.add(k);
            }

            for (Karyawan kr : list) {
                tabelModel.addRow(new Object[]{
                    kr.getKode(),
                    kr.getNama(),
                    kr.getKelamin(),
                    kr.getTanggalLahir()
                });
            }
            panel.jTabbedPane1.setVisible(false);
            panel.field_nama_path_file.setText(null);
        } catch (SQLException ex) {
            Logger.getLogger(KaryawanDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void login(FormLogin formLogin) {
        try {
            String query = "SELECT * FROM Karyawan where kode = '"+formLogin.jTextField1.getText()+"' "
                    + " and password = '"+formLogin.jPasswordField1.getText()+"' ";
            Helper.st = Helper.ambilKoneksi().createStatement();
            Helper.rs = Helper.st.executeQuery(query);
            if(Helper.rs.next()){
                FormUtama formUtama = new FormUtama();
                formUtama.nikKaryawan = Helper.rs.getString("kode");
                if(Helper.rs.getInt("role") == (Role.ADMIN.ordinal())){
                    formUtama.role = Role.ADMIN;
                }
                else  if(Helper.rs.getInt("role") == (Role.HC.ordinal())){
                    formUtama.role = Role.HC;
                }
                else  if(Helper.rs.getInt("role") == (Role.KARYAWAN.ordinal())){
                    formUtama.role = Role.KARYAWAN;
                }
                
                switch (formUtama.role) {
                    case ADMIN:
                        JOptionPane.showMessageDialog(formLogin, "Welcome, you are login as "+Role.ADMIN.name());
                        break;
                    case HC:
                        JOptionPane.showMessageDialog(formLogin, "Welcome, you are login as "+Role.HC.name());
                        break;
                    case KARYAWAN:
                        JOptionPane.showMessageDialog(formLogin, "Welcome, you are login as "+Role.KARYAWAN.name());
                        formUtama.btn_hc_menu.setEnabled(false);
                        formUtama.btn_laporan_menu.setEnabled(false);
                        break;
                    default:
                        break;
                }
                
                formUtama.setVisible(true);
                formLogin.dispose();
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(KaryawanDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Karyawan findByKode(PanelHc panelHc) {
        Karyawan karyawan = new Karyawan();
        try {
            int baris = panelHc.tabel_panel_hc.getSelectedRow();
            String kode = panelHc.tabel_panel_hc.getValueAt(baris, 0).toString();
            String query = "SELECT * FROM Karyawan where kode = '"+kode+"'";
            Helper.st = Helper.ambilKoneksi().createStatement();
            Helper.rs = Helper.st.executeQuery(query);
            if(Helper.rs.next()){
                karyawan.setId(Helper.rs.getInt(1));
                karyawan.setKode(Helper.rs.getString(2));
                karyawan.setNama(Helper.rs.getString(3));
                karyawan.setTanggalLahir(Helper.rs.getDate(4));
                karyawan.setTempatLahir(Helper.rs.getString(5));
                if(Helper.rs.getInt(6) == Kelamin.PRIA.ordinal()){
                    karyawan.setKelamin(Kelamin.PRIA);
                }
                else {
                    karyawan.setKelamin(Kelamin.WANITA);
                }
                
                karyawan.setTanggalBergabung(Helper.rs.getDate(7));
                karyawan.setTanggalKeluar(Helper.rs.getDate(8));
                karyawan.setAlamat(Helper.rs.getString(9));
                karyawan.setEmail(Helper.rs.getString(10));
                karyawan.setNomorTelepon(Helper.rs.getString(11));
                if(Helper.rs.getInt(12) == Status.MENIKAH.ordinal()){
                    karyawan.setStatusMenikah(Status.MENIKAH);
                }
                else if(Helper.rs.getInt(12) == Status.LAJANG.ordinal()){
                    karyawan.setStatusMenikah(Status.LAJANG);
                }
                else {
                     karyawan.setStatusMenikah(Status.CERAI);
                }
                if(Helper.rs.getInt(13) == StatusPekerjaan.TETAP.ordinal()){
                    karyawan.setStatusPekerjaan(StatusPekerjaan.TETAP);
                }
                else if(Helper.rs.getInt(13) == StatusPekerjaan.KONTRAK.ordinal()){
                    karyawan.setStatusPekerjaan(StatusPekerjaan.KONTRAK);
                }
                
                karyawan.setPassword(Helper.rs.getString(14));
                karyawan.setCreatedDate(Helper.rs.getDate(15));
                karyawan.setUpdatedDate(Helper.rs.getDate(16));
                
                if(Helper.rs.getInt(17) == Role.ADMIN.ordinal()){
                    karyawan.setRole(Role.ADMIN);
                }
                else if(Helper.rs.getInt(17) == Role.HC.ordinal()){
                    karyawan.setRole(Role.HC);
                }
                else {
                     karyawan.setRole(Role.KARYAWAN);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(KaryawanDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return karyawan;
    }
    
    public void tableKlik(PanelHc panelHc){
        Karyawan k = findByKode(panelHc);
        panelHc.lbl_isi_kode.setText(k.getKode());
        panelHc.lbl_isi_nama.setText(k.getNama());
        panelHc.lbl_tgl_lahir.setText(String.valueOf(k.getTanggalLahir()));
        panelHc.lbl_tempat_lahir.setText(k.getTempatLahir());
        panelHc.lbl_jk.setText(k.getKelamin().name());
        panelHc.lbl_tgl_gabung.setText(String.valueOf(k.getTanggalBergabung()));
        panelHc.lbl_tgl_keluar.setText(String.valueOf(k.getTanggalKeluar()));
        panelHc.textArea_isi_alamat.setText(k.getAlamat());
        panelHc.lbl_email.setText(k.getEmail());
        panelHc.lbl_nomor_hp.setText(k.getNomorTelepon());
        panelHc.lbl_status.setText(k.getStatusMenikah().name());
        panelHc.lbl_status_kerja.setText(k.getStatusPekerjaan().name());
        panelHc.lbl_create.setText(String.valueOf(k.getCreatedDate()));
        panelHc.lbl_update.setText(String.valueOf(k.getUpdatedDate()));
        panelHc.lbl_role.setText(k.getRole().name());
        
        panelHc.jTabbedPane1.remove(panelHc.subPanelHc);
        panelHc.jTabbedPane1.addTab("Informasi", panelHc.subPanelHcInformasi);
        panelHc.jTabbedPane1.setVisible(true);
        
    }

    @Override
    public Karyawan findByKode(String kode) {
      Karyawan karyawan = new Karyawan();
        try {
            String query = "SELECT * FROM Karyawan where kode = '"+kode+"'";
            Helper.st = Helper.ambilKoneksi().createStatement();
            Helper.rs = Helper.st.executeQuery(query);
            if(Helper.rs.next()){
                karyawan.setId(Helper.rs.getInt(1));
                karyawan.setKode(Helper.rs.getString(2));
                karyawan.setNama(Helper.rs.getString(3));
                karyawan.setTanggalLahir(Helper.rs.getDate(4));
                karyawan.setTempatLahir(Helper.rs.getString(5));
                if(Helper.rs.getInt(6) == Kelamin.PRIA.ordinal()){
                    karyawan.setKelamin(Kelamin.PRIA);
                }
                else {
                    karyawan.setKelamin(Kelamin.WANITA);
                }
                
                karyawan.setTanggalBergabung(Helper.rs.getDate(7));
                karyawan.setTanggalKeluar(Helper.rs.getDate(8));
                karyawan.setAlamat(Helper.rs.getString(9));
                karyawan.setEmail(Helper.rs.getString(10));
                karyawan.setNomorTelepon(Helper.rs.getString(11));
                if(Helper.rs.getInt(12) == Status.MENIKAH.ordinal()){
                    karyawan.setStatusMenikah(Status.MENIKAH);
                }
                else if(Helper.rs.getInt(12) == Status.LAJANG.ordinal()){
                    karyawan.setStatusMenikah(Status.LAJANG);
                }
                else {
                     karyawan.setStatusMenikah(Status.CERAI);
                }
                if(Helper.rs.getInt(13) == StatusPekerjaan.TETAP.ordinal()){
                    karyawan.setStatusPekerjaan(StatusPekerjaan.TETAP);
                }
                else if(Helper.rs.getInt(13) == StatusPekerjaan.KONTRAK.ordinal()){
                    karyawan.setStatusPekerjaan(StatusPekerjaan.KONTRAK);
                }
                
                karyawan.setPassword(Helper.rs.getString(14));
                karyawan.setCreatedDate(Helper.rs.getDate(15));
                karyawan.setUpdatedDate(Helper.rs.getDate(16));
                
                if(Helper.rs.getInt(17) == Role.ADMIN.ordinal()){
                    karyawan.setRole(Role.ADMIN);
                }
                else if(Helper.rs.getInt(17) == Role.HC.ordinal()){
                    karyawan.setRole(Role.HC);
                }
                else {
                     karyawan.setRole(Role.KARYAWAN);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(KaryawanDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return karyawan;
    }
}
