package com.cibersons.app2727.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cibersons.app2727.App2727;
import com.cibersons.app2727.R;
import com.cibersons.app2727.beans.Transaccion.Transaccion;
import com.cibersons.app2727.beans.Transaccion.TransaccionResponse;
import com.cibersons.app2727.comm.ApiImpl;
import com.cibersons.app2727.comm.CommReq;
import com.cibersons.app2727.utils.Utils;
import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Manuel on 8/19/2015.
 */
public class HistorialFragment extends RootFragment implements SwipeRefreshLayout.OnRefreshListener {

    private static HistorialFragment instance;

    SwipeRefreshLayout swipeLayout;
    private CardView cardView;
    private RecyclerView recyclerView;
    private TransaccionResponse transaccionResponse;


    public static HistorialFragment newInstance(String title) {
        if (instance == null) {
            instance = new HistorialFragment();
            Bundle args = new Bundle();
//            args.putString(ARG_FRAGMENT_TITLE, title);
            instance.setArguments(args);
        }
        return instance;
    }

    public HistorialFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_historial, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        setupRecyclerView(recyclerView);
        cardView = (CardView) rootView.findViewById(R.id.card_view);
        swipeLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(this);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        onGetHistorial();
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
    }


    @Override
    public void onResume() {
        super.onResume();

    }

    public void onGetHistorial() {
        String accion = CommReq.GET_USER;
        String appId = "";
        String celular = "0984000000";
        String cedula = "44444444";
        String operadora = "pytgo";
        String userAutent = "CnsgUser";
        String passAutent = "123456";

        ApiImpl postOkHttp = new ApiImpl();
        String transactionJsonHistorial = postOkHttp.getTransaction("historial");
        App2727.Logger.i("Mensaje enviado = " + transactionJsonHistorial);


        if (Utils.haveNetworkConnection(getActivity().getApplicationContext())) {
            try {
                postOkHttp.post(CommReq.BASE_URL, transactionJsonHistorial, new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        App2727.Logger.e(e.getMessage());
                    }

                    @Override
                    public void onResponse(Response response) throws IOException {
                        String responseStr = response.body().string();
                        App2727.Logger.i(responseStr);
                        try {
                            JSONObject jsonObject = new JSONObject(responseStr);
                            Gson gson = new Gson();

                            transaccionResponse = gson.fromJson(String.valueOf(jsonObject), TransaccionResponse.class);

                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    recyclerView.setAdapter(new HistorialRecyclerViewAdapter(getActivity(), transaccionResponse.getData().getTransaccion()));
                                    swipeLayout.setRefreshing(false);

                                }
                            });
                            Log.d("DEBUG", transaccionResponse.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                            App2727.Logger.e(e.getMessage());
                        }


                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
                App2727.Logger.e(e.getMessage());
            }
        } else {
            showDialogOk(CommReq.ERROR_CONEXION_TITLE, CommReq.ERROR_CONEXION_BODY);
        }

    }

    @Override
    public void onRefresh() {
        onGetHistorial();
    }


    public class HistorialRecyclerViewAdapter
            extends RecyclerView.Adapter<HistorialRecyclerViewAdapter.ViewHolder> {

        private final TypedValue mTypedValue = new TypedValue();
        private int mBackground;
        private List<Transaccion> mValues;

        public class ViewHolder extends RecyclerView.ViewHolder {
            public Transaccion mBoundString;

            public final View mView;
            public final TextView mTvMensaje;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mTvMensaje = (TextView) view.findViewById(R.id.tvMensaje);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mTvMensaje.getText();
            }
        }


        public HistorialRecyclerViewAdapter(Context context, List<Transaccion> items) {
            context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
            mBackground = mTypedValue.resourceId;
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.historial_list_item, parent, false);
            view.setBackgroundResource(mBackground);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mBoundString = mValues.get(position);
            holder.mTvMensaje.setText((mValues.get(position).getMensaje()));

        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }
    }
}
