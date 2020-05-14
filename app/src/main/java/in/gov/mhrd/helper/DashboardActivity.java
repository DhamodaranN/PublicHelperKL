package in.gov.mhrd.helper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class DashboardActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.userdashdrawer_layout);
        NavigationView navigationView = findViewById(R.id.userdashnav_view);
        navigationView.setNavigationItemSelectedListener(this);

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
                Intent in = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(in);
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
