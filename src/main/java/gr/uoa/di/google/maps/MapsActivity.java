package gr.uoa.di.google.maps;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import android.view.View;
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

import gr.uoa.di.R;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnMyLocationButtonClickListener, GoogleApiClient.ConnectionCallbacks {

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;//used for find Last current location
    private LatLng mylocation = null;



    int fetchType = Constants.USE_ADDRESS_NAME;
    TextView selleraddress;
    AddressResultReceiver mResultReceiver;
    LatLng sellersplace=null;

    ProgressBar progressBar;
    TextView infoText;
    TextView infoText2;


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



//       TODO:: (correct) selleraddress=(TextView) findViewById(R.id.selleraddress);

//        mResultReceiver = new AddressResultReceiver(null);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        enableMyLocation();


        Intent intent = getIntent();
//        intent.putExtra(Constants.LOCATION_COORDINATES_EXTRA,new LatLng(37.978966, 23.762810));
//        intent.putExtra(Constants.SELLER_NAME,"Public");
  /*
        intent.putExtra(Constants.LOCATION_COORDINATES_EXTRA,new LatLng(Seller.getLat(), Seller.getLongt()));
        intent.putExtra(Constants.SELLER_NAME,seller.getName());
        * */

        sellersplace= intent.getParcelableExtra(Constants.LOCATION_COORDINATES_EXTRA);
        String sellerName=intent.getStringExtra(Constants.SELLER_NAME);
        mMap.addMarker(new MarkerOptions().position(sellersplace).title(sellerName));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sellersplace));

        if(sellersplace!=null && mylocation!=null){
        PolylineOptions poly = new PolylineOptions().color(Color.RED);
        mMap.addPolyline(poly .add(mylocation,sellersplace)
                .width(5));}


        infoText2.setText("Seller's name::"+sellerName);

/*        //I need to take the shop's Lat Long by previous activity
        LatLng shop = new LatLng(37, 23);
        mMap.addMarker(new MarkerOptions().position(shop).title("Marker in somewhere rnadom"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(shop));
        //find the loction by anddress--------------------------- instead the dummy loc
        Intent intent = new Intent(this, FetchAddressIntentService.class);
        intent.putExtra(Constants.RECEIVER, mResultReceiver);
        intent.putExtra(Constants.FETCH_TYPE_EXTRA, fetchType);
        if(fetchType == Constants.USE_ADDRESS_NAME) {
            }

        infoText2.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        Log.e(TAG, "Starting Service");
        startService(intent);
        //------------------------------*/


    }

    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);

            Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

            if (mLastLocation != null) {
                Double latitude = mLastLocation.getLatitude();
                Double longitude = mLastLocation.getLongitude();

                mylocation = new LatLng(longitude, latitude);

                Log.d(TAG, "your location is loc==" + mylocation);
                infoText2.setText("your location is loc==" + mylocation);
                Toast toast = Toast.makeText(getApplicationContext(), "i found your location" + mylocation, Toast.LENGTH_SHORT);
                toast.show();
            }
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
        return false;
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
    }

    @Override
    public void onConnectionSuspended(int i) {
    }



//    public void giveDirections(View view) {
//        //this method activated when the user push the image button and the web service for giving directions starts!
//        if( sellersplace!=null && mylocation!=null){
//            //calculate directions!
//            mMap.addPolyline((new PolylineOptions()).add(mylocation).add(sellersplace)).setColor(Color.GREEN);
//        }
//
//    }


    @SuppressLint("ParcelCreator")
    class AddressResultReceiver extends ResultReceiver {

        public AddressResultReceiver(Handler handler) {
            super(handler);
        }
        @Override
        protected void onReceiveResult(int resultCode, final Bundle resultData) {
            if (resultCode == Constants.SUCCESS_RESULT) {
                final Address address = resultData.getParcelable(Constants.RESULT_ADDRESS);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.INVISIBLE);
                        infoText2.setText("Latitude: " + address.getLatitude() + "\n" +
                                "Longitude: " + address.getLongitude() + "\n" +
                                "Address: " + resultData.getString(Constants.RESULT_DATA_KEY));
                        double lng=address.getLongitude();
                        double lat=address.getLatitude();

                        Log.d(TAG,"in onReceiveResult, class AddressResultReceiver, the seller is on loc=="+lng+" , "+lat);
                        sellersplace = new LatLng(lat,lng );
                        mMap.addMarker(new MarkerOptions().position(sellersplace).title("Marker in seller's place"));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(sellersplace));
                    }
                });
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        infoText.setVisibility(View.VISIBLE);
                        infoText.setText(resultData.getString(Constants.RESULT_DATA_KEY));
                    }
                });
            }
        }

 }//inner class

}
