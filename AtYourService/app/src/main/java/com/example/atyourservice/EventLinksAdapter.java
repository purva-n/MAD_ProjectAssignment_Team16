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

public class EventLinksAdapter extends RecyclerView.Adapter<EventLinksViewHolder> {

    private final Context context;
    private final Embedded embeddedResponse;

    public EventLinksAdapter(Context context, Embedded embeddedResponse) {
        this.context = context;
        this.embeddedResponse = embeddedResponse;
    }

    @NonNull
    @Override
    public EventLinksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new EventLinksViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_recycler_template, null));
    }

    @Override
    public void onBindViewHolder(@NonNull EventLinksViewHolder holder, int position) {
        String name = embeddedResponse.getEvents().get(position).getName();
        String link = embeddedResponse.getEvents().get(position).getUrl();

        String hyperLink = "<a href='" + link + "'>" + name + "</a>";

        holder.eventName.setText(Html.fromHtml(hyperLink));
        holder.itemView.setOnClickListener(view -> {
            Uri uri = Uri.parse(link);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(uri);
            context.startActivity(intent);

        });


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
