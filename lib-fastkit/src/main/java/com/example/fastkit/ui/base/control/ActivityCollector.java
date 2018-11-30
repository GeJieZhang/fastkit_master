package com.example.fastkit.ui.base.control;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

public class ActivityCollector {

    public static List<Activity> activities = new ArrayList<Activity>();

    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    public static void finishAll() {
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }


    public static void toMainActivity() {
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {

                if (!activity.getLocalClassName().equals("MainActivity")) {
                    activity.finish();
                }

            }
        }
    }
}