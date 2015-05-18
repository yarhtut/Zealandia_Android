/**
 * Created by 21104216 on 2/04/2015.
 */

package info.Zealandia;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import info.Zealandia.adapter.BirdAdapter;
import info.Zealandia.app.AppConfig;
import info.Zealandia.app.AppController;
import info.Zealandia.app.CacheHelper;

import info.Zealandia.dbhelper.SQLiteHandler;
import info.Zealandia.dbhelper.SessionManager;

import info.Zealandia.model.SanctuaryView;
import info.Zealandia.R;

public class SanctuaryActivity extends ActionBarActivity {
  // private static final String TAG = MainActivity.class.getSimpleName();
  //  private static final String url = "http://yar.cloudns.org/SlimApi/api/list/all?mobile=1";
    private ProgressDialog pDialog;
    private List<SanctuaryView> birdList = new ArrayList<SanctuaryView>();
    private ListView listView;
    private BirdAdapter adapter;
    private TextView catId;
    private String CLICKED;

    private int _catId;


    private SQLiteHandler db;
   // private ActivitySQLiteHandler activityDb;
    private SessionManager session;

    public Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sanctuary);

        toolbar = (Toolbar) findViewById(R.id.app_bar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // School Activity Database SqLite database handler
       // activityDb = new ActivitySQLiteHandler(getApplicationContext());

        // session manager
        session = new SessionManager(getApplicationContext());

        //SQLiteHandler dbtest = new SQLiteHandler(getApplicationContext());

       // HashMap<String, String> getUserDetails = db.getUserDetails();
       // Log.d("SPLASH_SCREEN", "Fetching user detials SQLite: " + getUserDetails.toString());

        if (!session.isLoggedIn()) {
            logoutUser();
        }
        listView = (ListView) findViewById(R.id.list);
        birdList = CacheHelper.getInstance().updateTabFromJSON("active");

        adapter = new BirdAdapter(this, birdList);
        listView.setAdapter(adapter);

        pDialog = new ProgressDialog(this);

        // Showing progress dialog before making http request
        //pDialog.setMessage("Loading...");
        //  pDialog.show();

        adapter.notifyDataSetChanged();



         listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


        @Override public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {


             catId = (TextView) view.findViewById(R.id.textViewID);
             _catId = Integer.parseInt(catId.getText().toString());
            //db.insectCategoriesId(_catId);
            // String CLICKED = db.getUpdateClicked(_catId);
            //  StringBuffer buffer=new StringBuffer();
            // Toast.makeText(SanctuaryActivity.this,CLICKED, Toast.LENGTH_SHORT).show();
            //showMessage("Student Clicked", CLICKED.toString());

            //http://stackoverflow.com/questions/2115758/how-to-display-alert-dialog-in-android

            new AlertDialog.Builder(SanctuaryActivity.this)
                    .setTitle("ARE YOU SURE?")
                    .setMessage("Do you want to add this Categories into your lists? " + " " +  _catId )
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do something
                            db.insectCategoriesId(_catId);
                            CLICKED = db.getUpdateClicked(_catId);

                            //showMessage("You have  Clicked", CLICKED);
                           // showMessage(db.getUserDetailsAsJson(), db.getResults().toString());

                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing

                        }
                    })
                    .setIcon(android.R.drawable.ic_notification_overlay)
                    .show();
        }
        });

        }

    /**
     *
     * Function to get userid and json clicked  send User activity data
     */

    private  void sentUserData(){

        final String  userDetails = db.getUserDetailsAsJson() ;
        final String  data = db.getResults();
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("userDetails", userDetails);
        params.put("data", data);


        StringRequest strReq = new StringRequest(Request.Method.GET,
                AppConfig.URL_SEND_USER_DATA + "&userDetails=" + userDetails.toString()+ "&data="+ data.toString(), new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("TestSent", "Login Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);

                    Toast.makeText(getApplicationContext(),
                            response, Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
               // hideDialog();
            }
        }) {

            @Override
            protected HashMap<String, String> getParams() {
                // Posting parameters to login url
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("userDetails", userDetails);
                params.put("data", data);
                return params;
            }
        };
        // Adding request to request queue
        Log.d("TestSent", "string request: " + AppConfig.URL_SEND_USER_DATA + "&userDetails=" + userDetails.toString()+ "&data="+ data.toString());
        AppController.getInstance().addToRequestQueue(strReq, null);
    }


   // http://developer.android.com/training/monitoring-device-state/connectivity-monitoring.html
    public void  checkNetworkStatus(){

        ConnectivityManager cm =
                (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        boolean isWiFi = activeNetwork.getType() == ConnectivityManager.TYPE_WIFI;
        boolean isMobile = activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE;


        if (isWiFi) {

                Log.i("APP_TAG", "Wi-Fi - CONNECTED");



        } else if (isMobile) {

                Log.i("APP_TAG", "Mobile - CONNECTED");

        } else {
            Log.i("APP_TAG", "Wi-Fi - DISCONNECTED");
        }

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sub, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            return true;
        }
        if (id == R.id.syncData) {
            sentUserData();

            db.deleteActivityTable();
            // Toast.makeText(this,"This is my navigation action bar click" + item.getTitle(),Toast.LENGTH_LONG).show();
            return true;
        }
        if (id == R.id.logout_menu) {
            logoutUser();
            // Toast.makeText(this,"This is my navigation action bar click" + item.getTitle(),Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    /**
     * Logging out the user. Will set isLoggedIn flag to false in shared
     * preferences Clears the user data from sqlite users table
     * */
    private void logoutUser() {

       session.setLogin(false);

        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(SanctuaryActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
    public void showMessage(String title,String message)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
