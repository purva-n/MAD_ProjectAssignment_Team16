package com.example.atyourservice.togather;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.atyourservice.R;

public class HomePageActivity extends AppCompatActivity {

    private LinearLayout homeLayout;
    private LinearLayout exploreLayout;
    private LinearLayout chatLayout;
    private LinearLayout notificationLayout;
    private LinearLayout bannerLayout;

    private ImageView homeImg;
    private ImageView exploreImg;
    private ImageView chatImg;
    private ImageView notificationImg;
    private ImageView bannerImg;

    private TextView homeLabel;
    private TextView exploreLabel;
    private TextView chatLabel;
    private TextView notificationLabel;
    private TextView bannerLabel;

    private int selectedTab = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        homeLayout = findViewById(R.id.homeLayout);
        exploreLayout = findViewById(R.id.exploreLayout);
        chatLayout = findViewById(R.id.chatLayout);
        notificationLayout = findViewById(R.id.notificationLayout);
        bannerLayout = findViewById(R.id.bannerLayout);

        homeImg = findViewById(R.id.homeImg);
        exploreImg = findViewById(R.id.exploreImg);
        chatImg = findViewById(R.id.chatImg);
        notificationImg = findViewById(R.id.notificationImg);
        bannerImg = findViewById(R.id.bannerImg);

        homeLabel = findViewById(R.id.homeLabel);
        exploreLabel = findViewById(R.id.exploreLabel);
        chatLabel = findViewById(R.id.chatLabel);
        notificationLabel = findViewById(R.id.notificationLabel);
        bannerLabel = findViewById(R.id.bannerLabel);

        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragmentContainer, com.example.atyourservice.togather.HomeFragment.class, null)
                .commit();

        homeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedTab != 1) {

                    //Set Home Fragment as main screen
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fragmentContainer, com.example.atyourservice.togather.HomeFragment.class, null)
                            .commit();

                    //unselect other options
                    exploreLabel.setVisibility(View.GONE);
                    chatLabel.setVisibility(View.GONE);
                    notificationLabel.setVisibility(View.GONE);
                    bannerLabel.setVisibility(View.GONE);

                    exploreImg.setImageResource(R.drawable.explore_icon);
                    chatImg.setImageResource(R.drawable.chat_icon);
                    notificationImg.setImageResource(R.drawable.notification_icon);
                    bannerImg.setImageResource(R.drawable.banner_icon);

                    exploreLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    chatLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    notificationLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    bannerLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    //select Home
                    homeLabel.setVisibility(View.VISIBLE);
                    homeImg.setImageResource(R.drawable.home_selected_icon);
                    homeLayout.setBackgroundResource(R.drawable.round_back_home_100);

                    //create animation
                    ScaleAnimation animation = new ScaleAnimation(0.8f, 1.0f, 1f,1f, Animation.RELATIVE_TO_SELF,0.0f,Animation.RELATIVE_TO_SELF, 0.0f);
                    animation.setDuration(200);
                    animation.setFillAfter(true);
                    homeLayout.startAnimation(animation);

                    //change selected tab
                    selectedTab = 1;

                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fragmentContainer, com.example.atyourservice.togather.HomeFragment.class, null)
                            .commit();
                }
            }
        });

        exploreLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedTab != 2) {
                    //Set Explore Fragment as main screen
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fragmentContainer, ExploreFragment.class, null)
                            .commit();

                    //unselect other options
                    homeLabel.setVisibility(View.GONE);
                    chatLabel.setVisibility(View.GONE);
                    notificationLabel.setVisibility(View.GONE);
                    bannerLabel.setVisibility(View.GONE);

                    homeImg.setImageResource(R.drawable.home_icon);
                    chatImg.setImageResource(R.drawable.chat_icon);
                    notificationImg.setImageResource(R.drawable.notification_icon);
                    bannerImg.setImageResource(R.drawable.banner_icon);

                    homeLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    chatLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    notificationLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    bannerLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    //select Explore
                    exploreLabel.setVisibility(View.VISIBLE);
                    exploreImg.setImageResource(R.drawable.explore_selected_icon);
                    exploreLayout.setBackgroundResource(R.drawable.round_back_explore_100);

                    //create animation
                    ScaleAnimation animation = new ScaleAnimation(0.8f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
                    animation.setDuration(200);
                    animation.setFillAfter(true);
                    exploreLayout.startAnimation(animation);

                    //change selected tab
                    selectedTab = 2;
                }
            }
        });

        chatLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedTab != 3) {
                    //Set Chat Fragment as main screen
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fragmentContainer, com.example.atyourservice.togather.GroupChatFragment.class, null)
                            .commit();

                    //unselect other options
                    exploreLabel.setVisibility(View.GONE);
                    homeLabel.setVisibility(View.GONE);
                    notificationLabel.setVisibility(View.GONE);
                    bannerLabel.setVisibility(View.GONE);

                    exploreImg.setImageResource(R.drawable.explore_icon);
                    homeImg.setImageResource(R.drawable.home_icon);
                    notificationImg.setImageResource(R.drawable.notification_icon);
                    bannerImg.setImageResource(R.drawable.banner_icon);

                    exploreLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    homeLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    notificationLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    bannerLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    //select Chat
                    chatLabel.setVisibility(View.VISIBLE);
                    chatImg.setImageResource(R.drawable.chat_selected_icon);
                    chatLayout.setBackgroundResource(R.drawable.round_back_chat_100);

                    //create animation
                    ScaleAnimation animation = new ScaleAnimation(0.8f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
                    animation.setDuration(200);
                    animation.setFillAfter(true);
                    chatLayout.startAnimation(animation);

                    //change selected tab
                    selectedTab = 3;
                }
            }
        });

        notificationLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedTab != 4) {
                    //Set Notification Fragment as main screen
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fragmentContainer, com.example.atyourservice.togather.NotificationFragment.class, null)
                            .commit();

                    //unselect other options
                    exploreLabel.setVisibility(View.GONE);
                    chatLabel.setVisibility(View.GONE);
                    homeLabel.setVisibility(View.GONE);
                    bannerLabel.setVisibility(View.GONE);

                    exploreImg.setImageResource(R.drawable.explore_icon);
                    chatImg.setImageResource(R.drawable.chat_icon);
                    homeImg.setImageResource(R.drawable.home_icon);
                    bannerImg.setImageResource(R.drawable.banner_icon);

                    exploreLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    chatLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    homeLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    bannerLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    //select Notification
                    notificationLabel.setVisibility(View.VISIBLE);
                    notificationImg.setImageResource(R.drawable.notification_selected_icon);
                    notificationLayout.setBackgroundResource(R.drawable.round_back_notification_100);

                    //create animation
                    ScaleAnimation animation = new ScaleAnimation(0.8f, 1.0f, 1f,1f, Animation.RELATIVE_TO_SELF,0.0f,Animation.RELATIVE_TO_SELF, 0.0f);
                    animation.setDuration(200);
                    animation.setFillAfter(true);
                    notificationLayout.startAnimation(animation);

                    //change selected tab
                    selectedTab = 4;
                }
            }
        });

        bannerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(selectedTab != 5) {
                    //Set Banner Fragment as main screen
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fragmentContainer, com.example.atyourservice.togather.BannerFragment.class, null)
                            .commit();

                    //unselect other options
                    exploreLabel.setVisibility(View.GONE);
                    chatLabel.setVisibility(View.GONE);
                    notificationLabel.setVisibility(View.GONE);
                    homeLabel.setVisibility(View.GONE);

                    exploreImg.setImageResource(R.drawable.explore_icon);
                    chatImg.setImageResource(R.drawable.chat_icon);
                    notificationImg.setImageResource(R.drawable.notification_icon);
                    homeImg.setImageResource(R.drawable.home_icon);

                    exploreLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    chatLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    notificationLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    homeLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    //select Banner
                    bannerLabel.setVisibility(View.VISIBLE);
                    bannerImg.setImageResource(R.drawable.banner_selected_icon);
                    bannerLayout.setBackgroundResource(R.drawable.round_back_banner_100);

                    //create animation
                    ScaleAnimation animation = new ScaleAnimation(0.8f, 1.0f, 1f,1f, Animation.RELATIVE_TO_SELF,0.0f,Animation.RELATIVE_TO_SELF, 0.0f);
                    animation.setDuration(200);
                    animation.setFillAfter(true);
                    bannerLayout.startAnimation(animation);

                    //change selected tab
                    selectedTab = 5;
                }
            }
        });
    }
}