/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.uoa.di.mainsellers;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import gr.uoa.di.mainproducts.MyActivity;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

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
        Intent intent = new Intent(parent, MyActivity.class);
        parent.startActivity(intent);
    }
}