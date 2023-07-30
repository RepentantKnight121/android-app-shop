package com.example.android_app_shop.Model;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.android_app_shop.R;
import com.example.android_app_shop.View.CartActivity;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CustomAdapterCart extends BaseAdapter {
    Context context;
    ArrayList<Cart> cartList;
    public boolean[] itemChecked;
    public CustomAdapterCart(Context context, ArrayList<Cart> cartList) {
        this.context = context;
        this.cartList = cartList;
        itemChecked = new boolean[cartList.size()];
    }
    @Override
    public int getCount() {
        return cartList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.layout_item_cart, null);
            viewHolder = new ViewHolder();
            viewHolder.imgCartProduct = convertView.findViewById(R.id.imgCartProduct);
            viewHolder.tvCartProduct = convertView.findViewById(R.id.tvCartProduct);
            viewHolder.tvCartPrice = convertView.findViewById(R.id.tvCartPrice);
            viewHolder.btnValue = convertView.findViewById(R.id.tvValue);
            viewHolder.cbCartItem = convertView.findViewById(R.id.cbCartItem);
            viewHolder.btnMinus = convertView.findViewById(R.id.btnMinus);
            viewHolder.btnPlus = convertView.findViewById(R.id.btnPlus);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Cart cart = cartList.get(position);
        viewHolder.tvCartProduct.setText(cart.getProductName());
        // Load the image using Picasso
        if (!cart.getImageURL().isEmpty()) {
            Picasso.get().load(cart.getImageURL().get(0)).into(viewHolder.imgCartProduct);
        } else {
//            viewHolder.imgProduct.setImageResource(R.drawable.default_image);
        }
        viewHolder.btnValue.setText(String.valueOf(cart.getValue()));


        // Xử lý sự kiện khi nhấn nút "Trừ - Cộng" tổng tiền và các sử lý khác
        viewHolder.btnMinus.setTag(position);
        viewHolder.btnPlus.setTag(position);
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        String formattedPrice = decimalFormat.format(cart.getProductPrice()) +  " đ";
        viewHolder.tvCartPrice.setText(formattedPrice);
        viewHolder.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentValue = cart.getValue();
                if (currentValue > 1) {
                    currentValue--;
                    cart.setValue(currentValue);
                    notifyDataSetChanged();

                    // Save the updated cart list to SharedPreferences
                    CartManager cartManager = new CartManager(context);
                    cartManager.saveCartItems(cartList);

                    // Notify the activity to update the total amount
                    if (cartItemListener != null) {
                        cartItemListener.onCartItemUpdated();
                    }
                }
            }
        });
        viewHolder.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentValue = cart.getValue();
                currentValue++;
                cart.setValue(currentValue);
                notifyDataSetChanged();

                // Save the updated cart list to SharedPreferences
                CartManager cartManager = new CartManager(context);
                cartManager.saveCartItems(cartList);

                // Notify the activity to update the total amount
                if (cartItemListener != null) {
                    cartItemListener.onCartItemUpdated();
                }
            }
        });

        viewHolder.cbCartItem.setChecked(itemChecked[position]);
        viewHolder.cbCartItem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                itemChecked[position] = isChecked;


                if (cartItemListener != null) {
                    cartItemListener.onCartItemUpdated();
                }
                for (boolean checked : itemChecked) {
                    if (checked) {
                        ((CartActivity) context).btnRemove.setVisibility(View.VISIBLE);
                        return;
                    }
                }
                ((CartActivity) context).btnRemove.setVisibility(View.GONE);

            }
        });
        return convertView;
    }

    private static class ViewHolder {
        ImageView imgCartProduct;
        TextView tvCartProduct;
        TextView tvCartPrice;
        TextView btnValue;
        CheckBox cbCartItem;
        Button btnMinus;
        Button btnPlus;
    }
    public void setCartList(ArrayList<Cart> newCartList) {
        cartList = newCartList;
        notifyDataSetChanged();
    }
    public List<Cart> getCheckedItems() {
        List<Cart> checkedItems = new ArrayList<>();
        for (int i = 0; i < itemChecked.length; i++) {
            if (itemChecked[i]) {
                checkedItems.add(cartList.get(i));
            }
        }
        return checkedItems;
    }
    public interface CartItemListener {
        void onCartItemUpdated();
    }
    private CartItemListener cartItemListener;
    public void setCartItemListener(CartItemListener listener) {
        cartItemListener = listener;
    }
    // Phương thức cập nhật trạng thái của item
//    public void updateItemChecked(int position, boolean checked) {
//        itemChecked[position] = checked;
//        notifyDataSetChanged();
//    }
    public void setAllItemsChecked(boolean isChecked) {
        for (int i = 0; i < itemChecked.length; i++) {
            itemChecked[i] = isChecked;
        }
        notifyDataSetChanged();
    }

    public void removeItem(Cart cart) {
        cartList.remove(cart);
        itemChecked = new boolean[cartList.size()]; // Reset the itemChecked array after removing the item
        notifyDataSetChanged();
    }

    public ArrayList<Cart> getCartList() {
        return cartList;
    }



}

