package info.Zealandia.adapter;

/**
 * Created by Yar HTUT 21104216 on 4/04/2015.
 */


        import android.support.v4.app.Fragment;
        import android.support.v4.app.FragmentManager;
        import android.support.v4.app.FragmentStatePagerAdapter;

public class TabPagerAdapter  extends FragmentStatePagerAdapter{

    private int TOTAL_TABS = 3;

    public TabPagerAdapter(FragmentManager fm) {
        super(fm);
        // TODO Auto-generated constructor stub
    }

    @Override
    public Fragment getItem(int index) {
        // TODO Auto-generated method stub
        switch (index) {
            case 0:
               // return new TabFragmentBird();

            case 1:
               // return new TabFragmentInsect();

            case 2:
               // return new TabFragmentWaterPlants();
        }

        return null;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return TOTAL_TABS;
    }

}