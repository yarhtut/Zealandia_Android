package info.Zealandia;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import info.Zealandia.fragment.InsectView;
import info.Zealandia.fragment.MammalView;
import info.Zealandia.fragment.OtherView;
import info.Zealandia.fragment.PlantView;
import info.Zealandia.tabs.SlidingTabLayout;

public class MainActivity extends ActionBarActivity {

    public Toolbar toolbar;
    private ViewPager mPager;
    private SlidingTabLayout mTabs;
    public static final int TAB_BIRD = 0;
    //int corresponding to our 1st tab corresponding to the Fragment where  hits are dispalyed
    public static final int TAB_INSECT = 1;
    //int corresponding to our 2nd tab corresponding to the Fragment where upcoming  are displayed
    public static final int TAB_PLANTS = 2;
    //int corresponding to the number of tabs in our Activity
    public static final int TAB_MAMMALS= 3;
    //int corresponding to the number of tabs in our Activity
    public static final int TAB_OTHERS = 4;
    //int corresponding to the number of tabs in our Activity
    public static final int TAB_COUNT = 5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // changing action bar color

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationDrawerFragment drawerLayout = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);

        drawerLayout.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

        mTabs= (SlidingTabLayout) findViewById(R.id.tabs);
        mTabs.setDistributeEvenly(true);
        mTabs.setSelectedIndicatorColors(getResources().getColor(R.color.accent));
        mTabs.setBackgroundColor(getResources().getColor(R.color.primary_tab));


        //mTabs.setCustomTabView(R.layout.custom_tab_view,R.id.tabText);
        mTabs.setViewPager(mPager);


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            Toast.makeText(this, "Hay you just click" + item.getTitle(), Toast.LENGTH_SHORT).show();
            return true;
        }

        if (id == R.id.navigate) {
            startActivity(new Intent(this, SchoolActivity.class));
            // Toast.makeText(this,"This is my navigation action bar click" + item.getTitle(),Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    class MyPagerAdapter extends FragmentPagerAdapter{

        int icons[] = {R.drawable.bird,
                R.drawable.insect,
                R.drawable.plant};

        FragmentManager fragmentManager;

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
            fragmentManager = fm;
        }

        public Fragment getItem(int num) {
            //  return MyFragment.getInstance(num);
            Fragment fragment = null;
            //L.m("getItem called for " + num);
            switch (num) {
                case TAB_BIRD:
                    fragment =  info.Zealandia.fragment.BirdView.newInstance("", "");
                    break;
                case TAB_INSECT:
                    fragment =  InsectView.newInstance("", "");
                    break;
                case TAB_PLANTS:
                    fragment = PlantView.newInstance("", "");
                    break;
                case TAB_MAMMALS:
                    fragment = MammalView.newInstance("", "");
                    break;
                case TAB_OTHERS:
                    fragment = OtherView.newInstance("", "");
                    break;
            }
            return fragment;

        }

        @Override
        public CharSequence getPageTitle(int position) {
            return getResources().getStringArray(R.array.tabs)[position];
        }

        private Drawable getIcon(int position) {
            return getResources().getDrawable(icons[position]);
        }
        @Override
        public int getCount() {
            return  TAB_COUNT;
        }
    }


}
