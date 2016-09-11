/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.uoa.di.mainsellers;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import gr.uoa.di.google.maps.Constants;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author mark9
 */
public class CountCommentRest extends AsyncTask <String,Void,String>{
    private final Activity parent;
    String jsontogo;
    
    public CountCommentRest(Activity parent, String myjson){
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
        try {
            JSONObject jsonsend = new JSONObject(jsontogo);
            int comid = Integer.valueOf(result);
            comid++;
            jsonsend.put("comid", String.valueOf(comid));
            SaveCommentRest scr = new SaveCommentRest(parent, jsonsend.toString());
            scr.execute(Constants.POST_COMMENTS_API);
        } catch (JSONException ex) {
            Logger.getLogger(CountCommentRest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}