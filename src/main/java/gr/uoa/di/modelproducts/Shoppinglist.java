/*
 * This will be a class to handle the list the user makes
 */
package gr.uoa.di.modelproducts;

import gr.uoa.di.modelproducts.Products;
import java.util.ArrayList;

/**
 *
 * @author mark9
 */
public class Shoppinglist {
    private ArrayList<Products> myprod = new ArrayList<>();

    public ArrayList<Products> getMyprod() {
        return myprod;
    }

    public void setMyprod(ArrayList<Products> myprod) {
        this.myprod = myprod;
    }
    
    
}
