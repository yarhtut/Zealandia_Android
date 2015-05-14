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
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


import info.Zealandia.adapter.AdapterBirdRecyclerList;
import info.Zealandia.app.AppController;
import info.Zealandia.app.CacheHelper;
import info.Zealandia.model.SanctuaryView;
import info.Zealandia.R;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Use the {@link BirdView#newInstance} factory method to
 * create an instance of th fragment.
 */
public class OtherView extends Fragment  {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";




    private static final String TAG = BirdView.class.getSimpleName();
    private static final String url = "http://yar.cloudns.org/SlimApi/api/list/bird?mobile=1";
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


    public OtherView() {
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
    public static BirdView newInstance(String param1, String param2) {
        BirdView fragment = new BirdView();
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
        View view =  inflater.inflate(R.layout.fragment_other_view, container, false);


        RecyclerBird = (RecyclerView) view.findViewById(R.id.RecyclerListOther);

        RecyclerBird.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new AdapterBirdRecyclerList(getActivity());
        RecyclerBird.setAdapter(adapter);
        //Set adapter

        birdList = CacheHelper.getInstance().updateTabFromJSON("others");
        adapter.setListBird(birdList);
        adapter.notifyDataSetChanged();


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


