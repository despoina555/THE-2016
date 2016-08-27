package gr.uoa.di.myfirstapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class SearchProductsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_products);

//        Intent intent = getIntent();
//        ViewGroup layout = (ViewGroup) findViewById(R.id.activity_search_products);
////        layout.addView(searchstores);

    }


    public void searchMessage(View view) {
        EditText editText = (EditText) findViewById(R.id.search_products_message);
        String message = editText.getText().toString();

        Intent intent = new Intent(this, ShowProductsActivity.class);
        startActivity(intent);

        String serverURL = "http://androidexample.com/media/webservice/JsonReturn.php";
        new FindProducts(this).execute(serverURL);
    }
}
