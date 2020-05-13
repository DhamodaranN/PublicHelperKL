package in.gov.mhrd.helper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;


import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

    private EditText uname, uage, uusername, upassword,uconpassword,ucity,uaddress,upincode,umobile;
    private Button btnregister;
    private TextView tvlogin;
private Spinner udistrict,ugender;
    private PreferenceHelper preferenceHelper;
    private String registerURL = "https://pico.games/publichelper/scripts/register.php";
    private RequestQueue rQueue;

    //An ArrayList for Spinner Items
    private ArrayList<String> students;

    //JSON Array
    private JSONArray result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        preferenceHelper = new PreferenceHelper(this);

        if(preferenceHelper.getIsLogin()){
            Intent intent = new Intent(Register.this,DashboardActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            this.finish();
        }


        uname = (EditText) findViewById(R.id.name);
        uage = (EditText) findViewById(R.id.age);
        uusername = (EditText) findViewById(R.id.username);
        upassword = (EditText) findViewById(R.id.regpass);
        upassword = (EditText) findViewById(R.id.regconpass);
        ugender = (Spinner) findViewById(R.id.gender);
        umobile = (EditText) findViewById(R.id.phone);
        udistrict = (Spinner) findViewById(R.id.district);
        ucity = (EditText) findViewById(R.id.city);
        uaddress = (EditText) findViewById(R.id.address);
        upincode = (EditText) findViewById(R.id.pincode);

        btnregister = (Button) findViewById(R.id.btn_register);


        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerMe();
            }
        });

        getData();
    }

    private void getData(){
        //Creating a string request
        StringRequest stringRequest = new StringRequest(Config.DIST_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j = null;
                        try {
                            //Parsing the fetched Json String to JSON Object
                            j = new JSONObject(response);

                            //Storing the Array of JSON String to our JSON Array
                            result = j.getJSONArray(Config.dist_ARRAY);

                            //Calling method getStudents to get the students from the JSON Array
                            getStudents(result);
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

    private void getStudents(JSONArray j){
        //Traversing through all the items in the json array
        for(int i=0;i<j.length();i++){
            try {
                //Getting json object
                JSONObject json = j.getJSONObject(i);

                //Adding the name of the student to array list
                students.add(json.getString(Config.TAG_dist));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //Setting adapter to show the items in the spinner
        udistrict.setAdapter(new ArrayAdapter<String>(Register.this, android.R.layout.simple_spinner_dropdown_item, students));
    }
    private void registerMe(){

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

        StringRequest stringRequest = new StringRequest(Request.Method.POST, registerURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        rQueue.getCache().clear();
                        Toast.makeText(Register.this,response,Toast.LENGTH_LONG).show();

                        try {

                            parseData(response);

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

    private void parseData(String response) throws JSONException {

        JSONObject jsonObject = new JSONObject(response);
        if (jsonObject.optString("status").equals("true")){

            saveInfo(response);

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

        preferenceHelper.putIsLogin(true);
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getString("status").equals("true")) {
                JSONArray dataArray = jsonObject.getJSONArray("data");
                for (int i = 0; i < dataArray.length(); i++) {

                    JSONObject dataobj = dataArray.getJSONObject(i);
                    preferenceHelper.putName(dataobj.getString("name"));
                    preferenceHelper.putHobby(dataobj.getString("hobby"));
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
