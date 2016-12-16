package eticaret.androidciftci.Controller;

import eticaret.androidciftci.Model.RetrofitAddProductModel;
import eticaret.androidciftci.Model.RetrofitLoginModel;
import eticaret.androidciftci.Model.RetrofitRegisterModel;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by asus on 23.11.2016.
 */

public interface RestInterfaceController {
    // GET yada POST mu olduğunu belirliyoruz.
    @GET("/api/login?")
    //
    void getLoginJsonValues(@Query("email") String email, @Query("password") String password, Callback<RetrofitLoginModel> response);

    @GET("/api/register?")
    void getRegisterJsonValues(@Query("email") String email, @Query("password") String password, @Query("name") String name, @Query("surname") String surname, @Query("phone_number") String phone, Callback<RetrofitRegisterModel> response);

    @GET("/api/addProduct?")
    void getAddProductsJsonValues(@Query("uyeEmail") String uyeEmail, @Query("urunAdi") String urunAdi, @Query("sehir") String sehir, @Query("aciklama") String aciklama, @Query("fiyat") String fiyat, @Query("stok") String stok, @Query("token") String token, Callback<RetrofitAddProductModel> response);
}
