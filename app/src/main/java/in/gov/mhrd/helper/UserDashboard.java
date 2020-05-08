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
        System.out.println("hello");
    }

    public void HealthWorkerreg(View view) {
        Intent i=new Intent(this, Register.class);
      i.putExtra("type","health");
      startActivity(i);

    }
}
