package com.example.q.pj1;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.io.File;
import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter {

    private Context mContext;
    private GridView mgridView;
    private ArrayList<String> files = new ArrayList<>();

    public ImageAdapter(Context c, GridView gridView) {
        mContext = c;
        mgridView = gridView;
        Uri uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = { MediaStore.MediaColumns.DATA, MediaStore.MediaColumns.DISPLAY_NAME };


        Cursor cursor = mContext.getContentResolver().query(uri, projection, null, null, MediaStore.MediaColumns.DATE_ADDED + " desc");
        int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        int columnDisplayname = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DISPLAY_NAME);

        int lastIndex;
        while (cursor.moveToNext())
        {
            String absolutePathOfImage = cursor.getString(columnIndex);
            String nameOfFile = cursor.getString(columnDisplayname);
            lastIndex = absolutePathOfImage.lastIndexOf(nameOfFile);
            lastIndex = lastIndex >= 0 ? lastIndex : nameOfFile.length() - 1;

            if (!TextUtils.isEmpty(absolutePathOfImage))
            {
                files.add(absolutePathOfImage);
            }
        }


//        path = Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS).toString();
//        File directory = new File(path);
//        files = directory.listFiles();
    }

    public final void callImageViewer(int selectedIndex){
        Intent i = new Intent(mContext, ImagePopup.class);
        //String imgPath = files.get(selectedIndex);
        i.putExtra("fileindex", Integer.toString(selectedIndex));
        mContext.startActivity(i);
    }

    public int getCount() {
        return files.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(mgridView.getColumnWidth(), mgridView.getColumnWidth()));
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView.setBackgroundColor(0xff000000);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        File imgFile = new  File(files.get(position));
//        Log.d("Files",path+files[position].getName());
//        Log.v("Files",imgFile.exists()+"");
        if(imgFile.exists()) {
//            Log.d("Files","sdfsdff");
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            imageView.setImageBitmap(myBitmap);
        }

        //imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }

//    public View getView(int position, View convertView, ViewGroup parent) {
//        ImageView imageView;
//        if (convertView == null) {
//            // if it's not recycled, initialize some attributes
//            imageView = new ImageView(mContext);
//        } else {
//            imageView = (ImageView) convertView;
//        }
//        bm = BitmapFactory.decodeFile(mBasePath + File.separator + mImgList[position]);
//        Bitmap mThumbnail = ThumbnailUtils.extractThumbnail(bm, 300, 300);
//        imageView.setPadding(8, 8, 8, 8);
//        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        imageView.setLayoutParams(new GridView.LayoutParams(GridView.LayoutParams.MATCH_PARENT, GridView.LayoutParams.MATCH_PARENT));
//        imageView.setImageBitmap(mThumbnail);
//        return imageView;
//    }
    // references to our images
//    private Integer[] mThumbIds = {
//            R.drawable.sample_2, R.drawable.sample_3,
//            R.drawable.sample_4, R.drawable.sample_5,
//            R.drawable.sample_6, R.drawable.sample_7,
//            R.drawable.sample_0, R.drawable.sample_1,
//            R.drawable.sample_2, R.drawable.sample_3,
//            R.drawable.sample_4, R.drawable.sample_5,
//            R.drawable.sample_6, R.drawable.sample_7,
//            R.drawable.sample_0, R.drawable.sample_1,
//            R.drawable.sample_2, R.drawable.sample_3,
//            R.drawable.sample_4, R.drawable.sample_5,
//            R.drawable.sample_6, R.drawable.sample_7
//
//    };
}