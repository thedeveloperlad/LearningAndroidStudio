package com.example.filereader;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FileListActivity extends AppCompatActivity {

    ArrayList<String> dataList = new ArrayList<>();
    // Create and set the adapter


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.file_list_activity);

        printResultonScreen();
        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });*/
    }

    void printResultonScreen() {
        ListView listView = findViewById(R.id.simpleListView);
        String jsonString = getIntent().getStringExtra("JSON_DATA");

        /*ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                R.layout.item_view, R.id.itemTextView, dataList);
        listView.setAdapter(arrayAdapter);*/
        ArrayList<MediaItemModel> itemsList = new ArrayList<>();

        if (jsonString != null) {
            try {
                // Reconstruct the JSONArray from the String
                JSONArray jsonArray = new JSONArray(jsonString);

                for(int index = 0; index<jsonArray.length(); index++){
                    JSONObject innerObj = jsonArray.getJSONObject(index);
                    // System.out.println("innerObj= " + innerObj);
                    Log.d("jsonObject= ", innerObj.toString());
                    itemsList.add(new MediaItemModel(innerObj.get("title").toString(), innerObj.get("time").toString(), innerObj.get("category").toString(), innerObj.get("status").toString(), innerObj.get("link").toString(), innerObj.get("language").toString()));
                    //dataList.add(innerObj.toString());
                }

                DetailListItemAdapter adapter = new DetailListItemAdapter(this, R.layout.item_view, itemsList);
                listView.setAdapter(adapter);
                // Example: access the first object
                // JSONObject firstObject = jsonArray.getJSONObject(0);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            //Open video player for example
            //openVideo(link);
        }
    }
}
