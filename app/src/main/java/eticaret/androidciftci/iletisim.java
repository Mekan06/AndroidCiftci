package eticaret.androidciftci;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import eticaret.androidciftci.Controller.RestInterfaceController;
import eticaret.androidciftci.Model.RetrofitIletisimModel;
import eticaret.androidciftci.Model.RetrofitRegisterModel;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class iletisim extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iletisim);

        final EditText txtAd = (EditText) findViewById(R.id.txtAd);
        final EditText txtSoyad = (EditText) findViewById(R.id.txtSoyad);
        final EditText txtMesaj = (EditText) findViewById(R.id.txtMesaj);
        Button btnGonder = (Button) findViewById(R.id.btnGonder);



        btnGonder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Base_Url = getString(R.string.base_url);
                RestAdapter restAdapter = new RestAdapter.Builder()
                        .setEndpoint(Base_Url)
                        .build();
                RestInterfaceController restInterfaceController = restAdapter.create(RestInterfaceController.class);
                restInterfaceController.getIletisimJsonValues(txtAd.getText().toString(), txtSoyad.getText().toString(),txtMesaj.getText().toString(), new Callback<RetrofitIletisimModel>() {
                    @Override
                    public void success(RetrofitIletisimModel retrofitIletisimModel, Response response) {
                        if (retrofitIletisimModel._case.equals("1")) {
                            Toast.makeText(getApplicationContext(), "Mesaj yollandı", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Hata: " + retrofitIletisimModel.mesaj, Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                        Log.e("HATA", error.getLocalizedMessage());
                    }
                });
            }
        });

    }
}
