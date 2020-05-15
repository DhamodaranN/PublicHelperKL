package in.gov.mhrd.helper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;


public class MainActivity extends AppCompatActivity {
    private PreferenceHelper preferenceHelper=new PreferenceHelper(getApplicationContext());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int SPLASH_TIME_OUT = 1500;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(preferenceHelper.getIsUsage()){
                    if(preferenceHelper.getIsLogin()){
                        Intent intent = new Intent(MainActivity.this,DashboardActivity.class);
                        startActivity(intent);
                        finish();

                    }else{
                        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }else {
                    Intent intent = new Intent(MainActivity.this,Register.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, SPLASH_TIME_OUT);
    }
}
