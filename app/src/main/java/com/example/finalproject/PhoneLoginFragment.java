package com.example.finalproject;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;

import java.util.concurrent.TimeUnit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PhoneLoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PhoneLoginFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FirebaseAuth mAuth;
    private EditText phoneNumber;
    private Button btnPhoneSendOTP;
    private CountryCodePicker countryCodePicker;
    private static String phoneNum;

    public PhoneLoginFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PhoneLoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PhoneLoginFragment newInstance(String param1, String param2) {
        PhoneLoginFragment fragment = new PhoneLoginFragment();
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
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_phone_login, container, false);
        phoneNumber = (EditText)rootView.findViewById(R.id.id_text_phone_login);
        countryCodePicker = (CountryCodePicker)rootView.findViewById(R.id.id_phone_login_code_picker);
        btnPhoneSendOTP = (Button)rootView.findViewById(R.id.id_btn_code_login);
        btnPhoneSendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNum = phoneNumber.getText().toString().trim().replaceAll("-","");
                phoneNum = phoneNum.replaceAll(" ","");
                if(phoneNum.isEmpty()){
                    phoneNumber.setError("PHONE # REQUIRED");
                    return;
                }
                if(phoneNum.length()!=10){
                    phoneNumber.setError("CORRECT PHONE # REQUIRED");
                    return;
                }
                phoneNum = countryCodePicker.getFullNumberWithPlus();
                LoginHost.navController.navigate(R.id.navigationPhoneLoginCode);
            }
        });
        countryCodePicker.registerCarrierNumberEditText(phoneNumber);
        return rootView;
    }
    public static String getPhoneNumber() {
        return phoneNum;
    }
}