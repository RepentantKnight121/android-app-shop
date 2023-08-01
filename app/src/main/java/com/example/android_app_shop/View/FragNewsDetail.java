package com.example.android_app_shop.View;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android_app_shop.R;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragNewsDetail#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragNewsDetail extends Fragment {
    TextView tvTitle, tvContent, tvDate;
    ImageView imageView;
    Button btnBack;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragNewsDetail() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragNewsDetail.
     */
    // TODO: Rename and change types and number of parameters
    public static FragNewsDetail newInstance(String param1, String param2) {
        FragNewsDetail fragment = new FragNewsDetail();
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
        View view = inflater.inflate(R.layout.fragment_frag_news_detail, container, false);
        tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        tvContent = (TextView) view.findViewById(R.id.tvContent);
        tvDate = (TextView) view.findViewById(R.id.tvDate);
        imageView = (ImageView) view.findViewById(R.id.imageView);
        btnBack = (Button) view.findViewById(R.id.btnBack);
        loadData();
        addEvents();
        return view;
    }

    private void loadData(){
        String title = getArguments().getString("title");
        String content = getArguments().getString("content");
        String date = getArguments().getString("date");
        String imageURL = getArguments().getString("image");


        tvTitle.setText(title);
        tvContent.setText(content);
        tvDate.setText(date);
        Picasso.get().load(imageURL).into(imageView);
    }

    private void addEvents(){
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack();
            }
        });
    }
}