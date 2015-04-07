package info.Zealandia;

import android.app.Application;
import android.content.Context;

/**
 * Created by 21104216 on 30/03/2015.
 */
public class MyApplication extends Application {
    private static MyApplication sInstance;

    public void onCreate(){
        super.onCreate();
        sInstance=this;
    }

    public static MyApplication getsInstance(){
        return sInstance;
    }
    public static Context getAppContext(){
        return sInstance.getApplicationContext();
    }
}


