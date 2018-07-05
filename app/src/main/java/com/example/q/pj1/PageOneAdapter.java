package com.example.q.pj1;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class PageOneAdapter extends RecyclerView.Adapter<PageOneAdapter.ViewHolder> {
    // private ArrayList<AddressData> mDataset;

    private JSONArray addressBook;



    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        //public ImageView mImageView;
        public CircleImageView mCircleView;
        public TextView mNameView;
        public TextView mPhoneView;
        public TextView mEmailView;

        public ViewHolder(View v) {
            super(v);

            mCircleView = (CircleImageView) v.findViewById(R.id.profile_image);
            mCircleView.setBorderColor(0);

            mNameView  = (TextView) v.findViewById(R.id.name_view);
            mPhoneView = (TextView) v.findViewById(R.id.phoneNum_view);
            mEmailView = (TextView) v.findViewById(R.id.email_view);

            mPhoneView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                   String phoneNo = mPhoneView.getText().toString();
                    if(!phoneNo.equals("no phone number")) {
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNo, null));
                        v.getContext().startActivity(intent);
                    }
                }
            });

            mEmailView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    String address = mEmailView.getText().toString();
                    if(!address.equals("no email")) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" +address));
                        v.getContext().startActivity(intent);
                    }
                }
            });


        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public PageOneAdapter(Context context, Activity activity) {

        addressBook = new JSONArray();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {


                // Android version is lesser than 6.0 or the permission is already granted.
                Log.d("initDataset", "read contacts");

                ContentResolver cr = context.getContentResolver();
                Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                        null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME+" ASC");

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
                        try {
                            person_info.put("name", name);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            person_info.put("phoneNo", phoneNo);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            person_info.put("email", email);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            person_info.put("photo", photo_base64);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        addressBook.put(person_info);
                        Log.d("addressBoot", person_info.toString());
                    }
                }

                if (cur != null) {
                    cur.close();
                }

                //  Fragment frag = this;
                String jsonSt = addressBook.toString();
                Log.d("Print11", "printing addressbook");
                Log.d("addressBook11", jsonSt);
                Log.d("addressBook11", "" + addressBook.length());

        }
    }




    // Create new views (invoked by the layout manager)
    @Override
    public PageOneAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.pageone_element, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        try {
            JSONObject person = addressBook.getJSONObject(position);

            Log.d("onviewholder", position + person.toString());

            if(person.has("photo")) {
                byte[] image = Base64.decode(person.get("photo").toString(), Base64.DEFAULT);
                holder.mCircleView.setImageBitmap(BitmapFactory.decodeByteArray(image, 0, image.length));
            }
            else
                holder.mCircleView.setImageResource(R.drawable.contact_icon);


            if(person.has("name"))
                holder.mNameView.setText(person.get("name").toString());
            else
                holder.mNameView.setText("알 수 없는 이름");


            if(person.has("phoneNo"))
                holder.mPhoneView.setText(person.get("phoneNo").toString());
            else
                holder.mPhoneView.setText("no phone");


            if(person.has("email"))
                holder.mEmailView.setText(person.get("email").toString());
            else
                holder.mEmailView.setText("no email");


        } catch (JSONException e) {
            Log.d("onviewholder", "jsonexception");
        }


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return addressBook.length();
    }

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