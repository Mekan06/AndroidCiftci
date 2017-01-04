package eticaret.androidciftci;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import eticaret.androidciftci.Controller.RestInterfaceController;
import eticaret.androidciftci.Model.RetrofitUserPanelModel;
import eticaret.androidciftci.Model.Urunler;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class UserPanelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_panel);
        final Button btnAddProduct = (Button) findViewById(R.id.btnAddProduct);

        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddProductActivity.class);
                startActivity(intent);
                UserPanelActivity.this.finish();
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();

        final TextView txtEmail = (TextView) findViewById(R.id.lblEmail);
        final ArrayAdapter<String> adapter;
        final List<String> urunAdlari = new ArrayList<String>();
        final ListView listViewUrunler = (ListView) findViewById(R.id.listViewProducts);

        SharedPreferences mSharedPrefs = getSharedPreferences("kayitDosyasi", MODE_PRIVATE);
        String token = mSharedPrefs.getString("token", "N/A");
        String email = mSharedPrefs.getString("email", "N/A");
        if (!token.isEmpty()) { //giris basarili
            txtEmail.setText(email);

            String Base_Url = getString(R.string.base_url);
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(Base_Url)
                    .build();

            RestInterfaceController restInterfaceController = restAdapter.create(RestInterfaceController.class);
            restInterfaceController.getUserPanelJsonValues(token, new Callback<RetrofitUserPanelModel>() {
                @Override
                public void success(final RetrofitUserPanelModel retrofitUserPanelModel, Response response) {
                    if (retrofitUserPanelModel._case.equals("1")) {
                        final List<Urunler> listUrunler = retrofitUserPanelModel.urunler;
                        for (Urunler urn : listUrunler) {
                            urunAdlari.add(urn.getUrunAdi().toString());
                        }
                        ArrayAdapter<String> veriAdaptoru = new ArrayAdapter<String>(UserPanelActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, urunAdlari);
                        listViewUrunler.setAdapter(veriAdaptoru);
                        listViewUrunler.setOnItemClickListener(new AdapterView.OnItemClickListener() { //add_product sayfasına gidecek
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Log.e("TEST", listUrunler.get(position).getUrunAdi());
                                Intent myIntent = new Intent(getApplicationContext(), AddProductActivity.class);
                                myIntent.putExtra("productName", listUrunler.get(position).getUrunAdi());
                                myIntent.putExtra("city", listUrunler.get(position).getSehir());
                                myIntent.putExtra("price", listUrunler.get(position).getFiyat());
                                myIntent.putExtra("stock", listUrunler.get(position).getStok());
                                myIntent.putExtra("explanation", listUrunler.get(position).getAciklama());
                                startActivity(myIntent);
                            }
                        });
                    } else {
                        Toast.makeText(getApplicationContext(), retrofitUserPanelModel.mesaj, Toast.LENGTH_SHORT);
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    Toast.makeText(getApplicationContext(), "hata olustu: " + error.getMessage(), Toast.LENGTH_LONG).show();
                    Log.e("ERROR", error.getMessage());
                }
            });


        } else {
            Toast.makeText(getApplicationContext(), "Giriş Yapılmadı", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            UserPanelActivity.this.finish();
        }
    }
}