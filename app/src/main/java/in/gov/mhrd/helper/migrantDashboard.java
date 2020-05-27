package in.gov.mhrd.helper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class migrantDashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_migrant_dashboard);
    }

    public void add(View view) {
        Intent intent = new Intent(migrantDashboard.this, MigrantWorkerRegistration.class);
        startActivity(intent);
    }
    public void displaylist(View view) {
        Intent intent = new Intent(migrantDashboard.this, MigrantWorkerRegistration.class);
        startActivity(intent);
    }
}
