package eticaret.androidciftci.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by asus on 05.01.2017.
 */

public class RetrofitIletisimModel {
    @SerializedName("case")
    @Expose
    public String _case;
    @SerializedName("mesaj")
    @Expose
    public String mesaj;
}
