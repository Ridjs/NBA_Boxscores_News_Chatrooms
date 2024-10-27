package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PhoneLoginCodeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PhoneLoginCodeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FirebaseAuth mAuth;
    private String verificationCode;
    private Button btnPhoneLoginVerify;
    private EditText phoneLoginCode;

    public PhoneLoginCodeFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PhoneLoginCodeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PhoneLoginCodeFragment newInstance(String param1, String param2) {
        PhoneLoginCodeFragment fragment = new PhoneLoginCodeFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_phone_login_code, container, false);
        sendVerificationCode(PhoneLoginFragment.getPhoneNumber());
        phoneLoginCode = (EditText)rootView.findViewById(R.id.id_text_code_login);
        btnPhoneLoginVerify = (Button)rootView.findViewById(R.id.id_btn_verify_login);
        btnPhoneLoginVerify.setEnabled(false);
        btnPhoneLoginVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = phoneLoginCode.getText().toString().trim();
                if(code.isEmpty()){
                    phoneLoginCode.setError("CODE REQUIRED");
                    return;
                }
                if(code.length()!=6){
                    phoneLoginCode.setError("6 DIGIT CODE REQUIRED");
                    return;
                }
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCode,code);
                signInWithCredentials(credential);
            }
        });
        return rootView;
    }
    public void sendVerificationCode(String phoneNum){
        PhoneAuthOptions.Builder builder = PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber(phoneNum)
                .setTimeout(120L, TimeUnit.SECONDS)
                .setActivity(getActivity())
                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        signInWithCredentials(phoneAuthCredential);
                    }
                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                        Log.d("FireBase Message",e.getMessage());
                    }
                    @Override
                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(s, forceResendingToken);
                        btnPhoneLoginVerify.setEnabled(true);
                        verificationCode = s;
                    }
                });
        PhoneAuthProvider.verifyPhoneNumber(builder.build());
    }
    public void signInWithCredentials(PhoneAuthCredential phoneAuthCredential){
        mAuth.signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = mAuth.getCurrentUser();
                    if(user!=null){
                        if(task.getResult().getAdditionalUserInfo().isNewUser()){
                            LoginHost.navController.navigate(R.id.navigationPhoneRegisterUserName);
                        }
                        else{
                            Toast.makeText(getActivity(),"LOGIN SUCCESSFUL" + "\nWelcome back " + user.getDisplayName(),Toast.LENGTH_SHORT).show();
                            Intent launchApp = new Intent(getContext(),MainApp.class);
                            startActivity(launchApp);
                        }
                    }
                }
                else{
                    if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                        Toast.makeText(getActivity(), "Invalid verification code", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}