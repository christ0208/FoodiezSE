package com.example.index;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.index.ui.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Timer;
import java.util.TimerTask;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {
    @Rule
    public ActivityTestRule<LoginActivity> loginActivityActivityTestRule = new ActivityTestRule<>(LoginActivity.class);
    private String email = "foodiez@gmail.com";
    private String password = "asd";
    private FirebaseAuth mAuth;
    private LoginActivity loginActivity;

    @Test
    public void login(){
//        loginActivity = loginActivityActivityTestRule.getActivity();
//        loginActivity.findViewById();
        mAuth = FirebaseAuth.getInstance();
        onView(withId(R.id.txt_email)).perform(typeText(email), closeSoftKeyboard());
        onView(withId(R.id.txt_password)).perform(typeText(password), closeSoftKeyboard());
        onView(withId(R.id.btn_login)).perform(click());

//        new Timer().schedule(new PeriodicTask());
    }

}