package in.gov.mhrd.helper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;

public class Login extends AppCompatActivity {
private  Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }

    public void signuptxt(View view) {
        Intent intent = new Intent(Login.this,Register.class);
        startActivity(intent);
    }

    public void login(View view) {
        Intent intent = new Intent(Login.this,DashboardActivity.class);
        startActivity(intent);
    }


}
