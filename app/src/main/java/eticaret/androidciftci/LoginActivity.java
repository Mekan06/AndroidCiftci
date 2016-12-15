package eticaret.androidciftci;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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

        final String Base_Url = getString(R.string.base_url);
        final Button btnLogin = (Button) findViewById(R.id.btn_login);
        final TextView txtRegister = (TextView) findViewById(R.id.link_register);
        final TextView txtEmail = (TextView) findViewById(R.id.txtEmail);
        final TextView txtPassword = (TextView) findViewById(R.id.txtPassword);

        //session

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
                if (TextUtils.isEmpty(txtEmail.getText()) || TextUtils.isEmpty(txtPassword.getText()))
                    Toast.makeText(getApplicationContext(), "Email veya Şifre boş olamaz", Toast.LENGTH_LONG).show();
                else {
                    RestAdapter restAdapter = new RestAdapter.Builder()
                            .setEndpoint(Base_Url)
                            .build();
                    RestInterfaceController restInterfaceController = restAdapter.create(RestInterfaceController.class);
                    restInterfaceController.getJsonValues(txtEmail.getText().toString(), txtPassword.getText().toString(), new Callback<RetrofitLoginModel>() {
                        @Override
                        public void success(RetrofitLoginModel retrofitLoginModels, Response response) {
                            if (retrofitLoginModels._case.equals("1")) {// giris basarili
                                Toast.makeText(getApplicationContext(), "Giris basarili", Toast.LENGTH_LONG).show();

                                // android lokal token kayit
                                SharedPreferences mSharedPrefs = getSharedPreferences("kayitDosyasi", MODE_PRIVATE);
                                SharedPreferences.Editor mPrefsEditor = mSharedPrefs.edit(); //Düzenlemek için acilir
                                String token = retrofitLoginModels.token; //token adında String bir değişken belirliyoruz
                                mPrefsEditor.putString("token", token); //keydeger adını vererek veri değişkenindeki değeri dosyaya kaydediyoruz.
                                mPrefsEditor.putString("email", txtEmail.getText().toString());
                                mPrefsEditor.commit();//Bu satır düzenlenilen dosyayı kapatmaya yarıyor


                                //yonlendirme
                                Intent intent = new Intent(getApplicationContext(), UserPanelActivity.class);
                                startActivity(intent);
                                Toast.makeText(getApplicationContext(), "Üye girişi başarılı", Toast.LENGTH_LONG).show();
                                LoginActivity.this.finish();

                            } else {
                                Toast.makeText(getApplicationContext(), "HATALI GIRIS", Toast.LENGTH_LONG).show();
                            }


                        }

                        @Override
                        public void failure(RetrofitError error) {
                            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                            Log.e("HATA", error.getLocalizedMessage());
                        }
                    });
                }

            }
        });
    }

}



