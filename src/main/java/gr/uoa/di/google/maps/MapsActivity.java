package gr.uoa.di.google.maps;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import gr.uoa.di.R;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnMyLocationButtonClickListener, GoogleApiClient.ConnectionCallbacks {

    //


    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;//used for find Last current location
    private LatLng mylocation = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                   // .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // mMap.setOnMyLocationButtonClickListener(this);//TODO:: ??
        //Sets a callback that's invoked when the my location button is clicked.
        enableMyLocation();

        //I need to take the shop's Lat Long by previous activity
        LatLng shop = new LatLng(37, 23);
        mMap.addMarker(new MarkerOptions().position(shop).title("Marker in somewhere rnadom"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(shop));


    }

    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
//            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
//                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);

            Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

            if (mLastLocation != null) {
                Double latitude = mLastLocation.getLatitude();
                Double longitude = mLastLocation.getLongitude();

                mylocation = new LatLng(latitude, longitude);

                Toast toast = Toast.makeText(getApplicationContext(), "i found your location" + mylocation, Toast.LENGTH_SHORT);
                toast.show();
            /*When the My Location layer is enabled, the My Location button appears in the top right corner of the map.
             When a user clicks the button, the camera centers the map on the current location of the device, if it is known*/
            }
        }
    }


//    @Override
//    public void onConnected(Bundle connectionHint) {
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            Context context = getApplicationContext();
//            CharSequence text = "onConnected !";
//            int duration = Toast.LENGTH_SHORT;
//
//            Toast toast = Toast.makeText(context, text, duration);
//            toast.show();
//
//        } else {
//            Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
//
//            if (mLastLocation != null) {
//                Double latitude = mLastLocation.getLatitude();
//                Double longitude = mLastLocation.getLongitude();
//
//                mylocation = new LatLng(latitude, longitude);
//
//                Toast toast = Toast.makeText(getApplicationContext(), "i found your location" + mylocation, Toast.LENGTH_SHORT);
//                toast.show();
//            }
//
//        }
//    }

//    @Override
//    public void onConnectionSuspended(int i) {
//        Toast toast = Toast.makeText(getApplicationContext(), "unfortunately the connection has lost, please enable your wifi or data !! ", Toast.LENGTH_SHORT);
//    }

//    @Override
//    public boolean onMarkerClick(Marker marker) {
//        //when the user will click on Market it will shows some info about
//        //TODO:: add code later
//        Toast toast = Toast.makeText(getApplicationContext(), "A 5stars shop!! ", Toast.LENGTH_SHORT);
//        return false;
//    }

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

        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public boolean onMyLocationButtonClick() {
        return true;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }
}//classS
