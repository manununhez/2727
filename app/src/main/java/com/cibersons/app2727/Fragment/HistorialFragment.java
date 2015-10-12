package com.cibersons.app2727.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

/**
 * Created by Manuel on 8/19/2015.
 */
public class HistorialFragment extends RootFragment {

    private static HistorialFragment instance;

    private RecyclerView recyclerView;
    private TransaccionResponse transaccionResponse;
    private String ci;

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
//        swipeLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_container);
//        swipeLayout.setOnRefreshListener(this);

//        onGetHistorial();
        return rootView;
    }


    private void setupRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
    }

    @Override
    public void onResume() {
        super.onResume();
        onGetHistorial();
    }

    public void onGetHistorial() {

        final String appID = Utils.getSharedPreferences(getActivity()).getString(getString(R.string.token), getString(R.string.default_value));
        String ci = Utils.getSharedPreferences(getActivity()).getString(getString(R.string.save_ci), getString(R.string.default_value));
        String userAutentication = Utils.getSharedPreferences(getActivity()).getString(getString(R.string.user_autentication), getString(R.string.default_value));

        App2727.Logger.i("CI = " + ci);
        if (!ci.equals(getString(R.string.default_value)) && userAutentication.equals(CommReq.STATUS_OK)){

            String transactionJsonHistorial = ApiImpl.getTransaction("historial", appID);
            App2727.Logger.i("Mensaje enviado = " + transactionJsonHistorial);


            if (Utils.haveNetworkConnection(getActivity().getApplicationContext())) {
                try {
//                    final ProgressDialog progressDialog = Utils.getProgressDialog(getActivity(), "Cargando...", "Aguarde un momento por favor!");
//                    final int totalProgressTime = 100;
//
//                    final Thread t = new Thread() {
//                        @Override
//                        public void run() {
//
//                            try {
//                                sleep(2000);
//                                progressDialog.show();
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
//
//
//                        }
//
//                    };
//                    t.start();

                    new ApiImpl().post(CommReq.BASE_URL, transactionJsonHistorial, new Callback() {
                        @Override
                        public void onFailure(Request request, final IOException e) {
                            App2727.Logger.e(e.getMessage());
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
//                                    progressDialog.dismiss();
                                    Utils.customAlertDialogWithOk(getActivity(), "Error!", e.getMessage()).show();

                                }
                            });
//                        getActivity().runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                swipeLayout.setRefreshing(false);
//                            }
//                        });
                        }

                        @Override
                        public void onResponse(Response response) throws IOException {
                            String responseStr = response.body().string();
                            App2727.Logger.i(responseStr);
                            try {
//                                progressDialog.dismiss();
                                App2727.Logger.i("REcibido = "+responseStr);
                                JSONObject jsonObject = new JSONObject(responseStr);
                                Gson gson = new Gson();

                                transaccionResponse = gson.fromJson(String.valueOf(jsonObject), TransaccionResponse.class);

                                if(transaccionResponse.getStatus().equals(CommReq.STATUS_OK)) {
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            recyclerView.setAdapter(new HistorialRecyclerViewAdapter(getActivity(), transaccionResponse.getData().getTransaccion()));
//                                    swipeLayout.setRefreshing(false);

                                        }
                                    });
                                }else if(transaccionResponse.getStatus().equals(CommReq.STATUS_ERROR)){
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Utils.customAlertDialogWithOk(getActivity(), CommReq.STATUS_ERROR,transaccionResponse.getData().getTransaccion().get(0).getMensaje()).show();

                                        }
                                    });
                                }
                                Log.d("DEBUG", transaccionResponse.toString());
                            } catch (final JSONException e) {
//                                progressDialog.dismiss();
                                e.printStackTrace();
                                App2727.Logger.e(e.getMessage());
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Utils.customAlertDialogWithOk(getActivity(), "Error!", e.getMessage()).show();

                                    }
                                });

//                            showDialogOk("Error!", e.getMessage());
//                            getActivity().runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    swipeLayout.setRefreshing(false);
//                                }
//                            });
                            }


                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();

                    App2727.Logger.e(e.getMessage());
//                showDialogOk("Error!", e.getMessage());
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        swipeLayout.setRefreshing(false);
//                    }
//                });
                }
            } else {
//            getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    swipeLayout.setRefreshing(false);
//                }
//            });
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Utils.customAlertDialogWithOk(getActivity(), CommReq.ERROR_CONEXION_TITLE, CommReq.ERROR_CONEXION_BODY).show();

                    }
                });

//            showDialogOk(CommReq.ERROR_CONEXION_TITLE, CommReq.ERROR_CONEXION_BODY);
            }
        }

    }

//    @Override
//    public void onRefresh() {
//        onGetHistorial();
//    }


    public class HistorialRecyclerViewAdapter
            extends RecyclerView.Adapter<HistorialRecyclerViewAdapter.ViewHolder> {

        private final TypedValue mTypedValue = new TypedValue();
        private int mBackground;
        private List<Transaccion> mValues;

        public class ViewHolder extends RecyclerView.ViewHolder {
            public Transaccion mBoundString;

            public final View mView;
            public final TextView mTvMensaje;
            public final TextView mTvFecha;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mTvMensaje = (TextView) view.findViewById(R.id.tvMensaje);
                mTvFecha = (TextView) view.findViewById(R.id.tvFecha);
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
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            holder.mBoundString = mValues.get(position);
            if (position == 0) {
                ((LinearLayout) holder.mView.findViewById(R.id.llCardView)).setBackgroundResource(R.drawable.roundedrectangle_last);
//                holder.mView.animate().scaleX(2.5f).scaleY(2.5f).setDuration(2000);
//                holder.mView.animate().scaleX(1.0f).scaleY(1.0f).setDuration(2000);
            }else{
                ((LinearLayout) holder.mView.findViewById(R.id.llCardView)).setBackgroundResource(R.drawable.roundedrectangle);

            }

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity().getApplicationContext(), String.valueOf(position), Toast.LENGTH_LONG).show();

                }
            });
//                 }
            holder.mTvMensaje.setText((mValues.get(position).getMensaje()));
            holder.mTvFecha.setText((mValues.get(position).getFecha()));

        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }
    }
}
