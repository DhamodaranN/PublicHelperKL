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
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                Login.this,R.style.BottomSheetDialogTheme
        );

        View bottomSheetView = LayoutInflater.from(getApplicationContext()).inflate(
                R.layout.bottom_sheet_layout,
                (LinearLayout)findViewById(R.id.linear)
        );

        bottomSheetView.findViewById(R.id.go).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                        Login.this,R.style.BottomSheetStyle
                );

                View bottomSheetView = LayoutInflater.from(getApplicationContext()).inflate(
                        R.layout.otp_sheet,
                        (LinearLayout)findViewById(R.id.otplayout)
                );

                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            }
        });

        bottomSheetView.findViewById(R.id.why).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                        Login.this,R.style.BottomSheetStyle
                );

                View bottomSheetView = LayoutInflater.from(getApplicationContext()).inflate(
                        R.layout.why_isitneeded,
                        (LinearLayout)findViewById(R.id.whyisit)
                );

                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();

            }
        });
        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();

    }

    public void login(View view) {
        Intent intent = new Intent(Login.this,DashboardActivity.class);
        startActivity(intent);
    }


}
