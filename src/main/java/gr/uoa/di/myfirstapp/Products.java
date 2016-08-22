/*
 * This is the default product
 */
package gr.uoa.di.myfirstapp;

import java.util.ArrayList;

/**
 *
 * @author mark9
 */
class Products {
// Fields are not correct yet
    private int pprice;
    private String pname;
    private ArrayList<Sellers> psellers;
    
    public int getPprice() {
        return pprice;
    }

    public void setPprice(int pprice) {
        this.pprice = pprice;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public ArrayList<Sellers> getPsellers() {
        return psellers;
    }

    public void setPsellers(ArrayList<Sellers> psellers) {
        this.psellers = psellers;
    }

}
