package gr.uoa.di.mainproducts;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.maps.model.LatLng;

import gr.uoa.di.R;
import gr.uoa.di.google.maps.Constants;
import gr.uoa.di.mainlist.MyListActivity;


public class MyActivity extends AppCompatActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        //ask permition to see users loc

    }

    public void sendMessage(View view) {
        Intent intent = new Intent(this, ShowProductsActivity.class);
        startActivity(intent);
    }
    
    //Menu with 3 buttons (see main/res/menu/mainmenu.xml
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
