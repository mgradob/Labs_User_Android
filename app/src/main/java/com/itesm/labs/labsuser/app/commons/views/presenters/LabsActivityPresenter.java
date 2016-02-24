package com.itesm.labs.labsuser.app.commons.views.presenters;

import android.util.Log;

import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.app.bases.BaseActivityPresenter;
import com.itesm.labs.labsuser.app.commons.adapters.models.ItemLaboratory;
import com.itesm.labs.labsuser.app.commons.views.activities.LabsActivity;
import com.mgb.labsapi.clients.LaboratoryClient;
import com.mgb.labsapi.models.Laboratory;

import java.util.ArrayList;
import java.util.Random;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by mgradob on 2/21/16.
 */
public class LabsActivityPresenter extends BaseActivityPresenter {

    private static final String TAG = LabsActivityPresenter.class.getSimpleName();

    @Inject
    LaboratoryClient mLaboratoryClient;

    private LabsActivity mView;

    private ArrayList<ItemLaboratory> labsList = new ArrayList<>();

    private int[] colors;
    private Random random;

    public LabsActivityPresenter(LabsActivity mView) {
        super();
        this.mView = mView;
        colors = mView.getResources().getIntArray(R.array.material_colors);
        random = new Random();
    }

    //region UI
    public ItemLaboratory getLabItem(int position) {
        return labsList.get(position);
    }
    //endregion

    //region Api calls
    public void getAllowedLabs() {
        mLaboratoryClient.getLaboratories(mLabsPreferences.getToken())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArrayList<Laboratory>>() {
                    @Override
                    public void onStart() {
                        Log.d(TAG, "Task get allowed labs started");

                        labsList.clear();
                    }

                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "Task get allowed labs completed");

                        mView.updateInfo(labsList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Task get allowed labs error: " + e.getMessage());

                        mView.showError();
                    }

                    @Override
                    public void onNext(ArrayList<Laboratory> laboratories) {
                        if (laboratories == null)
                            throw new NullPointerException("Error getting labs, " + laboratories);


                        for (Laboratory laboratory : laboratories)
                            labsList.add(new ItemLaboratory.Builder()
                                            .setLaboratory(laboratory)
                                            .setColorResource(colors[random.nextInt(colors.length - 1)])
                                            .build()
                            );
                    }
                });
    }
    //endregion
}
