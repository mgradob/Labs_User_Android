package com.itesm.labs.labsuser.activities.fragments;


import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.itesm.labs.labsuser.R;
import com.itesm.labs.labsuser.activities.adapters.CartAdapter;
import com.itesm.labs.labsuser.activities.rest.models.Cart;
import com.itesm.labs.labsuser.activities.rest.models.CartItem;
import com.itesm.labs.labsuser.activities.rest.models.Component;
import com.itesm.labs.labsuser.activities.rest.queries.CartQuery;
import com.itesm.labs.labsuser.activities.rest.queries.ComponentQuery;

import java.util.ArrayList;

public class CartFragment extends Fragment {

    private CartFragInteractionListener cartFragInteractionListener;

    private ListView cartListView;
    private FloatingActionButton fab;
    private SwipeRefreshLayout refreshLayout;

    private String ENDPOINT;
    private String USER_ID;
    private Cart userCart;

    public String getENDPOINT() {
        return ENDPOINT;
    }

    public void setENDPOINT(String ENDPOINT) {
        this.ENDPOINT = ENDPOINT;
    }

    public String getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID;
    }

    public Cart getUserCart() {
        return userCart;
    }

    public void setUserCart(Cart userCart) {
        this.userCart = userCart;
    }

    public CartFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.fragment_cart_list_view_swipe_refresh);
        refreshLayout.setColorSchemeResources(R.color.material_blue, R.color.material_yellow, R.color.material_red);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshList();
            }
        });

        cartListView = (ListView) view.findViewById(R.id.fragment_cart_list_view);
        cartListView.setEmptyView(view.findViewById(R.id.fragment_cart_empty_list));

        fab = (FloatingActionButton) view.findViewById(R.id.fragment_cart_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postCart();
            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            cartFragInteractionListener = (CartFragInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement CartFragInteractionListener");
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if(isVisibleToUser){
            cartListView.setAdapter(new CartAdapter(getActivity().getApplication()
                    .getApplicationContext(), userCart));
        }
        else
            Log.d("CartFragment", "hidden");
    }

    private void postCart() {
        try {
            // Actualizar todos los items que haya actualmente en la base de datos, ya tenemos una
            // lista con todos los items nuevos en userCart.
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... params) {
                    CartQuery query = new CartQuery(ENDPOINT);
                    ArrayList<CartItem> tempItems = query.getCartOf(USER_ID);
                    for(CartItem item : tempItems){
                        query.deleteCart(item.getCartId());
                    }

                    for(CartItem item : userCart.getCartList()){
                        item.setStudentId(USER_ID);
                        item.setReady(false);
                        item.setCheckout(false);
                        query.postNewCartItem(item);
                    }

                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);

                    Snackbar.make(getView(), "Carrito agregado.", Snackbar.LENGTH_LONG)
                            .show();
                }
            }.execute();
        } catch (Exception ex) {
            Log.d("CART POST", "Error al agregar carrito " + ex.getMessage());
            Snackbar.make(getView(), "Error al agregar el carrito.", Snackbar.LENGTH_LONG)
                    .show();
        }
    }

    public void refreshList() {
        new AsyncTask<String, Void, ArrayList<CartItem>>() {
            @Override
            protected ArrayList<CartItem> doInBackground(String... params) {
                ArrayList<CartItem> tempList;
                try {
                    tempList = new CartQuery(params[0]).getCartOf(USER_ID);
                } catch (Exception ex) {
                    return null;
                }

                try {
                    for (CartItem item : tempList) {
                        Component tempComponent = new ComponentQuery(params[0])
                                .getComponent(item.getComponentId());
                        item.setComponentName(tempComponent.getName());
                        item.setComponentNote(tempComponent.getNote());
                    }
                } catch (Exception ex) {
                    return null;
                }

                return tempList;
            }

            @Override
            protected void onPostExecute(ArrayList<CartItem> cartItems) {
                super.onPostExecute(cartItems);

                userCart.setCartList(cartItems);

                cartListView.setAdapter(new CartAdapter(getActivity().getApplication()
                        .getApplicationContext(), userCart));

                refreshLayout.setRefreshing(false);
            }
        }.execute(ENDPOINT);
    }

    public interface CartFragInteractionListener {
        void refreshUserCart();
    }
}
