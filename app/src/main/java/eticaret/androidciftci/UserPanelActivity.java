package eticaret.androidciftci;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import eticaret.androidciftci.Controller.RestInterfaceController;
import eticaret.androidciftci.Model.RetrofitUserPanelModel;
import eticaret.androidciftci.Model.Urunler;
import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.http.Headers;

public class UserPanelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_panel);

        final TextView txtEmail = (TextView) findViewById(R.id.lblEmail);
        final Button btnAddProduct = (Button) findViewById(R.id.btnAddProduct);

        SharedPreferences mSharedPrefs = getSharedPreferences("kayitDosyasi", MODE_PRIVATE);
        String token = mSharedPrefs.getString("token", "N/A");
        String email = mSharedPrefs.getString("email", "N/A");
        if (!token.isEmpty()) { //giris basarili
            txtEmail.setText(email);

            //onStart() tarafına al
            final Spinner sp = (Spinner) findViewById(R.id.spinnerProducts);
            final ArrayAdapter<String> adapter;
            final List<String> urunAdlari = new ArrayList<String>();


            String Base_Url = getString(R.string.base_url);
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setRequestInterceptor(new RequestInterceptor() {
                        @Override
                        public void intercept(RequestFacade request) {
                            request.addHeader("Accept", "application/json;versions=1");
                        }
                    })
                    .setEndpoint(Base_Url)
                    .build();

            RestInterfaceController restInterfaceController = restAdapter.create(RestInterfaceController.class);
            restInterfaceController.getUserPanelJsonValues(token, new Callback<RetrofitUserPanelModel>() {
                @Override
                public void success(RetrofitUserPanelModel retrofitUserPanelModel, Response response) {
                    if (retrofitUserPanelModel._case.equals("1")) {
                        List<Urunler> urun = retrofitUserPanelModel.urunler;
                        for (Urunler urn : urun) {
                            Log.e("asd----------", urn.getUrunAdi());

                            //urunAdlari.add(urn.getUrunAdi().toString());
                        }
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    Toast.makeText(getApplicationContext(), "hata olustu: " + error.getMessage(), Toast.LENGTH_LONG).show();
                    Log.e("ERROR", error.getMessage());
                }
            });
            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, urunAdlari);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sp.setAdapter(adapter);
        } else {
            Toast.makeText(getApplicationContext(), "Giriş Yapılmadı", Toast.LENGTH_LONG).show();
            //YÖNLENDİR
        }

        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddProductActivity.class);
                startActivity(intent);
                UserPanelActivity.this.finish();
            }
        });

    }
}
