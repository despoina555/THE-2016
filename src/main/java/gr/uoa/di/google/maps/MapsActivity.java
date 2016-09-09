package gr.uoa.di.google.maps;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import android.os.ResultReceiver;

import android.util.Log;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import android.location.Address;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
//import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
//import com.google.android.gms.maps.StreetViewPanorama;
//import com.google.android.gms.maps.StreetViewPanoramaFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import gr.uoa.di.R;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnMyLocationButtonClickListener, GoogleApiClient.ConnectionCallbacks {

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;//used for find Last current location
    private LatLng mylocation ;


    int fetchType = Constants.USE_ADDRESS_NAME;
    TextView selleraddress;

    LatLng sellersplace = null;

    ProgressBar progressBar;
    TextView infoText;
    TextView infoText2;
    ImageButton directionsButton;


    private static final String TAG = "MapsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        if (mGoogleApiClient == null) {

            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addApi(LocationServices.API)
                    .addApi(AppIndex.API).build();
        }


        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        infoText = (TextView) findViewById(R.id.infoText);
        infoText2 = (TextView) findViewById(R.id.infoText2);
        directionsButton=(ImageButton) findViewById(R.id.directionsButton);

        directionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getApplicationContext(), "begins to give you directios" , Toast.LENGTH_SHORT);
                toast.show();
                giveDirections();
            }
        });
    }




    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        enableMyLocation();
        Intent intent = getIntent();
  /*
        intent.putExtra(Constants.LOCATION_COORDINATES_EXTRA,new LatLng(Seller.getLat(), Seller.getLongt()));
        intent.putExtra(Constants.SELLER_NAME,seller.getName());
        * */

        sellersplace = intent.getParcelableExtra(Constants.LOCATION_COORDINATES_EXTRA);
        String sellerName = intent.getStringExtra(Constants.SELLER_NAME);
        mMap.addMarker(new MarkerOptions().position(sellersplace).title(sellerName));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sellersplace,15));
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);


        infoText2.setText("Seller's name::" + sellerName);

    }

    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

        } else if (mMap != null) {

            mMap.setMyLocationEnabled(true);

        }
    }


    //methods used by mGoogleApiClient
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        if(mylocation!=null&&sellersplace!=null) {
            LatLng origin = mylocation;
            LatLng dest = sellersplace;

            mMap.addPolyline(new PolylineOptions().add(origin).add(dest).color(Color.MAGENTA));

            // Getting URL to the Google Directions API
            String url = getDirectionsUrl(origin, dest);

            DirectionsFetcher directionsFetcher = new DirectionsFetcher(getBaseContext());

            // Start downloading json data from Google Directions API
            directionsFetcher.execute(url);
        }
        return true;
    }

    @Override
    public boolean onMyLocationButtonClick() {
        return true;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            mylocation = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());

            Toast toast = Toast.makeText(getApplicationContext(), "i found your location" + mylocation, Toast.LENGTH_SHORT);
            toast.show();
        }

    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    public void giveDirections(){
        Toast toast = Toast.makeText(getApplicationContext(), "giveDirections", Toast.LENGTH_SHORT);
        toast.show();

        if(mylocation!=null&&sellersplace!=null) {

            LatLng origin = mylocation;
            LatLng dest = sellersplace;
            mMap.addMarker(new MarkerOptions().position(origin).title("you are here"));
            mMap.addPolyline(new PolylineOptions().add(origin).add(dest).color(Color.MAGENTA));

            String url = getDirectionsUrl(origin, dest);
            Toast toast2 = Toast.makeText(getApplicationContext(), "url "+url, Toast.LENGTH_SHORT);
            toast2.show();
            DirectionsFetcher directionsFetcher = new DirectionsFetcher(getBaseContext());
            Toast toast3 = Toast.makeText(getApplicationContext(), " directionsFetcher.execute(url)", Toast.LENGTH_SHORT);
            toast3.show();
            directionsFetcher.execute(url);
        }else{
            Toast toast2 = Toast.makeText(getApplicationContext(), "unable to find directions" , Toast.LENGTH_SHORT);
            toast2.show();
        }
    }


    private String getDirectionsUrl(LatLng origin, LatLng dest) {

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

        // Sensor enabled
        String sensor = "&mode=walking&key=AIzaSyC-trwnGXpljEs184ueqTVDqQy8VfdVYBQ";

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor;


        /*https://maps.googleapis.com/maps/api/directions/json?origin=+37.978966,23.762810&destination=37.973296,23.773169&mode=walking&key=AIzaSyC-trwnGXpljEs184ueqTVDqQy8VfdVYBQ*/

        // Building the url to the web service
        String url ="https://maps.googleapis.com/maps/api/directions/json?" + parameters;

        return url;
    }

    /**
     * A method to download json data from url
     */
    public String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        } catch (Exception e) {
//            Log.d("Exception while downloading url", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }


    class DirectionsFetcher extends AsyncTask<String, Void, String> {

        Context context;

        public DirectionsFetcher(Context context) {
            this.context = context;
        }

        // Downloading data in non-ui thread
        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try {
                // Fetching the data from web service
                data = downloadUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        // Executes in UI thread, after the execution of
        // doInBackground()
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();

            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);
        }
    }

    /**
     * A class to parse the Google Places in JSON format
     */
    class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();

                // Starts parsing data
                routes = parser.parse(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points = null;
            PolylineOptions lineOptions = null;
            MarkerOptions markerOptions = new MarkerOptions();
            String distance = "";
            String duration = "";

            if (result.size() < 1) {
//            Toast.makeText(this.context, "No Points", Toast.LENGTH_SHORT).show();
                return;
            }

            // Traversing through all the routes
            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList<LatLng>();
                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    if (j == 0) {    // Get distance from the list
                        distance = (String) point.get("distance");
                        continue;
                    } else if (j == 1) { // Get duration from the list
                        duration = (String) point.get("duration");
                        continue;
                    }

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(2);
                lineOptions.color(Color.RED);
            }


            // Drawing polyline in the Google Map for the i-th route
            mMap.addPolyline(lineOptions);
        }
    }
}
