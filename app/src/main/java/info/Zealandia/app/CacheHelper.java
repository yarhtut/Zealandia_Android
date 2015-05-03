package info.Zealandia.app;

import android.app.ProgressDialog;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import info.Zealandia.model.SanctuaryView;

/**
 * Created by Luke Hardiman on 3/05/2015.
 */
public class CacheHelper {
    //We are going to use singleton
    private static CacheHelper _instance;

    private static AppController controller;
    ProgressDialog pDialog;

    private CacheHelper()
    {

    }
    //help for singleton
    public static CacheHelper getInstance()
    {
        if (_instance == null)
        {
            _instance = new CacheHelper();
            controller = AppController.getInstance();
        }

        return _instance;
    }

    //This will update tab based on json response
    public ArrayList<SanctuaryView> updateTabFromJSON(String theCat){
        //retrieve file
        String theResponse = AppController.getInstance().readFromCache("alldata");
        ArrayList<SanctuaryView> theList = new ArrayList<SanctuaryView>();
        JSONArray response = null;
        try {
            response = new JSONArray(theResponse);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Parsing json
        for (int i = 0; i < response.length(); i++) {
            try {

                JSONObject obj = response.getJSONObject(i);


                if (obj.getString("list_cat").equals(theCat) || (theCat.equals("active") && obj.getString("list_active").equals("1"))) {

                    SanctuaryView theView = new SanctuaryView();

                    theView.setList_name(obj.getString("list_name"));
                    theView.setList_img(obj.getString("list_img"));
                    theView.setList_desc(obj.getString("list_desc"));
                    theView.setList_points(obj.getString("list_points"));

                    theList.add(theView);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        Log.d("updateTabFromJSON JSON:", theList.toString());
        return theList;
    }

    /*
     * Gets all the list from json and stores it, need this is run on SPLASHSCREEN LOAD
     */
    final public void getAllList()
    {
        String url = "http://yar.cloudns.org/SlimApi/api/list/all?mobile=1";

        // Creating volley request obj
        final JsonArrayRequest plantReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Log.d(TAG, response.toString());
                        AppController.getInstance().writeToFile("alldata",response.toString());
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("CacheHelper", "Error: " + error.getMessage());


            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(plantReq);
    }



}
