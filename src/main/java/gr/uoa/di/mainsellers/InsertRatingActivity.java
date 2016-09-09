/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.uoa.di.mainsellers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;
import gr.uoa.di.R;
import gr.uoa.di.mainproducts.DisplayResults;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author mark9
 */
public class InsertRatingActivity extends AppCompatActivity {
    int sellerid;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_insert_rating);
        Intent intent = getIntent();
        String message = intent.getStringExtra("infoforcom");
        JSONObject jsonResponse;
        try {
            jsonResponse = new JSONObject(message);
            sellerid = Integer.valueOf(jsonResponse.getString("selid"));
            
        } catch (JSONException ex) {
            Logger.getLogger(InsertRatingActivity.class.getName()).log(Level.SEVERE, null, ex);
        }
        //intent = new Intent(this, DisplayResults.class);
        //intent.putExtra("gr.uoa.di.RESULTMESSAGE", String.valueOf(sellerid));
        //startActivity(intent);
    }
    
    public void submitcomment(View view){
        EditText nameedit = (EditText) findViewById(R.id.Nameedit);
        String comname = nameedit.getText().toString();
        EditText prodnameedit = (EditText) findViewById(R.id.ProdNameedit);
        String prodname = prodnameedit.getText().toString();
        EditText realpriceedit = (EditText) findViewById(R.id.realPrice);
        String realprice = realpriceedit.getText().toString();
        RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        String rating = String.valueOf(ratingBar.getRating());
        EditText comedittext = (EditText) findViewById(R.id.comment_edit);
        String comtext = comedittext.getText().toString();
        JSONObject jsontogo = new JSONObject();
        try {
            //jsontogo.put("comid", "2");
            jsontogo.put("comname", comname);
            jsontogo.put("comtext", comtext);
            jsontogo.put("prodname", prodname);
            jsontogo.put("prodprice", realprice);
            jsontogo.put("rating", rating);
            jsontogo.put("sellerid", sellerid);
            CountCommentRest scr = new CountCommentRest(this, jsontogo.toString());
            scr.execute("http://192.168.1.6:8080/ListDB/webresources/entities.comments/count");
            Toast.makeText(this, "Your rating has been added.", Toast.LENGTH_LONG).show();
        } catch (JSONException ex) {
            Logger.getLogger(InsertRatingActivity.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if ( v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent( event );
    }
}
