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
        /*if (convertView == null){
            itemView = (RelativeLayout) mLayoutInflater.inflate(R.layout.activity_list_display, parent, false);
        }
        else{
            itemView = (RelativeLayout) convertView;
        }*/
       itemView = mLayoutInflater.inflate(R.layout.row_list_item, null);
       TextView titlet = (TextView) itemView.findViewById(R.id.listTitle);
       TextView pricet = (TextView) itemView.findViewById(R.id.listPrice);
       Button addlist = (Button) itemView.findViewById(R.id.addlist);
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
       pricet.setText("Lowest price found: E" + price + " (Price may be unavailable near you)");
       addlist.setOnClickListener(new OnClickListener() {            
            @Override
            public void onClick(View v) {
                try {
                    FileOutputStream fOut;
                    fOut = mContext.openFileOutput(file, MODE_APPEND);
                    fOut.write((mProducts.get(position).getProdnum() + " ").getBytes());
                    fOut.close();
                    Toast.makeText(mContext, mProducts.get(position).getProdname() + " has been added to list.", Toast.LENGTH_LONG).show();
                } catch (IOException ex) {
                    Logger.getLogger(CustomListAdapter.class.getName()).log(Level.SEVERE, null, ex);
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
