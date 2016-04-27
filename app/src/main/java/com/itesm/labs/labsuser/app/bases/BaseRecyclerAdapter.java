package com.itesm.labs.labsuser.app.bases;

import android.content.Context;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.itesm.labs.labsuser.app.application.LabsApp;
import com.squareup.otto.Bus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by mgradob on 11/19/15.
 * <p>
 * Base class for RecyclerAdapters, which receive a generic object type and generic ViewHolder.
 */
public abstract class BaseRecyclerAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    @Inject
    protected Context mContext;
    @Inject
    protected Bus mEventBus;

    protected List<T> DATA = new ArrayList<>();

    public BaseRecyclerAdapter() {
        LabsApp.get().inject(this);
    }

    @Override
    public int getItemCount() {
        return DATA.size();
    }

    @Override
    public void onBindViewHolder(VH holder, final int position) {
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @UiThread
    public void refresh(List<T> newData) {
        this.DATA = newData;
        notifyDataSetChanged();
    }
}
