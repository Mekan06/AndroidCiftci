package eticaret.androidciftci;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import eticaret.androidciftci.Controller.RestInterfaceController;
import eticaret.androidciftci.Model.RetrofitMainPanelModel;
import eticaret.androidciftci.Model.RetrofitUserPanelModel;
import eticaret.androidciftci.Model.Urunler;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Ana Ekran");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public void onStart() {
        super.onStart();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Menu m = navigationView.getMenu();

        //uye giris yapilmis mi
        SharedPreferences mSharedPrefs = getSharedPreferences("kayitDosyasi", MODE_PRIVATE);
        String token = mSharedPrefs.getString("token", "N/A"); //token adında String bir değişken belirliyoruz
        boolean ziyaretci = true;
        if (token.isEmpty())  // Ziyaretci
            ziyaretci = true;
        else ziyaretci = false;
        m.findItem(R.id.nav_uye_girisi).setEnabled(ziyaretci);
        m.findItem(R.id.nav_uye_kayit).setEnabled(ziyaretci);

        m.findItem(R.id.nav_kullanici_paneli).setEnabled(!ziyaretci);
        m.findItem(R.id.nav_urun_ekle).setEnabled(!ziyaretci);
        m.findItem(R.id.nav_urun_listele).setEnabled(!ziyaretci);
        m.findItem(R.id.nav_cikis_yap).setEnabled(!ziyaretci);

        //Ürünlerin Listelenmesi
        final List<String> urunAdlari = new ArrayList<String>();
        final ListView listViewUrunler = (ListView) findViewById(R.id.listViewProducts);

        String Base_Url = getString(R.string.base_url);
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(Base_Url)
                .build();

        RestInterfaceController restInterfaceController = restAdapter.create(RestInterfaceController.class);
        restInterfaceController.getMainPanelJsonValues(new Callback<RetrofitMainPanelModel>() {
            @Override
            public void success(final RetrofitMainPanelModel retrofitMainPanelModel, Response response) {
                if (retrofitMainPanelModel._case.equals("1")) {
                    final List<Urunler> listUrunler = retrofitMainPanelModel.urunler;
                    for (Urunler urn : listUrunler) {
                        urunAdlari.add(urn.getUrunAdi() + "   -   " + urn.getUyeEmail() + " - " + urn.getSehir());
                    }
                    ArrayAdapter<String> veriAdaptoru = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, urunAdlari);
                    listViewUrunler.setAdapter(veriAdaptoru);
                    listViewUrunler.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            final String uyeEmail = listUrunler.get(position).getUyeEmail();
                            final String urunAdi = listUrunler.get(position).getUrunAdi();
                            final String sehir = listUrunler.get(position).getSehir();
                            final String fiyat = listUrunler.get(position).getFiyat();
                            final String stok = listUrunler.get(position).getStok();
                            final String aciklama = listUrunler.get(position).getAciklama();


                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (!isFinishing()) {
                                        new AlertDialog.Builder(MainActivity.this)
                                                .setTitle("Seçilen Ürün")
                                                .setMessage("Ürün sahibi: " + uyeEmail + "\n" +
                                                        "Ürün adı: " + urunAdi + "\n" +
                                                        "Şehir: " + sehir + "\n" +
                                                        "Fiyat: " + fiyat + "tl\n" +
                                                        "Stok: " + stok + "\n" +
                                                        "Açıklama: " + aciklama)
                                                .setCancelable(false)
                                                .setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {

                                                    }
                                                }).show();
                                    }
                                }
                            });
/*
                            new AlertDialog.Builder(getApplicationContext())
                                    .setTitle("Delete entry")
                                    .setMessage("Are you sure you want to delete this entry?")
                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            // continue with delete
                                        }
                                    })
                                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            // do nothing
                                        }
                                    })
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .show();*/
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), retrofitMainPanelModel.mesaj, Toast.LENGTH_SHORT);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getApplicationContext(), "hata olustu: " + error.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("ERROR", error.getMessage());
            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_uye_girisi) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_uye_kayit) {
            Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_urun_ekle) {
            Intent intent = new Intent(getApplicationContext(), AddProductActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_kullanici_paneli) {
            Intent intent = new Intent(getApplicationContext(), UserPanelActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_cikis_yap) {
            //token ve email silme
            SharedPreferences mSharedPrefs = getSharedPreferences("kayitDosyasi", MODE_PRIVATE);
            SharedPreferences.Editor mPrefsEditor = mSharedPrefs.edit(); //Düzenlemek için acilir
            mPrefsEditor.putString("token", ""); //keydeger adını vererek veri değişkenindeki değeri dosyaya kaydediyoruz.
            mPrefsEditor.putString("email", "");
            mPrefsEditor.commit();//Bu satır düzenlenilen dosyayı kapatmaya yarıyor

            //navigation itemleri goster


            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
