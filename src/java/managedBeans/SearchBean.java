/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import ejb.ProductFacade;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;


@Named(value = "searchBean")
@RequestScoped
public class SearchBean {
    private String key; 
    private double min_price = 0;
    private double max_price = 10000;

    public double getMin_price() {
        return min_price;
    }

    public void setMin_price(double min_price) {
        this.min_price = min_price;
    }

    public double getMax_price() {
        return max_price;
    }

    public void setMax_price(double max_price) {
        this.max_price = max_price;
    }
    
    
    
    
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
    /**
     * Creates a new instance of SearchBean
     */
    public SearchBean() {
    }
    
}
