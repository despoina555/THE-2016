/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.uoa.di.mainsellers;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
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
import gr.uoa.di.modelproducts.Comments;

/**
 *
 * @author mark9
 */
class LoadCommentsRest extends AsyncTask <String,Void,String>{
    private final Activity parent;
    private final CustomCommentsAdapter mAdapter;
    private final String mSelid;
    
    public LoadCommentsRest(Activity parent, CustomCommentsAdapter adapter, String selid){
        this.parent=parent;
        this.mAdapter = adapter;
        this.mSelid = selid;
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
        ArrayList<Comments> alcm= new ArrayList<Comments>();
        Comments cm;
        JSONObject jsonResponse;
        String res = "Restmessage: "+result;

        try {
            JSONArray jsonMainNode = new JSONArray(result);
            for(int i=0 ; i<jsonMainNode.length() ; i++){
                jsonResponse =((JSONObject)jsonMainNode.get(i));
                if(Integer.valueOf(mSelid) == Integer.valueOf(jsonResponse.getString("sellerid"))){
                        cm = new Comments();
                        cm.setSelid(Integer.valueOf(jsonResponse.getString("sellerid")));
                        cm.setComname(jsonResponse.getString("comname"));
                        cm.setComtext(jsonResponse.getString("comtext"));
                        cm.setProdname(jsonResponse.getString("prodname"));
                        cm.setProdprice(jsonResponse.getString("prodprice"));
                        cm.setRating(jsonResponse.getString("rating"));
                        alcm.add(cm);
                }
            }
        } catch (JSONException ex) {
            Log.e("gr.uoa.hello","",ex);
        }
        mAdapter.upDateEntries(alcm);
    }
}