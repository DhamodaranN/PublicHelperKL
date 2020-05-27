package in.gov.mhrd.helper;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
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

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MigrantWorkerRegistration extends AppCompatActivity implements LocationListener {



    private Location currentBestLocation = null;
    ProgressDialog progressDialog;
    JSONObject j;
    JSONObject json;
    JSONArray districtslist;
    JSONObject j1;
    JSONObject json1;
    RadioGroup r1;
    private EditText uname, uage, uusername, ucity, uaddress, uoccuption, ucompany, upincode, umobile, Occuption, company, ncity, npincode, naddress;
    private Button btnregister;

    private Spinner udistrict, ugender, ndistrict, nstate;
    SharedPreferences mypreferences;
    static final int TWO_MINUTES = 1000 * 60 * 2;

    private String registerURL = "https://pico.games/publichelper/scripts/mwregister.php";
    private RequestQueue rQueue;

    //An ArrayList for Spinner Items
    private ArrayList<String> districts = new ArrayList<>();
    private ArrayList<String> states = new ArrayList<>();
    private ArrayList<String> ndistricts = new ArrayList<>();
    private ArrayList<String> genderlist = new ArrayList<>();
    //JSON Array
    private JSONArray result,result1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_migrant_worker_registration);
        uname = (EditText) findViewById(R.id.name);
        uage = (EditText) findViewById(R.id.age);
        uusername = (EditText) findViewById(R.id.username);
        ncity = (EditText) findViewById(R.id.ncity);
        naddress = (EditText) findViewById(R.id.naddress);
        uoccuption = (EditText) findViewById(R.id.occupation);
        ucompany = (EditText) findViewById(R.id.workplace);
        ugender = (Spinner) findViewById(R.id.gender);
        umobile = (EditText) findViewById(R.id.phone);
        udistrict = (Spinner) findViewById(R.id.district);
        nstate = (Spinner) findViewById(R.id.nstate);
        ndistrict = (Spinner) findViewById(R.id.ndistrict);
        ucity = (EditText) findViewById(R.id.city);
        uaddress = (EditText) findViewById(R.id.address);
        upincode = (EditText) findViewById(R.id.pincode);
        npincode = (EditText) findViewById(R.id.npincode);
        mypreferences = getSharedPreferences("settings", MODE_PRIVATE);
        btnregister = (Button) findViewById(R.id.btn_migrantreg);
        r1 = (RadioGroup) findViewById(R.id.migrantregtype);
        genderlist.add("Male");
        genderlist.add("Female");
        genderlist.add("Others");
        ugender.setAdapter(new ArrayAdapter<String>(MigrantWorkerRegistration.this, android.R.layout.simple_spinner_dropdown_item, genderlist));
        r1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                RadioButton rb = findViewById(checkedId);
                if (rb.getText().equals("myself")) {

                    loaddata();
                } else if (rb.getText().equals("Others")) {
                    cleardata();
                }

            }
        });

        nstate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Setting the values to textviews for a selected item

                List<String> items = new ArrayList<>();
                try {
                   if(position==0){
                       ndistrict.setAdapter(new ArrayAdapter<String>(MigrantWorkerRegistration.this, android.R.layout.simple_spinner_dropdown_item, items));

                    }else{

                    json = result.getJSONObject(position);
                    districtslist = json.getJSONArray("districts");

                    for (int li = 0; li <= districtslist.length() - 1; li++) {
                        String mob = districtslist.getString(li);
                        if (!mob.isEmpty()) {
                            items.add(mob);
                        }
                    }

                    if (ndistrict != null) {
                        ndistrict.setAdapter(new ArrayAdapter<String>(MigrantWorkerRegistration.this, android.R.layout.simple_spinner_dropdown_item, items));
                    }}


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            //When no item is selected this method would execute
            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });
        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = ProgressDialog.show(MigrantWorkerRegistration.this, "Connect with server", "Please Wait", false, false);
                final String name = uname.getText().toString();
                final String username = uusername.getText().toString();
                final String age = uage.getText().toString();
                final String gender = ugender.getSelectedItem().toString();
                final String district = udistrict.getSelectedItem().toString();
                final String mobile = umobile.getText().toString();
                final String city = ucity.getText().toString();
                final String address = uaddress.getText().toString();
                final String pincode = upincode.getText().toString();

                StringRequest stringRequest = new StringRequest(Request.Method.GET, registerURL + "?name=" + name + "&username=" + username + "&age=" + age + "&gender=" + gender + "&mobile=" + mobile + "&district=" + district + "&city=" + city + "&address=" + address + "&pincode=" + pincode+ "&nstate=" + nstate+ "&ndistrict=" + ndistrict + "&ncity=" + ncity + "&naddress=" + naddress + "&npincode=" + npincode+ "&district=" + district+ "&district=" + district,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                rQueue.getCache().clear();
                                try {
                                    JSONObject json = new JSONObject(response);
                                    Boolean status = json.optBoolean("status");
                                    String message = json.getString("message");
                                    JSONArray data = json.getJSONArray("data");
                                    if (status) {
                                        parseData(response);
                                        Toast.makeText(MigrantWorkerRegistration.this, message, Toast.LENGTH_LONG).show();
                                    }


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        },
                        new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(MigrantWorkerRegistration.this, error.toString(), Toast.LENGTH_LONG).show();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("name", name);
                        params.put("usename", username);
                        params.put("age", age);
                        params.put("gender", gender);
                        params.put("mobile", mobile);
                        params.put("district", district);
                        params.put("address", address);
                        params.put("city", city);
                        params.put("pincode", pincode);

                        return params;
                    }

                };

                rQueue = Volley.newRequestQueue(MigrantWorkerRegistration.this);
                rQueue.add(stringRequest);
            }
        });

        getData();
        getData1();
        loaddata();
    }

    void loaddata() {
        String uname = mypreferences.getString("id", "");
        getData3(uname);
    }

    void cleardata() {
        uname.setText("");
        uage.setText("");
        umobile.setText("");
        ucity.setText("");
        upincode.setText("");
        uaddress.setText("");
        uoccuption.setText("");
        ucompany.setText("");
        ncity.setText("");
        uaddress.setText("");
        upincode.setText("");
        ugender.setSelection(0);
        udistrict.setSelection(0);


    }
    private int getIndex(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }

        return 0;
    }
    public static String getMd5(String input) {
        try {

            // Static getInstance method is called with hashing MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // digest() method is called to calculate message digest
            //  of an input digest() return array of byte
            byte[] messageDigest = md.digest(input.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }

        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private void getData() {
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
                            int length = j.length();

                            //Calling method getdistrict to get the district from the JSON Array
                            getdistrict(result, length);
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

    private void getData1() {
        //Creating a string request
        StringRequest stringRequest = new StringRequest("https://www.pico.games/publichelper/json/states-and-districts.json",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            //Parsing the fetched Json String to JSON Object
                            j1 = new JSONObject(response);

                            //Storing the Array of JSON String to our JSON Array
                            result = j1.getJSONArray("states");
                            int length = j1.length();

                            //Calling method getdistrict to get the district from the JSON Array
                            getstates(result, length);
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

    private void getData3(String s) {
        //Creating a string request
        StringRequest stringRequest = new StringRequest("https://www.pico.games/publichelper/scripts/getprofile.php?userid=" + s + "&token=" + getMd5("1"),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            //Parsing the fetched Json String to JSON Object
                            j = new JSONObject(response);

                            //Storing the Array of JSON String to our JSON Array
                            result1 = j.getJSONArray("data");
                            int length = j.length();
                            JSONObject j = result1.getJSONObject(0);
                            uname.setText(j.getString("name"));
                            uage.setText(j.getString("age"));
                            umobile.setText(j.getString("mobile"));
                            uaddress.setText(j.getString("address"));
                            upincode.setText(j.getString("pincode"));
                            ucity.setText(j.getString("city_village"));
                            ugender.setSelection(Integer.parseInt(j.getString("gender")));
                            udistrict.setSelection(getIndex(udistrict,j.getString("district")));

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

    private void getdistrict(JSONArray j, int l) {
        for (int i = 0; i < j.length(); i++) {
            try {
                //Getting json object
                json = j.getJSONObject(i);
                districts.add(json.getString("district"));


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //Setting adapter to show the items in the spinner
        udistrict.setAdapter(new ArrayAdapter<String>(MigrantWorkerRegistration.this, android.R.layout.simple_spinner_dropdown_item, districts));
    }

    private void getstates(JSONArray j, int l) {
        for (int i = 0; i < j.length(); i++) {
            try {
                //Getting json object
                json1 = j.getJSONObject(i);
                states.add(json1.getString("state"));


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //Setting adapter to show the items in the spinner
        nstate.setAdapter(new ArrayAdapter<String>(MigrantWorkerRegistration.this, android.R.layout.simple_spinner_dropdown_item, states));
    }


    private void parseData(String response) throws JSONException {


        JSONObject jsonObject = new JSONObject(response);
        if (jsonObject.optString("status").equals("true")) {
            progressDialog.dismiss();
            Toast.makeText(MigrantWorkerRegistration.this, "Registered Successfully!", Toast.LENGTH_SHORT).show();
        } else {

            progressDialog.dismiss();
            Toast.makeText(MigrantWorkerRegistration.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        makeUseOfNewLocation(location);

        if (currentBestLocation == null) {
            currentBestLocation = location;
        }


    }


    /**
     * This method modify the last know good location according to the arguments.
     *
     * @param location The possible new location.
     */
    void makeUseOfNewLocation(Location location) {
        if (isBetterLocation(location, currentBestLocation)) {
            currentBestLocation = location;
        }
    }


    /** Determines whether one location reading is better than the current location fix
     * @param location  The new location that you want to evaluate
     * @param currentBestLocation  The current location fix, to which you want to compare the new one.
     */
    protected boolean isBetterLocation(Location location, Location currentBestLocation) {
        if (currentBestLocation == null) {
            // A new location is always better than no location
            return true;
        }

        // Check whether the new location fix is newer or older
        long timeDelta = location.getTime() - currentBestLocation.getTime();
        boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
        boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
        boolean isNewer = timeDelta > 0;

        // If it's been more than two minutes since the current location, use the new location,
        // because the user has likely moved.
        if (isSignificantlyNewer) {
            return true;
            // If the new location is more than two minutes older, it must be worse.
        } else if (isSignificantlyOlder) {
            return false;
        }

        // Check whether the new location fix is more or less accurate
        int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
        boolean isLessAccurate = accuracyDelta > 0;
        boolean isMoreAccurate = accuracyDelta < 0;
        boolean isSignificantlyLessAccurate = accuracyDelta > 200;

        // Check if the old and new location are from the same provider
        boolean isFromSameProvider = isSameProvider(location.getProvider(),
                currentBestLocation.getProvider());

        // Determine location quality using a combination of timeliness and accuracy
        if (isMoreAccurate) {
            return true;
        } else if (isNewer && !isLessAccurate) {
            return true;
        } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
            return true;
        }
        return false;
    }

    // Checks whether two providers are the same
    private boolean isSameProvider(String provider1, String provider2) {
        if (provider1 == null) {
            return provider2 == null;
        }
        return provider1.equals(provider2);
    }
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
