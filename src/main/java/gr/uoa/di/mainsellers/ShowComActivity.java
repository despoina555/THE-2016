/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.uoa.di.mainsellers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;
import gr.uoa.di.R;
import gr.uoa.di.mainproducts.DisplayResults;
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
}
