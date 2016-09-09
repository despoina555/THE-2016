/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.uoa.di.modelproducts;

/**
 *
 * @author mark9
 */
public class Comments {
    private String prodname;
    private int selid;
    private String rating;
    private String comname;
    private String prodprice;
    private String comtext;

    public String getProdname() {
        return prodname;
    }

    public void setProdname(String prodname) {
        this.prodname = prodname;
    }

    public int getSelid() {
        return selid;
    }

    public void setSelid(int selid) {
        this.selid = selid;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getComname() {
        return comname;
    }

    public void setComname(String comname) {
        this.comname = comname;
    }

    public String getProdprice() {
        return prodprice;
    }

    public void setProdprice(String prodprice) {
        this.prodprice = prodprice;
    }

    public String getComtext() {
        return comtext;
    }

    public void setComtext(String comtext) {
        this.comtext = comtext;
    }
    
    
}
