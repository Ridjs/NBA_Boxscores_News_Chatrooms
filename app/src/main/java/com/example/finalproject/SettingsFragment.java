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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button setUsername;
    private Button setFavTeamName;
    private EditText editTextTeam;
    private EditText editTextUserName;
    private FirebaseAuth mAuth;

    public SettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);
        setFavTeamName = rootView.findViewById(R.id.btnSetTeam);
        editTextTeam = rootView.findViewById(R.id.editTextText3);
        editTextUserName = rootView.findViewById(R.id.editTextText2);
        setUsername = rootView.findViewById(R.id.btnSetUsername);
        setFavTeamName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Arrays.asList(GamesFragment.teams).contains(editTextTeam.getText().toString().trim())){
                    try{
                        File file = new File(getContext().getFilesDir(),mAuth.getUid()+".txt");
                        if(file.exists()){
                            file.delete();
                            file.createNewFile();
                        }
                        FileWriter fileWriter = new FileWriter(file);
                        fileWriter.write(editTextTeam.getText().toString().trim());
                        fileWriter.close();
                        TextView profileTeam = (TextView)MainApp.headerNav.findViewById(R.id.profile_team);
                        profileTeam.setText(editTextTeam.getText().toString().trim());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                else{
                    editTextTeam.setError("NBA TEAM REQUIRED");
                }
            }
        });
        setUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!editTextUserName.getText().toString().trim().isEmpty()){
                    FirebaseUser user = mAuth.getCurrentUser();
                    if(user!=null){
                        UserProfileChangeRequest profileName = new UserProfileChangeRequest.Builder()
                                .setDisplayName(editTextUserName.getText().toString().trim())
                                .build();
                        user.updateProfile(profileName).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(getContext(), "USERNAME UPDATED", Toast.LENGTH_SHORT).show();
                                    TextView profileName = (TextView)MainApp.headerNav.findViewById(R.id.profile_name);
                                    profileName.setText(user.getDisplayName());
                                }
                            }
                        });
                    }
                }
                else{
                    editTextUserName.setError("USERNAME REQUIRED");
                }
            }
        });
        return rootView;
    }
}