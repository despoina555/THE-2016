package gr.uoa.di.google.maps;

import android.app.IntentService;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import gr.uoa.di.R;

/**
 * Created by Despina on 1/9/2016.
 *
 * this class is  address lookup service
 */
public class FetchAddressIntentService extends IntentService {

    public FetchAddressIntentService() {
        super("FetchAddressIntentService");
    }

    protected ResultReceiver resultReceiver;
    //is for When the IntentService has completed it’s task,
    // it should have a way to send the results back to the invoking Activity
    private static final String TAG = "FetchAddyIntentService";


    @Override
    protected void onHandleIntent(Intent intent) {
        String errorMessage = "";

        resultReceiver = intent.getParcelableExtra(Constants.RECEIVER);
//        int fetchType = intent.getIntExtra(Constants.FETCH_TYPE_EXTRA, 0);
        Locale locale= new Locale("el-GR");
        Locale.setDefault(locale);
        Geocoder geocoder = new Geocoder(this,Locale.getDefault());

        List<Address> addresses = null;

        try {
            String address = intent.getStringExtra(Constants.LOCATION_NAME_DATA_EXTRA);
            addresses = geocoder.getFromLocationName(address.toString(),1);//address.toString()==malakia??

        } catch (IOException ioException) {
            // Catch network or other I/O problems.
            errorMessage = getString(R.string.service_not_available);
           // Log.e(TAG, errorMessage, ioException);
        } catch (IllegalArgumentException illegalArgumentException) {
            // Catch invalid address name values.
            errorMessage = getString(R.string.invalid_address_used);
        }

        // Handle case where no address was found.
        if (addresses == null || addresses.size() == 0) {
            if (errorMessage.isEmpty()) {
                errorMessage = getString(R.string.no_address_found);
                Log.e(TAG, errorMessage);
            }
            deliverResultToReceiver(Constants.FAILURE_RESULT, errorMessage,null);
        } else {

            for(Address address : addresses) {
                String outputAddress = "";
                for(int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                    outputAddress += " --- " + address.getAddressLine(i);
                }
            }
            Address address = addresses.get(0);
            ArrayList<String> addressFragments = new ArrayList<String>();

            for(int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                addressFragments.add(address.getAddressLine(i));
            }
            Log.i(TAG, getString(R.string.address_found));
            deliverResultToReceiver(Constants.SUCCESS_RESULT,
                    TextUtils.join(System.getProperty("line.separator"),addressFragments), address);

        }
    }

    private void deliverResultToReceiver(int resultCode, String message, Address address) {
        Bundle bundle = new Bundle();
        if(address!=null){
        bundle.putParcelable(Constants.RESULT_ADDRESS, address);}
        bundle.putString(Constants.RESULT_DATA_KEY, message);
        resultReceiver.send(resultCode, bundle);
    }

}
