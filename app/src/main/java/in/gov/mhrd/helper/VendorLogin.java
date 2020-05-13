package in.gov.mhrd.helper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class VendorLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_login);
    }

    public void Login(View view) {
        Intent i = new Intent(VendorLogin.this, Vendor_DashBoard.class);
        startActivity(i);
    }
}
