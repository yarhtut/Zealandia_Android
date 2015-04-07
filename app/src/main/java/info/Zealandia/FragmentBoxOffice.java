package info.Zealandia;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.List;
import java.util.ArrayList;
import java.util.List;

import info.Zealandia.adapter.AdapterBirdRecyclerList;
import info.Zealandia.model.SanctuaryView;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Use the {@link FragmentBoxOffice#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentBoxOffice extends Fragment  {
    private RecyclerView recyclerView;
    public ZViewAdapter adapter;

    public static final String PREF_FILE_NAME="testpref";
    public static final String KEY_USER_LEAENER_DRAWER="user_learned_drawer";
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private View containerView;

    //The key used to store arraylist of movie objects to and from parcelable
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String url = "http://yar.cloudns.org/SlimApi/api/list/mobile/bird";
    private ProgressDialog pDialog;
    private ArrayList<SanctuaryView> birdListRe = new ArrayList<>();
    private List<SanctuaryView> birdList = new ArrayList<SanctuaryView>();
    private RecyclerView listBird;
    private AdapterBirdRecyclerList adapterBirdRecycler;
    //the sorter responsible for sorting our movie results based on choice made by the user in the FAB


    public FragmentBoxOffice() {
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
    public static FragmentBoxOffice newInstance(String param1, String param2) {
        FragmentBoxOffice fragment = new FragmentBoxOffice();
        Bundle args = new Bundle();
        //put any extra arguments that you may want to supply to this fragment
        fragment.setArguments(args);
        return fragment;
    }

    public void onMoviesLoaded() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout=inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        recyclerView = (RecyclerView) layout.findViewById(R.id.drawerList);

        adapter = new ZViewAdapter(getActivity(),getData());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager( new LinearLayoutManager(getActivity()));
        return layout;
    }

    public List<Information> getData()
    {
        List<Information> data = new ArrayList<>();
        int[] icons= {R.drawable.nextbtn, R.drawable.nextbtn, R.drawable.nextbtn, R.drawable.nextbtn,};
        String[] titles = {"Map","Login", "Sanctuary","Education"};
        for(int i=0; i<titles.length && i<icons.length ;i++)
        {
            Information current = new Information();
            current.iconId = icons[i];
            current.title = titles[i];
            data.add(current);
        }
        return data;
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
