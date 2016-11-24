package eticaret.androidciftci;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class LoginActivity extends AppCompatActivity {
    //onCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /*SharedPreferences mSharedPrefs = getSharedPreferences("kayitDosyasi", MODE_PRIVATE);
        if (!mSharedPrefs.getString("token", "N/A").equals("N/A")) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }*/

        final String Base_Url = "http://192.168.1.8:8000";
        final Button btnLogin = (Button) findViewById(R.id.btn_login);
        final TextView txtRegister = (TextView) findViewById(R.id.link_register);

        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
                LoginActivity.this.finish();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView txtEmail = (TextView) findViewById(R.id.txtEmail);
                TextView txtPassword = (TextView) findViewById(R.id.txtPassword);
                RestAdapter restAdapter = new RestAdapter.Builder()
                        .setEndpoint(Base_Url)
                        .build();
                RestInterfaceController restInterfaceController = restAdapter.create(RestInterfaceController.class);
                restInterfaceController.getJsonValues(txtEmail.getText().toString(), txtPassword.getText().toString(), new Callback<RetrofitLoginModel>() {
                    @Override
                    public void success(RetrofitLoginModel retrofitLoginModels, Response response) {
                        Toast.makeText(getApplicationContext(), "Giris basarili", Toast.LENGTH_SHORT).show();

                        SharedPreferences mSharedPrefs = getSharedPreferences("kayitDosyasi", MODE_PRIVATE);

                        SharedPreferences.Editor mPrefsEditor = mSharedPrefs.edit(); //Düzenlemek için bu satırı kullanarak dosyayı açıyoruz.
                        String token = retrofitLoginModels.token; //token adında String bir değişken belirliyoruz
                        mPrefsEditor.putString("token", token); //keydeger adını vererek veri //değişkenindeki değeri dosyaya kaydediyoruz.
                        mPrefsEditor.commit();//Bu satır düzenlenilen dosyayı kapatmaya yarıyor

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        LoginActivity.this.finish();
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



