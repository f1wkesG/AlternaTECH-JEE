/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import jpa.Product;


@Named(value = "cartBean")
@Dependent
public class CartBean {
    private List<Product> products = new ArrayList<Product>();

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
    
    public void addToCart(Product p){
        if(p != null){
            products.add(p);
        }
    }
    public int numInCart(){
        return products.size();
    }
    /**
     * Creates a new instance of CartBean
     */
    public CartBean() {
    }
    
}
