package com.example.anaghafishapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.anaghafishapp.Domain.FoodDomain;

public class vendorshowdetail extends Fragment {
    private TextView addToCartBtn;
    private TextView titleTxt, nameTxt, feeTxt, numberOrderTxt, totalPriceTxt;
    private ImageView plusBtn, minusBtn, picFood;
    private FoodDomain object;
    private int numberOrder = 1;
    private ManagementCartone managementCart;

    private static final String ARG_VENDOR_DOMAIN = "vendor_domain";

    public vendorshowdetail() {
        // Required empty public constructor
    }

    public static vendorshowdetail newInstance(FoodDomain vendorDomain) {
        vendorshowdetail fragment = new vendorshowdetail();
        Bundle args = new Bundle();
        args.putSerializable(ARG_VENDOR_DOMAIN, vendorDomain);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        managementCart = new ManagementCartone(requireContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vendorshowdetail, container, false);
        initView(view);
        getBundle();
        return view;
    }

    private void getBundle() {
        Bundle args = getArguments();
        if (args != null) {
            object = (FoodDomain) args.getSerializable(ARG_VENDOR_DOMAIN);
            if (object != null) {
                // Load image using Glide and the URL provided by object.getPic()
                Glide.with(this).load(object.getPic()).into(picFood);

                titleTxt.setText(object.getTitle());
                nameTxt.setText(object.getName());
                if (object.getFee() != null) {
                    feeTxt.setText("₹ " + object.getFee());
                } else {
                    feeTxt.setText("Price not available");
                }

                numberOrderTxt.setText(String.valueOf(numberOrder));

                double initialTotalPrice = numberOrder * (object.getFee() != null ? Double.valueOf(object.getFee()) : 0);
                totalPriceTxt.setText(String.valueOf(initialTotalPrice));
            }
        }

        // Add listeners for plusBtn, minusBtn, and addToCartBtn...
    }

    private void initView(View view) {
        addToCartBtn = view.findViewById(R.id.addToCartBtn);
        titleTxt = view.findViewById(R.id.titleTxt);
        feeTxt = view.findViewById(R.id.priceTxt);
        nameTxt = view.findViewById(R.id.nameTxt);
        numberOrderTxt = view.findViewById(R.id.numberItemTxt);
        plusBtn = view.findViewById(R.id.plusCartBtn);
        minusBtn = view.findViewById(R.id.minusCartBtn);
        picFood = view.findViewById(R.id.foodPic);
        totalPriceTxt = view.findViewById(R.id.totalPriceTxt);
    }
}
