package in.gov.mhrd.helper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class UserDashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);
    }

    public void HealthWorkerreg(View view) {
        Intent i=new Intent(this, HealthWorker.class);
      startActivity(i);

    }

    public void volunter(View view) {
        Intent i=new Intent(this, VolunteerReg.class);
        startActivity(i);

    }

    public void patient(View view) {
        Intent i=new Intent(this, Patient.class);
        startActivity(i);

    }

    public void people(View view) {
        Intent i=new Intent(this, People.class);
        startActivity(i);

    }

    public void migrantworker(View view) {
        Intent i=new Intent(this, MigrantWorker.class);
        startActivity(i);

    }

    public void returnkerala(View view) {
        Intent i=new Intent(this, ReturnKerala.class);
        startActivity(i);

    }

    public void fundraisers(View view) {
        Intent i=new Intent(this, Fundraisers.class);
        startActivity(i);

    }

    public void admin(View view) {
        Intent i=new Intent(this, Admin.class);
        startActivity(i);

    }
}
