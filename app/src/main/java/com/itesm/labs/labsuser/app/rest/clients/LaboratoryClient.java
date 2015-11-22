package com.itesm.labs.labsuser.app.rest.clients;

import com.itesm.labs.labsuser.app.rest.models.Laboratory;
import com.itesm.labs.labsuser.app.rest.services.LaboratoryService;

import java.util.ArrayList;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by mgradob on 10/28/15.
 */
public class LaboratoryClient {

    private LaboratoryService mLaboratoryService;

    public LaboratoryClient(LaboratoryService mLaboratoryService) {
        this.mLaboratoryService = mLaboratoryService;
    }

    public Observable<ArrayList<Laboratory>> getUserLabs(final String token) {
        return Observable.create(new Observable.OnSubscribe<ArrayList<Laboratory>>() {
            @Override
            public void call(Subscriber<? super ArrayList<Laboratory>> subscriber) {
                ArrayList<Laboratory> labs = mLaboratoryService.getLaboratories(token);

                subscriber.onNext(labs);
                subscriber.onCompleted();
            }
        });
    }

    public Observable<Laboratory> getSpecificLab(final String token, final String labName) {
        return Observable.create(new Observable.OnSubscribe<Laboratory>() {
            @Override
            public void call(Subscriber<? super Laboratory> subscriber) {
                Laboratory lab = mLaboratoryService.getLaboratoryFromUrl(token, labName);

                subscriber.onNext(lab);
                subscriber.onCompleted();
            }
        });
    }

    public Observable<ArrayList<Laboratory>> getSpecificLabs(final String token, final ArrayList<String> labs) {
        return Observable.create(new Observable.OnSubscribe<ArrayList<Laboratory>>() {
            @Override
            public void call(Subscriber<? super ArrayList<Laboratory>> subscriber) {
                ArrayList<Laboratory> labsList = new ArrayList<Laboratory>();

                for (String labName : labs) {
                    String[] params = labName.split("/");

                    labsList.add(mLaboratoryService.getLaboratoryFromUrl(token, params[params.length - 1]));
                }

                subscriber.onNext(labsList);
                subscriber.onCompleted();
            }
        });
    }
}
