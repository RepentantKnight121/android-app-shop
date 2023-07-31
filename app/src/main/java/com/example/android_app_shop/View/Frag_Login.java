package com.example.android_app_shop.View;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.android_app_shop.R;


public class Frag_Login extends Fragment {
    Button btnLogin ;
    EditText getUsername , getPassword;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_login, container, false);
        getUsername = view.findViewById(R.id.inputUsername);
        getPassword = view.findViewById(R.id.inputPassWord);
        btnLogin = view.findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = getUsername.getText().toString();
                String password = getPassword.getText().toString();

                if (username.equals("admin") && password.equals("123")) {
                    // Login successful
                    Toast.makeText(getContext(), "Login successful!", Toast.LENGTH_SHORT).show();

                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    // Replace fragment_Login with fragment_Register
                    fragmentManager.beginTransaction().replace(R.id.frameFragment, new Frag_Register()).commit();
                } else {
                    // Login failed
                    Toast.makeText(getContext(), "Login failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        // Inflate the layout for this fragment
        return view;
    }
}