package gr.uoa.di.mainproducts;

import gr.uoa.di.mainproducts.mainlist.MyListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import gr.uoa.di.R;

public class MyActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "gr.uoa.di.MESSAGE";
    public final static String RESULT_MESSAGE = "gr.uoa.di.RESULTMESSAGE";
    private String file = "mylist.txt";
    
//    //For displaying what user just wrote (as in tutorials)
//    public void sendMessage(View view) {
//        //Intent intent = new Intent(this, DisplayMessageActivity.class);
//        EditText editText = (EditText) findViewById(R.id.edit_message);
//        String message = editText.getText().toString();
//        //intent.putExtra(EXTRA_MESSAGE, message);
//        //startActivity(intent);
//        try {
//               FileOutputStream fOut = openFileOutput(file,Context.MODE_PRIVATE);
//               fOut.write(message.getBytes());
//               fOut.close();
//               Toast.makeText(getBaseContext(),"file saved",Toast.LENGTH_SHORT).show();
//            }
//            catch (Exception e) {
//
//               e.printStackTrace();
//            }
//    }


//    //For calling Restful service and displaying result with the next activity
//    public void searchMessage(View view){
//        new RestThread(this).execute("http://192.168.1.2:8080/CustomerDB/webresources/entities.customer/bubbles");
//    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

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

    //Just for testing the menu. Does nothing for now
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
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
}
