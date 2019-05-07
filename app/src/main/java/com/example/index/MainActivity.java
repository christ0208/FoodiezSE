package com.example.index;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.firebase.FirebaseApp;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;

    private HomeFragment homeFragment;
    private HistoryFragment historyFragment;
    private VoucherFragment voucherFragment;
    private FavoriteFragment favoriteFragment;
    private AccountFragment accountFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(this);

        mMainFrame = (FrameLayout) findViewById(R.id.main_frame);
        mMainNav = (BottomNavigationView) findViewById(R.id.main_nav);
        homeFragment = new HomeFragment();
        historyFragment = new HistoryFragment();
        voucherFragment = new VoucherFragment();
        favoriteFragment = new FavoriteFragment();
        accountFragment = new AccountFragment();
        setFragment(homeFragment);
        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.nav_home:
                        mMainNav.setItemBackgroundResource(R.color.colorAccent);
                        setFragment(homeFragment);
                        return true;

                    case R.id.nav_history:
                        mMainNav.setItemBackgroundResource(R.color.colorAccent);
                        setFragment(historyFragment);
                        return true;

                    case R.id.nav_voucher:
                        mMainNav.setItemBackgroundResource(R.color.colorAccent);
                        setFragment(voucherFragment);
                        return true;

                    case R.id.nav_favorite:
                        mMainNav.setItemBackgroundResource(R.color.colorAccent);
                        setFragment(favoriteFragment);
                        return true;

                    case R.id.nav_account:
                        mMainNav.setItemBackgroundResource(R.color.colorAccent);
                        setFragment(accountFragment);
                        return true;
                    default:
                            return false;
                }
            }
        });
    }
    private void setFragment(Fragment fragment)
    {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }
}