/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.uoa.di.mainlist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import gr.uoa.di.R;
import gr.uoa.di.google.maps.Constants;
import gr.uoa.di.mainproducts.SettingsActivity;
import gr.uoa.di.mainproducts.ShowProductsActivity;

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

        CustomListAdapter adapter = new CustomListAdapter(this);
        ListView listView = (ListView) findViewById(R.id.mobile_list);
        listView.setEmptyView(findViewById(R.id.anemptylist));
        listView.setAdapter(adapter);
        LoadListRest llr = new LoadListRest(this, adapter);
        llr.execute(Constants.LOAD_PRODUCTS_API);
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
