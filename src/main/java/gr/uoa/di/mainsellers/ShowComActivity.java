/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.uoa.di.mainsellers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import gr.uoa.di.R;
import gr.uoa.di.mainlist.MyListActivity;
import gr.uoa.di.mainproducts.SettingsActivity;
import gr.uoa.di.mainproducts.ShowProductsActivity;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author mark9
 */
public class ShowComActivity extends AppCompatActivity{
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_com);
        Intent intent = getIntent();
        String message = intent.getStringExtra("infoforcom");
        try {
            JSONObject jsonres = new JSONObject(message);
            String selname = jsonres.getString("selname");
            String selid = jsonres.getString("selid");
            TextView selnametext = (TextView) findViewById(R.id.selname);
            selnametext.setText(selname);
            CustomCommentsAdapter adapter = new CustomCommentsAdapter(this);
            ListView listView = (ListView) findViewById(R.id.comments_list);
            listView.setEmptyView(findViewById(R.id.emptycommentsview));
            listView.setAdapter(adapter);
            LoadCommentsRest lcr = new LoadCommentsRest(this, adapter, selid);
            lcr.execute("http://192.168.1.6:8080/ListDB/webresources/entities.comments/bubbles");
            
        } catch (JSONException ex) {
            Logger.getLogger(ShowComActivity.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);
        return true;
    }

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
            intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
}
