package eticaret.androidciftci.Model;

/**
 * Created by asus on 24.12.2016.
 */

public class Urunler {
    public Urunler(){

    }
    public Urunler(String id, String uyeEmail, String aciklama, String fiyat, String stok, String urunAdi) {
        this.id = id;
        this.uyeEmail = uyeEmail;
        this.aciklama = aciklama;
        this.fiyat = fiyat;
        this.stok = stok;
        this.urunAdi = urunAdi;
    }

    private String id;
    private String uyeEmail;
    private String aciklama;
    private String fiyat;
    private String stok;
    private String urunAdi;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUyeEmail() {
        return uyeEmail;
    }

    public void setUyeEmail(String uyeEmail) {
        this.uyeEmail = uyeEmail;
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
        return urunAdi;
    }

    public void setUrunAdi(String urunAdi) {
        this.urunAdi = urunAdi;
    }
}
