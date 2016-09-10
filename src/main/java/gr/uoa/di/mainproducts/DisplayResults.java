/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.uoa.di.mainproducts;

import gr.uoa.di.modelproducts.ShoppingList;
import gr.uoa.di.modelproducts.Sellers;
import gr.uoa.di.modelproducts.Products;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;
import android.widget.TextView;
import gr.uoa.di.R;

/**
 *
 * @author mark9
 */
public class DisplayResults extends AppCompatActivity {

    /**
     * Called when the activity is first created.
     * For now it's a stub. It just displays the msg returned
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_results);
        Intent intent = getIntent();
        String message = intent.getStringExtra(MyActivity.RESULT_MESSAGE);
        TextView textView = new TextView(this);
        textView.setTextSize(40);
        textView.setText(message);
        textView.setTextIsSelectable(true);
        Products prod;
        Sellers sel;
        ShoppingList shl;

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.rescont);
        layout.addView(textView);     
    }
    
}
