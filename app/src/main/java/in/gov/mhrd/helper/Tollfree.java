package in.gov.mhrd.helper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Tollfree extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tollfree);


        Spinner spinnerstates = (Spinner) findViewById(R.id.states);
        Spinner spinnerdist = (Spinner) findViewById(R.id.district);
        Spinner spinnerthaluk = (Spinner) findViewById(R.id.thaluk);

        // Spinner Drop down elements
        List<String> states = new ArrayList<String>();
        states.add("Select(State)");
        states.add("tamil nadu");
        states.add("punjab");
        states.add("maharastra");

        List<String> dist = new ArrayList<String>();
        dist.add("Select(dist)");
        dist.add("kanya kumari");
        dist.add("madurai");
        dist.add("theni");

        List<String> thaluk = new ArrayList<String>();
        thaluk.add("Select(thaluk)");
        thaluk.add("kalkulam");


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, states);
        ArrayAdapter<String> dataAdapterdist = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dist);
        ArrayAdapter<String> dataAdapterthaluk = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, thaluk);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapterdist.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapterthaluk.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerstates.setAdapter(dataAdapter);
        spinnerthaluk.setAdapter(dataAdapterthaluk);
        spinnerdist.setAdapter(dataAdapterdist);
    }


}