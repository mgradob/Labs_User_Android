package com.itesm.labs.labsuser.activities.rest.queries;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.itesm.labs.labsuser.activities.rest.models.CartItem;
import com.itesm.labs.labsuser.activities.rest.models.Category;
import com.itesm.labs.labsuser.activities.rest.models.Component;
import com.itesm.labs.labsuser.activities.rest.service.CategoryService;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by mgradob on 4/11/15.
 */
public class CategoryQuery {

    private String ENDPOINT;

    public CategoryQuery(String ENDPOINT) {
        this.ENDPOINT = ENDPOINT;
    }

    public Category getCategoryOf(CartItem cartItem) {
        Gson gson = new GsonBuilder()
                .create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(this.ENDPOINT)
                .setConverter(new GsonConverter(gson))
                .build();

        CategoryService service = restAdapter.create(CategoryService.class);

        Component component = new ComponentQuery(this.ENDPOINT).getComponent(cartItem.getComponentId());

        Category category = service.getCategory(component.getCategory());

        return category;
    }

    public Category getCategoryOf(int categoryId) {
        Gson gson = new GsonBuilder()
                .create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(this.ENDPOINT)
                .setConverter(new GsonConverter(gson))
                .build();

        CategoryService service = restAdapter.create(CategoryService.class);

        Category category = service.getCategory(categoryId);

        return category;
    }

    public Category getCategoryOf(Component component) {
        Gson gson = new GsonBuilder()
                .create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(this.ENDPOINT)
                .setConverter(new GsonConverter(gson))
                .build();

        CategoryService service = restAdapter.create(CategoryService.class);

        Category category = service.getCategory(component.getCategory());

        return category;
    }
}
