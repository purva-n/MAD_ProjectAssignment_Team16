package com.example.atyourservice;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atyourservice.api.response.pojo.Embedded;

import java.io.InputStream;
import java.net.URL;

public class EventLinksAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context context;
    private final Embedded embeddedResponse;
    private final int VIEW_EVENTS_LIST = 1;
    private final int VIEW_EVENTS_LOADER = 0;

    public EventLinksAdapter(Context context, Embedded embeddedResponse) {
        this.context = context;
        this.embeddedResponse = embeddedResponse;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_EVENTS_LIST) {
             return new EventLinksViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_recycler_template, null));
        } else {
            return new EventsLoader(LayoutInflater.from(context).inflate(R.layout.activity_events_loader, null));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if(viewHolder instanceof EventLinksViewHolder) {
            EventLinksViewHolder holder = (EventLinksViewHolder) viewHolder;
            String name = embeddedResponse.getEvents().get(position).getName();
            String link = embeddedResponse.getEvents().get(position).getUrl();
            String eventDate = embeddedResponse.getEvents().get(position).getDates().getStart().getLocalDate();
            String eventTime = embeddedResponse.getEvents().get(position).getDates().getStart().getLocalTime();

            String hyperLink = "<a href='" + link + "'>" + name + "</a>";

            holder.eventName.setText(Html.fromHtml(hyperLink));
            holder.eventDate.setText(eventDate);
            holder.eventTime.setText(eventTime);
            holder.itemView.setOnClickListener(view -> {
                Uri uri = Uri.parse(link);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(uri);
                context.startActivity(intent);
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        return embeddedResponse.getEvents().get(position) == null ? VIEW_EVENTS_LOADER : VIEW_EVENTS_LIST;
    }

    @Override
    public int getItemCount() {
        return embeddedResponse.getEvents().size();
    }

    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            return null;
        }
    }
}