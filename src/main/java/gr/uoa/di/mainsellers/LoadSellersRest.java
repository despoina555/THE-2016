/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.uoa.di.mainsellers;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import gr.uoa.di.modelproducts.Products;
import gr.uoa.di.modelproducts.Sellers;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author mark9
 */
public class LoadSellersRest extends AsyncTask <String,Void,String>{
    private final Activity parent;
    private final CustomSellersAdapter mAdapter;
    private final Products product;
    
    public LoadSellersRest(Activity parent, CustomSellersAdapter adapter, Products pd){
        this.parent=parent;
        this.mAdapter = adapter;
        this.product = pd;
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
        ArrayList<Sellers> alsl= new ArrayList<Sellers>();
        Sellers sl;
        JSONObject jsonResponse;
        String res = "Restmessage: "+result;
        
        ArrayList<Integer> temppdsel;
        ArrayList<Float> temppdprice;
        int myid = 0;
        try {
            JSONArray jsonMainNode = new JSONArray(result);
            for(int j=0 ; j<product.getSellerid().size() ; j++){
                for(int i=0 ; i<jsonMainNode.length() ; i++){
                    jsonResponse =((JSONObject)jsonMainNode.get(i));
                    if(product.getSellerid().get(j) == Integer.valueOf(jsonResponse.getString("sellerid"))){
                        sl = new Sellers();
                        sl.setId(Integer.valueOf(jsonResponse.getString("sellerid")));
                        sl.setAddress(jsonResponse.getString("selleraddress"));
                        sl.setEmail(jsonResponse.getString("sellermail"));
                        sl.setName(jsonResponse.getString("sellername"));
                        sl.setPrice(product.getProdprice().get(j));
                        sl.setLat(Float.valueOf(jsonResponse.getString("lat")));
                        sl.setLongt(Float.valueOf(jsonResponse.getString("long1")));
                        alsl.add(sl);
                    }
                }
            }
        } catch (JSONException ex) {
            Log.e("gr.uoa.hello","",ex);
        }
        mAdapter.upDateEntries(alsl);
    }
}