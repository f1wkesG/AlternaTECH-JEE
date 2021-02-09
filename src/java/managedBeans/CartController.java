/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import jpa.Product;
import ejb.CommandeFacade;


@ManagedBean(name = "cartController")
@SessionScoped
public class CartController {
    
    private List<CartItem> cart = new ArrayList<CartItem>();

    public List<CartItem> getCart() {
        return cart;
    }

    public void setCart(List<CartItem> cart) {
        this.cart = cart;
    }

    public int isExisting(Product p){
        for(int i = 0;i < this.cart.size(); i++){
            if(this.cart.get(i).getProduct().getId() == p.getId()){
                return i;
            }
        }
        return -1;
    }
    
    private void putCartInSession(List<CartItem> cart) {
        Map<String, Object> session = FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap();
        session.put("CART", cart);
    }
    
    private void putOrderIdInSession(int orderid) {
        Map<String, Object> session = FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap();
        session.put("ORDER_ID", orderid);
    }
    
    public void addToCart(Product p){
        int index = isExisting(p);
        if(index == -1){
            this.cart.add(new CartItem(p, 1));
            /*if(this.cart.size() == 1){
                putOrderIdInSession(getOrderId());
            }*/
            putCartInSession(cart);
        }else{
            int q = this.cart.get(index).getQuantity() + 1;
            this.cart.get(index).setQuantity(q);
        }
    }
    
    public double totalPrice(){
        double r = 0;
        for(int i = 0;i<cart.size();i++){
            r = r + (cart.get(i).getProduct().getPrice() * cart.get(i).getQuantity());
        }
        return r;
    }
    
    public void addQItem(int id){
        for(int i = 0;i < this.cart.size(); i++){
            if(this.cart.get(i).getProduct().getId() == id){
                int q = this.cart.get(i).getQuantity() + 1;
                this.cart.get(i).setQuantity(q);
            }
        }
        
    }
    
    public void removeQItem(int id){
        for(int i = 0;i < this.cart.size(); i++){
            if(this.cart.get(i).getProduct().getId() == id){
                int q = this.cart.get(i).getQuantity() - 1;
                this.cart.get(i).setQuantity(q);
            }
        }
    }
    
    public void removeItem(int id){
        for(int i = 0;i < this.cart.size(); i++){
            if(this.cart.get(i).getProduct().getId() == id){
                this.cart.remove(i);
            }
        }
    }
    
    public int numInCart(){
        return cart.size();
    }
    
    
}
