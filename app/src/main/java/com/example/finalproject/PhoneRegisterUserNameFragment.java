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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PhoneRegisterUserNameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PhoneRegisterUserNameFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FirebaseAuth mAuth;
    private EditText textUsername;
    private Button btnLogin;

    public PhoneRegisterUserNameFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PhoneRegisterUserNameFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PhoneRegisterUserNameFragment newInstance(String param1, String param2) {
        PhoneRegisterUserNameFragment fragment = new PhoneRegisterUserNameFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_phone_register_user_name, container, false);
        textUsername = (EditText) rootView.findViewById(R.id.id_text_username_phone_register);
        btnLogin = (Button)rootView.findViewById(R.id.id_btn_register_phone);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = textUsername.getText().toString().trim();
                if(username.isEmpty()){
                    textUsername.setError("USERNAME REQUIRED");
                    return;
                }
                FirebaseUser user = mAuth.getCurrentUser();
                if(user!=null){
                    UserProfileChangeRequest profileName = new UserProfileChangeRequest.Builder()
                            .setDisplayName(username)
                            .build();
                    user.updateProfile(profileName).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(getActivity(),"REGISTRATION SUCCESSFUL" + "\nWelcome " + user.getDisplayName(),Toast.LENGTH_SHORT).show();
                                Intent launchApp = new Intent(getContext(),MainApp.class);
                                startActivity(launchApp);
                            }
                        }
                    });
                }
            }
        });
        return rootView;
    }
}