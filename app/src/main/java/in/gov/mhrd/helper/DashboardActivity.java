package in.gov.mhrd.helper;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        getSupportActionBar().setTitle("Home Page");
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
}
