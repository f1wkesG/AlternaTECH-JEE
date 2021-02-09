/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import ejb.ProductFacade;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import jpa.Product;


@Named(value = "productBean")
@Dependent
public class productBean {
    
    @EJB
    private ProductFacade ProductEJB;
    private Product ProductEntity;
    private Product selectedProductEntity;
    
    @PostConstruct
    public void init() {
        ProductEntity = new Product();
        selectedProductEntity = new Product();
    }
    public Product getProductEntity() {
        return ProductEntity;
    }

    public void setProductEntity(Product ProductEntity) {
        this.ProductEntity = ProductEntity;
    }

    public Product getSelectedProductEntity() {
        return selectedProductEntity;
    }

    public void setSelectedProductEntity(Product selectedProductEntity) {
        this.selectedProductEntity = selectedProductEntity;
    }


    public List<Product> getProduct() {
        return ProductEJB.findAll();
    }
    public List<Product> search(String key) {
        return ProductEJB.findbyName(key);
    }
    
    public int numberSearch(String key) {
        return ProductEJB.findbyName(key).size();
    }
    public List<Product> search(String key, double minp, double maxp) {
        return ProductEJB.findbyName(key, minp, maxp);
    }

    
    public Product getProductById(int id){
        return ProductEJB.findbyId(id);
    }
    
    
    public List<Product> getProductByCat(int cat){
        return ProductEJB.findbyCat(cat);
    }


    
    public double getMaximum_price() {
        ProductFacade pf = new ProductFacade();
        return pf.maxPrice();
    }
    
    public int getNbrProduct(){
        return ProductEJB.nbrProduct();
    }    
    
    /**
     * Creates a new instance of productBean
     */
    public productBean() {
    }
    
}
