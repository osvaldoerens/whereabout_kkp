/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whereabout.model.karyawan;

import java.util.Date;
import whereabout.model.base.ModelBase;

/**
 *
 * @author khoerulAbu
 */
public class Karyawan extends ModelBase {
    
    private String      nama;
    private Kelamin     kelamin;
    private Date        tanggalLahir;
    private String      tempatLahir;
    private Date        tanggalBergabung;
    private Date        tanggalKeluar;
    private String      alamat;
    private String      email;
    private String      nomorTelepon;
    private Status      statusMenikah;
    private StatusPekerjaan statusPekerjaan;
    private String      password;
    private Role        role;

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public Kelamin getKelamin() {
        return kelamin;
    }

    public void setKelamin(Kelamin kelamin) {
        this.kelamin = kelamin;
    }

    public Date getTanggalLahir() {
        return tanggalLahir;
    }

    public void setTanggalLahir(Date tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }

    public String getTempatLahir() {
        return tempatLahir;
    }

    public void setTempatLahir(String tempatLahir) {
        this.tempatLahir = tempatLahir;
    }

    public Date getTanggalBergabung() {
        return tanggalBergabung;
    }

    public void setTanggalBergabung(Date tanggalBergabung) {
        this.tanggalBergabung = tanggalBergabung;
    }

    public Date getTanggalKeluar() {
        return tanggalKeluar;
    }

    public void setTanggalKeluar(Date tanggalKeluar) {
        this.tanggalKeluar = tanggalKeluar;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNomorTelepon() {
        return nomorTelepon;
    }

    public void setNomorTelepon(String nomorTelepon) {
        this.nomorTelepon = nomorTelepon;
    }

    public Status getStatusMenikah() {
        return statusMenikah;
    }

    public void setStatusMenikah(Status statusMenikah) {
        this.statusMenikah = statusMenikah;
    }

    public StatusPekerjaan getStatusPekerjaan() {
        return statusPekerjaan;
    }

    public void setStatusPekerjaan(StatusPekerjaan statusPekerjaan) {
        this.statusPekerjaan = statusPekerjaan;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
    
}