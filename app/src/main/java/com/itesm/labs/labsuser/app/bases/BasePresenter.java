package com.itesm.labs.labsuser.app.bases;

/**
 * Created by mgradob on 2/9/16.
 */
public interface BasePresenter {

    void updateToolbar(int colorRes, String title);

    void showSnackbar(int stringRes);

    void showDialog(int titleResource, int contentResource);

    void dismissDialog();
}
