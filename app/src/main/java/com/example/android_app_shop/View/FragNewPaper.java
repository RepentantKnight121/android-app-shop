package com.example.android_app_shop.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.android_app_shop.Controller.ImageProductHandler;
import com.example.android_app_shop.Controller.NewHandler;
import com.example.android_app_shop.Controller.ProductHandlder;
import com.example.android_app_shop.Model.CustomNewAdapter;
import com.example.android_app_shop.Model.ImageProduct;
import com.example.android_app_shop.Model.News;
import com.example.android_app_shop.Model.Product;
import com.example.android_app_shop.Model.ProductShowInDetailAdapter;
import com.example.android_app_shop.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragNewPaper#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragNewPaper extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    ListView lstNews;

    CustomNewAdapter adapter;
    NewHandler newHandler;
    ArrayList<News> newsArrayList = new ArrayList<>();


    public FragNewPaper() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragNewPaper.
     */
    // TODO: Rename and change types and number of parameters
    public static FragNewPaper newInstance(String param1, String param2) {
        FragNewPaper fragment = new FragNewPaper();
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
        View view = inflater.inflate(R.layout.new_paper, container, false);
        lstNews = (ListView) view.findViewById(R.id.lstNews);
        newHandler = new NewHandler(getContext(), "SMARTPHONE.db", null, 1);
        newHandler.initData();
        newsArrayList = newHandler.loadAllNews();
        ArrayList<News> lstArrNew = getAllNews();
        adapter = new CustomNewAdapter(lstArrNew, getContext());
        lstNews.setAdapter(adapter);
        addEvents();
        return view;
    }

    private ArrayList<News> getAllNews() {
        ArrayList<News> lstNews = new ArrayList<>();

        for (News news : newHandler.loadAllNews()) {
            String id = news.getID();
            String title = news.getTitle();
            String content = news.getContent();
            String date = news.getDate();
            String imageURL = news.getImageURL();
            String idProduct = news.getProductID();
            News n = new News();
            n.setID(id);
            n.setTitle(title);
            n.setContent(content);
            n.setDate(date);
            n.setImageURL(imageURL);
            n.setProductID(idProduct);
            lstNews.add(n);
        }
        return lstNews;
    }

    private void addEvents(){
        lstNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                News clickedNews = (News) adapterView.getItemAtPosition(i);
                if (clickedNews != null) {
                    String title = clickedNews.getTitle();
                    String content = clickedNews.getContent();
                    String image = clickedNews.getImageURL();
                    String date = clickedNews.getDate();
                    Intent intent = new Intent(getContext(), NewsActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("title", title);
                    bundle.putString("content", content);
                    bundle.putString("image", image);
                    bundle.putString("date", date);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
    }


//    private void showNews() {
//
//    }
}
