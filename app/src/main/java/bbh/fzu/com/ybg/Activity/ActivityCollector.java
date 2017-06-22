package bbh.fzu.com.ybg.Activity;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 2017/6/18.
 */

public class ActivityCollector {
    //Activity活动管理器

    public static List<Activity> activities = new ArrayList<>();

    public static void addActivity(Activity activity){
        activities.add(activity);
    }

    public static void removeActivity(Activity activity){
        activities.remove(activity);
    }

    public static void finishAll(){
        for (Activity activity:activities){
            if (!activity.isFinishing()){
                activity.finish();
            }
        }
        activities.clear();
    }
}
