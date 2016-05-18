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
public class EditCategoryDialogFragment extends BaseDialogFragment {

    public static final String ARG_CATEGORY_ID = "id";
    public static final String ARG_CATEGORY_NAME = "name";

    private static final String TAG = EditCategoryDialogFragment.class.getSimpleName();

    @Bind(R.id.edit_category_name)
    EditText editCategoryName;
    @Bind(R.id.edit_category_cancel)
    Button editCategoryCancel;
    @Bind(R.id.edit_category_done)
    Button editCategoryDone;
    @Bind(R.id.edit_category_delete)
    Button editCategoryDelete;

    @Inject
    CategoryClient mCategoryClient;

    private Category category;

    private IDialogContract contract;

    public static EditCategoryDialogFragment newInstance(Category category) {
        EditCategoryDialogFragment fragment = new EditCategoryDialogFragment();

        Bundle args = new Bundle();
        args.putInt(ARG_CATEGORY_ID, category.getId());
        args.putString(ARG_CATEGORY_NAME, category.getName());

        fragment.setArguments(args);

        return fragment;
    }

    public void setContract(IDialogContract contract) {
        this.contract = contract;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment_edit_category, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getDialog().setTitle(R.string.edit_category_title);

        Bundle args = getArguments();

        category = new Category.Builder()
                .setId(args.getInt(ARG_CATEGORY_ID))
                .setName(args.getString(ARG_CATEGORY_NAME))
                .build();

        editCategoryName.setText(category.getName());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.edit_category_cancel, R.id.edit_category_done, R.id.edit_category_delete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.edit_category_cancel:
                dismiss();
                break;
            case R.id.edit_category_done:
                String categoryName = editCategoryName.getText().toString();

                if (categoryName.isEmpty()) {
                    Toast.makeText(mContext, "Datos incompletos", Toast.LENGTH_LONG)
                            .show();
                    break;
                }

                category = new Category.Builder()
                        .setId(category.getId())
                        .setName(categoryName)
                        .build();

                editCategory();

                break;

            case R.id.edit_category_delete:
                deleteCategory();
                break;
        }
    }

    private void editCategory() {
        mCategoryClient.editCategory(mLabsPreferences.getToken(), mLabsPreferences.getLabLink(), category.getId(), category)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response>() {
                    @Override
                    public void onStart() {
                        Log.i(TAG, "onStart: task edit category");
                    }

                    @Override
                    public void onCompleted() {
                        Log.i(TAG, "onCompleted: task edit category");

                        contract.onActionSuccess();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: task edit category", e);

                        contract.onActionFailed();
                    }

                    @Override
                    public void onNext(Response response) {

                    }
                });
    }

    private void deleteCategory() {
        mCategoryClient.deleteCategory(mLabsPreferences.getToken(), mLabsPreferences.getLabLink(), category.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response>() {
                    @Override
                    public void onStart() {
                        Log.i(TAG, "onStart: task delete category");
                    }

                    @Override
                    public void onCompleted() {
                        Log.i(TAG, "onCompleted: task delete category");
                        contract.onActionSuccess();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: task delete category", e);
                        contract.onActionFailed();
                    }

                    @Override
                    public void onNext(Response response) {

                    }
                });
    }
}
