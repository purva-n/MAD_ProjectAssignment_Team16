package com.example.atyourservice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.atyourservice.api.response.pojo.Date;
import com.example.atyourservice.api.response.pojo.Dates;
import com.example.atyourservice.api.response.pojo.Embedded;
import com.example.atyourservice.api.response.pojo.Events;
import com.example.atyourservice.api.response.pojo.Images;

import java.util.ArrayList;

public class ActivityRecyclerView extends AppCompatActivity {
    private Embedded linkList;
    RecyclerView linkRecycleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        linkList = new Embedded();
        linkList.setEvents(new ArrayList<Events>() {
            {
                add(new Events() {
                    {
                        setName("Concert ABC");
                        setType("event");
                        setId("music_1");
                        setUrl("http://ticketmaster.com");
                        setImages(new ArrayList<Images>() {
                            {
                                add(new Images() {
                                    {
                                        setRatio("4_3");
                                        setWidth(400);
                                        setHeight(300);
                                        setUrl("https://s1.ticketm.net/dam/a/c62/0636ff21-e369-4b8c-bee4-214ea0a81c62_1339761_CUSTOM.jpg");
                                    }
                                });

                                add(new Images() {
                                    {

                                        setRatio("16_9");
                                        setWidth(560);
                                        setHeight(320);
                                        setUrl("https://s1.ticketm.net/dam/a/c62/0636ff21-e369-4b8c-bee4-214ea0a81c62_1339761_RETINA_PORTRAIT_16_9.jpg");
                                    }
                                });
                            }
                        });

                        setDates(new Dates() {
                            {
                                setStart(new Date() {
                                    {
                                        setLocalDate("2022-10-25");
                                        setLocalTime("19:00:00");
                                    }
                                });
                            }
                        });
                    }
                });

                add(new Events() {
                    {
                        setName("Football Match DEF");
                        setType("event");
                        setId("dgsfbfb");
                        setUrl("http://ticketmaster.com");
                        setImages(new ArrayList<Images>() {
                            {
                                add(new Images() {
                                    {
                                        setRatio("4_3");
                                        setWidth(400);
                                        setHeight(300);
                                        setUrl("https://s1.ticketm.net/dam/a/c62/0636ff21-e369-4b8c-bee4-214ea0a81c62_1339761_CUSTOM.jpg");
                                    }
                                });

                                add(new Images() {
                                    {
                                        setRatio("16_9");
                                        setWidth(560);
                                        setHeight(320);
                                        setUrl("https://s1.ticketm.net/dam/a/c62/0636ff21-e369-4b8c-bee4-214ea0a81c62_1339761_RETINA_PORTRAIT_16_9.jpg");
                                    }
                                });
                            }
                        });

                        setDates(new Dates() {
                            {
                                setStart(new Date() {
                                    {
                                        setLocalDate("2022-11-25");
                                        setLocalTime("19:01:00");
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });

        linkRecycleView = findViewById(R.id.recyclerView);
        linkRecycleView.setLayoutManager(new LinearLayoutManager(ActivityRecyclerView.this));
        linkRecycleView.setAdapter(new EventLinksAdapter(ActivityRecyclerView.this, linkList));
    }
}