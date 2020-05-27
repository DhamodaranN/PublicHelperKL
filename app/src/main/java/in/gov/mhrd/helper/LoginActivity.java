package in.gov.mhrd.helper;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText etUname, etPass;
     SharedPreferences mypreferences;
     ProgressDialog progressDialog;
    JSONObject dataobj;

     JSONArray dataArray;
    private String URLline = "https://pico.games/publichelper/scripts/login.php";
    private RequestQueue rQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mypreferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor =mypreferences.edit();

        etUname = (EditText) findViewById(R.id.logphone);
        etPass = (EditText) findViewById(R.id.logpassword);

    }

    private void loginUser(){

        final String username = etUname.getText().toString().trim();
        final String password = etPass.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLline+"?username="+username+"&password="+password,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        rQueue.getCache().clear();


                        parseData(response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this,error.toString(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put("username",username);
                params.put("password",password);

                return params;
            }

        };

        rQueue = Volley.newRequestQueue(LoginActivity.this);
        rQueue.add(stringRequest);
    }

    private void parseData(String response){
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getString("status").equals("true")) {

                saveInfo(response);
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this, "Login Successfully!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this,DashboardActivity.class);

                intent.putExtra("id",dataobj.getString("id"));
                intent.putExtra("name",dataobj.getString("name"));
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                this.finish();
            }else{

                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void saveInfo(String response){



        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getString("status").equals("true")) {
                 dataArray = jsonObject.getJSONArray("data");
                for (int i = 0; i < dataArray.length(); i++) {

                     dataobj = dataArray.getJSONObject(i);
                    SharedPreferences.Editor editor = mypreferences.edit();


                    editor.putString("name",dataobj.getString("name"));
                    editor.putString("mobile",dataobj.getString("mobile"));
                    editor.putString("id",dataobj.getString("id"));
                    editor.putBoolean("login",true);
                    editor.putBoolean("usage",true);
                    editor.apply();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void login(View view) {
        progressDialog = ProgressDialog.show(LoginActivity.this,"Connect with server","Please Wait",false,false);
        loginUser();
    }

    public void signuptxt(View view) {
        Intent intent = new Intent(LoginActivity.this, Register.class);
        startActivity(intent);
        LoginActivity.this.finish();
    }
}
