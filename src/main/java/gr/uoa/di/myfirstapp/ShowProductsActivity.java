package gr.uoa.di.myfirstapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Despina on 27/8/2016.
 */
public class ShowProductsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_products);

        Intent intent = getIntent();
        String message = intent.getStringExtra(MyActivity.RESULT_MESSAGE);
        TextView textView = new TextView(this);
        textView.setTextSize(20);
        textView.setHighlightColor(123);
        textView.setText(message);
//        Products prod;
//        Sellers sel;
//        Shoppinglist shl;
//
        LinearLayout layout = (LinearLayout) findViewById(R.id.activity_show_products);
        layout.addView(textView);
    }



}

