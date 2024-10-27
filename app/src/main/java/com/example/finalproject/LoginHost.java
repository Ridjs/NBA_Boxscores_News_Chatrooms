package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Bundle;

public class LoginHost extends AppCompatActivity {
    static NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_host);
        navController = Navigation.findNavController(this,R.id.id_navHost_login_container);
        navController.navigate(R.id.navigationLoginOptions);
    }
}