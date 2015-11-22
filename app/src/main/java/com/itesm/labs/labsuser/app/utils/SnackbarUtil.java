package com.itesm.labs.labsuser.app.utils;

import android.support.design.widget.Snackbar;

/**
 * Created by mgradob on 11/21/15.
 */
public interface SnackbarUtil {
    void showSnackbar(String stringResource);

    void showSnackbarWithCallback(String stringResource, Snackbar.Callback callback);
}
