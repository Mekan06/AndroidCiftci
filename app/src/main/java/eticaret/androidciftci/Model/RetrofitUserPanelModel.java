package eticaret.androidciftci.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus on 24.12.2016.
 */

public class RetrofitUserPanelModel {
    @SerializedName("case")
    @Expose
    public String _case;

    @SerializedName("urunler")
    @Expose
    public List<Urunler> urunler;

    @SerializedName("mesaj")
    @Expose
    public String mesaj;
}
