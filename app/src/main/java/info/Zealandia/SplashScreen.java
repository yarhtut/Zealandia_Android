package info.Zealandia;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;
import java.util.HashMap;
import info.Zealandia.R;
import info.Zealandia.app.CacheHelper;
import info.Zealandia.dbhelper.SQLiteHandler;


/**
 * Created by 21104216 on 2/04/2015.
 */

public class SplashScreen extends Activity {
    private static int SPLASH_SCREEN_DELAY = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        try{

            ConnectivityManager cm =
                    (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

            boolean isConnected = activeNetwork != null &&
                    activeNetwork.isConnectedOrConnecting();

            if (isConnected == true ) {
                Log.i("APP_TAG", "Wi-Fi - CONNECTED");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Executed after timer is finished (Opens MainActivity)
                        Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                        startActivity(intent);
                        //we will try download a JSON file
                       // CacheHelper.getInstance().getAllList();

                    }

                }, SPLASH_SCREEN_DELAY);
            } else {
                Log.i("APP_TAG", "NO-INTERNET CONNECTION");
                new AlertDialog.Builder(this)
                        .setTitle("NO-INTERNET-CONNECTION")
                        .setMessage("Do you want to connect Wifi or 3G?" )
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do something

                               Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                                startActivity(intent);
                                //CacheHelper.getInstance().getAllList();
                                System.exit(0);


                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                                // Kills this Activity

                                finish();
                                System.exit(0);
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .show();



            }
        }
        catch(Exception e ){
                    e.printStackTrace();
        }
        CacheHelper.getInstance().getAllList();

    }
    public void showMessage(String title)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
      //  builder.setMessage(message);
        builder.show();
    }
}

