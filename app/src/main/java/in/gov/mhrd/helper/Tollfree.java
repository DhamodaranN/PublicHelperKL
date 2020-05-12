package in.gov.mhrd.helper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Tollfree extends AppCompatActivity {
    private RequestQueue mQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tollfree);
        mQueue = Volley.newRequestQueue(this);

        String url = "https://myfirstprojectsamuel.000webhostapp.com/helpline.json";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Toast.makeText(getApplicationContext(),"please wait....",Toast.LENGTH_LONG).show();
                            final ListView listView = (ListView) findViewById(R.id.list_view);
                            listView.setClickable(true);

                            List<String> items = new ArrayList<>();
                            JSONArray jsonArray = response.getJSONArray("array");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject employee = jsonArray.getJSONObject(i);
                                items.add(employee.getString("company"));

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
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mQueue.add(request);
    }
}