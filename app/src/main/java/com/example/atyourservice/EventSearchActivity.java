package com.example.atyourservice;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;
import com.ticketmaster.api.discovery.DiscoveryApi;
import com.ticketmaster.api.discovery.operation.SearchEventsOperation;
import com.ticketmaster.api.discovery.response.PagedResponse;
import com.ticketmaster.discovery.model.Events;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class EventSearchActivity extends AppCompatActivity {
    private static final String tmApiKey = "WMqw4xi5StCjkwj6c1ifQnxlmVuBGxDw";
    private static final String TAG = "LOGTAG";
    private EditText citySearch;
    private String city;
    private String keyword;
    private String eventType;
    private DiscoveryApi discoveryApi;
    private TextView noResults;
    private ProgressDialog progDialog;
    protected DatabaseReference db;
    protected RecyclerView recyclerView;
    protected List<Event> tmEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_search);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        CircleImageView profile = findViewById(R.id.profileImage);
        Picasso.get()
                .load(App.Constants.profileImage)
                .placeholder(R.drawable.empty_profile)
                .into(profile);

        setUpEventTypeSpinner();
        db = App.Constants.database;
        tmEvents = new ArrayList<>();
        noResults = findViewById(R.id.noResultsMsg);
        recyclerView = findViewById(R.id.searchResultsRecyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL));

        discoveryApi = new DiscoveryApi(tmApiKey);
        citySearch = findViewById(R.id.citySearch);
        final SearchView keywordSearch = findViewById(R.id.keywordSearch);

        citySearch.requestFocus();
        keywordSearch.setSubmitButtonEnabled(true);
        keywordSearch.setIconified(false);
        keywordSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (eventType == null) {
                    String typeMsg = getResources().getString(R.string.chooseType);
                    Snackbar.make(findViewById(android.R.id.content),
                            typeMsg, Snackbar.LENGTH_LONG).show();
                    return false;
                } else {
                    keyword = query;
                    city = (!citySearch.getText().toString().equals(""))
                            ? citySearch.getText().toString() : "Vancouver";
                    new Ticketmaster().execute();
                    keywordSearch.clearFocus();
                    return true;
                }
            }

            @Override
            public boolean onQueryTextChange(String newText) { return false; }
        });
    }

    private void setUpEventTypeSpinner() {
        MaterialBetterSpinner spinner = findViewById(R.id.eventType);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.eventTypesArray, android.R.layout.simple_dropdown_item_1line);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                eventType = spinner.getText().toString();
            }
        } );
        spinner.setAdapter(adapter);
    }

    private class Ticketmaster extends AsyncTask<Void, Void, List<Event>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progDialog = new ProgressDialog(EventSearchActivity.this);
            if (!isFinishing()) {
                progDialog.setMessage("Searching...");
                progDialog.setIndeterminate(false);
                progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progDialog.setCancelable(true);
                try {
                    progDialog.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        protected List<Event> doInBackground(Void... voids) {
            PagedResponse<Events> page = null;
            try {
                page = discoveryApi.searchEvents(new SearchEventsOperation()
                        .city(city)
                        .keyword(keyword)
                        .pageSize(12)
                        .sort("date,asc")
                );
            } catch (IOException e) {
                e.printStackTrace();
            }
            tmEvents = (page != null && page.getContent() != null)
                    ? page.getContent().getEvents() : Collections.emptyList();
            return tmEvents;
        }

        @Override
        protected void onPostExecute(List<Event> events) {
            if (progDialog != null && progDialog.isShowing()) {
                progDialog.dismiss();
                progDialog = null;
            }
            if (!events.isEmpty()) {
                List<Event> results = new ArrayList<>();
                if (eventType.equals("All")) {
                    results = events;
                } else {
                    //filter results to only show ones that match event type from spinner
                    for (Event e : events) {
                        if (e.getClassifications().get(0).getSegment().getName().contains(eventType)) {
                            results.add(e);
                        }
                    }
                }
                if (!results.isEmpty()) {
                    if (recyclerView.getVisibility() != View.VISIBLE) {
                        recyclerView.setVisibility(View.VISIBLE);
                        noResults.setVisibility(LinearLayout.GONE);
                    }
                    RecyclerView.Adapter myAdapter =
                            new EventSearchAdapter(EventSearchActivity.this, results);
                    recyclerView.setAdapter(myAdapter);
                    myAdapter.notifyDataSetChanged();
                } else {
                    if (recyclerView.getVisibility() != View.INVISIBLE) {
                        recyclerView.setVisibility(View.GONE);
                        noResults.setVisibility(LinearLayout.VISIBLE);
                    }
                }
            } else {
                if (recyclerView.getVisibility() != View.INVISIBLE) {
                    recyclerView.setVisibility(View.GONE);
                    noResults.setVisibility(LinearLayout.VISIBLE);
                }
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(progDialog != null) {
            progDialog.dismiss();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(progDialog != null) {
            progDialog.dismiss();
        }
    }
}