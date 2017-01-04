package eticaret.androidciftci.Model;

/**
 * Created by asus on 24.12.2016.
 */

public class Urunler {
    private String id;
    private String uye_email;
    private String aciklama;
    private String fiyat;
    private String stok;
    private String urun_adi;
    private String sehir;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUyeEmail() {
        return uye_email;
    }

    public void setUyeEmail(String uyeEmail) {
        this.uye_email = uyeEmail;
    }

    public String getAciklama() {
        return aciklama;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }

    public String getFiyat() {
        return fiyat;
    }

    public void setFiyat(String fiyat) {
        this.fiyat = fiyat;
    }

    public String getStok() {
        return stok;
    }

    public void setStok(String stok) {
        this.stok = stok;
    }

    public String getUrunAdi() {
        return urun_adi;
    }

    public void setUrunAdi(String urunAdi) {
        this.urun_adi = urunAdi;
    }

    public String getSehir() {
        return sehir;
    }

    public void setSehir(String sehir) {
        this.sehir = sehir;
    }
}
