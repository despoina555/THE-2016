package gr.uoa.di.mainproducts;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import gr.uoa.di.R;

/**
 * Created by Despina on 27/8/2016.
 */
public class ShowProductsActivity extends AppCompatActivity {
    
    private String file = "mylist.txt";
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_products);

        
        CustomProductsAdapter adapter = new CustomProductsAdapter(this);
        ListView listView = (ListView) findViewById(R.id.show_products_list);
        listView.setAdapter(adapter);
        LoadProductsRest llr = new LoadProductsRest(this, adapter);
        llr.execute("http://192.168.1.9:8080/ListDB/webresources/entities.product/bubbles");
        
    }



}

