/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBeans;

import ejb.CategoryFacade;
import ejb.ProductFacade;
import ejb.UserFacade;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import jpa.Category;
import jpa.User;


@Named(value = "categoryBean")
@Dependent
public class CategoryBean {
    @EJB
    private CategoryFacade CategoryEJB;
    private Category CategoryEntity;
    private Category selectedCategoryEntity;
    
    @PostConstruct
    public void init() {
        CategoryEntity = new Category();
        selectedCategoryEntity = new Category();
    }
    
    public CategoryBean() {
    }
    
    public List<Category> getAllCategory(){
        return CategoryEJB.findAll();
    } 
    
    public CategoryFacade getCategoryEJB() {
        return CategoryEJB;
    }

    public void setCategoryEJB(CategoryFacade CategoryEJB) {
        this.CategoryEJB = CategoryEJB;
    }


    public Category getCategoryEntity() {
        return CategoryEntity;
    }

    public void setCategoryEntity(Category CategoryEntity) {
        this.CategoryEntity = CategoryEntity;
    }

    public Category getSelectedCategoryEntity() {
        return selectedCategoryEntity;
    }

    public void setSelectedCategoryEntity(Category selectedCategoryEntity) {
        this.selectedCategoryEntity = selectedCategoryEntity;
    }
    
}
