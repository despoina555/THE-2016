/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.uoa.di.mainsellers;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;
import gr.uoa.di.R;
import gr.uoa.di.mainproducts.DisplayResults;
import gr.uoa.di.mainproducts.MyActivity;
import gr.uoa.di.mainproducts.SettingsActivity;
import gr.uoa.di.modelproducts.Products;
import gr.uoa.di.modelproducts.Sellers;
import android.support.v4.content.ContextCompat;

/**
 *
 * @author mark9
 */
public class DisplaySellersActivity extends AppCompatActivity implements ConnectionCallbacks, OnConnectionFailedListener{
    
    GoogleApiClient mGoogleApiClient = null;
    Location mLastLocation = null;
    CustomSellersAdapter adapter;
    Products proddisp;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_sellers);
        Intent intent = getIntent();
        String message = intent.getStringExtra("gr.uoa.di.prod_to_show");
        Gson gson = new Gson();
        proddisp = gson.fromJson(message, Products.class);
        TextView prodnametext = (TextView) findViewById(R.id.pdname);
        prodnametext.setText(proddisp.getProdname());
        

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .addApi(LocationServices.API).build();
        }
        
        
        
        adapter = new CustomSellersAdapter(this);
        ListView listView = (ListView) findViewById(R.id.seller_list);
        listView.setEmptyView(findViewById(R.id.emptyview));
        listView.setAdapter(adapter);
    }

    @Override
    protected void onResume(){
        mGoogleApiClient.connect();
        super.onResume();
    }
    
    @Override
    protected void onStart(){
        super.onStart();
        
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    protected void onStop(){
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
        super.onStop();
    }
    
    @Override
    public void onConnected(Bundle bundle) {
        if (mGoogleApiClient != null) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);
    }
}

        if (mLastLocation != null) {
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
            String dist = sharedPref.getString("distanceStore", "");
            String order = sharedPref.getString("storeOrder", "");
            LoadSellersRest lsr = new LoadSellersRest(this, adapter, proddisp, mLastLocation, dist, order);
           lsr.execute("http://192.168.1.6:8080/ListDB/webresources/entities.sellers/bubbles");
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onConnectionFailed(com.google.android.gms.common.ConnectionResult cr) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
