package com.example.finalproject;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginOptionsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginOptionsFragment extends Fragment{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    CardView cardPhoneLogin;
    CardView cardPhoneRegister;
    CardView cardGmailLogin;
    CardView cardGmailRegister;

    public LoginOptionsFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginOptionsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginOptionsFragment newInstance(String param1, String param2) {
        LoginOptionsFragment fragment = new LoginOptionsFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_login_options, container, false);
        cardGmailLogin = rootView.findViewById(R.id.id_card_gmail_login);
        cardGmailRegister = rootView.findViewById(R.id.id_card_gmail_register);
        cardPhoneLogin = rootView.findViewById(R.id.id_card_phone_login);
        cardPhoneRegister = rootView.findViewById(R.id.id_card_phone_register);
        cardGmailLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginHost.navController.navigate(R.id.navigationGmailLogin);
            }
        });
        cardGmailRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginHost.navController.navigate(R.id.navigationGmailRegister);
            }
        });
        cardPhoneLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginHost.navController.navigate(R.id.navigationPhoneLogin);
            }
        });
        cardPhoneRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginHost.navController.navigate(R.id.navigationPhoneLogin);
            }
        });
        return rootView;
    }
}