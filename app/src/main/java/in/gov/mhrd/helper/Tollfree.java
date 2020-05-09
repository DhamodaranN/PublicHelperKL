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


        Spinner spinnerdist = (Spinner) findViewById(R.id.district);

        // Spinner Drop down elements
        List<String> dist = new ArrayList<String>();
        dist.add("Select(dist)");
        dist.add("kanya kumari");
        dist.add("madurai");
        dist.add("theni");



        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapterdist = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dist);

        // Drop down layout style - list view with radio button

        dataAdapterdist.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerdist.setAdapter(dataAdapterdist);
    }


}