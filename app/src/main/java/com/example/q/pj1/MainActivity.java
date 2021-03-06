package com.example.q.pj1;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


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
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
    private PageOne pageone ;
    private PageTwo pagetwo ;
    private PageThree pagethree;

    private PointerDrawable pointer = new PointerDrawable();
    private boolean isTracking;
    private boolean isHitting;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private FloatingActionButton mFloat;

    //  public  static final int PERMISSIONS_MULTIPLE_REQUEST = 123;

    private static int PERMISSIONS_REQUEST_WRITE_CONTACTS = 1111;
    int PERMISSION_ALL = 1;

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        if (requestCode == PERMISSION_ALL) {

            if (grantResults.length > 0) {
                for (int i = 0; i < permissions.length; i++) {
//TODO:문제있음
                    if (permissions[i].equals(Manifest.permission.READ_CONTACTS)) {
                        if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                            //PageOne pg1 = (PageOne) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.container + ":" + mViewPager.getCurrentItem());
                            //pageone.initDataset(this, this);
                            pageOne_Update();

                        }
                    } else if (permissions[i].equals(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                            //PageTwo pg2 = (PageTwo) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.container + ":" + mViewPager.getCurrentItem());

                            Log.d("pageone!!", pagetwo+"");

                            pagetwo.setimageadpater(this, this);
                            //pagetwo.ia = new ImageAdapter(this, this);
                        }
                    }
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        savedInstanceState = null;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //permission 확인하고 requeset


        pageone = PageOne.newInstance();
        pagetwo = PageTwo.newInstance();
        pagethree = PageThree.newInstance();

        Log.d("pageone!!", pagetwo+"");

        String[] PERMISSIONS = {Manifest.permission.READ_CONTACTS, Manifest.permission. READ_EXTERNAL_STORAGE};

        if(!hasPermissions(this, PERMISSIONS)){
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tabLayout.getSelectedTabPosition()){
                    case 0:
                        mFloat.show();
                        break;
                    case 1:
                    case 2:
                        mFloat.hide();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        //TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        tabLayout.setBackgroundColor(Color.GRAY);
        //tabLayout.setupWithViewPager(mViewPager);
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            if(i==0)
                tabLayout.getTabAt(i).setIcon(R.drawable.phonebook);
            if(i==1)
                tabLayout.getTabAt(i).setIcon(R.drawable.gallery);
            if(i==2)
                tabLayout.getTabAt(i).setIcon(R.drawable.ar);
        }
        mFloat = findViewById(R.id.floatingButton);
        //  mFloat.setOnClickListener(this);



        mFloat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // if(tabLayout.getSelectedTabPosition() == 0)
                    makePopUp();
            }
        });



        //checkPermission();

    }
    @Override
    protected void onStart(){

        super.onStart();
    }

    @Override
    protected  void onStop(){
        super.onStop();
    }

    @Override
    protected void onResume(){
        super.onResume();
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
    }
//onrequestPermissionresult(){ fragrment findfragmentbyID ().initDataset

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
                //pageone = PageOne.newInstance();
                Log.d("fffffff","pageone instance");
                return pageone;

            }
            else if (position == 1) {
                //pagetwo = PageTwo.newInstance();
                return pagetwo;
            }
            else
                return pagethree;

        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

    }

    public void pageOne_Update() {  //TODO:error viewpager current item

        PageOne pageone = (PageOne) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.container + ":" + mViewPager.getCurrentItem());
        Log.v("mainactivity", "aa");

        if (pageone == null)
            Log.v("mainactivity", "null");

        //pageone.initDataset(this, this);
        // if(pageone.getFragmentManager() != null) {
        FragmentTransaction ft = pageone.getFragmentManager().beginTransaction();
        ft.detach(pageone);
        ft.attach(pageone);
        ft.commit();


    }
}


