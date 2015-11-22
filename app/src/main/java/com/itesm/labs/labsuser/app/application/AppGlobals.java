package com.itesm.labs.labsuser.app.application;

import com.itesm.labs.labsuser.app.rest.models.Category;
import com.itesm.labs.labsuser.app.rest.models.History;
import com.itesm.labs.labsuser.app.rest.models.Laboratory;
import com.itesm.labs.labsuser.app.rest.models.User;
import com.itesm.labs.labsuser.app.rest.models.UserCart;

import java.util.ArrayList;

/**
 * Created by mgradob on 10/31/15.
 */
public class AppGlobals {

    private User user;
    private Laboratory laboratory;
    private String labLink;
    private UserCart userCart;
    private Category category;
    private ArrayList<History> history;

    public AppGlobals() {
        this.user = new User();
        this.laboratory = new Laboratory();
        this.labLink = "";
        this.userCart = new UserCart();
        this.category = new Category();
        this.history = new ArrayList<>();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Laboratory getLaboratory() {
        return laboratory;
    }

    public void setLaboratory(Laboratory laboratory) {
        this.laboratory = laboratory;
    }

    public String getLabLink() {
        return labLink;
    }

    public void setLabLink(String labLink) {
        this.labLink = labLink;
    }

    public UserCart getUserCart() {
        return userCart;
    }

    public void setUserCart(UserCart userCart) {
        this.userCart = userCart;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public ArrayList<History> getHistory() {
        return history;
    }

    public void setHistory(ArrayList<History> history) {
        this.history = history;
    }

    @Override
    public String toString() {
        return "AppGlobals{" +
                "user=" + user +
                ", laboratory=" + laboratory +
                ", labLink='" + labLink + '\'' +
                ", userCart=" + userCart +
                ", category=" + category +
                ", history=" + history +
                '}';
    }
}
