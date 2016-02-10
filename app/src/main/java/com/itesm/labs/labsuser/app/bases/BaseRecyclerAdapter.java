package com.itesm.labs.labsuser.app.bases;

import android.content.Context;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.itesm.labs.labsuser.app.application.LabsApp;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by mgradob on 11/19/15.
 * <p/>
 * Base class for RecyclerAdapters, which receive a generic object type and generic ViewHolder.
 */
public abstract class BaseRecyclerAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    @Inject
    public Context mContext;

    @Inject
    public AppGlobals mAppGlobals;

    public ArrayList<T> DATA = new ArrayList<>();

    private IOnClickListener mOnClickListener;

    public BaseRecyclerAdapter() {
        LabsApp.get().inject(this);
    }

    @Override
    public int getItemCount() {
        return DATA.size();
    }

    @Override
    public void onBindViewHolder(VH holder, final int position) {
        if (holder != null && mOnClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnClickListener.onItemClick(position);
                }
            });
        }
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @UiThread
    public void refresh(ArrayList<T> newData) {
        this.DATA = newData;
        notifyDataSetChanged();
    }

    // region OnClickListener
    public void setOnClickListener(IOnClickListener mOnClickListener) {
        this.mOnClickListener = mOnClickListener;
    }

    public interface IOnClickListener {
        void onItemClick(int position);
    }
    // endregion
}
