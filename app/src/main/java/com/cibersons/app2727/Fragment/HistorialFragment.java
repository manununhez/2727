package com.cibersons.app2727.fragment;

import android.app.Activity;
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
import com.cibersons.app2727.R;
import com.cibersons.app2727.beans.Transaccion.Transaccion;
import com.cibersons.app2727.beans.Transaccion.TransaccionResponse;
import com.cibersons.app2727.comm.ApiImpl;
import com.cibersons.app2727.comm.CommReq;
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
public class HistorialFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static HistorialFragment instance;

    SwipeRefreshLayout swipeLayout;
    // OnHistorialListener mCallback;
    CardView cardView;
    ArrayList<String> historialList;

    @Override
    public void onRefresh() {
        fetchTimelineAsync(0);

    }

    public void fetchTimelineAsync(int page) {
        /*client.getHomeTimeline(0, new JsonHttpResponseHandler() {
            public void onSuccess(JSONArray json) {
                // Remember to CLEAR OUT old items before appending in the new ones
                adapter.clear();
                // ...the data has come back, add new items to your adapter...
                adapter.addAll(...);
                // Now we call setRefreshing(false) to signal refresh has finished
                swipeContainer.setRefreshing(false);
            }

            public void onFailure(Throwable e) {
                Log.d("DEBUG", "Fetch timeline error: " + e.toString());
            }
        });*/
    }


    // Container Activity must implement this interface
  /*  public interface OnHistorialListener {
        public boolean onGetHistorial();
    }*/

  /*  @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception

        Activity activity = null;

        if (context instanceof Activity) {
            activity = (Activity) context;
        }
        try {
            mCallback = (OnHistorialListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHistorialListener");
        }
    }*/

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
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        setupRecyclerView(recyclerView);
        cardView = (CardView) rootView.findViewById(R.id.card_view);
        swipeLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(this);
        //swipeLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW);
        /*swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override public void run() {
                        swipeLayout.setRefreshing(false);
                    }
                }, 5000);
            }
        });*/
        /*swipeLayout.setColorScheme(android.R.color.holo_green_dark,
                android.R.color.holo_red_dark,
                android.R.color.holo_blue_dark,
                android.R.color.holo_orange_dark);*/
        return rootView;
    }

    private void setupRecyclerView(RecyclerView recyclerView){
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        //recyclerView.setAdapter(new HistorialRecyclerViewAdapter(getActivity(), historialList));
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onGetHistorial();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    public void onGetHistorial() {
        Log.i("2727", "onGETHISTORIAL");
        String accion = CommReq.GET_USER;
        String appId = "";
        String celular = "0984000000";
        String cedula = "44444444";
        String operadora = "pytgo";
        String userAutent = "CnsgUser";
        String passAutent = "123456";

        ApiImpl example = new ApiImpl();
        String userJson = example.getUserJson();

        /*Log.i("DEBUG","userJson enviado = "+userJson);
        try {
            example.post(CommReq.BASE_URL, userJson, new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {

                }

                @Override
                public void onResponse(Response response) throws IOException {
                    String responseStr = response.body().string();
                    Log.i("DEBUG", "SUCESS!!");
                    Log.i("DEBUG", responseStr);
                    Log.i("DEBUG", response.message());
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        String transactionJsonHistorial = example.getTransaction("historial");
        Log.i("DEBUG", "transactionJsonHistorial enviado = " + transactionJsonHistorial);
        try {
            example.post(CommReq.BASE_URL, transactionJsonHistorial, new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    String responseStr = response.body().string();

                    Log.i("DEBUG", "SUCESS!!");
                    Log.i("DEBUG", responseStr);
                    //try {
                        String prueba = "{\"status\":\"OK\",\"cantidad\":\"10\",\"data\":{[{\"idTransaccion\": \"26\", \"mensaje\": \"CI 44444444 Su saldo es 896,192 Gs.-\"},{\"idTransaccion\": \"25\", \"mensaje\": \"CI 44444444 Su saldo es 896,192 Gs.-\"},{\"idTransaccion\": \"24\", \"mensaje\": \"CI 44444444 Su saldo es 896,192 Gs.-\"},{\"idTransaccion\": \"23\", \"mensaje\": \"CI 44444444 Su saldo es 896,192 Gs.-\"},{\"idTransaccion\": \"22\", \"mensaje\": \"CI 44444444 Su saldo es 896,192 Gs.-\"},{\"idTransaccion\": \"21\", \"mensaje\": \"CI 44444444 Su saldo es 896,192 Gs.-\"},{\"idTransaccion\": \"20\", \"mensaje\": \"CI 44444444 Su saldo es 896,192 Gs.-\"},{\"idTransaccion\": \"19\", \"mensaje\": \"CI 44444444 Su saldo es 896,192 Gs.-\"},{\"idTransaccion\": \"18\", \"mensaje\": \"CI 44444444 Su saldo es 896,192 Gs.-\"},{\"idTransaccion\": \"17\", \"mensaje\": \"CI 44444444 Su saldo es 896,192 Gs.-\"}]}}";
                      //  JSONObject jsonObject = new JSONObject(prueba);
                        Gson gson = new Gson();
                        TransaccionResponse transaccionResponse = gson.fromJson(prueba, TransaccionResponse.class);
                       /* JSONArray jsonArray = new JSONArray(jsonObject.getString("data"));
                        List<Transaccion> list = new ArrayList<Transaccion>();
                        for (int i=0; i<jsonArray.length(); i++) {
                            list.add(((JSONObject) jsonArray.getString(i)).getString("idTransaccion"));
                        }*/
                        Log.d("DEBUG",transaccionResponse.toString());
                   /* } catch (JSONException e) {
                        e.printStackTrace();
                    }*/
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            swipeLayout.setRefreshing(false);

                        }
                    });

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*String transactionJsonUnico = example.getTransaction("unico");
        Log.i("DEBUG","transactionJsonUnico enviado = "+transactionJsonUnico);
        try {
            example.post(CommReq.BASE_URL, transactionJsonUnico, new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {

                }

                @Override
                public void onResponse(Response response) throws IOException {
                    String responseStr = response.body().string();

                    Log.i("DEBUG", "SUCESS!!");
                    Log.i("DEBUG", responseStr);
                    Log.i("DEBUG", response.message());
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        //String json = example.bowlingJson("Jesse", "Jake");
        /*try {
            example.post("http://www.roundsapp.com/post", json, new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    Log.i("DEBUG", "FAILURE!!");
                    Log.i("DEBUG", request.toString());
                    Log.i("DEBUG", e.getMessage());
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    String responseStr = response.body().string();
                    Log.i("DEBUG", "SUCESS!!");
                    Log.i("DEBUG", responseStr);
                    Log.i("DEBUG", response.message());

                    if (!response.isSuccessful())
                        throw new IOException("Unexpected code " + response);

                    Headers responseHeaders = response.headers();
                    for (int i = 0; i < responseHeaders.size(); i++) {
                        Log.i("PRUEBA", responseHeaders.name(i) + ": " + responseHeaders.value(i));
                    }

                    Log.i("PRUEBA", response.body().string());

                }
            });
        } catch (IOException e){
            e.printStackTrace();
        }*/


    }

    public class HistorialRecyclerViewAdapter
            extends RecyclerView.Adapter<HistorialRecyclerViewAdapter.ViewHolder> {

        private final TypedValue mTypedValue = new TypedValue();
        private int mBackground;
        private ArrayList<Transaccion> mValues;

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

//        public String getValueAt(int position) {
//            return mValues.get(position);
//        }

        public HistorialRecyclerViewAdapter(Context context,ArrayList<Transaccion> items) {
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
