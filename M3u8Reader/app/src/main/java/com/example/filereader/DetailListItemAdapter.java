package com.example.filereader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class DetailListItemAdapter extends ArrayAdapter<MediaItemModel>{
    private int resourceLayout;
    private Context context;

    public DetailListItemAdapter(Context context, int resource, List<MediaItemModel> items){
        super(context, resource, items);
        this.context = context;
        this.resourceLayout = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resourceLayout, parent, false);
        }

        MediaItemModel item = getItem(position);

        if(item != null)
        {
            //     findViewById
            TextView title = convertView.findViewById(R.id.itemTitle);
            TextView time = convertView.findViewById(R.id.itemTime);
            TextView category = convertView.findViewById(R.id.itemCategory);
            TextView status = convertView.findViewById(R.id.itemStatus);
            TextView link = convertView.findViewById(R.id.itemLink);
            TextView language = convertView.findViewById(R.id.itemLanguaje);

            //     Populate the detailed UI elements with the object's data
            title.setText(item.getTitle());
            time.setText(item.getTime());
            category.setText(item.getCategory());
            status.setText(item.getStatus());
            link.setText(item.getLink());
            language.setText(item.getLanguage());
        }

        return convertView;
    }
}
