package com.example.android_app_shop.View;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.android_app_shop.Controller.AccountHandler;
import com.example.android_app_shop.R;


public class Frag_Login extends Fragment {

    Button btnLogin ;
    EditText getUsername , getPassword;
    TextView NavRegister;
    AccountHandler accountHandler ;

    public void LoadFragment(String username , String password){
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
    }

    public void LoginAuto(){
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("us" , null);
        String password = sharedPreferences.getString("pw" , null);

        if(!(username == null &&  password == null))
        {
            LoadFragment(username,password ); // truyền vào để nó truyền data này vào fragment được mở lên
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_login, container, false);
        getUsername = view.findViewById(R.id.inputUsername);
        getPassword = view.findViewById(R.id.inputPassWord);
        btnLogin = view.findViewById(R.id.btnLogin);
        NavRegister = view.findViewById(R.id.NavRegister);

        LoginAuto();
        accountHandler = new AccountHandler( getContext(), "smartphone.db", null, 1);
        accountHandler.initData();

        NavRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.frameFragment, new Frag_Register()).commit();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String username = getUsername.getText().toString();
                String password = String.valueOf(getPassword.getText().toString());

                if (accountHandler.checkUser(username, password)) {
                    Toast.makeText(getContext(), "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();

                    SharedPreferences sharedPreferences = getContext().getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    editor.putString("us" , username);
                    editor.putString("pw" , password);
                    editor.commit();

                    LoadFragment(username,password); // truyền vào để nó truyền data này vào fragment được mở lên

                } else {
                    Toast.makeText(getContext(), "Đăng nhập thất bại!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }
}