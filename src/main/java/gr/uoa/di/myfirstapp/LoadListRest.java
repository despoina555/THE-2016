/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.uoa.di.myfirstapp;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author mark9
 */
public class LoadListRest extends AsyncTask <String,Void,String>{
    private final Activity parent;
    private final CustomListAdapter mAdapter;
    private String file = "mylist.txt";
    
    public LoadListRest(Activity parent, CustomListAdapter adapter){
        this.parent=parent;
        this.mAdapter = adapter;
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
        ArrayList<Products> alpd= new ArrayList<Products>();
        Products pd;
        JSONObject jsonResponse;
        String res = "Restmessage: "+result;
        int myid = 0;
        try {
            JSONArray jsonMainNode = new JSONArray(result);
            for(int i=0 ; i<jsonMainNode.length() ; i++){
                jsonResponse =((JSONObject)jsonMainNode.get(i));
                if(Integer.valueOf(jsonResponse.getString("prodnum")) == 1){
                    pd = new Products();
                    pd.setProdname(jsonResponse.getString("prodname"));
                    pd.setProdprice(Float.valueOf(jsonResponse.getString("prodprice")));
                    alpd.add(pd);
                    //res = jsonResponse.getString("prodprice");
                }
            }
        } catch (JSONException ex) {
            Log.e("gr.uoa.hello","",ex);
        }
        mAdapter.upDateEntries(alpd);
        //Intent resultIntent= new Intent(parent, DisplayResults.class);
        /*String msg;
        if (alpd.size() != 0){
            msg = alpd.get(0).getProdname();
        }
        else{
            msg = "NO SHIT SHERLOCK";
        }*/
        //res = alpd.get(1).getProdname();
        //resultIntent.putExtra(MyActivity.RESULT_MESSAGE, res);
        //parent.startActivity(resultIntent); 
    }
}
