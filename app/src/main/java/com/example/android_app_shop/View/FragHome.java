package com.example.android_app_shop.View;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.fragment.app.Fragment;

import com.example.android_app_shop.Controller.ImageProductHandler;
import com.example.android_app_shop.Controller.ProductHandlder;
import com.example.android_app_shop.Model.PagerImageProductDetail;
import com.example.android_app_shop.Model.Product;
import com.example.android_app_shop.Model.ProductShowInDetailAdapter;
import com.example.android_app_shop.R;

import java.util.ArrayList;

public class FragHome extends Fragment {

    GridView ListProductHomePage;
    ProductShowInDetailAdapter adapterGrid;
    ProductHandlder productHandlder;
    ImageProductHandler imageProductHandler;
    ArrayList<Product> productArrayList = new ArrayList<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        productHandlder = new ProductHandlder(getContext(), "SMARTPHONE.db", null, 1);
        imageProductHandler = new ImageProductHandler(getContext(), "SMARTPHONE.db", null, 1);
        imageProductHandler.initData();
        productHandlder.initData();
        productArrayList = productHandlder.loadProduct();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.frag_home, container, false);
        ListProductHomePage = view.findViewById(R.id.gridProductHomePage);
        ArrayList<Product> productListHomePage = new ArrayList<>(productArrayList.subList(0, 3));
        adapterGrid = new ProductShowInDetailAdapter(getContext(), productListHomePage);
        ListProductHomePage.setAdapter(adapterGrid);
        return view;
    }
}