/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.uoa.di.mainlist;

import gr.uoa.di.modelproducts.Products;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import java.util.ArrayList;
import gr.uoa.di.R;
import gr.uoa.di.mainsellers.DisplaySellersActivity;
import gr.uoa.di.modelproducts.ShoppingList;
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
        Float tempprice, totalprice=0.0f;
        
       itemView = mLayoutInflater.inflate(R.layout.row_list_item, null);
       TextView titlet = (TextView) itemView.findViewById(R.id.mylistTitle);
       TextView pricet = (TextView) itemView.findViewById(R.id.mylistPrice);
       final Button removelist = (Button) itemView.findViewById(R.id.removelist);
       final Button showsels = (Button) itemView.findViewById(R.id.showsellers);
       if((mProducts.get(position).getProdid() == -1) && (mProducts.get(position).getProdname().equals("Total"))){
           for(int i=0 ; i<position ; i++){
               showprice = mProducts.get(i).getProdprice();
               tempprice = showprice.get(0);
               for(int j=1 ; j<showprice.size() ; j++){
                    if(showprice.get(j) < tempprice){
                        tempprice = showprice.get(j);
                    }
                }
                totalprice += tempprice;
           }
           titlet.setText("Total Price:");
           String price = String.valueOf(totalprice);
           pricet.setText(price + " Euro");
           removelist.setVisibility(itemView.GONE);
           showsels.setVisibility(itemView.GONE);
       }
       else{
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
            showsels.setOnClickListener(new OnClickListener() {            
                 @Override
                 public void onClick(View v) {
                     Gson curprod = new Gson();
                     String jsonprod = curprod.toJson(mProducts.get(position));
                     Intent resultIntent= new Intent(mContext, DisplaySellersActivity.class);
                     resultIntent.putExtra("gr.uoa.di.prod_to_show", jsonprod);
                     mContext.startActivity(resultIntent);  
                 }
             }); 
       }
       return itemView;
    }
    
    public void upDateEntries(ArrayList<Products> alpd){
        mProducts = alpd;
        notifyDataSetChanged();
    }
    
}
