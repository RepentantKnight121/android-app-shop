package com.example.android_app_shop.Model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CartManager {
    private static final String CART_PREFERENCES_KEY = "cart_preferences";
    private static final String CART_ITEMS_KEY = "cart_items";

    private Context context;

    public CartManager(Context context) {
        this.context = context;
    }

    // Thêm một sản phẩm vào giỏ hàng
    public void addToCart(Cart cart) {
        List<Cart> cartList = getCartItems();
        boolean found = false;

        // Kiểm tra xem sản phẩm đã có trong giỏ hàng chưa
        for (Cart c : cartList) {
            if (c.getId() == cart.getId()) {
                // Nếu sản phẩm đã có trong giỏ hàng, tăng giá trị value
                c.setValue(c.getValue() + 1);
                found = true;
                break;
            }
        }
        if (!found) {
            cartList.add(cart);
        }
        saveCartItems(cartList);
    }

    // Lấy danh sách sản phẩm trong giỏ hàng
    public List<Cart> getCartItems() {
        List<Cart> cartList;
        SharedPreferences preferences = context.getSharedPreferences(CART_PREFERENCES_KEY, Context.MODE_PRIVATE);
        String cartItemsJson = preferences.getString(CART_ITEMS_KEY, "");
        if (cartItemsJson.isEmpty()) {
            cartList = new ArrayList<>();
        } else {
            Type cartListType = new TypeToken<List<Cart>>(){}.getType();
            cartList = new Gson().fromJson(cartItemsJson, cartListType);
        }
        return cartList;
    }

    // Lưu danh sách sản phẩm vào SharedPreferences
    public void saveCartItems(List<Cart> cartList) {
        String cartItemsJson = new Gson().toJson(cartList);
        SharedPreferences preferences = context.getSharedPreferences(CART_PREFERENCES_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(CART_ITEMS_KEY, cartItemsJson);
        editor.apply();
    }
    // Clear all cart items from SharedPreferences
    public void clearCart() {
        SharedPreferences preferences = context.getSharedPreferences(CART_PREFERENCES_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }
    public void removeCartItem(Cart cart) {
        List<Cart> cartList = getCartItems();
        cartList.remove(cart);
        saveCartItems(cartList);
    }

    public void updateCartItemQuantity(Cart cart) {
        List<Cart> cartList = getCartItems();
        for (Cart c : cartList) {
            if (c.getId() == cart.getId()) {
                c.setValue(cart.getValue());
                break;
            }
        }
        saveCartItems(cartList);
    }
    public List<Pay> getCart_in_PayItems() {
        List<Pay> cartList;
        SharedPreferences preferences = context.getSharedPreferences(CART_PREFERENCES_KEY, Context.MODE_PRIVATE);
        String cartItemsJson = preferences.getString(CART_ITEMS_KEY, "");
        if (cartItemsJson.isEmpty()) {
            cartList = new ArrayList<>();
        } else {
            Type cartListType = new TypeToken<List<Pay>>(){}.getType();
            cartList = new Gson().fromJson(cartItemsJson, cartListType);
        }
        return cartList;
    }
}
