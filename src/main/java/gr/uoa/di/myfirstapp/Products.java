/*
 * This is the default product
 */
package gr.uoa.di.myfirstapp;

import java.util.ArrayList;

/**
 *
 * @author mark9
 */
public class Products {
// Fields are not correct yet
    private int prodid;
    private int prodnum;
    private String prodname;
    private int sellerid;
    private float prodprice;

    public int getProdid() {
        return prodid;
    }

    public void setProdid(int prodid) {
        this.prodid = prodid;
    }

    public int getProdnum() {
        return prodnum;
    }

    public void setProdnum(int prodnum) {
        this.prodnum = prodnum;
    }

    public String getProdname() {
        return prodname;
    }

    public void setProdname(String prodname) {
        this.prodname = prodname;
    }

    public int getSellerid() {
        return sellerid;
    }

    public void setSellerid(int sellerid) {
        this.sellerid = sellerid;
    }

    public float getProdprice() {
        return prodprice;
    }

    public void setProdprice(float prodprice) {
        this.prodprice = prodprice;
    }
    

}
