package com.example.q.pj1;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;



public class MainActivity extends AppCompatActivity{

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private FloatingActionButton mFloat;

    //  public  static final int PERMISSIONS_MULTIPLE_REQUEST = 123;

    private static int PERMISSIONS_REQUEST_WRITE_CONTACTS = 1111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        mFloat = findViewById(R.id.floatingButton);
        //  mFloat.setOnClickListener(this);

        mFloat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                makePopUp();
            }
        });

        //checkPermission();


    }


    public void makePopUp(){
        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
        Add_Contact fragment1 = new Add_Contact();
        trans.add(R.id.popup_frag, fragment1, "addContact");

        trans.addToBackStack(null);

        trans.commit();
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).

            //return PlaceholderFragment.newInstance(position + 1);

            if (position == 0){
               /* FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                PageOne pageone = PageOne.newInstance();
                ft.add(R.id.page_fragment, pageone);
                ft.commit();
                return pageone;*/
                return PageOne.newInstance();

            }
            else if (position == 1)
                return PageTwo.newInstance();
            else
                return PageThree.newInstance();

        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

    }

    public void pageOne_Update(){
        PageOne pageone = (PageOne) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.container + ":" + mViewPager.getCurrentItem());
        Log.v("mainactivity", "aa");

        if(pageone == null)
            Log.v("mainactivity", "null");

        pageone.initDataset();

    }


}