package com.example.sheha.myfirstapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sheha on 12/4/2017.
 */

public class UsersAdapter extends ArrayAdapter<LogTransactionError> {
    public UsersAdapter(Context context, ArrayList<LogTransactionError> error) {
        super(context, 0, error);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        LogTransactionError error = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_logtransactionerrorlist, parent, false);
        }
        // Lookup view for data population
        TextView errorId = (TextView) convertView.findViewById(R.id.errorId);
        TextView errorDesc = (TextView) convertView.findViewById(R.id.errorDesc);
        TextView errorTime= (TextView)  convertView.findViewById(R.id.errorTime);
        TextView transactionType= (TextView)  convertView.findViewById(R.id.errorTransactionType);
        // Populate the data into the template view using the data object
        errorId.setText(error.errorId);
        errorDesc.setText(error.errorDesc);
        errorTime.setText(error.errorTime);
        transactionType.setText(error.transactionType);
        // Return the completed view to render on screen
        return convertView;
    }
}
