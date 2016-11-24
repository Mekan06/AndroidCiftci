package eticaret.androidciftci;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by asus on 23.11.2016.
 */

public class RetrofitLoginModel {

    @SerializedName("case")
    @Expose
    public String _case;
    @SerializedName("mesaj")
    @Expose
    public String mesaj;
    @SerializedName("token")
    @Expose
    public String token;

}

