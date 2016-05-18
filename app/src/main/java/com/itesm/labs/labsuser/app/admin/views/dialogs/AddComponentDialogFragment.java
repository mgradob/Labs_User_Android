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
import com.mgb.labsapi.clients.ComponentClient;
import com.mgb.labsapi.models.Category;
import com.mgb.labsapi.models.Component;

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
public class AddComponentDialogFragment extends BaseDialogFragment {

    private static final String TAG = AddComponentDialogFragment.class.getSimpleName();

    private static final String ARG_CATEGORY_ID = "category_id";
    @Bind(R.id.add_component_name)
    EditText addComponentName;
    @Bind(R.id.add_component_note)
    EditText addComponentNote;
    @Bind(R.id.add_component_total)
    EditText addComponentTotal;
    @Bind(R.id.add_component_available)
    EditText addComponentAvailable;
    @Bind(R.id.add_component_cancel)
    Button addComponentCancel;
    @Bind(R.id.add_component_done)
    Button addComponentDone;

    @Inject
    ComponentClient mComponentClient;

    private Category category;
    private Component component;

    private IDialogContract contract;

    public static AddComponentDialogFragment newInstance(Category category) {
        AddComponentDialogFragment fragment = new AddComponentDialogFragment();

        Bundle args = new Bundle();
        args.putInt(ARG_CATEGORY_ID, category.getId());

        fragment.setArguments(args);

        return fragment;
    }

    public void setContract(IDialogContract contract) {
        this.contract = contract;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment_add_component, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getDialog().setTitle(R.string.add_component_title);

        Bundle args = getArguments();
        category = new Category.Builder()
                .setId(args.getInt(ARG_CATEGORY_ID))
                .build();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.add_component_cancel, R.id.add_component_done})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_component_cancel:
                dismiss();
                break;
            case R.id.add_component_done:
                String name = addComponentName.getText().toString();
                String note = addComponentNote.getText().toString();
                int total = Integer.parseInt(addComponentTotal.getText().toString());
                int available = Integer.parseInt(addComponentAvailable.getText().toString());

                if (name.isEmpty()) {
                    Toast.makeText(mContext, "Datos incompletos", Toast.LENGTH_LONG)
                            .show();
                    break;
                }

                component = new Component.Builder()
                        .setCategory(category.getId())
                        .setName(name)
                        .setNote(note)
                        .setTotal(total)
                        .setAvailable(available)
                        .build();

                addComponent();

                break;
        }
    }

    private void addComponent() {
        mComponentClient.postComponent(mLabsPreferences.getToken(), mLabsPreferences.getLabLink(), component)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response>() {
                    @Override
                    public void onStart() {
                        Log.i(TAG, "onStart: task post component");
                    }

                    @Override
                    public void onCompleted() {
                        Log.i(TAG, "onCompleted: task post component");
                        contract.onActionSuccess();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: task post component", e);
                        contract.onActionFailed();
                    }

                    @Override
                    public void onNext(Response response) {

                    }
                });
    }
}
