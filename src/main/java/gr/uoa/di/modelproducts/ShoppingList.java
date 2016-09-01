/*
 * This will be a class to handle the list the user makes
 */
package gr.uoa.di.modelproducts;

import android.content.Context;
import static android.content.Context.MODE_APPEND;
import static android.content.Context.MODE_PRIVATE;
import android.util.Log;
import android.widget.Toast;
import gr.uoa.di.mainproducts.CustomProductsAdapter;
import gr.uoa.di.modelproducts.Products;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mark9
 */
public class ShoppingList {
    private final static String FILE_NAME = "noodlemaplist.txt";
    
    public ArrayList<String> readfromfile(Context mCont){
        ArrayList<String> curlist = new ArrayList<>();
        String templ[];
        try{
               FileInputStream fin = mCont.openFileInput(FILE_NAME);
               int c;
               String temp="";
               
               while( (c = fin.read()) != -1){
                  temp = temp + Character.toString((char)c);
               }
               if(temp != ""){
                    templ = temp.split(" ");
                    curlist.addAll(Arrays.asList(templ));
                    return curlist;
               }
               else{
                   return null;
               }
            }
            catch(Exception ex){
                Log.e("gr.uoa.di.list","",ex);
                return null;
            }
    }
    
    public boolean writetofile(String addition, Context mContext){
        ArrayList<String> curlist = this.readfromfile(mContext);
        if((curlist == null)){
            try {
                    FileOutputStream fOut;
                    fOut = mContext.openFileOutput(FILE_NAME, MODE_PRIVATE);
                    fOut.write((addition + " ").getBytes());
                    fOut.close();
                    return true;
                } catch (IOException ex) {
                    Log.e("gr.uoa.di.io","",ex);
                    return false;
                }
        }
        else if (curlist.contains(addition)){
            return false;
        }
        else{
            try {
                    FileOutputStream fOut;
                    fOut = mContext.openFileOutput(FILE_NAME, MODE_APPEND);
                    fOut.write((addition + " ").getBytes());
                    fOut.close();
                    return true;
                } catch (IOException ex) {
                    Log.e("gr.uoa.di.io","",ex);
                    return false;
                }
        }
    }
    
    public boolean deletefromfile(String todelete, Context mContext){
        ArrayList<String> curlist = this.readfromfile(mContext);
        if((curlist == null)){
            return false;
        }
        else if (!curlist.contains(todelete)){
            return false;
        }
        else{
            curlist.remove(todelete);
            mContext.deleteFile(FILE_NAME);
            for(int i=0; i<curlist.size();i++){
                writetofile(curlist.get(i), mContext);
            }
            return true;
        }
    }
}
