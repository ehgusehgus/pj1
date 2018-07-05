package com.example.q.pj1;

import android.app.Activity;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.ContactsContract;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class Add_Contact extends android.support.v4.app.Fragment {

    TextView mName;
    TextView mPhone;
    TextView mEmail;
    Button bCancel;
    Button bAdd;

    MainActivity mHostActivity;

    private static int PERMISSIONS_REQUEST_WRITE_CONTACTS =11;

    public Add_Contact() {
        // Required empty public constructor
    }


    public static Add_Contact newInstance() {
        Add_Contact fragment = new Add_Contact();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onAttach( Activity activity ) {
        super.onAttach( activity );
        mHostActivity = (MainActivity)activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add__contact, container, false);

        if ( ContextCompat.checkSelfPermission(this.getActivity(), android.Manifest.permission.WRITE_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            Log.d("initDataset", "request permission");

            requestPermissions( new String[]{android.Manifest.permission.WRITE_CONTACTS}, PERMISSIONS_REQUEST_WRITE_CONTACTS);
        }

        mName = view.findViewById(R.id.name);
        mPhone = view.findViewById(R.id.phone);
        mEmail = view.findViewById(R.id.email);
        bCancel = view.findViewById(R.id.cancel);
        bAdd = view.findViewById(R.id.add);


        view.setBackgroundColor(Color.DKGRAY);


        bCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removePopUp(view);


                if (view != null) {
                    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        });

        bAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get android phone contact content provider uri.
                //Uri addContactsUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
                // Below uri can avoid java.lang.UnsupportedOperationException: URI: content://com.android.contacts/data/phones error.
                Uri addContactsUri = ContactsContract.Data.CONTENT_URI;



                // Add contact name data.
                String inputName = mName.getText().toString();
                String name = (inputName.equals("")) ? null : inputName;

                // Add contact phone data.
                String inputPhone = mPhone.getText().toString();
                String phone = (inputPhone.equals("")) ? null : inputPhone;


                String inputEmail = mEmail.getText().toString();
                String email = (inputEmail.equals("")) ? null : inputEmail;

                Log.d("add button", inputName +","+ inputPhone +","+ inputEmail);
                if(name != null && (phone != null || email != null)){
                    Log.d("add button", "add!!");
                    // Add an empty contact and get the generated id.
                    long rowContactId = getRawContactId();

                    insertContactDisplayName(addContactsUri, rowContactId, name);

                    if(phone != null)
                        insertContactPhoneNumber (addContactsUri, rowContactId, phone);

                    if(email != null)
                         insertContactEmail (addContactsUri, rowContactId, inputEmail);

                    removePopUp(view);
                    mHostActivity.pageOne_Update();
                }
                else{
                    Context context = getContext();
                    CharSequence text = "You should add info : \n[Name && (PhoneNo || Email)]";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }

                //removePopUp(view);

                //PageOne pageone = (PageOne) getFragmentManager().findFragmentById(R.id.frameLayout);
                //pageone.initDataset();
                //mHostActivity.pageOne_Update();


            }});



        return view;

    }




    public void removePopUp(View v){
        FragmentManager fragmanager =  getFragmentManager();
        FragmentTransaction trans = fragmanager.beginTransaction();
        trans.remove(fragmanager.findFragmentById(R.id.popup_frag));
        trans.commit();
    }

    private long getRawContactId()
    {
        // Inser an empty contact.
        ContentValues contentValues = new ContentValues();
        Uri rawContactUri = getActivity().getContentResolver().insert(ContactsContract.RawContacts.CONTENT_URI, contentValues);
        // Get the newly created contact raw id.
        long ret = ContentUris.parseId(rawContactUri);
        return ret;
    }


    // Insert newly created contact display name.
    private void insertContactDisplayName(Uri addContactsUri, long rawContactId, String displayName)
    {
        ContentValues contentValues = new ContentValues();

        contentValues.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);

        // Each contact must has an mime type to avoid java.lang.IllegalArgumentException: mimetype is required error.
        contentValues.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);

        // Put contact display name value.
        contentValues.put(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, displayName);

        getActivity().getContentResolver().insert(addContactsUri, contentValues);

    }

    private void insertContactPhoneNumber(Uri addContactsUri, long rawContactId, String phoneNumber)
    {
        // Create a ContentValues object.
        ContentValues contentValues = new ContentValues();

        // Each contact must has an id to avoid java.lang.IllegalArgumentException: raw_contact_id is required error.
        contentValues.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);

        // Each contact must has an mime type to avoid java.lang.IllegalArgumentException: mimetype is required error.
        contentValues.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);

        // Put phone number value.
        contentValues.put(ContactsContract.CommonDataKinds.Phone.NUMBER, phoneNumber);

        // Calculate phone type by user selection.
        int phoneContactType = ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE;

        // Put phone type value.
        contentValues.put(ContactsContract.CommonDataKinds.Phone.TYPE, phoneContactType);

        // Insert new contact data into phone contact list.
        getActivity().getContentResolver().insert(addContactsUri, contentValues);

    }

    private void insertContactEmail(Uri addContactsUri, long rawContactId, String email)
    {
        // Create a ContentValues object.
        ContentValues contentValues = new ContentValues();

        // Each contact must has an id to avoid java.lang.IllegalArgumentException: raw_contact_id is required error.
        contentValues.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);

        // Each contact must has an mime type to avoid java.lang.IllegalArgumentException: mimetype is required error.
        contentValues.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE);

        // Put phone number value.
        contentValues.put(ContactsContract.CommonDataKinds.Email.DATA, email);

        // Insert new contact data into phone contact list.
        getActivity().getContentResolver().insert(addContactsUri, contentValues);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {

        Log.d("onRequestPermission", "hi");
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSIONS_REQUEST_WRITE_CONTACTS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                Log.d("onRequestPermission", "hi2");

                //bAdd.setClickable(true);

            } else {
                Log.d("onRequestPermission", "hi3");

                // Toast.makeText(getActivity(), "Until you grant the permission, we cannot display the names", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Log.d("kk", "qqqq");
            //Toast.makeText("Until you grant the permission, we canot display anything", Toast.LENGTH_SHORT).show();
        }
    }


}