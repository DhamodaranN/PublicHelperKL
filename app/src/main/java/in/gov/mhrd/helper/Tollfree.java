package in.gov.mhrd.helper;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import in.gov.mhrd.helper.Config;
import in.gov.mhrd.helper.R;

public class Tollfree extends AppCompatActivity implements Spinner.OnItemSelectedListener{
    private ListView listView;
    //Declaring an Spinner
    Spinner spinner;
    JSONObject j = null;
    JSONArray result;
    JSONObject json;
    JSONArray numbers;
    //An ArrayList for Spinner Items
    private ArrayList<String> district;



    //TextViews to display details

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tollfree);

        //Initializing the ArrayList
        district = new ArrayList<String>();

        //Initializing Spinner
        spinner = (Spinner) findViewById(R.id.spinner);

        //Adding an Item Selected Listener to our Spinner
        //As we have implemented the class Spinner.OnItemSelectedListener to this class iteself we are passing this to setOnItemSelectedListener

        spinner.setOnItemSelectedListener(this);
        //Initializing TextViews

        //This method will fetch the data from the URL
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
                district.add(json.getString("district"));


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //Setting adapter to show the items in the spinner
        spinner.setAdapter(new ArrayAdapter<String>(Tollfree.this, android.R.layout.simple_spinner_dropdown_item, district));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //Setting the values to textviews for a selected item

        final ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setClickable(true);

        List<String> items = new ArrayList<>();
        try {
            json = result.getJSONObject(position);
            numbers= json.getJSONArray("mobile");

            for(int li=0;li<=numbers.length()-1;li++){
                String  mob= numbers.getString(li);
                if(!mob.isEmpty()){
                    items.add(mob);
                }
            }


            final ArrayAdapter<String> adapter;
            adapter = new ArrayAdapter<String>(getApplicationContext(),
                    android.R.layout.simple_list_item_1, items);

            if (listView != null) {
                listView.setAdapter(adapter);
            }
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    String selectedFromList = (String) listView.getItemAtPosition(position);

                    if(Build.VERSION.SDK_INT > 23)
                    {
                        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(Tollfree.this, new String[]{Manifest.permission.CALL_PHONE}, 101);
                            return;
                        }

                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:" + selectedFromList));
                        startActivity(callIntent);

                    }
                    else {
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:" + selectedFromList));
                        startActivity(callIntent);
                    }
                }

            });

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    //When no item is selected this method would execute
    @Override
    public void onNothingSelected(AdapterView<?> parent) {


    }
}