package gr.uoa.di.myfirstapp;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

public class RestThread extends AsyncTask <String,Void,String>{
    private Activity parent;
    public RestThread(Activity parent){
        this.parent=parent;
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
                String line;
                while((line = reader.readLine())!= null)
                    bf.append(line);
                return bf.toString();
            }
        }catch(MalformedURLException ex){
            Log.d("gr.uoa.hello","",ex);
        }catch(IOException ex){
            Log.d("gr.uoa.hello","",ex);
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
    protected void onPostExecute (String result){
        JSONObject jsonResponse;
        String msg= "Restmessage: "+result;
        try{
/******Creates a new JSONObject with name/value mappings from the
JSON string.********/
            //jsonResponse=new JSONObject(result);
// extract the Android array
            JSONArray jsonMainNode = new JSONArray(result);
// get the name attribute of the first element in the array
           jsonResponse =((JSONObject)jsonMainNode.get(0));
           msg = jsonResponse.getString("name");
        }
        catch(JSONException ex){
            Log.e("gr.uoa.hello","",ex);
        }
// put this message in an intent object and fire it to the ResultActivity
        Intent resultIntent= new Intent(parent, DisplayResults.class);
        resultIntent.putExtra(MyActivity.RESULT_MESSAGE, msg);
        parent.startActivity(resultIntent);
    }
}
