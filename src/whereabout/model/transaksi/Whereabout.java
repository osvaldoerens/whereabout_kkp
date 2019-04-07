/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whereabout.model.transaksi;

import whereabout.model.base.ModelBase;

/**
 *
 * @author khoerulAbu
 */
public class Whereabout extends ModelBase{
    
    private String tujuan;
    private String deskripsi;
    private String jamPergi;
    private String jamKembali;
    private String kodeKaryawan;

    public String getTujuan() {
        return tujuan;
    }

    public void setTujuan(String tujuan) {
        this.tujuan = tujuan;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getJamPergi() {
        return jamPergi;
    }

    public void setJamPergi(String jamPergi) {
        this.jamPergi = jamPergi;
    }

    public String getJamKembali() {
        return jamKembali;
    }

    public void setJamKembali(String jamKembali) {
        this.jamKembali = jamKembali;
    }

    public String getKodeKaryawan() {
        return kodeKaryawan;
    }

    public void setKodeKaryawan(String kodeKaryawan) {
        this.kodeKaryawan = kodeKaryawan;
    }   
}
