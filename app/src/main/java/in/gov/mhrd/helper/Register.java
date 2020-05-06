package in.gov.mhrd.helper;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hbb20.CountryCodePicker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Register extends AppCompatActivity {
    CountryCodePicker ccp;
    Spinner spinner;

    EditText username,phone,geotag,password,confirmpassword;
    String str_name,str_phone,str_password,str_geotag,str_confirmpass,str_status;
    String url = "https://myfirstprojectsamuel.000webhostapp.com/mysqlvolleylogin/register.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ccp = findViewById(R.id.ccp);

        phone = findViewById(R.id.phone);
        username = findViewById(R.id.username);
        password = findViewById(R.id.regpass);
        confirmpassword = findViewById(R.id.regconpass);
        geotag = findViewById(R.id.geotag);


         spinner = (Spinner) findViewById(R.id.status);

        // Spinner Drop down elements
        List<String> status = new ArrayList<String>();
        status.add("Select Any");
        status.add("Citizen");
        status.add("Volunteer");
        status.add("Doctor");
        status.add("Health Worker");
        status.add("Essential Worker");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, status);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }
    public void onCountryPickerClick(View view) {
        ccp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                //Alert.showMessage(RegistrationActivity.this, ccp.getSelectedCountryCodeWithPlus());
                ccp.getFullNumberWithPlus();
            }
        });
    }

    public void register(View view) {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait..");


        if (username.getText().toString().equals("")) {
            Toast.makeText(this, "Enter Username", Toast.LENGTH_SHORT).show();
        } else if (phone.getText().toString().equals("")) {
            Toast.makeText(this, "Enter Phone Number", Toast.LENGTH_SHORT).show();
        } else if (password.getText().toString().equals("")) {
            Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();
        } else if (confirmpassword.getText().toString().equals("")) {
            Toast.makeText(this, "Enter Confirm Password", Toast.LENGTH_SHORT).show();
        } else {

            progressDialog.show();
            str_name = username.getText().toString().trim();
            str_phone = phone.getText().toString().trim();
            str_password = password.getText().toString().trim();
            str_geotag = geotag.getText().toString().trim();
            str_confirmpass = confirmpassword.getText().toString().trim();
            str_status=spinner.getSelectedItem().toString().trim();


            if (str_password.equals(str_confirmpass)) {
                StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        username.setText("");
                        phone.setText("");
                        password.setText("");
                        geotag.setText("");




                        Toast.makeText(Register.this, response, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), Home.class));
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(Register.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                ) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();

                        params.put("name", str_name);
                        params.put("phone", str_phone);
                        params.put("password", str_password);
                        params.put("geotag", str_geotag);
                        params.put("status",str_status);
                        return params;

                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(Register.this);
                requestQueue.add(request);


            }
            else {

                Toast.makeText(Register.this,"Password Mismatched", Toast.LENGTH_SHORT).show();
            }
        }

    }
}

