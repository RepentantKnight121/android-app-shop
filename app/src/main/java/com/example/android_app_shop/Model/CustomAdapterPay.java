package com.example.android_app_shop.Model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android_app_shop.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CustomAdapterPay extends BaseAdapter {
    Context context;
    ArrayList<Pay> payArrayList;

    public CustomAdapterPay(Context context, ArrayList<Pay> payArrayList) {
        this.context = context;
        this.payArrayList = payArrayList;
    }

    @Override
    public int getCount() {
        return payArrayList.size();
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
            convertView = inflater.inflate(R.layout.layout_item_pay, null);
            viewHolder = new ViewHolder();
            viewHolder.imgCartProduct = convertView.findViewById(R.id.imgCartProduct);
            viewHolder.tvCartProduct = convertView.findViewById(R.id.tvCartProduct);
            viewHolder.tvCartPrice = convertView.findViewById(R.id.tvCartPrice);
            viewHolder.btnValue = convertView.findViewById(R.id.tvValue);
            viewHolder.cbCartItem = convertView.findViewById(R.id.cbCartItem);
            viewHolder.tvDungLuong = convertView.findViewById(R.id.tvDungLuong);
            viewHolder.tvMauSac = convertView.findViewById(R.id.tvMauSac);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Pay pay = payArrayList.get(position);
        viewHolder.tvCartProduct.setText(pay.getProductName());
        // Load the image using Picasso
        if (!pay.getImageURL().isEmpty()) {
            Picasso.get().load(pay.getImageURL().get(0)).into(viewHolder.imgCartProduct);
        }
        viewHolder.btnValue.setText(String.valueOf(pay.getValue()));

        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        String formattedPrice = "$" + decimalFormat.format(pay.getProductPrice());
        viewHolder.tvCartPrice.setText(formattedPrice);
        viewHolder.tvDungLuong.setText(pay.getStorage());
        viewHolder.tvMauSac.setText(pay.getColor());
        return convertView;
    }
    static class ViewHolder {
        ImageView imgCartProduct;
        TextView tvCartProduct;
        TextView tvCartPrice;
        TextView btnValue;
        CheckBox cbCartItem;
        TextView tvDungLuong;
        TextView tvMauSac;
    }
}
