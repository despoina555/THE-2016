package gr.uoa.di.myfirstapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Despina on 27/8/2016.
 */
public class FindProducts extends AsyncTask<String,Void,String> {
    private Activity parent;
    public FindProducts(Activity parent){
        this.parent=parent;
    }
    protected void onPreExecute() {
        //useless yet
    }

    /*execution of service call in the background.
    */
    @Override
    protected String doInBackground(String... params){
// buffer used for storing collected response
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
                StringBuilder sb = new StringBuilder();//despoina
                String line;
                while((line = reader.readLine())!= null) {
                    sb.append(line + "");//desp
                    bf.append(line);

                }
                return sb.toString();//bf.toString();
        }
        }catch(MalformedURLException ex){
            Log.d("gr.uoa.di","",ex);
        }catch(IOException ex){
            Log.d("gr.uoa.di","",ex);
        }
        finally{
            return bf.toString();
        }
    }


    /**this method is called after the execution of the doInBackground method.It receives as
     input the outcome of the
     *doInBackground method and performs any additional required task.
     *
     * In this case it parses
     the string outcome of the
     *method call and creates a JSON Object representation that is afterwards used for further
     processing.
     */
    @Override
    protected void onPostExecute (String result) {


        Context context = parent.getApplicationContext();
        CharSequence text = "On post execute!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();



        if (result !=null){
            JSONObject jsonResponse;
            String msg = "Restmessage: " + result;
            String repsonse4debugg="Response for debugging :)) ";//debug
            try {
        /******Creates a new JSONObject with name/value mappings from the
         JSON string.********/
                        //jsonResponse=new JSONObject(result);
        // extract the Android array
                        JSONArray jsonMainNode = new JSONArray(result);//error
        // get the name attribute of the first element in the array

//                        jsonResponse = ((JSONObject) jsonMainNode.get(0));//later uncomment

                //maybe later comment --my changes
                for (int i = 0; i < jsonMainNode.length() ; i++) {
                    jsonResponse = ((JSONObject) jsonMainNode.get(i));
                    String pname = jsonResponse.optString("name").toString();// jsonResponse.getString("name");//pname
                    msg.concat(" "+pname);
                }
                //maybe later comment --my changes end
//                or for debugging
               repsonse4debugg=  jsonMainNode.toString();


//                        msg = jsonResponse.getString("pname"); //later uncomment


                } catch (JSONException ex) {
                        Log.e("gr.uoa.di", "", ex);
                }
        // put this message in an intent object and fire it to the ResultActivity
                    //ERROR here
                    Intent resultIntent = new Intent(parent, ShowProductsActivity.class);
                    resultIntent.putExtra(MyActivity.RESULT_MESSAGE, repsonse4debugg);//msg
                    parent.startActivity(resultIntent);
        }//if result not null
    }
}
