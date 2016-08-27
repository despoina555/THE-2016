/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.uoa.di.myfirstapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 *
 * @author mark9
 */
public class CustomListAdapter extends BaseAdapter{
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private ArrayList<Products> mProducts = new ArrayList<Products>();
    
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
    public View getView(int position, View convertView, ViewGroup parent) {
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
       pricet.setText("Lowest price found: E" + price);
       return itemView;
    }
    
    public void upDateEntries(ArrayList<Products> alpd){
        mProducts = alpd;
        notifyDataSetChanged();
    }
    
}
