package com.example.q.pj1;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;

import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;
import static android.support.v4.content.ContextCompat.checkSelfPermission;


public class PageOne extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    //private ArrayList<AddressData> mMyData;
    private JSONArray Book;

    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    private static final int PERMISSIONS_REQUEST_WRITE_CONTACTS = 5;

    //private FloatingActionButton mFloat;
    public  static final int PERMISSIONS_MULTIPLE_REQUEST = 123;

    private static final int REQUEST_READ_CONTACTS = 11;

    public static PageOne newInstance() {
        Bundle args = new Bundle();
        PageOne fragment = new PageOne();
        //fragment.Book = new JSONArray();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        //checkPermission();
        //initDataset(this.getContext(),this.getActivity());
        //Book = new JSONArray();


        //setContentView(R.layout.);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_page_one, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);

        Log.d("Book", "onCreateView");
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

       // Book = new JSONArray();

        mAdapter = new PageOneAdapter(this.getContext(), this.getActivity());
        mRecyclerView.setAdapter(mAdapter);

        //mFloat = view.findViewById(R.id.floatingButton);
        // mFloat.setOnClickListener(this);

        return view;
    }
/*
    public void onClick(View v){
        int id = v.getId();

        switch (id){
            case R.id.floatingButton :
                Log.d("aaaaaaaa","click");

        }

    }
*/


/*
    public void initDataset(Context context, Activity activity) {

        //mMyData = new ArrayList<AddressData>();

        Book = new JSONArray();

        Log.d("initDataset", "start!!!!!!");
        //Book = new JSONArray();


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            Log.d("initDataset", "request permission");


            try {
                // Android version is lesser than 6.0 or the permission is already granted.
                Log.d("initDataset", "read contacts");

                ContentResolver cr = context.getContentResolver();
                Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                        null, null, null, null);

                Log.d("print full query", "" + cur.getCount());


                if ((cur != null ? cur.getCount() : 0) > 0) {
                    while (cur != null && cur.moveToNext()) {
                        String id = cur.getString(
                                cur.getColumnIndex(ContactsContract.Contacts._ID));
                        String name = cur.getString(
                               cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));


                        int photo_id = cur.getInt(cur.getColumnIndex(ContactsContract.Contacts.PHOTO_ID));
                        String photo_base64 = queryContactImage(photo_id, context);

                        String email = null;
                        Cursor ce = cr.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
                                ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?", new String[]{id}, null);
                        if (ce != null && ce.moveToFirst()) {
                            email = ce.getString(ce.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                            ce.close();
                        }
                        //Log.d("Get email", email);

                        String phoneNo = null;
                        if (cur.getInt(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                            Cursor pCur = cr.query(
                                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                    null,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                                    new String[]{id}, null);
                            while (pCur.moveToNext()) {
                                phoneNo = pCur.getString(pCur.getColumnIndex(
                                        ContactsContract.CommonDataKinds.Phone.NUMBER));
                            }
                            pCur.close();
                        }

                        JSONObject person_info = new JSONObject();
                        person_info.put("name", name);
                        person_info.put("phoneNo", phoneNo);
                        person_info.put("email", email);
                        person_info.put("photo", photo_base64);

                        Book.put(person_info);
                        Log.d("addressBoot", person_info.toString());
                    }
                }

                if (cur != null) {
                    cur.close();
                }

                //  Fragment frag = this;
                String jsonSt = Book.toString();
                Log.d("Print11", "printing Book");
                Log.d("Book11", jsonSt);
                Log.d("Book11", "" + Book.length());

               /* for(int i = 0; i < Book.length(); i++){
                    Log.d("Book access get", Book.get(i).toString());
                }

                for(int i = 0; i < Book.length(); i++){
                    if(Book.getJSONObject(i).get("name") == null)
                        Log.d("Book11 name : ", "null") ;
                    else
                      Log.d("Book11 name : ", "" + Book.getJSONObject(i).get("name").toString());

                }
/*
                for(int i = 0; i < Book.length(); i++){
                    Log.d("Book11 name2 : ", "" + Book.get(i).toString());

                }*/
     /*           if(getFragmentManager() != null) {
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.detach(this);
                    ft.attach(this);
                    ft.commit();
                }
            } catch (JSONException e) {
                System.out.print("json exception");
            }

            String jsonSt = Book.toString();
            Log.d("Print", "printing Book");
            Log.d("Book", jsonSt);

        }
    }
*/
/*
    private String queryContactImage(int imageDataRow, Context context) {
        Cursor c =context.getContentResolver().query(ContactsContract.Data.CONTENT_URI,
                new String[] {ContactsContract.CommonDataKinds.Photo.PHOTO},
                ContactsContract.Data._ID + "=?",
                new String[] {Integer.toString(imageDataRow)},
                null);
        byte[] imageBytes = null;
        if (c != null) {
            if (c.moveToFirst()) {
                imageBytes = c.getBlob(0);
            }
            c.close();
        }


        if (imageBytes != null) {
            return Base64.encodeToString(imageBytes, Base64.DEFAULT);
        } else {
            return null;
        }
    }



    public void setOneAdapter(Context context, Activity activity) {
        /*if (gridView != null) {
            ia = new ImageAdapter(context, gridView, activity);
            gridView.setAdapter(ia);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView parent, View v, int position, long id) {
                    ia.callImageViewer(position);
                }
            });
            Log.d("shitshit", "shitshit");
        }
        return;*/

        //mAdapter = new PageOneAdapter(context, activity);


}
