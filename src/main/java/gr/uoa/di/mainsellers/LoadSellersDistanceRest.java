/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.uoa.di.mainsellers;

import android.app.Activity;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;
import gr.uoa.di.modelproducts.Sellers;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author mark9
 */
public class LoadSellersDistanceRest extends AsyncTask <String,Void,ArrayList<String>>{
    private final Activity parent;
    private final CustomSellersAdapter mAdapter;
    private final Location loc;
    private final String maxdist;
    private ArrayList<Sellers> mSellers;
    
    private static final String DIRECTIONS_API_BASE = "https://maps.googleapis.com/maps/api/directions";
    private static final String OUT_JSON = "/json";

    private static final String API_KEY = "AIzaSyCdwh7xsNn7GjgRF9zqmSgJIpvUGA153-M";
    
    public LoadSellersDistanceRest(Activity parent, CustomSellersAdapter adapter, Location curloc, String dist, ArrayList<Sellers> alsl){
        this.parent=parent;
        this.mAdapter = adapter;
        this.loc = curloc;
        this.maxdist = dist;
        this.mSellers = alsl;
    }
    
    @Override
    protected ArrayList<String> doInBackground(String... params) {
        ArrayList<String> tbf= new ArrayList<String>();
        try{
            if(params.length>0){
                for(int i=0 ; i<mSellers.size() ; i++){
                            StringBuffer bf= new StringBuffer();
// formulating theURL that will be used for establishing the connection to the service
                        StringBuilder sb = new StringBuilder(DIRECTIONS_API_BASE + OUT_JSON);
                        sb.append("?origin=" + URLEncoder.encode(String.valueOf(loc.getLatitude()), "utf8") + "," + URLEncoder.encode(String.valueOf(loc.getLongitude()), "utf8"));
                        sb.append("&destination=" + URLEncoder.encode(String.valueOf(mSellers.get(i).getLat()), "utf8") + "," + URLEncoder.encode(String.valueOf(mSellers.get(i).getLongt()), "utf8"));
                        sb.append("&mode=walking");
                        sb.append("&key=" + API_KEY);
                URL serviceURL= new URL(sb.toString());
// openning the http connection to the service
                HttpURLConnection connection= (HttpURLConnection) serviceURL.openConnection();
// setting the HTTP method that will be to something different than the deafult get
        // connection.setRequestMethod("POST");
// if you need to send data along with the request
        //connection.setDoOutput(true);
// establishingtheconnection
                connection.connect();
// openning the input stream for reading the response of the service and
//piping the input stream through a buffered reader object
                BufferedReader reader= new BufferedReader(new InputStreamReader(connection.getInputStream()));
//reading the returned input line by line till the EOF is returned 
                String line;
                while((line = reader.readLine())!= null)
                    bf.append(line);
                tbf.add(bf.toString());
                
                }
                return tbf;
            }
        }catch(MalformedURLException ex){
            Log.d("gr.uoa.di.myfirstapp","",ex);
        }catch(IOException ex){
            Log.d("gr.uoa.di.myfirstapp","",ex);
        }
        finally{
            return tbf;
        }
    }
    
    protected void onPostExecute(ArrayList<String> tbf) {
        JSONObject jsonres = null;
        StringBuilder sb = new StringBuilder();
        
        for(int i=mSellers.size()-1 ; i>=0 ; i--){
            try {
                jsonres = new JSONObject(tbf.get(i))
                                        .getJSONArray("routes")
                                        .getJSONObject(0)
                                        .getJSONArray ("legs")
                                        .getJSONObject(0)
                                        .getJSONObject("duration");
                
                if((Integer.valueOf(jsonres.getString("value")) > Integer.valueOf(maxdist)) && (Integer.valueOf(maxdist) != -1)){
                    mSellers.remove(i);
                }
            } catch (JSONException ex) {
                Logger.getLogger(LoadSellersDistanceRest.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        mAdapter.upDateEntries(mSellers);
    }
}