/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import ejb.CommandeFacade;
import ejb.ProductFacade;
import ejb.UserFacade;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import jpa.User;
import jpa.Commande;
import jpa.Product;


@Named(value = "commandeBean")
@ManagedBean
@RequestScoped
public class CommandeBean {
    @EJB
    private CommandeFacade CommandeEJB;
    @EJB
    private ProductFacade ProductEJB;
    
    private Commande CommandeEntity;
    private static final long serialVersionUID = 7765876811740798583L;
    
    private Object getObjectFromSession(String key) {
        return FacesContext.getCurrentInstance().getExternalContext()
                .getSessionMap().get(key);
    }
    
    public double totalPrice(List<CartItem> cart){
        double r = 0;
        for(int i = 0;i<cart.size();i++){
            r = r + (cart.get(i).getProduct().getPrice() * cart.get(i).getQuantity());
        }
        return r;
    }
    
    public String getDetails(List<CartItem> cart){
        String r = "";
        r = cart.get(0).getProduct().getId() + "," + cart.get(0).getQuantity();
        for(int i = 1;i<cart.size();i++){
            r = r + ";" + cart.get(i).getProduct().getId() + "," + cart.get(i).getQuantity();
        }
        return r;
    }
    
    
    
    public void checkout(){
        Date date = new Date();
            
        User u = (User) getObjectFromSession("LOGGEDIN_USER");
        List<CartItem> cart = (List<CartItem>) getObjectFromSession("CART");
        
        int orderId = CommandeEJB.nbrProduct() + 1;
        int userid = u.getId();
        String details = getDetails(cart);
        double totalPrice = totalPrice(cart);
        long time = date.getTime();

        System.out.println("1");
        System.out.println(orderId);
        System.out.println(userid);
        System.out.println(details);
        System.out.println(totalPrice);
        CommandeEJB.payer(orderId, userid, details, totalPrice);
        
    }
    
    public int getOrderId(){
        CommandeFacade c = new CommandeFacade();
        return c.nbrProduct();
    }
    
    
    public List<List<Integer>> getCommandeItemList(String s){
        List<List<Integer>> items = new ArrayList<List<Integer>>();
        items.add(new ArrayList<Integer>());
        items.add(new ArrayList<Integer>());
        String[] commandeItem = s.split(";");
        String[] commandeSubItem = new String[2];
        for(int i=0;i<commandeItem.length;i++){
            commandeSubItem = commandeItem[i].split(",");
            // adding product id to first list in items
            items.get(0).add(Integer.parseInt(commandeSubItem[0]));
            // adding product quantity to seconde list in items
            items.get(1).add(Integer.parseInt(commandeSubItem[1]));
        }
        List<CartItem> cart = new ArrayList<CartItem>();
        return items;
    }
    
    
    public List<CartItem> getCartItems(Commande commande){
        List<List<Integer>> items = new ArrayList<List<Integer>>();
        items.add(new ArrayList<Integer>());
        items.add(new ArrayList<Integer>());
        System.out.println("commande details : " + commande.getDetail());
        List<String> commandeItem = new ArrayList<String>();
        commandeItem = Arrays.asList("1,2;5,1".split(";"));
        String[] commandeSubItem = new String[2];
        for(int i=0;i<commandeItem.size();i++){
            commandeSubItem = commandeItem.get(i).split(",");
            items.get(0).add(Integer.parseInt(commandeSubItem[0]));
            items.get(1).add(Integer.parseInt(commandeSubItem[1]));
        }
        List<CartItem> cart = new ArrayList<CartItem>();
        CartItem c;
        for(int i=0;i<items.size();i++){
            c = new CartItem();
            c.setProduct(ProductEJB.findbyId(items.get(0).get(i)));
            c.setQuantity(items.get(1).get(i));
            cart.add(c);
        }
        return cart;
    }
    
    
    public List<Commande> myCommandes(){
        User u = (User) getObjectFromSession("LOGGEDIN_USER");
        return CommandeEJB.getMyCommandes(u.getId());
    }
    
    public List<Integer> getTopProducts(){
        List<Integer> result = new ArrayList<Integer>();
        String r = CommandeEJB.getAllProductDetails();
        String[] commandeItem = r.split(";");
        String[] commandeSubItem = new String[2];
        int[] allElements = new int[commandeItem.length];
        for(int i=0;i<commandeItem.length;i++){
            commandeSubItem = commandeItem[i].split(",");
            // adding product id to first list in items
            allElements[i] = Integer.parseInt(commandeSubItem[0]);
        }
        
        result = sortArrayElementsByFrequency(allElements);
        return result;
    }
    
    private static List<Integer> sortArrayElementsByFrequency(int[] inputArray)
    {
        Map<Integer, Integer> elementCountMap = new LinkedHashMap<>();
        for (int i = 0; i < inputArray.length; i++) 
        {
            if (elementCountMap.containsKey(inputArray[i])){
                elementCountMap.put(inputArray[i], elementCountMap.get(inputArray[i])+1);
            }
            else{
                elementCountMap.put(inputArray[i], 1);
            }
        }
        List<Integer> sortedElements = new ArrayList<>();

        elementCountMap.entrySet()
                        .stream()
                        .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                        .forEach(entry -> { 
                            for(int i = 1; i <= entry.getValue(); i++) 
                                sortedElements.add(entry.getKey());
                                });
        List<Integer> result = new ArrayList<Integer>();
        for(int i = 0;i < sortedElements.size();i++){
            if(!result.contains(sortedElements.get(i))){
                result.add(sortedElements.get(i));
            }
        }
        return result;
    }

    public CommandeBean() {
    }
    
}
