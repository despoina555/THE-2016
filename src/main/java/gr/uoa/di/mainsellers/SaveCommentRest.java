/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.uoa.di.mainsellers;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.SphericalUtil;
import gr.uoa.di.mainproducts.DisplayResults;
import gr.uoa.di.mainproducts.MyActivity;
import gr.uoa.di.modelproducts.Products;
import gr.uoa.di.modelproducts.Sellers;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author mark9
 */
public class SaveCommentRest extends AsyncTask <String,Void,String>{
    
    private final Activity parent;
    String jsontogo;
    
    public SaveCommentRest(Activity parent, String myjson){
        this.parent=parent;
        this.jsontogo = myjson;
    }
    
    @Override
    protected String doInBackground(String... params) {
        StringBuffer bf= new StringBuffer();
        try{
            if(params.length>0){
// formulating theURL that will be used for establishing the connection to the service
                URL serviceURL= new URL(params[0]);
// openning the http connection to the service
                HttpURLConnection connection= (HttpURLConnection) serviceURL.openConnection();
// setting the HTTP method that will be to something different than the deafult get
         connection.setRequestMethod("POST");
// if you need to send data along with the request
        connection.setDoOutput(true);
// establishingtheconnection
        connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        
                connection.connect();
                
                OutputStream os = connection.getOutputStream();
                os.write(jsontogo.getBytes("UTF-8"));
                os.close();
                
// openning the input stream for reading the response of the service and
//piping the input stream through a buffered reader object
                BufferedReader reader= new BufferedReader(new InputStreamReader(connection.getInputStream()));
//reading the returned input line by line till the EOF is returned 
                String line;
                while((line = reader.readLine())!= null)
                    bf.append(line);
                return bf.toString();
            }
        }catch(MalformedURLException ex){
            Log.d("gr.uoa.di.myfirstapp","",ex);
        }catch(IOException ex){
            Log.d("gr.uoa.di.myfirstapp","",ex);
        }
        finally{
            return bf.toString();
        }
    }
    
    protected void onPostExecute(String result) {
        /*ArrayList<Sellers> alsl= new ArrayList<Sellers>();
        Sellers sl;
        JSONObject jsonResponse;*/
        String res = "Restmessage: "+result;
        /*
        ArrayList<Integer> temppdsel;
        ArrayList<Float> temppdprice;
        int myid = 0;
        try {
            JSONArray jsonMainNode = new JSONArray(result);
            for(int j=0 ; j<product.getSellerid().size() ; j++){
                for(int i=0 ; i<jsonMainNode.length() ; i++){
                    jsonResponse =((JSONObject)jsonMainNode.get(i));
                    if(product.getSellerid().get(j) == Integer.valueOf(jsonResponse.getString("sellerid"))){
                        LatLng coord = new LatLng(Double.parseDouble(jsonResponse.getString("lat")), Double.parseDouble(jsonResponse.getString("long1")));
                        LatLng curcoord = new LatLng(loc.getLatitude(),loc.getLongitude());
                        double dist = SphericalUtil.computeDistanceBetween(coord, curcoord);
                        if( dist < (Double.parseDouble(maxdist)*1000.0) || Double.parseDouble(maxdist) == -1){
                            sl = new Sellers();
                            sl.setId(Integer.valueOf(jsonResponse.getString("sellerid")));
                            sl.setAddress(jsonResponse.getString("selleraddress"));
                            sl.setEmail(jsonResponse.getString("sellermail"));
                            sl.setName(jsonResponse.getString("sellername"));
                            sl.setPrice(product.getProdprice().get(j));
                            sl.setLat(Float.valueOf(jsonResponse.getString("lat")));
                            sl.setLongt(Float.valueOf(jsonResponse.getString("long1")));
                            sl.setDist(dist);
                            alsl.add(sl);
                        }
                    }
                }
            }
            if("1".equals(order)){
                Collections.sort(alsl, Sellers.SelPriceComparator);
            }
            else if("2".equals(order)){
                Collections.sort(alsl, Sellers.SelDistComparator);
            }
        } catch (JSONException ex) {
            Log.e("gr.uoa.hello","",ex);
        }
        mAdapter.upDateEntries(alsl);*/
        Intent intent = new Intent(parent, MyActivity.class);
        parent.startActivity(intent);
    }
}