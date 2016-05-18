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
import com.mgb.labsapi.ApiConstants;
import com.mgb.labsapi.clients.UserClient;
import com.mgb.labsapi.models.NewUser;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.client.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by mgradob on 5/5/16.
 */
public class AddUserDialogFragment extends BaseDialogFragment {

    private static final String TAG = AddUserDialogFragment.class.getSimpleName();

    @Bind(R.id.add_user_mat)
    EditText addUserMat;
    @Bind(R.id.add_user_name)
    EditText addUserName;
    @Bind(R.id.add_user_ap_1)
    EditText addUserAp1;
    @Bind(R.id.add_user_ap_2)
    EditText addUserAp2;
    @Bind(R.id.add_user_career)
    EditText addUserCareer;
    @Bind(R.id.add_user_done)
    Button addUserDone;

    @Inject
    UserClient mUserClient;

    IDialogContract callback;

    public void setCallback(IDialogContract callback) {
        this.callback = callback;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment_add_user, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getDialog().setTitle(R.string.dialog_create_user_title);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.add_user_done)
    public void onClick() {
        String mat = addUserMat.getText().toString();
        String name = addUserName.getText().toString();
        String ap1 = addUserAp1.getText().toString();
        String ap2 = addUserAp2.getText().toString();
        String career = addUserCareer.getText().toString();
        String password = ApiConstants.DEFAULT_PASSWORD;
        String mail = addUserMat.getText().toString() + "@itesm.mx";
        ArrayList<String> labs = mLabsPreferences.getUserAllowedLabs();

        if (mat.isEmpty() || name.isEmpty() || ap1.isEmpty() || ap2.isEmpty() || career.isEmpty() || password.isEmpty() || mail.isEmpty()) {
            Toast.makeText(mContext, "Datos incompletos", Toast.LENGTH_LONG)
                    .show();
            return;
        }

        postNewUser(new NewUser(mat, name, ap1, ap2, career, password, mail, labs));
    }

    private void postNewUser(NewUser user) {
        mUserClient.postNewUser(mLabsPreferences.getToken(), user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response>() {
                    @Override
                    public void onStart() {
                        Log.i(TAG, "onStart: task add user");
                    }

                    @Override
                    public void onCompleted() {
                        Log.i(TAG, "onCompleted: task add user");

                        callback.onActionSuccess();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: task add user", e);

                        callback.onActionFailed();
                    }

                    @Override
                    public void onNext(Response response) {

                    }
                });
    }
}
