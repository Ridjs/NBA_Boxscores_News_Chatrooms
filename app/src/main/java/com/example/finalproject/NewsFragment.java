package com.example.finalproject;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    static RecyclerView recyclerView;
    static WebView webview;
    static Button btnBack;
    ArrayList<ArticleContainer> cardList = new ArrayList<ArticleContainer>();
    RecyclerViewAdapter adapter;

    public NewsFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewsFragment newInstance(String param1, String param2) {
        NewsFragment fragment = new NewsFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_news, container, false);
        new GetNews().execute();
        webview = rootView.findViewById(R.id.webview2);
        btnBack = rootView.findViewById(R.id.back);
        recyclerView = rootView.findViewById(R.id.recycler_view);
        adapter = new RecyclerViewAdapter(cardList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.setSmoothScrollbarEnabled(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setOnFlingListener(null);
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webview.setVisibility(View.INVISIBLE);
                btnBack.setVisibility(View.INVISIBLE);
            }
        });
        return rootView;
    }
    private class GetNews extends AsyncTask<Void,Void,Void> {
        String responseString;
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                responseString = "";
                URL apiCall = new URL("https://nba-stories.onrender.com/articles?limit=10");
                HttpURLConnection apiCallConnection = (HttpURLConnection)apiCall.openConnection();
                InputStream inputStream = apiCallConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String reader;
                while ((reader = bufferedReader.readLine()) != null) {
                    responseString += reader;
                }
                bufferedReader.close();
                inputStream.close();
                apiCallConnection.disconnect();
            }catch(IOException e){
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            Log.d("JSON",responseString);
            try{
                JSONArray articlesArray = new JSONArray(responseString);
                for (int i = 0; i < articlesArray.length(); i++) {
                    JSONObject articleObject = articlesArray.getJSONObject(i);
                    cardList.add(new ArticleContainer(articleObject.getString("title"),articleObject.getString("url")));
                }
                adapter.notifyDataSetChanged();
            }catch(JSONException e){
                cancel(true);
            }
        }
    }
    static class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

        static ArrayList<ArticleContainer> cardList;

        public RecyclerViewAdapter(ArrayList<ArticleContainer> cardList) {
            this.cardList = cardList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.bind(cardList.get(position));
        }

        @Override
        public int getItemCount() {
            return cardList.size();
        }
        static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            private final int[] players = new int[]{R.drawable.curry,R.drawable.jordan,R.drawable.lebron,R.drawable.wade_lebron,R.drawable.rivalry,R.drawable.shaq,R.drawable.shaq_2,R.drawable.duncan,R.drawable.dunk,R.drawable.goat,R.drawable.iverson,R.drawable.kareem,R.drawable.kobe,R.drawable.memes};
            private final TextView textView;
            private final ImageView imageView;
            private final Context context;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                textView = itemView.findViewById(R.id.text_view);
                imageView = itemView.findViewById(R.id.imageView7);
                context = itemView.getContext();
                itemView.setOnClickListener(this);
            }
            public void onClick(View v) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    ArticleContainer articleContainer = cardList.get(position);
                    NewsFragment.webview.getSettings().setJavaScriptEnabled(true);
                    NewsFragment.webview.setWebViewClient(new WebViewClient());
                    NewsFragment.webview.loadUrl(articleContainer.getUrl());
                    NewsFragment.webview.setVisibility(View.VISIBLE);
                    NewsFragment.btnBack.setVisibility(View.VISIBLE);
                }
            }
            public void bind(ArticleContainer articleContainer) {
                textView.setText(articleContainer.getArticleTitle());
                imageView.setImageResource(players[(int)(Math.random()*players.length)]);
            }
        }
    }
    static class ArticleContainer{
        private final String articleTitle;
        private final String url;
        public ArticleContainer(String articleTitle, String url) {
            this.articleTitle = articleTitle;
            this.url = url;
        }
        public String getArticleTitle() {
            return articleTitle;
        }
        public String getUrl() {
            return url;
        }
    }
}