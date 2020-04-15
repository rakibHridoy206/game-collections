package com.example.gamecollections;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements LoginFragment.UserAuthListener, LoginFragment.SignupListener, SignupFragment.OnAddUserSuccessListener, HomePageFragment.PCGamesListener, HomePageFragment.AndroidGamesListener {

    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        LoginFragment loginFragment = new LoginFragment();
        fragmentTransaction.add(R.id.fragmentContainer, loginFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onLoginSuccessful() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        HomePageFragment homePageFragment = new HomePageFragment();
        fragmentTransaction.replace(R.id.fragmentContainer, homePageFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onSignupClickSuccess() {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        SignupFragment signupFragment = new SignupFragment();
        ft.replace(R.id.fragmentContainer, signupFragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void onAddUserSuccessfull() {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        LoginFragment loginFragment = new LoginFragment();
        ft.replace(R.id.fragmentContainer, loginFragment);
        ft.commit();
    }

    @Override
    public void onPCGamesClickSuccessful() {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        PCGamesFragment pcGamesFragment = new PCGamesFragment();
        ft.replace(R.id.fragmentContainer, pcGamesFragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void onAndroidGamesClickSuccess() {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        AndroidGamesFragment androidGamesFragment = new AndroidGamesFragment();
        ft.replace(R.id.fragmentContainer, androidGamesFragment);
        ft.addToBackStack(null);
        ft.commit();
    }
}