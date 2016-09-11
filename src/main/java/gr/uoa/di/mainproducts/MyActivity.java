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
        case R.id.maps:
           /* intent = new Intent(android.content.Ic
           ntent.ACTION_VIEW,
            Uri.parse("http://maps.google.com/maps?daddr=37.93295,23.70155"));
            startActivity(intent);
            return true;*/
            Intent intentMap = new Intent(this,gr.uoa.di.google.maps.MapsActivity.class);
            intentMap.putExtra(Constants.LOCATION_COORDINATES_EXTRA,new LatLng(37.978966, 23.762810));
            intentMap.putExtra(Constants.SELLER_NAME,"Public");
            startActivity(intentMap);
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
}
