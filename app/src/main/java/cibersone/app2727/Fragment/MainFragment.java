package cibersone.app2727.Fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cibersone.app2727.R;
import cibersone.app2727.Utils.CustomDialog;
import cibersone.app2727.beans.Recycler;

/**
 * Created by Manuel on 8/19/2015.
 */
public class MainFragment extends Fragment {

    private static MainFragment instance;
//    private EditText phoneNo;
//    private EditText messageBody;
    private LinearLayout send;
    private LinearLayout llTextoBienvenida;
//    private Button btnOK;
//    private CardView cardView;

    private static int PRESSED = 1;
    OnHeadlineSelectedListener mCallback;

    // Container Activity must implement this interface
    public interface OnHeadlineSelectedListener {
        public void onSelectedInstructivo(int pressed);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnHeadlineSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }
    public static MainFragment newInstance(String title) {
        if (instance == null) {
            instance = new MainFragment();
            Bundle args = new Bundle();
//            args.putString(ARG_FRAGMENT_TITLE, title);
            instance.setArguments(args);
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
//        RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.recyclerview);
        send = (LinearLayout) rootView.findViewById(R.id.btnSendSMS);
        llTextoBienvenida = (LinearLayout) rootView.findViewById(R.id.llTextoBienvenida);
//        phoneNo = (EditText) rootView.findViewById(R.id.mobileNumber);
//        messageBody = (EditText) rootView.findViewById(R.id.smsBody);
//        btnOK = (Button) rootView.findViewById(R.id.btnOk);
//        cardView = (CardView) rootView.findViewById(R.id.card_view_bienvenida);

//        btnOK.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                cardView.setVisibility(View.GONE);
//            }
//        });
//        send = (Button) findViewById(R.id.send);
        llTextoBienvenida.getBackground().setAlpha(80);

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.setContentView(R.layout.custom_dialog);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                final LinearLayout dialogButton = (LinearLayout) dialog.findViewById(R.id.btn_dialog);
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // if button is clicked, close the custom dialog
                        mCallback.onSelectedInstructivo(PRESSED);
                        dialog.dismiss();
                    }
                });

                dialog.show();

//                String number = "0982484860";
//                String sms = "prueba";
//
//                try {
//                    SmsManager smsManager = SmsManager.getDefault();
//                    smsManager.sendTextMessage(number, null, sms, null, null);
//                    Toast.makeText(getActivity().getApplicationContext(), "Mensaje Enviado!",
//                            Toast.LENGTH_LONG).show();
//                } catch (Exception e) {
//                    Toast.makeText(getActivity().getApplicationContext(),
//                            "Envio de SMS fallido, intente de nuevo!",
//                            Toast.LENGTH_LONG).show();
//                    e.printStackTrace();
//                }
            }
        });

        return rootView;
    }

//    private void setupRecyclerView(RecyclerView recyclerView) {
//        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext(), LinearLayoutManager.VERTICAL, false));
//        recyclerView.setAdapter(new MainRecyclerViewAdapter(getActivity(),
//                getRandomSublist(Recycler.sRecyclerStrings, Recycler.sRecyclerStrings.length)));
//    }
//
//    private List<String> getRandomSublist(String[] array, int amount) {
//        ArrayList<String> list = new ArrayList<>(amount);
//        Random random = new Random();
//        int i = 0;
//        while (list.size() < amount) {
//            list.add(array[i++]);
//        }
//        return list;
//    }
//
//    public class MainRecyclerViewAdapter
//            extends RecyclerView.Adapter<MainRecyclerViewAdapter.ViewHolder> {
//
//        private final TypedValue mTypedValue = new TypedValue();
//        private int mBackground;
//        private List<String> mValues;
//
//        public class ViewHolder extends RecyclerView.ViewHolder {
//            public String mBoundString;
//
//            public final View mView;
//            public final ImageView mImageView;
//            public final TextView mTextView;
//
//            public ViewHolder(View view) {
//                super(view);
//                mView = view;
//                mImageView = (ImageView) view.findViewById(R.id.avatar);
//                mTextView = (TextView) view.findViewById(android.R.id.text1);
//            }
//
//            @Override
//            public String toString() {
//                return super.toString() + " '" + mTextView.getText();
//            }
//        }
//
//        public String getValueAt(int position) {
//            return mValues.get(position);
//        }
//
//        public MainRecyclerViewAdapter(Context context, List<String> items) {
//            context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
//            mBackground = mTypedValue.resourceId;
//            mValues = items;
//        }
//
//        @Override
//        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            View view = LayoutInflater.from(parent.getContext())
//                    .inflate(R.layout.main_list_item, parent, false);
//            view.setBackgroundResource(mBackground);
//            return new ViewHolder(view);
//        }
//
//        @Override
//        public void onBindViewHolder(final ViewHolder holder, int position) {
//            holder.mBoundString = mValues.get(position);
//            holder.mTextView.setText(mValues.get(position));
//
//            holder.mView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Context context = v.getContext();
////                    Intent intent = new Intent(context, ProductDetailActivity.class);
////                    intent.putExtra(ProductDetailActivity.EXTRA_NAME, holder.mBoundString);
////                    context.startActivity(intent);
////                    LoginFragment loginFragment = new LoginFragment();
////                    FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
////
////                    // Store the Fragment in stack
////                    transaction.addToBackStack(null);
////                    transaction.replace(R.id.framelayout_product_cheese, loginFragment).commit();
//                }
//            });
//
//            Glide.with(holder.mImageView.getContext())
//                    .load(Recycler.getRandomRecyclerDrawable())
//                    .into(holder.mImageView);
//        }
//
//        @Override
//        public int getItemCount() {
//            return mValues.size();
//        }
//    }

}
