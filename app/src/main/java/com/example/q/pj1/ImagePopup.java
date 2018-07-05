package com.example.q.pj1;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ImagePopup extends AppCompatActivity {

    private Context mContext = null;
    private final int imgWidth = 320;
    private final int imgHeight = 372;
    private ViewPager mViewPager;
    private CustomAdapter adapter2;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_popup);
        mContext = this;

        CustomAdapter adapter= new CustomAdapter(this);
        adapter2 = adapter;
        //mSectionsPagerAdapter = new ImagePopup.SectionsPagerAdapter(getSupportFragmentManager());
        // Set up the ViewPager with the sections adapter.

        /** 전송메시지 */
        Intent i = getIntent();
        Bundle extras = i.getExtras();
        int index = Integer.parseInt(extras.getString("fileindex"));

        mViewPager = (ViewPager) findViewById(R.id.container2);
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(index);

//        mViewPager.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.d("fuck","fuck");
//                Toast.makeText(mContext, "File name", Toast.LENGTH_SHORT).show();
//                return ;
//            }
//        });

//        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeToRefresh2);
//        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
//        mSwipeRefreshLayout.setDistanceToTriggerSync(150);
//        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                mSwipeRefreshLayout.setRefreshing(false);
//                finish();
//            }
//        });
//        //mViewPager.setAdapter(mSectionsPagerAdapter);


//
//        /** 완성된 이미지 보여주기  */
////        BitmapFactory.Options bfo = new BitmapFactory.Options();
////        bfo.inSampleSize = 2;
//        ImageView iv = (ImageView)findViewById(R.id.imageView2);
//        iv.setBackgroundColor(0xff000000);
//        iv.setPadding(8, 8, 8, 8);
//        Bitmap bm = BitmapFactory.decodeFile(imgPath);
//        //Bitmap resized = Bitmap.createScaledBitmap(bm, imgWidth, imgHeight, true);
//        iv.setImageBitmap(bm);
//        iv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        /** 리스트로 가기 버튼 */
//        Button btn = (Button)findViewById(R.id.button2);
//        btn.setBackgroundColor((Color.TRANSPARENT));
//        btn.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View view) {
//                Log.d("fuck","fuck");
//                Toast.makeText(mContext, "File name", Toast.LENGTH_SHORT).show();
//                return false;
//            }
//        });

    }
    /* (non-Javadoc)
     * @see android.view.View.OnClickListener#onClick(android.view.View)
     */
//    public void onLongClick(View v) {
//        switch(v.getId()){
//            case R.id.button:
//                finish();
//        }
//    }

}
