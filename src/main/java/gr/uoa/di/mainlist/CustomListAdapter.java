/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.uoa.di.mainlist;

import gr.uoa.di.modelproducts.Products;
import android.content.Context;
import static android.content.Context.MODE_APPEND;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import gr.uoa.di.R;
import gr.uoa.di.modelproducts.ShoppingList;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author mark9
 */
class CustomListAdapter extends BaseAdapter{
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private ArrayList<Products> mProducts = new ArrayList<Products>();
    private String file = "mylist.txt";
    
    public CustomListAdapter(Context context){
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    
    @Override
    public int getCount() {
        return mProducts.size();
    }

    @Override
    public Object getItem(int position) {
        return mProducts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View itemView;
        ArrayList<Float> showprice;
        Float tempprice;
        
       itemView = mLayoutInflater.inflate(R.layout.row_list_item, null);
       TextView titlet = (TextView) itemView.findViewById(R.id.mylistTitle);
       TextView pricet = (TextView) itemView.findViewById(R.id.mylistPrice);
       final Button removelist = (Button) itemView.findViewById(R.id.removelist);
       final Button showsels = (Button) itemView.findViewById(R.id.showsellers);
       String title = mProducts.get(position).getProdname();
       titlet.setText(title);
       showprice = mProducts.get(position).getProdprice();
       tempprice = showprice.get(0);
       for(int i=1 ; i<showprice.size() ; i++){
           if(showprice.get(i) < tempprice){
               tempprice = showprice.get(i);
           }
       }
       String price = String.valueOf(tempprice);
       pricet.setText("Lowest price found: E" + price + "\n(Price may be unavailable near you)");
       removelist.setOnClickListener(new OnClickListener() {            
            @Override
            public void onClick(View v) {
                ShoppingList shl = new ShoppingList();
                if(shl.deletefromfile(String.valueOf(mProducts.get(position).getProdnum()), mContext) == true){
                    Toast.makeText(mContext, mProducts.get(position).getProdname() + " has been removed from the list.", Toast.LENGTH_LONG).show();
                    removelist.setEnabled(false);
                    removelist.setText("Removed");
                }
                else{
                    Toast.makeText(mContext, "Error. Product could not be removed from the list.", Toast.LENGTH_LONG).show();
                }
            }
        }); 
       return itemView;
    }
    
    public void upDateEntries(ArrayList<Products> alpd){
        mProducts = alpd;
        notifyDataSetChanged();
    }
    
}