package gr.uoa.di.mainproducts;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import gr.uoa.di.R;
import gr.uoa.di.google.maps.Constants;
import gr.uoa.di.mainlist.MyListActivity;

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
        LoadProductsRest lpr = new LoadProductsRest(this, adapter);
        lpr.execute(Constants.LOAD_PRODUCTS_API);
        
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

