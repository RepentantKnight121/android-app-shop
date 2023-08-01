package com.example.android_app_shop.View;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.android_app_shop.Controller.ImageProductHandler;
import com.example.android_app_shop.Controller.ProductHandlder;
import com.example.android_app_shop.Model.ImageProduct;
import com.example.android_app_shop.Model.Product;
import com.example.android_app_shop.Model.ProductShowInDetailAdapter;
import com.example.android_app_shop.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Frag_Search#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Frag_Search extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    List<Product> productList;
    ProductHandlder productHandlder;
    GridView gridShowProduct;
    ArrayList<Product> productArrayList = new ArrayList<>();
    ImageProductHandler imageProductHandler;

    ProductShowInDetailAdapter adapter;

    public Frag_Search() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Frag_Search.
     */
    // TODO: Rename and change types and number of parameters
    public static Frag_Search newInstance(String param1, String param2) {
        Frag_Search fragment = new Frag_Search();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_frag__search, container, false);
        Bundle bundle = getArguments();
        String query = bundle.getString("query");
        gridShowProduct = (GridView) view.findViewById(R.id.gridShowProduct);
        if (query != null && !query.isEmpty()) {
            productHandlder = new ProductHandlder(getContext(), "SMARTPHONE.db", null, 1);
            productHandlder.openDatabase();
            productHandlder.initData();
            imageProductHandler = new ImageProductHandler(getContext(), "SMARTPHONE.db", null, 1);
            imageProductHandler.initData();
            productArrayList = productHandlder.loadProduct();
            productList = productHandlder.searchProducts(query);
            showProduct();
            addEvents();
        }
        productHandlder.closeDatabase();
        return view;
    }

    private ArrayList<Product> getAllProduct() {
        ArrayList<Product> lstPr= new ArrayList<>();
        for (Product pr : productList) {
            int productID = pr.getID();
            String productName = pr.getNameProduct();
            String category = pr.getID_Category();
            String color = pr.getColor();
            int storage = pr.getStorage();
            float price = pr.getPrice();
            List<ImageProduct> imageURLs = imageProductHandler.getListImagesByProductId(productID);
            List<String> allImageURLs = new ArrayList<>();
            for (ImageProduct img : imageURLs) {
                allImageURLs.add(img.getURL());
            }
            Product product = new Product();
            product.setID(productID);
            product.setNameProduct(productName);
            product.setColor(color);
            product.setStorage(storage);
            product.setPrice(price);
            product.setImageURL(allImageURLs);
            product.setID_Category(category);
            lstPr.add(product);
        }
        return lstPr;
    }

    private void addEvents(){
        gridShowProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Product product = (Product) adapter.getItem(i);
                int id = product.getID();
                Intent intent = new Intent( getContext(), ProductDetails.class);
                Bundle bundle = new Bundle();
                bundle.putInt("id", id);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void showProduct() {
        ArrayList<Product> productList = getAllProduct();
        adapter = new ProductShowInDetailAdapter(getContext(), productList);
        gridShowProduct.setAdapter(adapter);
    }
}