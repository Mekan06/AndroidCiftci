package eticaret.androidciftci.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by asus on 05.01.2017.
 */

public class RetrofitMainPanelModel {
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
