/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.uoa.di.mainsellers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import gr.uoa.di.R;
import gr.uoa.di.modelproducts.Comments;
import java.util.ArrayList;

/**
 *
 * @author mark9
 */
class CustomCommentsAdapter extends BaseAdapter{
    private final Context mContext;
    private final LayoutInflater mLayoutInflater;
    private ArrayList<Comments> mComments = new ArrayList<Comments>();
    
    public CustomCommentsAdapter(Context context){
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    
    @Override
    public int getCount() {
        return mComments.size();
    }

    @Override
    public Object getItem(int position) {
        return mComments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View commentView;

       commentView = mLayoutInflater.inflate(R.layout.row_comments, null);
       TextView comname = (TextView) commentView.findViewById(R.id.commentName);
       TextView comrating = (TextView) commentView.findViewById(R.id.comrating);
       TextView comprodname = (TextView) commentView.findViewById(R.id.comprodname);
       TextView comprodprice = (TextView) commentView.findViewById(R.id.comprodprice);
       TextView comtext = (TextView) commentView.findViewById(R.id.comtext);
       comname.setText("Name: " + mComments.get(position).getComname());
       comrating.setText("Rating: " + mComments.get(position).getRating() + "/5");
       comprodname.setText("Item bought: " + mComments.get(position).getProdname());
       comprodprice.setText("It's real price was: " + mComments.get(position).getProdprice() + " Euro");
       comtext.setText("Comments:\n" + mComments.get(position).getComtext());
       
       return commentView;
    }
    
    public void upDateEntries(ArrayList<Comments> alcm){
        mComments = alcm;
        notifyDataSetChanged();
    }
    
}