package com.example.filereader;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonParser {

    JSONObject convertStringToJsonObject(String str){
        // JSONParser parser = new JSONParser();
        //JSONObject jsonObject = new JSONObject(str);
        try {
            return new JSONObject(str);
        }catch (JSONException err){
            //Log.d("Error", err.toString());
        }
        return null;
    }

    JSONArray convertStringToJsonArray(String str){
        try {
            JSONArray jsonArray = new JSONArray(str);
            return jsonArray;
        }catch (JSONException err){
            System.out.printf("Error"+ err.toString());
        }
        return null;
    }

    JSONObject convertJsonArrayToJsonObject(JSONArray jsonArray) throws JSONException {
        return jsonArray.getJSONObject(0);
    }
}
