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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GmailLoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GmailLoginFragment extends Fragment {

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
    private Button btnLogin;

    public GmailLoginFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GmailLoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GmailLoginFragment newInstance(String param1, String param2) {
        GmailLoginFragment fragment = new GmailLoginFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_gmail_login, container, false);
        textGmail = (EditText)rootView.findViewById(R.id.id_text_gmail);
        textPassword = (EditText)rootView.findViewById(R.id.id_text_password);
        btnLogin = (Button)rootView.findViewById(R.id.id_btn_gmail_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = textGmail.getText().toString().trim();
                String password = textPassword.getText().toString().trim();
                if(email.isEmpty()){
                    textGmail.setError("GMAIL REQUIRED");
                    return;
                }
                if(password.isEmpty()){
                    textPassword.setError("PASSWORD REQUIRED");
                    return;
                }
                userValidation(email,password);
            }
        });
        return rootView;
    }
    public void userValidation(String email,String password){
        btnLogin.setEnabled(false);
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = mAuth.getCurrentUser();
                    if(user!=null){
                        Toast.makeText(getActivity(),"LOGIN SUCCESSFUL" + "\nWelcome back "+user.getDisplayName(),Toast.LENGTH_SHORT).show();
                        Intent launchApp = new Intent(getContext(),MainApp.class);
                        startActivity(launchApp);
                    }
                }
                else{
                    btnLogin.setEnabled(true);
                    Toast.makeText(getActivity(),"LOGIN FAILED",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}