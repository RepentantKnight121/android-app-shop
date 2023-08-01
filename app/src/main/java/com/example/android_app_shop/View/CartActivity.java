package com.example.android_app_shop.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.util.Printer;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android_app_shop.Controller.CartHandler;
import com.example.android_app_shop.Controller.ImageProductHandler;
import com.example.android_app_shop.Controller.ProductHandlder;
import com.example.android_app_shop.Model.Cart;
import com.example.android_app_shop.Model.CartManager;
import com.example.android_app_shop.Model.CustomAdapterCart;
import com.example.android_app_shop.Model.ImageProduct;
import com.example.android_app_shop.Model.Pay;
import com.example.android_app_shop.Model.Product;
import com.example.android_app_shop.Model.ProductShowInDetailAdapter;
import com.example.android_app_shop.R;

import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    TextView tvNotification, tvShowPrice;
    ListView lstCart;
    CheckBox cbAllProduct;
    public Button btnRemove, btnPurchase;
    ArrayList<Cart> cartList = new ArrayList<>();
    ProductHandlder productHandlder;
    ImageProductHandler imageProductHandler;
    CartHandler cartHandler;
    CartManager cartManager;
    CustomAdapterCart customAdapterCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        addControls();
        loadCartItems();
        addEvents();
    }
    private void addControls(){
        tvNotification = (TextView) findViewById(R.id.tvNotification);
        tvShowPrice  = (TextView) findViewById(R.id.tvShowPrice);
        lstCart = (ListView) findViewById(R.id.lstCart);
        cbAllProduct = (CheckBox) findViewById(R.id.cbAllProduct);
        btnRemove = (Button) findViewById(R.id.btnRemove);
        btnPurchase = (Button) findViewById(R.id.btnPurchase);
    }
    private void addEvents() {
        cbAllProduct.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Khi checkbox cbAllProduct được check
                    customAdapterCart.setAllItemsChecked(true);
                    btnRemove.setVisibility(View.VISIBLE);
                    // Lấy danh sách sản phẩm trong giỏ hàng từ SharedPreferences
                    List<Cart> cartList = getCartItemsFromSharedPreferences();
                    double totalAmount = calculateTotalAmount(cartList);
                    displayTotalAmount(totalAmount);
                } else {
                    customAdapterCart.setAllItemsChecked(false);
                    btnRemove.setVisibility(View.GONE);
                    // Hiển thị lại giá tiền mặc định (0) lên TextView tvShowPrice
                    displayTotalAmount(0);
                }
            }
        });
        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cbAllProduct.isChecked()){
                    showClearCartConfirmationDialog();
                }else {
                    updateCartItems();
                }
                cbAllProduct.setChecked(false);
                btnRemove.setVisibility(View.GONE);
            }
        });
        btnPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Integer> checkedProductIds = getCheckedProductIds();
                List<Cart> selectedProducts = customAdapterCart.getCheckedItems();

                double totalAmount = 0;
                for (Cart cart : selectedProducts) {
                    totalAmount += cart.getProductPrice() * cart.getValue();
                }
                Intent intent = new Intent(CartActivity.this, PayActivity.class);
                intent.putIntegerArrayListExtra("checkedProductIds", checkedProductIds);
                intent.putExtra("totalAmount", totalAmount);
                startActivity(intent);


            }
        });
    }
    private void loadCartItems() {
        cartHandler = new CartHandler(this, "SMARTPHONE.db", null, 1);
        if (imageProductHandler == null) {
            imageProductHandler = new ImageProductHandler(this, "SMARTPHONE.db", null, 1);
        }
        cartManager = new CartManager(this);
        cartList = (ArrayList<Cart>) cartManager.getCartItems();
        // Tải các hình ảnh cho từng sản phẩm trong giỏ hàng
        for (Cart cart : cartList) {
            int productId = cart.getId();
            // Lấy danh sách URL hình ảnh từ ImageProductHandler dựa vào ID sản phẩm
            List<ImageProduct> imageURLs = imageProductHandler.getListImagesByProductId(productId);
            // Cập nhật danh sách URL hình ảnh cho mỗi sản phẩm trong giỏ hàng
            for (ImageProduct img : imageURLs) {
                cart.addImageURL(img.getURL());
            }
        }
        // Hiển thị danh sách sản phẩm trong ListView bằng CustomAdapterCart
        customAdapterCart = new CustomAdapterCart(this, cartList);
        lstCart.setAdapter(customAdapterCart);

        // Hiển thị thông báo hoặc ẩn dựa trên danh sách sản phẩm trong giỏ hàng
        if (cartList.isEmpty()) {
            tvNotification.setVisibility(View.VISIBLE);
            btnRemove.setVisibility(View.GONE);
            lstCart.setVisibility(View.GONE);
        } else {
            tvNotification.setVisibility(View.GONE);
            btnRemove.setVisibility(View.GONE);
            lstCart.setVisibility(View.VISIBLE);
        }
        customAdapterCart.setCartItemListener(new CustomAdapterCart.CartItemListener() {
            @Override
            public void onCartItemUpdated() {
                double updatedTotalAmount = calculateTotalAmount(customAdapterCart.getCheckedItems());
                displayTotalAmount(updatedTotalAmount);

            }
        });
    }
    private List<Cart> getCartItemsFromSharedPreferences() {
        CartManager cartManager = new CartManager(this);
        return cartManager.getCartItems();
    }
    private double calculateTotalAmount(List<Cart> cartList) {
        double totalAmount = 0;
        for (Cart cart : cartList) {
            totalAmount += cart.getProductPrice() * cart.getValue();
        }
        return totalAmount;
    }
    private void displayTotalAmount(double totalAmount) {
        // Tạo một đối tượng DecimalFormat để định dạng số thành chuỗi
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        // Chuyển đổi tổng tiền thành chuỗi với định dạng đầy đủ (có dấu phẩy)
        String formattedTotalAmount = "Tổng tiền là: " + decimalFormat.format(totalAmount) + " đ";
        // Hiển thị tổng tiền lên TextView tvShowPrice
        tvShowPrice.setText(formattedTotalAmount);
    }
    private void clearCartSharedPreferences() {
        CartManager cartManager = new CartManager(this);
        cartManager.clearCart();
    }
    private void showClearCartConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xác nhận xóa");
        builder.setMessage("Bạn có chắc chắn muốn xóa tất cả các sản phẩm trong giỏ hàng không?");
        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                clearCartSharedPreferences();
                loadCartItems();
                Toast.makeText(CartActivity.this, "Đã xóa tất cả các sản phẩm trong giỏ hàng", Toast.LENGTH_SHORT).show();
                btnRemove.setVisibility(View.GONE);
            }
        });
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
    private void updateCartItems() {
        List<Cart> selectedItems = customAdapterCart.getCheckedItems();
        if (selectedItems.isEmpty()) {
            // Nếu không có sản phẩm nào được chọn, không làm gì cả
            return;
        }
        showRemoveSelectedItemsConfirmationDialog();
    }
    private void showRemoveSelectedItemsConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xác nhận xóa");
        builder.setMessage("Bạn có chắc chắn muốn xóa các sản phẩm đã chọn khỏi giỏ hàng không?");
        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                removeSelectedItems();
                btnRemove.setVisibility(View.GONE);
                Toast.makeText(CartActivity.this, "Đã xóa các sản phẩm được chọn khỏi giỏ hàng", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                customAdapterCart.setAllItemsChecked(false);
                btnRemove.setVisibility(View.GONE);
                dialog.dismiss();
            }
        });
        builder.show();
    }
    private void removeSelectedItems() {
        List<Cart> selectedItems = customAdapterCart.getCheckedItems();
        for (Cart cart : selectedItems) {
            customAdapterCart.removeItem(cart);
        }
        List<Cart> updatedCartList = customAdapterCart.getCartList();
        cartManager.saveCartItems(updatedCartList);
        if (updatedCartList.isEmpty()) {
            tvNotification.setVisibility(View.VISIBLE);
            btnRemove.setVisibility(View.VISIBLE);
            lstCart.setVisibility(View.GONE);
        } else {
            tvNotification.setVisibility(View.GONE);
            btnRemove.setVisibility(View.GONE);
            lstCart.setVisibility(View.VISIBLE);
            tvShowPrice.setText("Tổng tiền là: 0đ");
        }

        customAdapterCart.notifyDataSetChanged();
    }

    private ArrayList<Integer> getCheckedProductIds() {
        ArrayList<Integer> checkedIds = new ArrayList<>();
        for (int i = 0; i < customAdapterCart.itemChecked.length; i++) {
            if (customAdapterCart.itemChecked[i]) {
                Cart cart = cartList.get(i);
                checkedIds.add(cart.getId());
            }
        }
        return checkedIds;
    }

    private ArrayList<Integer> getPutQuantity() {
        ArrayList<Integer> quantity = new ArrayList<>();
        for (int i = 0; i < customAdapterCart.itemChecked.length; i++) {
            if (customAdapterCart.itemChecked[i]) {
                Cart cart = cartList.get(i);
                quantity.add(cart.getValue());
            }
        }
        return quantity;
    }



}