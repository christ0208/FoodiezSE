package com.example.index;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.index.Adapter.RecentSearchRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.Collections;

public class SearchActivity extends AppCompatActivity {

    ImageView btnBack;
    EditText txtSearch;
    RecyclerView recentSearchesRvView;

    ArrayList<String> recentSearches;

    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    RecentSearchRecyclerViewAdapter recentSearchRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        registerVariables();
        registerListeners();
        registerAdapters();
    }

    private void registerAdapters() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recentSearchesRvView.setLayoutManager(linearLayoutManager);
        recentSearchesRvView.setAdapter(recentSearchRecyclerViewAdapter);
    }

    private void registerListeners() {
        txtSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    insertIntoCache(txtSearch.getText().toString());
//                    Toast.makeText(SearchActivity.this, txtSearch.getText().toString(), Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(SearchActivity.this, SearchResultActivity.class);
                    i.putExtra("search", txtSearch.getText().toString());
                    startActivity(i);
                    recentSearchRecyclerViewAdapter.notifyDataSetChanged();
                    return true;
                }
                return false;
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchActivity.this.finish();
            }
        });
    }

    private void insertIntoCache(String searchKey) {
        ArrayList<String> recentSearches = getRecentSearches();
        if(!recentSearches.contains(searchKey)) {
            recentSearches.add(0, searchKey);
            this.recentSearches.add(0, searchKey);
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < recentSearches.size(); i++) {
            sb.append(recentSearches.get(i)).append(",");
        }
        editor.putString("searches", sb.toString());
        editor.commit();
    }

    private ArrayList<String> getRecentSearches() {
        Log.d("recentsearch", Boolean.toString(prefs.contains("searches")));
        if(!prefs.contains("searches")) return new ArrayList<>();
        else{
            String[] searches = prefs.getString("searches", null).split(",");
            ArrayList<String> al_searches = new ArrayList<>();
            for (String s :
                    searches) {
                Log.d("recentsearch", s);
                al_searches.add(s);
            }
            Collections.reverse(al_searches);
            return al_searches;
        }
    }

    private void registerVariables() {
        prefs = getSharedPreferences("RecentSearchPrefs", MODE_PRIVATE);
        editor = prefs.edit();

        btnBack = findViewById(R.id.btn_back);

        txtSearch = findViewById(R.id.txt_search);

        recentSearchesRvView = findViewById(R.id.recent_search_rv_view);
        recentSearchRecyclerViewAdapter = new RecentSearchRecyclerViewAdapter(recentSearches = getRecentSearches(), this);
    }


}
