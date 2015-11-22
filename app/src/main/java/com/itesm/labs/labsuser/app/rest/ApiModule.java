package com.itesm.labs.labsuser.app.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.itesm.labs.labsuser.app.rest.clients.CartClient;
import com.itesm.labs.labsuser.app.rest.clients.CategoryClient;
import com.itesm.labs.labsuser.app.rest.clients.ComponentClient;
import com.itesm.labs.labsuser.app.rest.clients.LaboratoryClient;
import com.itesm.labs.labsuser.app.rest.clients.RecordClient;
import com.itesm.labs.labsuser.app.rest.clients.UserClient;
import com.itesm.labs.labsuser.app.rest.services.CartService;
import com.itesm.labs.labsuser.app.rest.services.CategoryService;
import com.itesm.labs.labsuser.app.rest.services.ComponentService;
import com.itesm.labs.labsuser.app.rest.services.LaboratoryService;
import com.itesm.labs.labsuser.app.rest.services.RecordService;
import com.itesm.labs.labsuser.app.rest.services.UserService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by mgradob on 10/25/15.
 */
@Module(
        complete = false,
        library = true,
        injects = {
                UserClient.class,
                LaboratoryClient.class,
                CategoryClient.class,
                CartClient.class,
                ComponentClient.class,
                RecordClient.class
        })
public class ApiModule {

    private final String endpoint;

    public ApiModule(final String endpoint) {
        super();
        this.endpoint = endpoint;
    }

    @Provides
    @Singleton
    Gson providesGson() {
        return new GsonBuilder()
                .setPrettyPrinting()
                .create();
    }

    @Provides
    @Singleton
    RestAdapter providesAdapter(Gson gson) {
        return new RestAdapter.Builder()
                .setEndpoint(endpoint)
                .setConverter(new GsonConverter(gson))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
    }

    @Provides
    @Singleton
    UserService providesUserService(RestAdapter restAdapter) {
        return restAdapter.create(UserService.class);
    }

    @Provides
    @Singleton
    LaboratoryService providesLaboratoryService(RestAdapter restAdapter) {
        return restAdapter.create(LaboratoryService.class);
    }

    @Provides
    @Singleton
    CategoryService providesCategoryService(RestAdapter restAdapter) {
        return restAdapter.create(CategoryService.class);
    }

    @Provides
    @Singleton
    ComponentService providesComponentService(RestAdapter restAdapter) {
        return restAdapter.create(ComponentService.class);
    }

    @Provides
    @Singleton
    CartService providesCartService(RestAdapter restAdapter) {
        return restAdapter.create(CartService.class);
    }

    @Provides
    @Singleton
    RecordService providesRecordService(RestAdapter restAdapter) {
        return restAdapter.create(RecordService.class);
    }
}
