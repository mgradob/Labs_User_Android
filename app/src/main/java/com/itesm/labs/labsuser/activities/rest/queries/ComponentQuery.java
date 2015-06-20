package com.itesm.labs.labsuser.activities.rest.queries;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.itesm.labs.labsuser.activities.rest.models.Component;
import com.itesm.labs.labsuser.activities.rest.service.ComponentService;

import java.util.ArrayList;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by mgradob on 4/11/15.
 */
public class ComponentQuery {

    private String ENDPOINT;

    public ComponentQuery(String ENDPOINT) {
        this.ENDPOINT = ENDPOINT;
    }

    public Component getComponent(int componentId) {
        try {
            Gson gson = new GsonBuilder()
                    .create();

            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(this.ENDPOINT)
                    .setConverter(new GsonConverter(gson))
                    .build();

            ComponentService service = restAdapter.create(ComponentService.class);

            Component component = service.getComponent(componentId);

            return component;
        } catch (Exception ex){
            return null;
        }
    }

    public ArrayList<Component> getComponents(String category) {
        try {
            Gson gson = new GsonBuilder()
                    .create();

            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(this.ENDPOINT)
                    .setConverter(new GsonConverter(gson))
                    .build();

            ComponentService service = restAdapter.create(ComponentService.class);

            ArrayList<Component> components = service.getComponents(category);

            return components;
        } catch (Exception ex){
            return null;
        }
    }

    public ArrayList<Component> getAllComponents() {
        try {
            Gson gson = new GsonBuilder()
                    .create();

            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(this.ENDPOINT)
                    .setConverter(new GsonConverter(gson))
                    .build();

            ComponentService service = restAdapter.create(ComponentService.class);

            ArrayList<Component> components = service.getAllComponents();

            return components;
        } catch (Exception ex){
            return null;
        }
    }
}
