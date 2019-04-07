/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whereabout.dao;


import whereabout.model.karyawan.Karyawan;
import whereabout.view.FormLogin;
import whereabout.view.PanelHc;

/**
 *
 * @author khoerulAbu
 */
public interface KaryawanDao {
    public void loadRecord(PanelHc panelHc);
    public void uploadKaryawan(String namaFile);
    public void login(FormLogin formLogin);
    public Karyawan findByKode(PanelHc panelHc);
    public Karyawan findByKode(String kode);
}
