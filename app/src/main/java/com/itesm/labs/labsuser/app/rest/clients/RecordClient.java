package com.itesm.labs.labsuser.app.rest.clients;

import com.itesm.labs.labsuser.app.rest.models.Category;
import com.itesm.labs.labsuser.app.rest.models.Component;
import com.itesm.labs.labsuser.app.rest.models.History;
import com.itesm.labs.labsuser.app.rest.services.CategoryService;
import com.itesm.labs.labsuser.app.rest.services.ComponentService;
import com.itesm.labs.labsuser.app.rest.services.RecordService;

import java.util.ArrayList;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by mgradob on 10/31/15.
 */
public class RecordClient {

    private ComponentService mComponentService;

    private CategoryService mCategoryService;

    private RecordService mRecordService;

    public RecordClient(ComponentService mComponentService, CategoryService mCategoryService, RecordService mRecordService) {
        this.mComponentService = mComponentService;
        this.mCategoryService = mCategoryService;
        this.mRecordService = mRecordService;
    }

    public Observable<ArrayList<History>> getRecordOf(final String token, final String lab, final String studentId) {
        return Observable.create(new Observable.OnSubscribe<ArrayList<History>>() {
            @Override
            public void call(Subscriber<? super ArrayList<History>> subscriber) {
                ArrayList<History> histories = mRecordService.getRecordOf(token, lab, studentId);

                for (History item : histories) {
                    Component tempComponent = mComponentService.getComponent(token, lab, item.getComponentId());

                    Category tempCategory = mCategoryService.getCategory(token, lab, tempComponent.getCategory());

                    item.setCategoryName(tempCategory.getName());

                    String tempString = "";
                    if (tempComponent.getName() != null) tempString += tempComponent.getName();
                    else tempString += "";
                    tempString += " ";
                    if (tempComponent.getNote() != null) tempString += tempComponent.getNote();
                    else tempString += "";
                    item.setComponentNameNote(tempString);
                }

                subscriber.onNext(histories);
                subscriber.onCompleted();
            }
        });
    }
}
