package com.example.q.pj1;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class PageOneAdapter extends RecyclerView.Adapter<PageOneAdapter.ViewHolder> {
   // private ArrayList<AddressData> mDataset;

    private JSONArray mAddressBook;



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
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", mPhoneView.getText().toString(), null));
                    v.getContext().startActivity(intent);
                }
            });

            mEmailView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    String address = mEmailView.getText().toString();
                    if(!address.equals("no email")) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + mEmailView.getText().toString()));
                        v.getContext().startActivity(intent);
                    }
                }
            });


        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public PageOneAdapter(JSONArray jarray) {
       // mDataset = myDataset;
        mAddressBook = jarray;
        return;
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
            if(mAddressBook.getJSONObject(position).get("photo") != null) {
                byte[] image = Base64.decode(mAddressBook.getJSONObject(position).get("photo").toString(), Base64.DEFAULT);
                holder.mCircleView.setImageBitmap(BitmapFactory.decodeByteArray(image, 0, image.length));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            holder.mNameView.setText(mAddressBook.getJSONObject(position).get("name").toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            if(mAddressBook.getJSONObject(position).get("phoneNo") != null){
                    holder.mPhoneView.setText(mAddressBook.getJSONObject(position).get("phoneNo").toString());
                }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            if(mAddressBook.getJSONObject(position).get("email") != null) {
                    holder.mEmailView.setText(mAddressBook.getJSONObject(position).get("email").toString());
                }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mAddressBook.length();
    }



}