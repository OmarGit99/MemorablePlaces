package com.example.memorableplaces;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<String> arrayList;
    ArrayAdapter<String> arrayAdapter;
    Intent intent;
    ArrayList<String> coordinateslat;
    ArrayList<String> coordinateslng;
    int firsttime;
    int j;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intent = new Intent(getApplicationContext(), MapsActivity.class);
        coordinateslat = new ArrayList<String>();
        coordinateslng = new ArrayList<String>();
        coordinateslng.add("fist");
        coordinateslat.add("wat");
        firsttime = 0;
        j = 0;
        listView = (ListView)findViewById(R.id.listView);
        arrayList = new ArrayList<String>();
        arrayList.add("Add a new place");
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){

                if(arrayList.get(position).compareTo("Add a new place") == 0){
                    intent.putExtra("MARKING", "marking");
                    firsttime = 1;
                    intent.putExtra("ARRAYOFSTUFF", arrayList);
                    intent.putExtra("ARRAYOFCOORDLAT", coordinateslat);
                    intent.putExtra("ARRAYOFCOORDLNG", coordinateslng);
                    startActivity(intent);



                }
                else{
                    firsttime = 1;

                    intent.putExtra("LAT", coordinateslat.get(position));
                    intent.putExtra("LNG", coordinateslng.get(position));
                    intent.putExtra("MARKING", "locating");
                    intent.putExtra("ARRAYOFSTUFF", arrayList);



                    startActivity(intent);
                }
            }
        });
        Intent intent = getIntent();

        if(intent.getStringExtra("FIRSTTIME") != null) {
            firsttime = Integer.parseInt(intent.getStringExtra("FIRSTTIME"));
        }
        Log.i("address", intent.getStringExtra("CITYPLUSCODE")+"");
        Log.i("long and lat", intent.getStringExtra("LAT")+" " + intent.getStringExtra("LNG"));
        Log.i("times", Integer.toString(firsttime));
        Log.i("No of times dis ran", Integer.toString(j));
        if(firsttime == 1 ){
            arrayList = intent.getStringArrayListExtra("ARRAYOFSTUFF");
            coordinateslat = intent.getStringArrayListExtra("ARRAYOFCOORDLAT");
            coordinateslng = intent.getStringArrayListExtra("ARRAYOFCOORDLNG");

            arrayList.add(intent.getStringExtra("CITYPLUSCODE"));
            coordinateslat.add(intent.getStringExtra("LAT"));
            coordinateslng.add(intent.getStringExtra("LNG"));
            for(int i = 0; i<arrayList.size();i++) {
                Log.i("hi", arrayList.get(i));
                Log.i("hi2", coordinateslat.get(i));
                Log.i("hi2", coordinateslng.get(i));
            }
            arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
            listView.setAdapter(arrayAdapter);
        }








    }
}
