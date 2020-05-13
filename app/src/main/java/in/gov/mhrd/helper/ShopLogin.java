package in.gov.mhrd.helper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class ShopLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_login);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.vendor,menu);
        return true;
    }

    public void sign(View view) {
        Intent i = new Intent(ShopLogin.this, ShopRegister.class);
        startActivity(i);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.vendorLogin:
                Intent i = new Intent(ShopLogin.this,VendorLogin.class);
                startActivity(i);


        }
        return super.onOptionsItemSelected(item);

    }

    public void login(View view) {
        Intent i = new Intent(ShopLogin.this, Shop_User_Dashboard.class);
        startActivity(i);
    }
}

