/*
* This is a class to handle sellers for a product
*/
package gr.uoa.di.modelproducts;

import java.util.ArrayList;

/**
 *
 * @author mark9
 */

public class Sellers {
    
    private int id;
    private String name;
    private String address;
    private String email;
    private int phone;
    private Float price;
    private Float lat;
    private Float longt;

    public Float getLat() {
        return lat;
    }

    public void setLat(Float lat) {
        this.lat = lat;
    }

    public Float getLongt() {
        return longt;
    }

    public void setLongt(Float longt) {
        this.longt = longt;
    }
    
    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }
    
}
