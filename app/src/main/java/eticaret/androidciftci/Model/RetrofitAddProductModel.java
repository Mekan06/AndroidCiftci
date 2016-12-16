package eticaret.androidciftci.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by asus on 16.12.2016.
 */

public class RetrofitAddProductModel {
    @SerializedName("case")
    @Expose
    public String _case;
    @SerializedName("mesaj")
    @Expose
    public String mesaj;
}
