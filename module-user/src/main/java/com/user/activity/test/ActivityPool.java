package com.user.activity.test;

import android.app.Activity;

import java.util.ArrayList;

public class ActivityPool {

    public static ActivityPool activityPool = new ActivityPool();
    public static ArrayList<Activity> activities = new ArrayList<>();

    public static ActivityPool getActivity() {
        return activityPool;
    }

    public static void addActivity(Activity activity) {
        activities.add(activity);
    }
}