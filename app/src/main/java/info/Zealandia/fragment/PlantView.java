package info.Zealandia.fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import info.Zealandia.R;
import info.Zealandia.adapter.AdapterBirdRecyclerList;
import info.Zealandia.app.AppController;
import info.Zealandia.model.SanctuaryView;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Use the {@link info.Zealandia.fragment.PlantView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlantView extends Fragment  {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";




    private static final String TAG = PlantView.class.getSimpleName();
    private static final String url =  "http://yar.cloudns.org/SlimApi/api/list/plants?mobile=1";
    private ProgressDialog pDialog;
    private ArrayList<SanctuaryView> birdList = new ArrayList<SanctuaryView>();
    private AppController myVolleySingleton;
    private RecyclerView RecyclerBird;
    public AdapterBirdRecyclerList adapter;



    @Override
    public String toString() {
        return super.toString();
    }


   // private BirdAdapter adapter;

    //   private BirdAdapter adapter;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //the sorter responsible for sorting our movie results based on choice made by the user in the FAB


    public PlantView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentBoxOffice.
     */
    // TODO: Rename and change types and number of parameters
    public static PlantView newInstance(String param1, String param2) {
        PlantView fragment = new PlantView();
        Bundle args = new Bundle();
        //put any extra arguments that you may want to supply to this fragment
        fragment.setArguments(args);
        return fragment;
    }

    public void onMoviesLoaded() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_bird_view, container, false);


        RecyclerBird = (RecyclerView) view.findViewById(R.id.RecyclerListBird);

        RecyclerBird.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new AdapterBirdRecyclerList(getActivity());
        RecyclerBird.setAdapter(adapter);
        adapter.setListBird(birdList);

        // Creating volley request obj
        final JsonArrayRequest plantReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        hidePDialog();



                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {


                                JSONObject obj = response.getJSONObject(i);
                                SanctuaryView bird = new SanctuaryView();
                                bird.setList_name(obj.getString("list_name"));
                                bird.setList_img(obj.getString("list_img"));
                                bird.setList_desc(obj.getString("list_desc"));
                                bird.setList_points(obj.getString("list_points"));

                              //  getData().add(bird);
                               // Log.d("Inbox JSON: ", response().toString());

                                // adding movie to movies array
                                //movieList.add(movie);
                                birdList.add(bird);
                                Log.d("Bird JSON:", birdList.toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hidePDialog();

            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(plantReq);


        return view;
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //save the movie list to a parcelable prior to rotation or configuration change
        //  outState.putParcelableArrayList(STATE_MOVIES, mListMovies);
    }
}

/**

 @Override
 public void onSortByName() {
 //  mSorter.sortMoviesByName(mListMovies);
 //  mAdapter.notifyDataSetChanged();
 }

 @Override
 public void onSortByDate() {
 //  mSorter.sortMoviesByDate(mListMovies);
 //  mAdapter.notifyDataSetChanged();
 }


 @Override
 public void onSortByRating() {
 //   mSorter.sortMoviesByRating(mListMovies);
 //  mAdapter.notifyDataSetChanged();
 }
 */
/**
 * Called when the AsyncTask finishes load the list of movies from the web
 */
    /*
    @Override
    public void onBoxOfficeMoviesLoaded(ArrayList<Movie> listMovies) {
        L.m("FragmentBoxOffice: onBoxOfficeMoviesLoaded Fragment");
        //update the Adapter to contain the new movies downloaded from the web
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        mAdapter.setMovies(listMovies);
    }

    @Override
    public void onRefresh() {
        L.t(getActivity(), "onRefresh");
        //load the whole feed again on refresh, dont try this at home :)
        new TaskLoadMoviesBoxOffice(this).execute();

    }
}*/
