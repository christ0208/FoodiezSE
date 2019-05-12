package com.example.index.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.index.R;
import com.example.index.SearchResultActivity;

import java.util.ArrayList;

public class RecentSearchRecyclerViewAdapter extends RecyclerView.Adapter<RecentSearchViewHolder> {
    private ArrayList<String> recentSearches;
    private Context context;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    public RecentSearchRecyclerViewAdapter(ArrayList<String> recentSearches, Context context) {
        this.recentSearches = recentSearches;
        this.context = context;

        prefs = context.getSharedPreferences("RecentSearchPrefs", Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    @NonNull
    @Override
    public RecentSearchViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.recent_search_rv_item, viewGroup, false);
        RecentSearchViewHolder holder = new RecentSearchViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecentSearchViewHolder recentSearchViewHolder, int i) {
        final String currRecentSearch = recentSearches.get(i);
        recentSearchViewHolder.lblRecentSearch.setText(currRecentSearch);
        recentSearchViewHolder.lblRecentSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, SearchResultActivity.class);
                i.putExtra("search", currRecentSearch);
                context.startActivity(i);
            }
        });

        recentSearchViewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteFromCache(recentSearchViewHolder.lblRecentSearch.getText().toString());
            }
        });
    }

    private void deleteFromCache(String searchKey) {
        ArrayList<String> recentSearches = getRecentSearches();
        recentSearches.remove(searchKey);
        this.recentSearches.remove(searchKey);
//        Log.d("recentsearchinserted", searchKey);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < recentSearches.size(); i++) {
            sb.append(recentSearches.get(i)).append(",");
        }
        editor.putString("searches", sb.toString());
        editor.commit();
        this.notifyDataSetChanged();
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
            return al_searches;
        }
    }

    @Override
    public int getItemCount() {
        return (recentSearches.size() < 5) ? recentSearches.size() : 5;
    }
}

class RecentSearchViewHolder extends RecyclerView.ViewHolder{

    TextView lblRecentSearch;
    ImageView btnDelete;
    public RecentSearchViewHolder(@NonNull View itemView) {
        super(itemView);
        lblRecentSearch = itemView.findViewById(R.id.lbl_recent_search);
        btnDelete = itemView.findViewById(R.id.btn_delete);
    }
}
