package com.mgb.labsapi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mgb.labsapi.clients.CartClient;
import com.mgb.labsapi.clients.CategoryClient;
import com.mgb.labsapi.clients.ComponentClient;
import com.mgb.labsapi.clients.HistoryClient;
import com.mgb.labsapi.clients.LaboratoryClient;
import com.mgb.labsapi.clients.UserClient;
import com.mgb.labsapi.models.LabsRequestInterceptor;

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
                .setErrorHandler(new LabsErrorHandler())
                .setRequestInterceptor(new LabsRequestInterceptor())
                .build();
    }

    @Provides
    @Singleton
    UserClient providesUserClient(RestAdapter restAdapter) {
        return restAdapter.create(UserClient.class);
    }

    @Provides
    @Singleton
    LaboratoryClient providesLaboratoryClient(RestAdapter restAdapter) {
        return restAdapter.create(LaboratoryClient.class);
    }

    @Provides
    @Singleton
    CategoryClient providesCategoryClient(RestAdapter restAdapter) {
        return restAdapter.create(CategoryClient.class);
    }

    @Provides
    @Singleton
    ComponentClient providesComponentClient(RestAdapter restAdapter) {
        return restAdapter.create(ComponentClient.class);
    }

    @Provides
    @Singleton
    CartClient providesCartClient(RestAdapter restAdapter) {
        return restAdapter.create(CartClient.class);
    }

    @Provides
    @Singleton
    HistoryClient providesHistoryClient(RestAdapter restAdapter) {
        return restAdapter.create(HistoryClient.class);
    }
}
