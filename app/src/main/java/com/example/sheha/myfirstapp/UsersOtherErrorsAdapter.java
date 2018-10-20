package com.example.sheha.myfirstapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sheha on 12/9/2017.
 */

public class UsersOtherErrorsAdapter extends ArrayAdapter<LogOtherError> {
    public UsersOtherErrorsAdapter(Context context, ArrayList<LogOtherError> error) {
        super(context, 0, error);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        LogOtherError error = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_log_othererrorlist, parent, false);
        }
        // Lookup view for data population
        TextView errorId = (TextView) convertView.findViewById(R.id.errorIdOther);
        TextView errorDesc = (TextView) convertView.findViewById(R.id.errorDescOther);
        TextView errorTime= (TextView)  convertView.findViewById(R.id.errorTimeOther);

        // Populate the data into the template view using the data object
        errorId.setText(error.errorId);
        errorDesc.setText(error.errorDesc);
        errorTime.setText(error.errorTime);

        // Return the completed view to render on screen
        return convertView;
    }
}
