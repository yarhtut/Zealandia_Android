/**
 * Created by 21104216 on 6/04/2015.
 */
package info.Zealandia;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import info.Zealandia.R;



   // navigation drawer class
public class NavigationDrawerFragment extends Fragment {

    private RecyclerView recyclerView;
    public ZViewAdapter adapter;


    public static final String PREF_FILE_NAME="testpref";
    public static final String KEY_USER_LEAENER_DRAWER="user_learned_drawer";

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private View containerView;

       //on create which manage boolean option for two activity
    private boolean mUserLearnedDrawer;
    private boolean mFromInstanceState;

    private String mParam1;
    private String mParam2;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types and number of parameters
    public static NavigationDrawerFragment newInstance(String param1, String param2) {
        NavigationDrawerFragment fragment = new NavigationDrawerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    public NavigationDrawerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserLearnedDrawer = Boolean.valueOf(readFromPreferences(getActivity(), KEY_USER_LEAENER_DRAWER, "false"));

        if(savedInstanceState !=null){
            mFromInstanceState = true;
        }

        /*if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout=inflater.inflate(R.layout.fragment_navigation_drawer, container, false);

        //recycler view for left navigation fragment
        recyclerView = (RecyclerView) layout.findViewById(R.id.drawerList);


        adapter = new ZViewAdapter(getActivity(),getData());
        recyclerView.setAdapter(adapter);
       recyclerView.setLayoutManager( new LinearLayoutManager(getActivity()));

        return layout;
    }

       //array for the recycler view
    public List<Information> getData()
    {
        //define array
        List<Information> data = new ArrayList<>();

        //icon for navigation drawer
        int[] icons= {android.R.drawable.ic_menu_gallery, android.R.drawable.ic_menu_myplaces};
        //list for navigation drawer
        String[] titles = {"Sanctuary View","Login"};
        //loop through the icon and list arry to show recycler view
        for(int i=0; i<titles.length && i<icons.length ;i++)
        {
            Information current = new Information();
            current.iconId = icons[i];
            current.title = titles[i];
            data.add(current);
        }
        return data;
    }
    //setup toobar , fragment to share in one activity

    public void setUp(int fragmentId,DrawerLayout drawerLayout, final Toolbar toolbar) {
        containerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {


            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if(!mUserLearnedDrawer){
                    mUserLearnedDrawer = true;
                    saveToPreferences(getActivity(), KEY_USER_LEAENER_DRAWER,mUserLearnedDrawer+"");
                }
                getActivity().invalidateOptionsMenu();
            }

            public void onDrawerClosed(View drawerView) {
               super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            public void onDrawerSlide(View drawerView, float slideOffset){
                super.onDrawerSlide(drawerView, slideOffset);
                if(slideOffset <0.6){
                    toolbar.setAlpha(1-slideOffset);
                }

            }
        };
        if(!mUserLearnedDrawer && !mFromInstanceState){
            mDrawerLayout.openDrawer(containerView);
        }
    mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
    }
    public static void saveToPreferences(Context context, String preferenceName, String preferenceValue){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(preferenceName,preferenceValue);
        editor.apply();
    }
    public static String readFromPreferences(Context context, String preferenceName, String defaultValue){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        return  sharedPreferences.getString(preferenceName, defaultValue);
    }

}
