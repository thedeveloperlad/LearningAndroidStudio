package com.example.filereader;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class DetailListIPTVItemAdapter extends ArrayAdapter<MediaIPTVItemModel> {
    private int resourceLayout;
    private Context context;

    public DetailListIPTVItemAdapter(Context context, int resource, List<MediaIPTVItemModel> items){
        super(context, resource, items);
        this.context = context;
        this.resourceLayout = resource;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resourceLayout, parent, false);
        }

        convertView.setBackgroundColor(Color.parseColor("#45009d"));
        convertView.setPadding(32, 32, 32, 32);
        MediaIPTVItemModel item = getItem(position);

        if(item != null)
        {
            //     findViewById
            TextView title = convertView.findViewById(R.id.tvItemTitle);
            TextView group = convertView.findViewById(R.id.tv_item_group);
            TextView link = convertView.findViewById(R.id.tv_item_link);
            /*TextView category = convertView.findViewById(R.id.itemCategory);
            TextView status = convertView.findViewById(R.id.itemStatus);
            TextView link = convertView.findViewById(R.id.itemLink);
            TextView language = convertView.findViewById(R.id.itemLanguaje);*/

            /*
            public MediaIPTVItemModel(String group, String logo, String name, String number, String CUID, String country, String language, String radio) {
        this.group = group;
        this.logo = logo;
        this.name = name;
        this.number = number;
        this.CUID = CUID;
        this.country = country;
        this.language = language;
        this.radio = radio;
    }
            * */

            //     Populate the detailed UI elements with the object's data
            Log.d("getView().item.getName()= ", item.getName());
           // Log.d("getView().item.getGroup()= ", item.getGroup());
            //Log.d("getView().item.getUrl()= ", item.getUrl());
            title.setText(item.getName());
            group.setText(item.getGroup());
            link.setText(item.getUrl());
            /*category.setText(item.getCategory());
            status.setText(item.getStatus());
            link.setText(item.getLink());
            language.setText(item.getLanguage());*/
        }

        // Handle the click here!
        /*convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("AdapterClick", "Item clicked at position: " + position);
            }
        });*/

        return convertView;
    }
}
