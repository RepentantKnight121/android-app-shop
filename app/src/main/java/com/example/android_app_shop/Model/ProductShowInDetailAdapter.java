package com.example.android_app_shop.Model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android_app_shop.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductShowInDetailAdapter extends BaseAdapter {
    List<Product> productList;
    Context context;
    LayoutInflater layoutInflater;

    public ProductShowInDetailAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return Math.min(productList.size(), 4);
    }

    @Override
    public Object getItem(int position) {
        return productList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.custom_layout_grib_product_detail, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.imgProduct = convertView.findViewById(R.id.imgProduct);
            viewHolder.nameProduct = convertView.findViewById(R.id.nameProduct);
            viewHolder.storageProduct = convertView.findViewById(R.id.storageProduct);
            viewHolder.priceProduct = convertView.findViewById(R.id.priceProduct);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Product product = productList.get(position);
        viewHolder.nameProduct.setText(product.getNameProduct());
        viewHolder.storageProduct.setText(String.valueOf(product.getStorage()));
        viewHolder.priceProduct.setText(String.valueOf(product.getPrice()));
        if (!product.getImageURL().isEmpty()) {
            Picasso.get().load(product.getImageURL().get(0)).into(viewHolder.imgProduct);
        } else {
//            viewHolder.imgProduct.setImageResource(R.drawable.default_image);
        }

        return convertView;
    }

    static class ViewHolder {
        ImageView imgProduct;
        TextView nameProduct;
        TextView storageProduct;
        TextView priceProduct;
    }

}
