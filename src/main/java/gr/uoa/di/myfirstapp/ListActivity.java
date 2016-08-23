/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.uoa.di.myfirstapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.io.FileInputStream;
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
public class ListActivity extends AppCompatActivity {

    private String file = "mylist.txt";
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_list_display);
        String mylist = readListFile();
        if (mylist!=null){
            String items[] = mylist.split(" ");
            ArrayList<String> al = new ArrayList<>();
            String itemname;
            //for(int i=0 ; i<items.length ; i++){
                new RestThread(this).execute("http://192.168.1.2:8080/ListDB/webresources/entities.product/bubbles");
        /*Intent resultIntent= new Intent(this, DisplayResults.class);
        resultIntent.putExtra(MyActivity.RESULT_MESSAGE, itemname);
        this.startActivity(resultIntent);                
                /*JSONObject jobj;
                try {
                    jobj = new JSONObject(itemname);
                    al.add(jobj.getString("prodname"));
                } catch (JSONException ex) {
                    Logger.getLogger(ListActivity.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.row_list_item, al);
            ListView listView = (ListView) findViewById(R.id.mobile_list);
            listView.setAdapter(adapter);*/
        }
        else{
            LinearLayout layout = (LinearLayout) findViewById(R.id.linearlist);
            TextView textView = new TextView(this);
            textView.setTextSize(40);
            textView.setText("Your list is empty");
            layout.addView(textView);
        } 
    }

    private String readListFile() {
        try{
               FileInputStream fin = openFileInput(file);
               int c;
               String temp="";
               
               while( (c = fin.read()) != -1){
                  temp = temp + Character.toString((char)c);
               }
               return temp;
            }
            catch(Exception ex){
                Log.e("gr.uoa.di.list","",ex);
                return null;
            }
    }
    
}
