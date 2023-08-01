package com.example.android_app_shop.View;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android_app_shop.Controller.ImageProductHandler;
import com.example.android_app_shop.Controller.ProductHandlder;
import com.example.android_app_shop.Model.Cart;
import com.example.android_app_shop.Model.CartManager;
import com.example.android_app_shop.Model.ImageProduct;
import com.example.android_app_shop.Model.PagerImageProductDetail;
import com.example.android_app_shop.Model.Product;
import com.example.android_app_shop.Model.ProductShowInDetailAdapter;
import com.example.android_app_shop.R;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragProductDetails#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragProductDetails extends Fragment {

    GridView gridShowProduct;
    TextView tvNameProduct, tvNameProductSecond, tvPriceProduct, tvNameMenu;
    RadioButton rdo128GB, rdo256GB, rdo512GB, rdo1TB, rdoColor;
    TabLayout tabLayout;
    ViewPager viewPager;

    Button btnPayNow;
    PagerImageProductDetail adapterPager;
    ProductShowInDetailAdapter adapterGrid;
    ProductHandlder productHandlder;
    ImageProductHandler imageProductHandler;
    ArrayList<Product> productArrayList = new ArrayList<>();

    ArrayList<Product> cartItems = new ArrayList<>();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragProductDetails() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Frag_ProductDetails.
     */
    // TODO: Rename and change types and number of parameters
    public static FragProductDetails newInstance(String param1, String param2) {
        FragProductDetails fragment = new FragProductDetails();
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
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_frag__product_details, container, false);
        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        viewPager = (ViewPager) view.findViewById(R.id.pager);
        rdo128GB = (RadioButton) view.findViewById(R.id.rdo128);
        rdo256GB = (RadioButton) view.findViewById(R.id.rdo256);
        rdo512GB = (RadioButton) view.findViewById(R.id.rdo512);
        rdo1TB = (RadioButton) view.findViewById(R.id.rdo1tb);
        tvNameProduct = (TextView) view.findViewById(R.id.tvNameProduct);
        tvNameProductSecond = (TextView) view.findViewById(R.id.tvNameProductSecond);
        tvPriceProduct = (TextView) view.findViewById(R.id.tvPriceProduct);
        gridShowProduct = (GridView) view.findViewById(R.id.gridProduct);
        rdoColor = (RadioButton) view.findViewById(R.id.color);
        btnPayNow = (Button) view.findViewById(R.id.btnBuy);
        tvNameMenu = (TextView) view.findViewById(R.id.tvNameMenu);
        if(getArguments()!= null){
            int idProduct = getArguments().getInt("id");
            productHandlder = new ProductHandlder(getContext(), "SMARTPHONE.db", null, 1);
            imageProductHandler = new ImageProductHandler(getContext(), "SMARTPHONE.db", null, 1);
            imageProductHandler.initData();
            productHandlder.initData();
            productArrayList = productHandlder.loadProduct();
            addEvent(idProduct);
            showProduct();
        }


        return view;
    }


    private void addEvent(int idProduct){
        Product product = getProductDetails(idProduct);
        setColorProduct(product.getColor());
        setNameProduct(product.getNameProduct());
        setPriceProduct(String.valueOf(product.getPrice()));
        setupViewPager(product.getImageURL());
        setupTabLayout(product.getImageURL());
        checkStorage(product.getStorage());

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
                fragmentTransaction.replace(R.id.frameFragment, frag_productDetails);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        btnPayNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Product product = getProductDetails(idProduct);
                int id = product.getID();
                // Tạo một đối tượng Cart với các chi tiết cần thiết của sản phẩm đã chọn
                Cart cart = new Cart();
                cart.setId(id);
                cart.setProductName(product.getNameProduct());
                cart.setProductPrice(product.getPrice());
                cart.setStorage(product.getStorage());
                cart.setColor(product.getColor());

                // Đặt số lượng hiện tại là 1, bạn có thể cập nhật nếu cần
                cart.setValue(1);
                // Thêm sản phẩm đã chọn vào giỏ hàng bằng CartManager
                CartManager cartManager = new CartManager(getContext());
                cartManager.addToCart(cart);
                // Hiển thị thông báo sản phẩm đã được thêm vào giỏ hàng
                Toast.makeText(getContext(), "Product added to cart", Toast.LENGTH_SHORT).show();
                //Sửa activity nhận tại đây
                Intent intent = new Intent(getContext(), CartActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putInt("id", id);
//                intent.putExtras(bundle);
                startActivity(intent);

            }
        });
    }
    private void setNameProduct(String name){
        tvNameProduct.setText(name);
        tvNameMenu.setText(name);
        tvNameProductSecond.setText(name);
    }
    private void setPriceProduct(String price){
        tvPriceProduct.setText(price + "đ");
    }
    private void setColorProduct(String color) {
        int backgroundColor;
        try {
            Field colorField = Color.class.getDeclaredField(color.toUpperCase());
            backgroundColor = colorField.getInt(null);
        } catch (Exception e) {
            backgroundColor = Color.WHITE;
            e.printStackTrace();
        }
        rdoColor.setBackgroundColor(backgroundColor);
    }
    private void setupViewPager(List<String> listImgProduct) {
        adapterPager = new PagerImageProductDetail(getContext(), listImgProduct);
        viewPager.setAdapter(adapterPager);
    }
    private void setupTabLayout(List<String> listImgProduct) {
        tabLayout.setupWithViewPager(viewPager);
        for (int i = 0; i < listImgProduct.size(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab != null) {
                tab.setCustomView(createTabView(i, listImgProduct));
            }
        }
    }

    private View createTabView(int position,List<String> listImgProduct ) {
        View tabView = LayoutInflater.from(getContext()).inflate(R.layout.tab_layout_product_detail, null);
        ImageView tabImage = tabView.findViewById(R.id.tabImage);
        Picasso.get().load(listImgProduct.get(position)).into(tabImage);
        return tabView;
    }

    private void checkStorage(int storage){
        switch (storage){
            case 128:
                rdo128GB.isChecked();
                rdo128GB.setBackgroundColor(Color.BLACK);
                rdo128GB.setTextColor(Color.WHITE);
                break;
            case  256:
                rdo256GB.isChecked();
                rdo256GB.setBackgroundColor(Color.BLACK);
                rdo256GB.setTextColor(Color.WHITE);
                break;
            case 512:
                rdo512GB.isChecked();
                rdo512GB.setBackgroundColor(Color.BLACK);
                rdo512GB.setTextColor(Color.WHITE);
                break;
            case 1024:
                rdo1TB.isChecked();
                rdo1TB.setBackgroundColor(Color.BLACK);
                rdo1TB.setTextColor(Color.WHITE);
                break;
        }
    }

    private Product getProductDetails(int id) {
        Product pr = productHandlder.getProductById(id);
//        String categoryID = pr.getID_Category();
        int productID = pr.getID();
        String productName = pr.getNameProduct();
        String color = pr.getColor();
        List<ImageProduct> imageURLs = imageProductHandler.getListImagesByProductId(productID);
        int storage = pr.getStorage();
        float price = pr.getPrice();
        Product product = new Product();
//        product.setID_Category(categoryID);
        product.setID(productID);
        product.setNameProduct(productName);
        product.setColor(color);
        product.setStorage(storage);
        product.setPrice(price);


        for (ImageProduct img : imageURLs) {
            product.addImageURL(img.getURL());
        }
        return product;
    }

    private ArrayList<Product> getAllProduct() {
        ArrayList<Product> productList = new ArrayList<>();
        for (Product pr : productHandlder.loadProduct()) {
            int productID = pr.getID();
            String productName = pr.getNameProduct();
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
            productList.add(product);
        }
        return productList;
    }

    private void showProduct() {
        ArrayList<Product> productList = getAllProduct();
        adapterGrid = new ProductShowInDetailAdapter(getContext(), productList);
        gridShowProduct.setAdapter(adapterGrid);
    }

    private Product findProduct (int id, ArrayList<Product> lstProduct){
        for (Product pr: lstProduct){
            if(pr.getID() == id){
                return pr;
            }
        }
        return null;
    }

}
