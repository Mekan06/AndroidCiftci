package eticaret.androidciftci;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.internal.NavigationMenuItemView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    public void onStart(){
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
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
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
