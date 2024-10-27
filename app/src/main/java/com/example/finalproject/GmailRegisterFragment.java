package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GmailRegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GmailRegisterFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FirebaseAuth mAuth;
    private EditText textGmail;
    private EditText textPassword;
    private EditText textUsername;
    private Button btnRegister;

    public GmailRegisterFragment() {
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GmailRegisterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GmailRegisterFragment newInstance(String param1, String param2) {
        GmailRegisterFragment fragment = new GmailRegisterFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_gmail_register, container, false);
        textGmail = (EditText)rootView.findViewById(R.id.id_text_mail_register);
        textUsername = (EditText)rootView.findViewById(R.id.id_text_gmail_username_register);
        textPassword = (EditText)rootView.findViewById(R.id.id_text_gmail_password_register);
        btnRegister = (Button)rootView.findViewById(R.id.id_btn_gmail_register);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String gmail = textGmail.getText().toString().trim();
                String password = textPassword.getText().toString().trim();
                String username = textUsername.getText().toString().trim();
                if(gmail.isEmpty()){
                    textGmail.setError("GMAIL REQUIRED");
                    return;
                }
                if(password.isEmpty()){
                    textPassword.setError("PASSWORD REQUIRED");
                    return;
                }
                if(username.isEmpty()){
                    textUsername.setError("USERNAME REQUIRED");
                    return;
                }
                createUser(gmail, password, username);
            }
        });
        return rootView;
    }
    public void createUser(String gmail,String password, String username){
        btnRegister.setEnabled(false);
        mAuth.createUserWithEmailAndPassword(gmail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = mAuth.getCurrentUser();
                    if(user!=null){
                        UserProfileChangeRequest profileName = new UserProfileChangeRequest.Builder()
                                .setDisplayName(username)
                                .build();
                        user.updateProfile(profileName).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(getActivity(),"REGISTRATION SUCCESSFUL" + "\nWelcome " + username,Toast.LENGTH_SHORT).show();
                                    Intent launchApp = new Intent(getContext(),MainApp.class);
                                    startActivity(launchApp);
                                }
                            }
                        });
                    }
                }
                else{
                    btnRegister.setEnabled(true);
                    Toast.makeText(getActivity(),"REGISTRATION FAILED",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}