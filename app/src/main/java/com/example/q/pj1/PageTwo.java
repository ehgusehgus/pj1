package com.example.q.pj1;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PageTwo.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PageTwo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PageTwo extends Fragment {
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//    }

    private View view ;
    private GridView gridView ;
    public ImageAdapter ia;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PageTwo.
     */
    // TODO: Rename and change types and number of parameters
    public static PageTwo newInstance() {
        PageTwo fragment = new PageTwo();
        Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        Log.d("!!!!!!", "newInstance: " + fragment.toString());
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
//        if (ContextCompat.checkSelfPermission(this.getActivity(),
//                Manifest.permission.READ_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED) {
//
//            requestPermissions(
//                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
//                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
//            }

        view = inflater.inflate(R.layout.fragment_page_two,container,false);
        gridView = (GridView) view.findViewById(R.id.gridview);
        setimageadpater(view.getContext(), this.getActivity());
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeToRefresh);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setimageadpater(view.getContext(), PageTwo.this.getActivity());
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });


        Log.d("shitshit", "shitshit2");
       /* ia = new ImageAdapter(this.getContext(), gridView, this.getActivity());
        gridView.setAdapter(ia);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                ia.callImageViewer(position);
            }
        });*/
        return view;
    }

    @Override
    public void onResume(){
        Log.d("??????","??????");
        Log.d("!!!!!!", "onResume: " + this.toString());
        Log.v("shitshit", gridView+"");
        super.onResume();
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode,
//                                           String permissions[], int[] grantResults) {
//        switch (requestCode) {
//            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
//                if (grantResults.length > 0
//                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    if (gridView != null) {
//                        ia = new ImageAdapter(view.getContext(), gridView, this.getActivity());
//                        gridView.setAdapter(ia);
//                        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                            public void onItemClick(AdapterView parent, View v, int position, long id) {
//                                ia.callImageViewer(position);
//                            }
//                        });
//                    }
//                } else {
//
//                }
//                return;
//        }
//    }

    public void setimageadpater(Context context, Activity activity) {
        if (gridView != null) {
            ia = new ImageAdapter(context, gridView, activity);
            gridView.setAdapter(ia);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView parent, View v, int position, long id) {
                    ia.callImageViewer(position);
                }
            });
            Log.d("shitshit", "shitshit");
        }
        return;
    }
}