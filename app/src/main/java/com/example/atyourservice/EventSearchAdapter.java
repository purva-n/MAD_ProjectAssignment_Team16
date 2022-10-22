package com.example.atyourservice;

import static com.example.atyourservice.App.Constants.eventsUser;

import android.content.Context;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.appbar.AppBarLayout;
import android.support.design.widget.Snackbar;
import androidx.core.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.ticketmaster.discovery.model.Event;
import com.ticketmaster.discovery.model.Venue;

import java.util.List;

public class EventSearchAdapter extends RecyclerView.Adapter<EventSearchAdapter.ViewHolder> {
    private List<Event> events;
    private Context context;
    private DatabaseReference db = App.Constants.database;

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView resultTitle;
        TextView resultVenue;
        TextView resultDate;
        ImageView resultImage;
        LinearLayout result;

        ViewHolder(View v) {
            super(v);
            resultTitle = v.findViewById(R.id.resultTitle);
            resultVenue = v.findViewById(R.id.resultVenue);
            resultDate = v.findViewById(R.id.resultDate);
            resultImage = v.findViewById(R.id.resultImage);
            result = v.findViewById(R.id.searchResult);
        }
    }

    public EventSearchAdapter(Context context, List<Event> events) {
        super();
        this.context = context;
        this.events = events;
    }

    @NonNull
    @Override
    public EventSearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_result, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final Event event = events.get(position);

        if (event != null) {
            View.OnClickListener onClick;
            holder.resultTitle.setTextColor(ContextCompat.getColor(this.context,
                    R.color.colorPrimaryDark));
            holder.resultTitle.setText(event.getName());
            List<Venue> venues = event.getVenues();
            holder.resultVenue.setText(venues.get(0).getName());
            java.util.Date date = App.Constants.parseDate(
                    event.getDates().getStart().getLocalDate());
            holder.resultDate.setText(App.Constants.df.format(date));
            ImageHelper.loadImage(event.getImages().get(0).getUrl(), holder.resultImage);

            onClick = v -> addEventDialog(event);
            holder.result.setOnClickListener(onClick);
            holder.resultImage.setOnClickListener(onClick);
        }
    }

    private void addEventDialog(final Event e) {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(context);
        builder.setMessage(R.string.addEventDialog);
        builder.setTitle(R.string.addEventTitle);

        builder.setPositiveButton(R.string.confirm, (dialog, which) -> {
            //code to add event
            com.dannydiiorio.eventhub.Model.Event event
                    = new com.dannydiiorio.eventhub.Model.Event();
            event.setTitle(e.getName());
            event.setCustomEvent(false);
            java.util.Date eventDate = App.Constants
                    .parseDate(e.getDates().getStart().getLocalDate());
            event.setEventDate(App.Constants.df.format(eventDate));
            event.setImgUrl(e.getImages().get(0).getUrl());
            event.setVenueName(e.getVenues().get(0).getName());
            event.setEventDateMillis(eventDate.getTime());
            event.setUrl(e.getUrl());
            String addy1 = e.getVenues().get(0).getAddress().getLine1();
            String addy2 = e.getVenues().get(0).getAddress().getLine2();
            if (addy2 != null) {
                event.setVenueAddress(addy1 + addy2);
            } else {
                event.setVenueAddress(addy1);
            }
            int eventHash = event.hashCode();
            event.setId(String.valueOf(eventHash));
            if (App.Constants.eventsAll.containsKey(event.getId())) {
                if (!eventsUser.contains(event)) {
                    eventsUser.add(event);
                    db.child("users/").child(App.Constants.currentUser.getUid()).child("events")
                            .child(event.getId()).setValue(true);
                    dialog.dismiss();
                    ((EventSearchActivity)context).finish();
                } else {
                    dialog.dismiss();
                    String alreadyExists = context.getResources().getString(R.string.alreadyExists);
                    Snackbar.make(((EventSearchActivity)context).findViewById(R.id.searchContent),
                            alreadyExists, Snackbar.LENGTH_LONG).show();
                }
            } else {
                App.Constants.eventsAll.put(event.getId(), event);
                eventsUser.add(event);
                db.child("events").child(event.getId()).setValue(event);
                db.child("users/").child(App.Constants.currentUser.getUid()).child("events")
                        .child(event.getId()).setValue(true);
                dialog.dismiss();
                ((EventSearchActivity)context).finish();
            }
        }).setNegativeButton(android.R.string.cancel,
                (dialog, which) -> dialog.dismiss()).show();
    }

    @Override
    public int getItemCount() {
        return events.size();
    }
}
