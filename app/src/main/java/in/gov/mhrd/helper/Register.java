package in.gov.mhrd.helper;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;


import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    ProgressDialog progressDialog;
    private EditText uname, uage, uusername, upassword,uconpassword,ucity,uaddress,upincode,umobile;
    private Button btnregister;
    private TextView tvlogin;
    private Spinner udistrict,ugender;
  SharedPreferences mypreferences;

    private String registerURL = "https://pico.games/publichelper/scripts/register.php";
    private RequestQueue rQueue;
    JSONObject j;
    JSONObject json;
    //An ArrayList for Spinner Items
    private ArrayList<String> districts=new ArrayList<>();
    private ArrayList<String> genderlist=new ArrayList<>();
    //JSON Array
    private JSONArray result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        uname = (EditText) findViewById(R.id.name);
        uage = (EditText) findViewById(R.id.age);
        uusername = (EditText) findViewById(R.id.username);
        upassword = (EditText) findViewById(R.id.regpass);
        uconpassword = (EditText) findViewById(R.id.regconpass);
        ugender = (Spinner) findViewById(R.id.gender);
        umobile = (EditText) findViewById(R.id.phone);
        udistrict = (Spinner) findViewById(R.id.district);
        ucity = (EditText) findViewById(R.id.city);
        uaddress = (EditText) findViewById(R.id.address);
        upincode = (EditText) findViewById(R.id.pincode);
        mypreferences=getSharedPreferences("settings", Context.MODE_PRIVATE);
        btnregister = (Button) findViewById(R.id.btn_reg);

        genderlist.add("Male");
        genderlist.add("Female");
        genderlist.add("Others");
        ugender.setAdapter(new ArrayAdapter<String>(Register.this, android.R.layout.simple_spinner_dropdown_item, genderlist));

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 progressDialog= ProgressDialog.show(Register.this, "Connect with server", "Please Wait", false, false);
                final String name = uname.getText().toString();
                final String username = uusername.getText().toString();
                final String age = uage.getText().toString();
                final String gender = ugender.getSelectedItem().toString();
                final String district = udistrict.getSelectedItem().toString();
                final String mobile = umobile.getText().toString();
                final String city = ucity.getText().toString();
                final String address = uaddress.getText().toString();
                final String pincode = upincode.getText().toString();
                final String password = upassword.getText().toString();
                final String confirmpassword = uconpassword.getText().toString();

                StringRequest stringRequest = new StringRequest(Request.Method.GET, registerURL+"?name="+name+"&username="+username+"&age="+age+"&gender="+gender+"&mobile="+mobile+"&district="+district+"&city="+city+"&address="+address+"&password="+password+"&pincode="+pincode,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                rQueue.getCache().clear();
                                try {
                                    JSONObject json=new JSONObject(response);
                                    Boolean status =  json.optBoolean("status");
                                    String message =  json.getString("message");
                                    JSONArray data =  json.getJSONArray("data");
                                    if(status){
                                        parseData(response);
                                        Toast.makeText(Register.this,message,Toast.LENGTH_LONG).show();
                                    }


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        },
                        new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(Register.this,error.toString(),Toast.LENGTH_LONG).show();
                            }
                        }){
                    @Override
                    protected Map<String,String> getParams(){
                        Map<String,String> params = new HashMap<String, String>();
                        params.put("name",name);
                        params.put("usename",username);
                        params.put("age",age);
                        params.put("gender",gender);
                        params.put("mobile",mobile);
                        params.put("district",district);
                        params.put("address",address);
                        params.put("city",city);
                        params.put("pincode",pincode);
                        params.put("password",password);

                        return params;
                    }

                };

                rQueue = Volley.newRequestQueue(Register.this);
                rQueue.add(stringRequest);
            }
        });

        getData();
    }

    private void getData(){
        //Creating a string request
        StringRequest stringRequest = new StringRequest("https://www.pico.games/publichelper/json/tollfree.json",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            //Parsing the fetched Json String to JSON Object
                            j = new JSONObject(response);

                            //Storing the Array of JSON String to our JSON Array
                            result = j.getJSONArray("tollfree");
                            int length =j.length();

                            //Calling method getdistrict to get the district from the JSON Array
                            getdistrict(result,length);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        //Creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }

    private void getdistrict(JSONArray j,int l){
        for(int i=0;i<j.length();i++){
            try {
                //Getting json object
                json = j.getJSONObject(i);

                //Adding the name of the student to array list
                districts.add(json.getString("district"));


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //Setting adapter to show the items in the spinner
        udistrict.setAdapter(new ArrayAdapter<String>(Register.this, android.R.layout.simple_spinner_dropdown_item, districts));
    }


    private void parseData(String response) throws JSONException {


        JSONObject jsonObject = new JSONObject(response);
        if (jsonObject.optString("status").equals("true")){

            saveInfo(response);
            progressDialog.dismiss();
            Toast.makeText(Register.this, "Registered Successfully!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Register.this,DashboardActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            this.finish();
        }else {

            Toast.makeText(Register.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
        }
    }

    private void saveInfo(String response){
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getString("status").equals("true")) {
                JSONArray dataArray = jsonObject.getJSONArray("data");
                for (int i = 0; i < dataArray.length(); i++) {

                    JSONObject dataobj = dataArray.getJSONObject(i);
                    SharedPreferences.Editor edit =mypreferences.edit();
                    edit.putString("login",dataobj.getString("name"));
                    edit.putString("login",dataobj.getString("id"));
                    edit.putString("login",dataobj.getString("mobile"));
                    edit.putBoolean("login",true);
                    edit.putBoolean("usage",true);
                    edit.commit();

                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void loginview(View view) {
        Intent intent = new Intent(Register.this,LoginActivity.class);
        startActivity(intent);
        Register.this.finish();
    }
}
