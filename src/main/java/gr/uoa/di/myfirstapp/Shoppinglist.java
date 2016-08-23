/*
 * This will be a class to handle the list the user makes
 */
package gr.uoa.di.myfirstapp;

import java.util.ArrayList;

/**
 *
 * @author mark9
 */
class Shoppinglist {
    private ArrayList<Products> myprod = new ArrayList<>();

    public ArrayList<Products> getMyprod() {
        return myprod;
    }

    public void setMyprod(ArrayList<Products> myprod) {
        this.myprod = myprod;
    }
    
    
}
