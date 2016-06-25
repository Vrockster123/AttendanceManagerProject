package com.example.vrock.attendancemanager2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by vvvro on 6/24/2016.
 */
public class DayFragment extends Fragment {
    public RecyclerView mRecyclerView;
    public RecyclerView.LayoutManager mLayoutManager;
    public AttendanceDbHelper dbHelper;
    public DayAdapter mAdapter;
    public static ArrayList<String> days = new ArrayList<String>();
    public void SubjectListFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragmentdaylist, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.my_day_recycler_view);
        mLayoutManager = new LinearLayoutManager(getActivity());
        if(days.isEmpty()) {
            days.add("Monday");
            days.add("Tuesday");
            days.add("Wednesday");
            days.add("Thursday");
            days.add("Friday");
        }
        mAdapter = new DayAdapter(days,this.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    public static DayFragment newInstance(){
        DayFragment dayListFragment = new DayFragment();
        return dayListFragment;
    }
}
