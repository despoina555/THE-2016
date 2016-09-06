/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.uoa.di.mainlist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import gr.uoa.di.mainproducts.CustomProductsAdapter;
import gr.uoa.di.mainproducts.LoadProductsRest;
import java.io.FileInputStream;
import gr.uoa.di.R;
import gr.uoa.di.mainproducts.DisplayResults;
import gr.uoa.di.mainproducts.MyActivity;
import gr.uoa.di.mainproducts.ShowProductsActivity;
import gr.uoa.di.modelproducts.ShoppingList;
import java.util.ArrayList;

/**
 *
 * @author mark9
 */
public class MyListActivity extends AppCompatActivity {

    private final String file = "mylist.txt";
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_list_display);
        //ShoppingList shl = new ShoppingList();
        /*ArrayList<String> templ;
        templ = shl.readfromfile(this);
        if(templ == null){
            LinearLayout layout = (LinearLayout) findViewById(R.id.linearlist);
            TextView textView = new TextView(this);
            textView.setTextSize(40);
            textView.setText("Your list is empty");
            layout.addView(textView);
        }
        else{*/
            CustomListAdapter adapter = new CustomListAdapter(this);
            ListView listView = (ListView) findViewById(R.id.mobile_list);
            listView.setEmptyView(findViewById(R.id.anemptylist));
            listView.setAdapter(adapter);
            LoadListRest llr = new LoadListRest(this, adapter);
            llr.execute("http://192.168.1.6:8080/ListDB/webresources/entities.product/bubbles");
        //}
        //String mylist = readListFile();
        //if (mylist!=null){
           // String items[] = mylist.split(" ");
            /*CustomListAdapter adapter = new CustomListAdapter(this);
            ListView listView = (ListView) findViewById(R.id.mobile_list);
            listView.setAdapter(adapter);
            LoadListRest llr = new LoadListRest(this, adapter);
            llr.execute("http://192.168.1.9:8080/ListDB/webresources/entities.product/bubbles");*/
            //ArrayList<String> al = new ArrayList<>();
            //String itemname;
            //for(int i=0 ; i<items.length ; i++){
              //  new RestThread(this).execute("http://192.168.1.2:8080/ListDB/webresources/entities.product/bubbles");
        //Intent resultIntent= new Intent(this, DisplayResults.class);
       // resultIntent.putExtra(MyActivity.RESULT_MESSAGE, templ.toString());
        //this.startActivity(resultIntent);                
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
       /* }
        else{
            LinearLayout layout = (LinearLayout) findViewById(R.id.linearlist);
            TextView textView = new TextView(this);
            textView.setTextSize(40);
            textView.setText("Your list is empty");
            layout.addView(textView);
        } */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);
        return true;
    }

    //Just for testing the menu. Does nothing for now
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    // Handle item selection
    switch (item.getItemId()) {
        case R.id.mylist:
            Intent intent = new Intent(this, MyListActivity.class);
            startActivity(intent);
            return true;
        case R.id.allprod:
            intent = new Intent(this, ShowProductsActivity.class);
            startActivity(intent);
            return true;
        case R.id.settings:
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
    
}
