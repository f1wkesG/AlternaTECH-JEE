/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webServices.service;

import ejb.ProductFacade;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import jpa.Product;


@Path("/services")
@ApplicationPath("/webresources")
public class ApplicationConfig extends Application {

    @EJB
    private ProductFacade productEJB;
    
    @GET
    @Path("/product")
    public List<Product> getProduct(){
        return productEJB.findAll();
    }
    
    @GET
    @Path("/productjs")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Product> getProductJS(){
        return productEJB.findAll();
    }
    
    @GET
    @Path("/searchService")
    public List<Product> getSearchProduct(@QueryParam("prod") String name){
        return productEJB.findbyName(name);
    }
    
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(webServices.service.ApplicationConfig.class);
        resources.add(webServices.service.CategoryFacadeREST.class);
        resources.add(webServices.service.CommandeFacadeREST.class);
        resources.add(webServices.service.CommentFacadeREST.class);
        resources.add(webServices.service.ProductFacadeREST.class);
        resources.add(webServices.service.UserFacadeREST.class);
    }
    
}
