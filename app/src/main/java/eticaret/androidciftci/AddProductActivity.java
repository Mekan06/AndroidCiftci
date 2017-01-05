package eticaret.androidciftci;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import eticaret.androidciftci.Controller.RestInterfaceController;
import eticaret.androidciftci.Model.RetrofitAddProductModel;
import eticaret.androidciftci.Model.RetrofitLoginModel;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class AddProductActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        setTitle("Ürün Ekleme Ekranı");

        //token kontrol
        SharedPreferences mSharedPrefs = getSharedPreferences("kayitDosyasi", MODE_PRIVATE);
        String token = mSharedPrefs.getString("token", "N/A");
        if (token.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Giris yapilmadi", Toast.LENGTH_LONG).show();
            AddProductActivity.this.finish();
        }

        final EditText txtProductName = (EditText) findViewById(R.id.txtProductName);
        final EditText txtCity = (EditText) findViewById(R.id.txtCity);
        final EditText txtPrice = (EditText) findViewById(R.id.txtPrice);
        final EditText txtStock = (EditText) findViewById(R.id.txtStock);
        final EditText txtExplanation = (EditText) findViewById(R.id.txtExplanation);
        Button btnAddProduct = (Button) findViewById(R.id.btnAddProduct);
        Button btnDeleteProduct = (Button) findViewById(R.id.btnDeleteProduct);

        Intent intent = getIntent();
        txtProductName.setText(intent.getStringExtra("productName"));
        txtCity.setText(intent.getStringExtra("city"));
        txtPrice.setText(intent.getStringExtra("price"));
        txtStock.setText(intent.getStringExtra("stock"));
        txtExplanation.setText(intent.getStringExtra("explanation"));

        //Ürün silme butonu ve işlemleri
        btnDeleteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ürün silme özelliği eklenecek
            }
        });

        // Ürün ekleme Butonu ve işlemleri
        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String productName = txtProductName.getText().toString();
                String city = txtCity.getText().toString();
                String price = txtPrice.getText().toString();
                String stock = txtStock.getText().toString();
                String explation = txtExplanation.getText().toString();

                //urun giris kontrol ve uyarilar
                if (productName.isEmpty()) {
                    txtProductName.setError("Boş olamaz");
                    txtProductName.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(txtProductName, InputMethodManager.SHOW_IMPLICIT);
                } else if (city.isEmpty()) {
                    txtCity.setError("Boş olamaz");
                    txtCity.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(txtCity, InputMethodManager.SHOW_IMPLICIT);
                } else if (price.isEmpty()) {
                    txtPrice.setError("Boş olamaz");
                    txtPrice.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(txtPrice, InputMethodManager.SHOW_IMPLICIT);
                } else if (stock.isEmpty()) {
                    txtStock.setError("Boş olamaz");
                    txtStock.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(txtStock, InputMethodManager.SHOW_IMPLICIT);
                }
                //urun kayit
                else {
                    SharedPreferences mSharedPrefs = getSharedPreferences("kayitDosyasi", MODE_PRIVATE);
                    String token = mSharedPrefs.getString("token", "N/A");
                    String email = mSharedPrefs.getString("email", "N/A");

                    final String Base_Url = getString(R.string.base_url);
                    RestAdapter restAdapter = new RestAdapter.Builder()
                            .setEndpoint(Base_Url)
                            .build();
                    RestInterfaceController restInterfaceController = restAdapter.create(RestInterfaceController.class);
                    restInterfaceController.getAddProductsJsonValues(email, productName, city, explation, price, stock, token, new Callback<RetrofitAddProductModel>() {
                        @Override
                        public void success(RetrofitAddProductModel retrofitAddProductModel, Response response) {
                            if (retrofitAddProductModel._case.equals("1")) {// kayit basarili
                                Toast.makeText(getApplicationContext(), retrofitAddProductModel.mesaj, Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(), UserPanelActivity.class);
                                startActivity(intent);
                                AddProductActivity.this.finish();
                            } else if (retrofitAddProductModel._case.equals("0")) {
                                Toast.makeText(getApplicationContext(), retrofitAddProductModel.mesaj, Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }
}
