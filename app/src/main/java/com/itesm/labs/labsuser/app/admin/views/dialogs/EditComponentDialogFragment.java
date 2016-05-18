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
public class EditComponentDialogFragment extends BaseDialogFragment {

    public static final String ARG_COMPONENT_ID = "id";
    public static final String ARG_COMPONENT_NAME = "name";
    public static final String ARG_COMPONENT_NOTE = "note";
    public static final String ARG_COMPONENT_TOTAL = "total";
    public static final String ARG_COMPONENT_AVAILABLE = "available";

    private static final String TAG = EditComponentDialogFragment.class.getSimpleName();

    @Inject
    ComponentClient mComponentClient;

    @Bind(R.id.edit_component_name)
    EditText editComponentName;
    @Bind(R.id.edit_component_note)
    EditText editComponentNote;
    @Bind(R.id.edit_component_total)
    EditText editComponentTotal;
    @Bind(R.id.edit_component_available)
    EditText editComponentAvailable;
    @Bind(R.id.edit_component_cancel)
    Button editComponentCancel;
    @Bind(R.id.edit_component_done)
    Button editComponentDone;
    @Bind(R.id.edit_component_delete)
    Button editComponentDelete;

    private Category category;
    private Component component;

    private IDialogContract contract;

    public static EditComponentDialogFragment newInstance(Component component) {
        EditComponentDialogFragment fragment = new EditComponentDialogFragment();

        Bundle args = new Bundle();
        args.putInt(ARG_COMPONENT_ID, component.getId());
        args.putString(ARG_COMPONENT_NAME, component.getName());
        args.putString(ARG_COMPONENT_NOTE, component.getNote());
        args.putInt(ARG_COMPONENT_TOTAL, component.getTotal());
        args.putInt(ARG_COMPONENT_AVAILABLE, component.getAvailable());

        fragment.setArguments(args);

        return fragment;
    }

    public void setContract(IDialogContract contract) {
        this.contract = contract;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment_edit_component, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getDialog().setTitle(R.string.edit_component_title);

        Bundle args = getArguments();
        component = new Component.Builder()
                .setId(args.getInt(ARG_COMPONENT_ID))
                .setName(args.getString(ARG_COMPONENT_NAME))
                .setNote(args.getString(ARG_COMPONENT_NOTE))
                .setTotal(args.getInt(ARG_COMPONENT_TOTAL))
                .setAvailable(args.getInt(ARG_COMPONENT_AVAILABLE))
                .build();

        editComponentName.setText(component.getName());
        editComponentNote.setText(component.getNote());
        editComponentTotal.setText("" + component.getTotal());
        editComponentAvailable.setText("" + component.getAvailable());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.edit_component_cancel, R.id.edit_component_done, R.id.edit_component_delete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.edit_component_cancel:
                dismiss();
                break;
            case R.id.edit_component_done:
                String name = editComponentName.getText().toString();
                String note = editComponentNote.getText().toString();
                int total = Integer.parseInt(editComponentTotal.getText().toString());
                int available = Integer.parseInt(editComponentAvailable.getText().toString());

                if (name.isEmpty() || total < 0 || available < 0) {
                    Toast.makeText(mContext, "Datos insuficientes o inválidos", Toast.LENGTH_LONG)
                            .show();
                    break;
                }

                component = new Component.Builder()
                        .setId(component.getId())
                        .setName(name)
                        .setNote(note)
                        .setTotal(total)
                        .setAvailable(available)
                        .build();

                editComponent();

                break;
            case R.id.edit_component_delete:
                break;
        }
    }

    private void editComponent() {
        mComponentClient.editComponent(mLabsPreferences.getToken(), mLabsPreferences.getLabLink(), component.getId(), component)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Component>() {
                    @Override
                    public void onStart() {
                        Log.i(TAG, "onStart: task edit component");
                    }

                    @Override
                    public void onCompleted() {
                        Log.i(TAG, "onCompleted: task edit component");
                        contract.onActionSuccess();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: task edit component", e);
                        contract.onActionFailed();
                    }

                    @Override
                    public void onNext(Component component) {

                    }
                });
    }

    private void deleteComponent() {
        mComponentClient.deleteComponent(mLabsPreferences.getToken(), mLabsPreferences.getLabLink(), component.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response>() {
                    @Override
                    public void onStart() {
                        Log.i(TAG, "onStart: task delete component");
                    }

                    @Override
                    public void onCompleted() {
                        Log.i(TAG, "onCompleted: task delete component");
                        contract.onActionSuccess();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: task delete component", e);
                        contract.onActionFailed();
                    }

                    @Override
                    public void onNext(Response response) {

                    }
                });
    }
}
