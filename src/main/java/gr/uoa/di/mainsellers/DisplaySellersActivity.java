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
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.gson.Gson;
import gr.uoa.di.R;
import gr.uoa.di.mainproducts.MyActivity;
import gr.uoa.di.modelproducts.Products;
import gr.uoa.di.modelproducts.Sellers;

/**
 *
 * @author mark9
 */
public class DisplaySellersActivity extends AppCompatActivity{
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_sellers);
        Intent intent = getIntent();
        String message = intent.getStringExtra("gr.uoa.di.prod_to_show");
        Gson gson = new Gson();
        Products proddisp = gson.fromJson(message, Products.class);
        TextView prodnametext = (TextView) findViewById(R.id.pdname);
        prodnametext.setText(proddisp.getProdname());
        
        CustomSellersAdapter adapter = new CustomSellersAdapter(this);
        ListView listView = (ListView) findViewById(R.id.seller_list);
        listView.setAdapter(adapter);
        LoadSellersRest lsr = new LoadSellersRest(this, adapter, proddisp);
        lsr.execute("http://192.168.1.6:8080/ListDB/webresources/entities.sellers/bubbles");
    }
}
