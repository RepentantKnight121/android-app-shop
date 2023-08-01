package com.example.android_app_shop.View;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.android_app_shop.Controller.AccountHandler;
import com.example.android_app_shop.Controller.ProductHandlder;
import com.example.android_app_shop.Model.Account;
import com.example.android_app_shop.R;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Frag_Login extends Fragment {

    Button btnLogin ;
    EditText getUsername , getPassword;

    AccountHandler accountHandler ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_login, container, false);
        getUsername = view.findViewById(R.id.inputUsername);
        getPassword = view.findViewById(R.id.inputPassWord);
        btnLogin = view.findViewById(R.id.btnLogin);
        accountHandler = new AccountHandler( getContext(), "smartphone.db", null, 1);
        accountHandler.initData();

        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String username = getUsername.getText().toString();
                String password = getPassword.getText().toString();

                if (accountHandler.checkUser(username, password)) {
                    Toast.makeText(getContext(), "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();

                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    // Replace fragment_Login with fragment_Register

                    // Create a Bundle object
                    Bundle bundle = new Bundle();

                    // Put the data in the Bundle object
                    bundle.putString("us", username);
                    bundle.putString("ps", password);

                    // Create a new FragmentB object
                    FragProfile fragmentProfile = new FragProfile();

                    // Set the arguments for the FragmentB object
                    fragmentProfile.setArguments(bundle);
                    fragmentManager.beginTransaction().replace(R.id.frameFragment, fragmentProfile).commit();
                } else {
                    Toast.makeText(getContext(), "Đăng nhập thất bại!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }
}