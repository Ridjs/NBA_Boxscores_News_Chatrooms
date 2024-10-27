package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;

import java.io.File;

public class MainApp extends AppCompatActivity {

    float scale;
    ImageButton navigationButton;
    DrawerLayout mainScreenLayout;
    NavigationView navigationView;
    NavController navController;
    CardView cardViewGames;
    CardView cardViewNews;
    CardView cardViewChat;
    CardView cardViewSettings;
    CardView prevCardView;
    TextView profileName;
    TextView profileInfo;
    static View headerNav;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_app);
        mAuth = FirebaseAuth.getInstance();
        scale = getApplicationContext().getResources().getDisplayMetrics().density;
        mainScreenLayout = (DrawerLayout)findViewById(R.id.id_main_screen_layout);
        cardViewGames = (CardView)findViewById(R.id.id_card_game);
        cardViewNews = (CardView)findViewById(R.id.id_card_news);
        cardViewChat = (CardView)findViewById(R.id.id_card_chat);
        cardViewSettings = (CardView)findViewById(R.id.id_card_settings);
        navigationView = (NavigationView)findViewById(R.id.id_navigation_view);
        navController = Navigation.findNavController(this,R.id.navHostFragment);
        NavigationUI.setupWithNavController(navigationView,navController);
        navigationButton = (ImageButton)findViewById(R.id.id_navigation_button);
        headerNav = navigationView.getHeaderView(0);
        profileName = (TextView)headerNav.findViewById(R.id.profile_name);
        profileInfo = (TextView)headerNav.findViewById(R.id.profile_email);
        TextView profileTeam = (TextView)headerNav.findViewById(R.id.profile_team);
        FirebaseUser user = mAuth.getCurrentUser();
        if(user!=null){
            profileName.setText(user.getDisplayName());
            profileTeam.setText(GamesFragment.favTeam);
            if (user.getEmail()!= null) {
                profileInfo.setText(user.getEmail());
            } else if (user.getPhoneNumber()!=null) {
                profileInfo.setText(user.getPhoneNumber());
            }
        }
        navigationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainScreenLayout.openDrawer(GravityCompat.START);
            }
        });
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController navController, @NonNull NavDestination navDestination, @Nullable Bundle bundle) {
                if(navDestination.getId()==R.id.navigationGames)
                    OnClickCards(cardViewGames);
                else if(navDestination.getId()==R.id.navigationNews)
                    OnClickCards(cardViewNews);
                else if(navDestination.getId()==R.id.navigationChat)
                    OnClickCards(cardViewChat);
                else
                    OnClickCards(cardViewSettings);
            }
        });
        OnClickCards(cardViewGames);
    }
    public void OnClickCards(View view){
        if(prevCardView!=(CardView)view){
            int dp = (int) (5 * scale + 0.5f);
            if(prevCardView!=null){
                prevCardView.setContentPadding(0,0,0,0);
                prevCardView.setRadius((int) (20 * scale + 0.5f));
                prevCardView.setCardElevation(dp);
            }
            CardView card = (CardView)view;
            card.setContentPadding(dp,dp,dp,dp);
            card.setRadius(0);
            card.setCardElevation((int) (8 * scale + 0.5f));
            prevCardView = card;
            if(card==cardViewGames)
                navController.navigate(R.id.navigationGames);
            else if(card==cardViewNews)
                navController.navigate(R.id.navigationNews);
            else if(card==cardViewChat)
                navController.navigate(R.id.navigationChat);
            else
                navController.navigate(R.id.navigationSettings);
        }
    }
}