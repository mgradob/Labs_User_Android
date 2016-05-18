package com.itesm.labs.labsuser.app.commons.views.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.app.bases.BaseDialogFragment;
import com.itesm.labs.labsuser.app.commons.contracts.IDialogContract;
import com.mgb.labsapi.clients.UserClient;
import com.mgb.labsapi.models.ChangePassword;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.client.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by mgradob on 5/3/16.
 */
public class ChangePasswordDialogFragment extends BaseDialogFragment {

    private static final String TAG = ChangePasswordDialogFragment.class.getSimpleName();

    @Bind(R.id.change_password_old)
    EditText changePasswordOld;
    @Bind(R.id.change_password_new)
    EditText changePasswordNew;
    @Bind(R.id.change_password_new_verify)
    EditText changePasswordNewVerify;
    @Bind(R.id.change_password_cancel)
    Button changePasswordCancel;
    @Bind(R.id.change_password_done)
    Button changePasswordDone;
    @Bind(R.id.change_password_old_til)
    TextInputLayout changePasswordOldTil;
    @Bind(R.id.change_password_new_til)
    TextInputLayout changePasswordNewTil;
    @Bind(R.id.change_password_new_verify_til)
    TextInputLayout changePasswordNewVerifyTil;

    @Inject
    UserClient mUserClient;

    private IDialogContract dialogContract;

    public void setDialogContract(IDialogContract dialogContract) {
        this.dialogContract = dialogContract;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment_change_password, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getDialog().setTitle(getString(R.string.reset_password_title));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.change_password_cancel, R.id.change_password_done})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.change_password_cancel:
                dismiss();
                break;
            case R.id.change_password_done:
                validatePassword();
                break;
        }
    }

    private void validatePassword() {
        String oldPass = changePasswordOld.getText().toString();
        String newPass = changePasswordNew.getText().toString();
        String newPassVerify = changePasswordNewVerify.getText().toString();

        if (oldPass.isEmpty()) {
            changePasswordOldTil.setError("Requerido");
            return;
        } else changePasswordOldTil.setError(null);

        if (newPass.isEmpty()) {
            changePasswordNewTil.setError("Requerido");
            return;
        } else changePasswordNewTil.setError(null);

        if (newPassVerify.isEmpty()) {
            changePasswordNewVerifyTil.setError("Requerido");
            return;
        } else if (!newPass.equals(newPassVerify)) {
            changePasswordNewVerifyTil.setError("Los passwords no coinciden");
            return;
        } else changePasswordNewVerifyTil.setError(null);

        mUserClient.changeUserPass(mLabsPreferences.getToken(), new ChangePassword(oldPass, newPass))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response>() {
                    @Override
                    public void onStart() {
                        Log.d(TAG, "onStart: task change password");
                    }

                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted: task change password");
                        dialogContract.onActionSuccess();
                        dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: task change password", e);

                        dialogContract.onActionFailed();
                    }

                    @Override
                    public void onNext(Response response) {

                    }
                });
    }
}
