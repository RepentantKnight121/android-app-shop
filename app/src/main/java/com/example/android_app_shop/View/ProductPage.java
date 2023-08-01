package com.example.android_app_shop.View;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.android_app_shop.Controller.ImageProductHandler;
import com.example.android_app_shop.Controller.ProductHandlder;
import com.example.android_app_shop.Model.ImageProduct;
import com.example.android_app_shop.Model.PagerImageProductDetail;
import com.example.android_app_shop.Model.Product;
import com.example.android_app_shop.Model.ProductShowInDetailAdapter;
import com.example.android_app_shop.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductPage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductPage extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    GridView gridShowProduct;
    PagerImageProductDetail adapterPager;
    ProductShowInDetailAdapter adapterGrid;
    ProductHandlder productHandlder;
    ImageProductHandler imageProductHandler;
    ArrayList<Product> productArrayList = new ArrayList<>();

    public ProductPage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProductPage.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductPage newInstance(String param1, String param2) {
        ProductPage fragment = new ProductPage();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_page, container, false);
        gridShowProduct = (GridView) view.findViewById(R.id.gridShowProduct);
        productHandlder = new ProductHandlder(getContext(), "SMARTPHONE.db", null, 1);
        imageProductHandler = new ImageProductHandler(getContext(), "SMARTPHONE.db", null, 1);
        imageProductHandler.initData();
        productHandlder.initData();
        productArrayList = productHandlder.loadProduct();
        addEvents();
        showProduct();
        return view;
    }

    private void addEvents(){
        gridShowProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Product product = (Product) adapterGrid.getItem(i);
                int id = product.getID();
                FragProductDetails frag_productDetails = new FragProductDetails();
                Bundle bundle = new Bundle();
                bundle.putInt("id", id);
                frag_productDetails.setArguments(bundle);
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment currentFragment = fragmentManager.findFragmentById(R.id.frameFragment);
                if (currentFragment != null) {
                    fragmentTransaction.hide(currentFragment);
                }
                fragmentTransaction.add(R.id.frameFragment, frag_productDetails);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }
    private ArrayList<Product> getAllProduct() {
        ArrayList<Product> productList = new ArrayList<>();
        for (Product pr : productHandlder.loadProduct()) {
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
            productList.add(product);
        }
        return productList;
    }

    private void showProduct() {
        ArrayList<Product> productList = getAllProduct();
        adapterGrid = new ProductShowInDetailAdapter(getContext(), productList);
        gridShowProduct.setAdapter(adapterGrid);
    }
}