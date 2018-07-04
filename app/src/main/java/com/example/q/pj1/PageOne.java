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
    private JSONArray addressBook;

    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    private static final int PERMISSIONS_REQUEST_WRITE_CONTACTS = 5;

    //private FloatingActionButton mFloat;
    public  static final int PERMISSIONS_MULTIPLE_REQUEST = 123;

    private static final int REQUEST_READ_CONTACTS = 11;

    public static PageOne newInstance() {
        Bundle args = new Bundle();
        PageOne fragment = new PageOne();
        //fragment.addressBook = new JSONArray();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        //checkPermission();
        initDataset(this.getContext(),this.getActivity());
        //addressBook = new JSONArray();


        //setContentView(R.layout.);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_page_one, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);

        Log.d("addressBook", "onCreateView");
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

       // addressBook = new JSONArray();

        mAdapter = new PageOneAdapter(addressBook);
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



    public void initDataset(Context context, Activity activity) {

        //mMyData = new ArrayList<AddressData>();

        addressBook = new JSONArray();

        Log.d("initDataset", "start!!!!!!");
        //addressBook = new JSONArray();


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            Log.d("initDataset", "request permission");


            try {
                // Android version is lesser than 6.0 or the permission is already granted.
                Log.d("initDataset", "read contacts");

                ContentResolver cr = context.getContentResolver();
                Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                        null, null, null, null);

                if ((cur != null ? cur.getCount() : 0) > 0) {
                    while (cur != null && cur.moveToNext()) {
                        String id = cur.getString(
                                cur.getColumnIndex(ContactsContract.Contacts._ID));
                        String name = cur.getString(cur.getColumnIndex(
                                ContactsContract.Contacts.DISPLAY_NAME));

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


                        if (cur.getInt(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                            Cursor pCur = cr.query(
                                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                    null,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                                    new String[]{id}, null);
                            while (pCur.moveToNext()) {
                                String phoneNo = pCur.getString(pCur.getColumnIndex(
                                        ContactsContract.CommonDataKinds.Phone.NUMBER));

                            /*Log.i(TAG, "Name: " + name);
                            Log.i(TAG, "Phone Number: " + phoneNo);
                            mMyData.add(new AddressData(photo_base64, name, phoneNo, email));*/

                                JSONObject person_info = new JSONObject();
                                person_info.put("name", name);
                                person_info.put("phoneNo", phoneNo);
                                person_info.put("email", email);
                                person_info.put("photo", photo_base64);

                                addressBook.put(person_info);

                            }
                            pCur.close();
                        }
                    }
                }

                if (cur != null) {
                    cur.close();
                }

                //  Fragment frag = this;
                String jsonSt = addressBook.toString();
                Log.d("Print11", "printing addressbook");
                Log.d("addressBook11", jsonSt);

                if(getFragmentManager() != null) {

                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.detach(this);
                    ft.attach(this);
                    ft.commit();
                }
            } catch (JSONException e) {
                System.out.print("json exception");
            }

            String jsonSt = addressBook.toString();
            Log.d("Print", "printing addressbook");
            Log.d("addressBook", jsonSt);

        }
    }

/*
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {

        Log.d("onRequestPermission", "hi");
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                Log.d("onRequestPermission", "hi2");

                initDataset();

            } else {
                Log.d("onRequestPermission", "hi3");

                Toast.makeText(getActivity(), "Until you grant the permission, we cannot display the names", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Log.d("kk", "qqqq");
            //Toast.makeText("Until you grant the permission, we canot display anything", Toast.LENGTH_SHORT).show();
        }
    }

*/
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


}
