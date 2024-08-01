package com.example.anaghafishapp.Useractivity;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anaghafishapp.Adapter.CartListAdapter;
import com.example.anaghafishapp.Interface.ChangeNumberItemsListener;
import com.example.anaghafishapp.R;
import com.example.anaghafishapp.helper.ManagementCart;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CartFragment extends Fragment implements PaymentResultListener {

    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewList;
    private ManagementCart managementCart;
    private TextView totalFeeTxt, taxTxt, deliveryTxt, totalTxt, emptyTxt, pstatus;
    private double tax;
    private ScrollView scrollView;
    //payment
    Button paybtn;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public CartFragment() {
        // Required empty public constructor
    }

    public static CartFragment newInstance(String param1, String param2) {
        CartFragment fragment = new CartFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        // Now, initialize your ManagementCart instance here
        managementCart = new ManagementCart(requireContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        // Call initView to initialize your views
        initView(view);
        initList(view);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        String phoneNumber = user.getPhoneNumber();
        // Set the OnClickListener after paybtn is initialized
        paybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String samount = String.valueOf(totalTxt.getText()).replace("₹", "");// Remove the currency symbol
                int amount = Math.round(Float.parseFloat(samount) * 100);

                Checkout checkout = new Checkout();

                checkout.setKeyID("rzp_test_18P57LdQikECv4");
                checkout.setImage(R.drawable.raw);

                try {
                    JSONObject object = new JSONObject();
                    JSONArray paymentMethods = new JSONArray();
                    object.put("name", "Anaghafish");
                    object.put("description", "Fish order payment");
                    object.put("theme.color", "");
                    object.put("amount", amount);
                    object.put("prefill.contact", "1234567890");//phonenumber change here
                    object.put("prefill.email", "demo@gmail.com");

                    paymentMethods.put("card");
                    paymentMethods.put("netbanking");
                    paymentMethods.put("wallet");
                    paymentMethods.put("upi");
                    paymentMethods.put("gpay");
                    paymentMethods.put("paytm");

                    JSONObject options = new JSONObject();
                    options.put("payment_method", paymentMethods);
                    object.put("options", options);

                    checkout.open(getActivity(), object);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        calculateCart();
        return view;
    }

    private void initList(View view) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewList = view.findViewById(R.id.view);
        recyclerViewList.setLayoutManager(linearLayoutManager);

        adapter = new CartListAdapter(managementCart.getListCart(), requireContext(), new ChangeNumberItemsListener() {
            @Override
            public void changed() {
                calculateCart();
            }
        });

        recyclerViewList.setAdapter(adapter);
        if (managementCart.getListCart().isEmpty()) {
            emptyTxt.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.GONE);
        } else {
            emptyTxt.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
        }
    }

    private void calculateCart() {
        double percentTax = 0.18;
        double delivery = 10;
        double itemTotal = managementCart.getTotalFee();
        double tax = Math.round((itemTotal * percentTax) * 100.0) / 100.0;
        double total = Math.round((itemTotal + tax + delivery) * 100.0) / 100.0;

        totalFeeTxt.setText("₹" + itemTotal);
        taxTxt.setText("₹" + tax);
        deliveryTxt.setText("₹" + delivery);
        totalTxt.setText("₹" + total);

        if (managementCart.getListCart().isEmpty()) {
            emptyTxt.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.GONE);
        } else {
            emptyTxt.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
        }
    }

    private void initView(View view) {
        totalFeeTxt = view.findViewById(R.id.totalFeeTxt);
        taxTxt = view.findViewById(R.id.taxTxt);
        deliveryTxt = view.findViewById(R.id.deliveryTxt);
        totalTxt = view.findViewById(R.id.totalTxt);
        recyclerViewList = view.findViewById(R.id.view);
        scrollView = view.findViewById(R.id.scrollview);
        emptyTxt = view.findViewById(R.id.emptyTxt);

        paybtn = view.findViewById(R.id.paybtn);
        pstatus = view.findViewById(R.id.pstatus);
    }
    @Override
    public void onPaymentSuccess(String s) {
        // Implementation for successful payment
        Toast.makeText(getActivity(), "Payment Successful. Transaction ID: " + s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPaymentError(int i, String s) {
        // Implementation for payment error
    }
  /*  @Override
    public void onPaymentSuccess(String s) {
       // pstatus.setVisibility(View.VISIBLE);
        //pstatus.setText("Order Successfully Transaction No " + s);
        //totalTxt.setText("0.00");
        Toast.makeText(getActivity(), "Payment Successful. Transaction ID: " + s, Toast.LENGTH_SHORT).show();

        Log.i("RazorpaySuccess", "Transaction ID: " + s);
    }

    @Override
    public void onPaymentError(int i, String s) {
        pstatus.setVisibility(View.VISIBLE);
        pstatus.setText("Order UnSuccessfully Transaction No " + s);
        totalTxt.setText("0.00");
        Log.e("RazorpayError", "Error code: " + i + ", Description: " + s);
    }
    */

}
