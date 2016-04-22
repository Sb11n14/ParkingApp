package com.opendata.myparking.parkingapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.opendata.myparking.parkingapp.R;
import com.opendata.myparking.parkingapp.adapter.ParkingsAdapter;
import com.opendata.myparking.parkingapp.database.DBOpenHelper;

/**
 * Created by Shamel on 22/04/2016.
 */
public class UserFragment extends android.support.v4.app.Fragment {

    private TextView user_id;
    private TextView credit_number;
    private TextView plate_number;
    private TextView balance;
    private TextView error_balance;

    private FloatingActionButton fabButton;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_user, container, false);


        user_id = (TextView) rootView.findViewById(R.id.user_id);
        credit_number = (TextView) rootView.findViewById(R.id.credit_number);
        plate_number = (TextView) rootView.findViewById(R.id.plate_number);
        balance = (TextView) rootView.findViewById(R.id.balance);
        error_balance = (TextView) rootView.findViewById(R.id.error_balance);

        user_id.setText("User id");
        credit_number.setText("Credit number");
        plate_number.setText("Plate number");
        balance.setText("Balance");
        error_balance.setText("Error balance");

        fabButton = (FloatingActionButton) rootView.findViewById(R.id.fabButton);

        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*String location = inputLocation.getText().toString().trim();
                int cost = Integer.parseInt(inputCost.getText().toString().trim());
                inputLocation(location, cost);*/

                Intent intent = new Intent(UserFragment.this.getActivity(), EditUserActivity.class);
                startActivity(intent);

            }
        });
        return rootView;
    }

}
