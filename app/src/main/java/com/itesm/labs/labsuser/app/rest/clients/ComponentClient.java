package com.itesm.labs.labsuser.app.rest.clients;

import com.itesm.labs.labsuser.app.rest.models.Component;
import com.itesm.labs.labsuser.app.rest.services.ComponentService;

import java.util.ArrayList;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by mgradob on 10/29/15.
 */
public class ComponentClient {

    private ComponentService mComponentService;

    public ComponentClient(ComponentService mComponentService) {
        this.mComponentService = mComponentService;
    }

    public Observable<ArrayList<Component>> getAllComponents(final String token, final String lab) {
        return Observable.create(new Observable.OnSubscribe<ArrayList<Component>>() {
            @Override
            public void call(Subscriber<? super ArrayList<Component>> subscriber) {
                ArrayList<Component> components = mComponentService.getAllComponents(token, lab);

                subscriber.onNext(components);
                subscriber.onCompleted();
            }
        });
    }

    public Observable<ArrayList<Component>> getAllCategoryComponents(final String token, final String lab, final int category) {
        return Observable.create(new Observable.OnSubscribe<ArrayList<Component>>() {
            @Override
            public void call(Subscriber<? super ArrayList<Component>> subscriber) {
                ArrayList<Component> components = mComponentService.getComponents(token, lab, category);

                subscriber.onNext(components);
                subscriber.onCompleted();
            }
        });
    }

    public Observable<Component> getSpecificComponent(final String token, final String lab, final int idComponent) {
        return Observable.create(new Observable.OnSubscribe<Component>() {
            @Override
            public void call(Subscriber<? super Component> subscriber) {
                Component component = mComponentService.getComponent(token, lab, idComponent);

                subscriber.onNext(component);
                subscriber.onCompleted();
            }
        });
    }
}
