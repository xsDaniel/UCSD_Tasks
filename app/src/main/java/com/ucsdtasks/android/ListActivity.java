package com.ucsdtasks.android;

import android.*;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;


/**
 * Class ListActivity
 *
 * Generates a list view of the tasks to be done in the user's area.
 */

public class ListActivity extends AppCompatActivity {
    private ListView listView;
    private LatLng currentLoc;
    // private List<Task> tasks = new ArrayList<Task>();

    private double SEARCH_RADIUS = 0.6; // TODO temporary

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        // Checks if user has location enabled
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED) {
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            currentLoc = new LatLng(location.getLatitude(), location.getLongitude());
            populateListView();
        }
        // User does not have location enabled, requests permissions
        else {
            ActivityCompat.requestPermissions(this, new String[]{
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        }
    }


    /**
     * Method populateListView
     *
     * This callback is triggered when the user approves locations permissions.
     * It will enable location tracking and zoom to user's location.
     */

    public void populateListView() {
        // Get ListView object from xml
        listView = (ListView) findViewById(R.id.list);

        // Requests tasks passing in user's current location and radius to look for tasks
        //tasks = getTasks(currentLoc, SEARCH_RADIUS);

        // Task objects will have ID, title, author, bid, description, and location

        // Values to be shown in ListView
        String[] values = new String[] {
                "Walk dog",
                "Buy milk",
                "Sing a song",
                "Bring me food",
                "Etc"
        };

        // (Context, Layout for row, ID of TextView to write data to, Array of data)
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);

        // Assign adapter to ListView
        listView.setAdapter(adapter);


        /**
         * Method onItemClick
         *
         * Listens for clicks on the list's items.
         *
         * @param position   Index of item clicked
         */

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // Grabs name of clicked item
                String itemName = (String) listView.getItemAtPosition(position);
            }
        });
    }


    /**
     * Method onRequestPermissionsResults
     *
     * This callback is triggered when the user approves locations permissions.
     */

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        populateListView();
    }
}