package com.example.android_app_shop.View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.android_app_shop.Controller.ImageProductHandler;
import com.example.android_app_shop.Controller.ProductHandlder;
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

public class ProductDetails extends AppCompatActivity {
    GridView gridShowProduct;
    TextView tvNameProduct, tvNameProductSecond, tvPriceProduct;
    RadioButton rdo128GB, rdo256GB, rdo512GB, rdo1TB, rdoColor;
    TabLayout tabLayout;
    ViewPager viewPager;

    Button btnPayNow;
    PagerImageProductDetail adapterPager;
    ProductShowInDetailAdapter adapterGrid;
    ProductHandlder productHandlder;
    ImageProductHandler imageProductHandler;
    ArrayList<Product> productArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        addControls();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        // nhận id product từ đây
        int idProduct = bundle.getInt("id", 0);
        //Nếu chưa được gửi id
//        int idProduct = 1;

        productHandlder = new ProductHandlder(getApplicationContext(), "SMARTPHONE.db", null, 1);
        imageProductHandler = new ImageProductHandler(getApplicationContext(), "SMARTPHONE.db", null, 1);
        imageProductHandler.initData();
        productHandlder.initData();
        productArrayList = productHandlder.loadProduct();
        addEvent(idProduct);
        showProduct();
    }

    private void addControls() {
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.pager);
        rdo128GB = (RadioButton) findViewById(R.id.rdo128);
        rdo256GB = (RadioButton) findViewById(R.id.rdo256);
        rdo512GB = (RadioButton) findViewById(R.id.rdo512);
        rdo1TB = (RadioButton) findViewById(R.id.rdo1tb);
        tvNameProduct = (TextView) findViewById(R.id.tvNameProduct);
        tvNameProductSecond = (TextView) findViewById(R.id.tvNameProductSecond);
        tvPriceProduct = (TextView) findViewById(R.id.tvPriceProduct);
        gridShowProduct = (GridView) findViewById(R.id.gridProduct);
        rdoColor = (RadioButton) findViewById(R.id.color);
        btnPayNow = (Button) findViewById(R.id.btnBuy);
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
                Intent intent = new Intent(ProductDetails.this, ProductDetails.class);
                Bundle bundle = new Bundle();
                bundle.putInt("id", id);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        btnPayNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Product product = getProductDetails(idProduct);
                int id = product.getID();
                //Sửa activity nhận tại đây
                Intent intent = new Intent(ProductDetails.this, ProductDetails.class);
                Bundle bundle = new Bundle();
                bundle.putInt("id", id);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void setNameProduct(String name){
        tvNameProduct.setText(name);
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
        adapterPager = new PagerImageProductDetail(this, listImgProduct);
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
        View tabView = LayoutInflater.from(this).inflate(R.layout.tab_layout_product_detail, null);
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
        String categoryID = pr.getID_Category();
        int productID = pr.getID();
        String productName = pr.getNameProduct();
        String color = pr.getColor();
        List<ImageProduct> imageURLs = imageProductHandler.getListImagesByProductId(productID);
        int storage = pr.getStorage();
        float price = pr.getPrice();
        Product product = new Product();
        product.setID_Category(categoryID);
        product.setID(productID);
        product.setNameProduct(productName);
        product.setColor(color);
        product.setStorage(storage);
        product.setPrice(price);
        System.out.println(price);

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
            adapterGrid = new ProductShowInDetailAdapter(ProductDetails.this, productList);
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