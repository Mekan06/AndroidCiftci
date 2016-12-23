package eticaret.androidciftci;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
        if(token.isEmpty()){
            Toast.makeText(getApplicationContext(), "Giriş Yapılmadı", Toast.LENGTH_LONG).show();
            //YÖNLENDİR
        }
        else{
            txtEmail.setText(email);
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
