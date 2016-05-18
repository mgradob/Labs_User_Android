package com.itesm.labs.labsuser.app.admin.views.dialogs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.app.bases.BaseDialogFragment;
import com.itesm.labs.labsuser.app.commons.contracts.IDialogContract;
import com.mgb.labsapi.clients.CategoryClient;
import com.mgb.labsapi.models.Category;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.client.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by mgradob on 5/8/16.
 */
public class AddCategoryDialogFragment extends BaseDialogFragment {


    private static final String TAG = AddCategoryDialogFragment.class.getSimpleName();

    @Bind(R.id.add_category_name)
    EditText addCategoryName;
    @Bind(R.id.add_category_cancel)
    Button addCategoryCancel;
    @Bind(R.id.add_category_done)
    Button addCategoryDone;

    @Inject
    CategoryClient mCategoryClient;

    private Category category;

    private IDialogContract contract;

    public void setContract(IDialogContract contract) {
        this.contract = contract;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment_add_category, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getDialog().setTitle(R.string.add_category_title);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.add_category_cancel, R.id.add_category_done})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_category_cancel:
                dismiss();
                break;
            case R.id.add_category_done:
                String categoryName = addCategoryName.getText().toString();
                if (categoryName.isEmpty()){
                    Toast.makeText(mContext, "Datos Incompletos", Toast.LENGTH_LONG)
                            .show();
                    break;
                }

                category = new Category.Builder()
                        .setName(categoryName)
                        .build();

                postNewCategory();
                break;
        }
    }

    private void postNewCategory() {
        mCategoryClient.postNewCategory(mLabsPreferences.getToken(), mLabsPreferences.getLabLink(), category)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response>() {
                    @Override
                    public void onStart() {
                        Log.i(TAG, "onStart: task post category.");
                    }

                    @Override
                    public void onCompleted() {
                        Log.i(TAG, "onCompleted: task post category.");

                        contract.onActionSuccess();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: task post category", e);

                        contract.onActionFailed();
                    }

                    @Override
                    public void onNext(Response response) {

                    }
                });
    }
}
