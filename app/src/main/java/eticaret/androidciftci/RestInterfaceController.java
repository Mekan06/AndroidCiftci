package eticaret.androidciftci;

import javax.xml.transform.Result;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by asus on 23.11.2016.
 */

public interface RestInterfaceController {
    // GET yada POST mu olduğunu belirliyoruz.
    @GET("/api/login?") //
    void getJsonValues(@Query("email") String email ,@Query("password") String password, Callback<RetrofitLoginModel> response );

    @GET("/api/register?")
    void getJsonValues(@Query("email") String email ,@Query("password") String password, @Query("name") String name, @Query("surname") String surname, @Query("phone_number") String phone ,Callback<RetrofitRegisterModel> response );
}
