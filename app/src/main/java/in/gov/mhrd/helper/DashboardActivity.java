package in.gov.mhrd.helper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class DashboardActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener{
    SharedPreferences mypreferences;
    private DrawerLayout drawer;
    TextView dpname,dpid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle bundle = getIntent().getExtras();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        mypreferences=getSharedPreferences("settings", Context.MODE_PRIVATE);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.userdashdrawer_layout);
        NavigationView navigationView = findViewById(R.id.userdashnav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header=navigationView.getHeaderView(0);
        /*View view=navigationView.inflateHeaderView(R.layout.nav_header_main);*/
        dpname = (TextView)header.findViewById(R.id.displayname);
        dpid = (TextView)header.findViewById(R.id.displayid);

        navigationView.setNavigationItemSelectedListener(this);
        dpname.setText(bundle.getString("name"));
        dpid.setText(bundle.getString("id"));
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.editprofile:

                break;
            case R.id.language:
                break;
            case R.id.share:

                break;
            case R.id.settings:
                break;
            case R.id.logout:
                Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                SharedPreferences.Editor edit=mypreferences.edit();
                edit.putBoolean("login",false);
                edit.apply();
                startActivity(intent);
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void toolfree(View view) {
        Intent intent = new Intent(DashboardActivity.this,Tollfree.class);
        startActivity(intent);
    }

    public void courses(View view) {
        Intent intent = new Intent(DashboardActivity.this,Courses.class);
        startActivity(intent);
    }

    public void appointment(View view) {
        Intent intent = new Intent(DashboardActivity.this,DoctorAppointment.class);
        startActivity(intent);
    }

    public void medreq(View view) {
        Intent intent = new Intent(DashboardActivity.this,MedicineRequest.class);
        startActivity(intent);
    }


    public void virus(View view) {
        Intent intent = new Intent(DashboardActivity.this,CoronaActivity.class);
        startActivity(intent);
    }

    public void hospitals(View view) {
        Intent intent = new Intent(DashboardActivity.this,MapsActivity.class);
        startActivity(intent);
    }

    public void medicalshops(View view) {
        Intent intent = new Intent(DashboardActivity.this,MedicalStore.class);
        startActivity(intent);
    }

    public void addfund(View view) {
        Intent intent = new Intent(DashboardActivity.this,FundActivity.class);
        startActivity(intent);
    }

    public void shop(View view) {
        Intent intent = new Intent(DashboardActivity.this,ShopLogin.class);
        startActivity(intent);

    }

    public void migrant(View view) {
        Intent intent = new Intent(DashboardActivity.this, MapsActivity.class);
        startActivity(intent);

    }

    public void shelters(View view) {
        Intent intent = new Intent(DashboardActivity.this,Shelter_maps.class);
        startActivity(intent);

    }
}
