package com.example.vrock.attendancemanager2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by vvvro on 6/22/2016.
 */
public class DayTimeTableFragment extends Fragment {
    String DAY_NUMBER="DayNumber";

    public DayTimeTableFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_time_table, container, false);
        int dayNumber = getArguments().getInt(DAY_NUMBER);
        switch (dayNumber) {
            case 0: {
                TimeTableActivity.d0recyclerView = (RecyclerView) rootView.findViewById(R.id.my_time_table_recycler_view);
                TimeTableActivity.d0layoutManager = new LinearLayoutManager(getActivity());
                TimeTableActivity.d0recyclerView.setLayoutManager(TimeTableActivity.d0layoutManager);
            }break;
            case 1: {
                TimeTableActivity.d1recyclerView = (RecyclerView) rootView.findViewById(R.id.my_time_table_recycler_view);
                TimeTableActivity.d1layoutManager = new LinearLayoutManager(getActivity());
                TimeTableActivity.d1recyclerView.setLayoutManager(TimeTableActivity.d1layoutManager);
            }break;
            case 2: {
                TimeTableActivity.d2recyclerView = (RecyclerView) rootView.findViewById(R.id.my_time_table_recycler_view);
                TimeTableActivity.d2layoutManager = new LinearLayoutManager(getActivity());
                TimeTableActivity.d2recyclerView.setLayoutManager(TimeTableActivity.d2layoutManager);
            }break;
            case 3: {
                TimeTableActivity.d3recyclerView = (RecyclerView) rootView.findViewById(R.id.my_time_table_recycler_view);
                TimeTableActivity.d3layoutManager = new LinearLayoutManager(getActivity());
                TimeTableActivity.d3recyclerView.setLayoutManager(TimeTableActivity.d3layoutManager);
            }break;
            case 4: {
                TimeTableActivity.d4recyclerView = (RecyclerView) rootView.findViewById(R.id.my_time_table_recycler_view);
                TimeTableActivity.d4layoutManager = new LinearLayoutManager(getActivity());
                TimeTableActivity.d4recyclerView.setLayoutManager(TimeTableActivity.d4layoutManager);
            }break;
        }
        ArrayList<String> daySubjects = new ArrayList<String>();
        for(String temp:MainActivity.monday.keySet()){
            daySubjects.add(temp);
        }
        switch (dayNumber){
            case 0:{
                TimeTableActivity.mondayAdapter = new MyDayAdapter(daySubjects,rootView.getContext(),dayNumber);
                TimeTableActivity.d0recyclerView.setAdapter(TimeTableActivity.mondayAdapter);
            }break;
            case 1:{
                TimeTableActivity.tuesdayAdapter = new MyDayAdapter(daySubjects,rootView.getContext(),dayNumber);
                TimeTableActivity.d1recyclerView.setAdapter(TimeTableActivity.tuesdayAdapter);
            }break;
            case 2:{
                TimeTableActivity.wednesdayAdapter = new MyDayAdapter(daySubjects,rootView.getContext(),dayNumber);
                TimeTableActivity.d2recyclerView.setAdapter(TimeTableActivity.wednesdayAdapter);
            }break;
            case 3:{
                TimeTableActivity.thursdayAdapter = new MyDayAdapter(daySubjects,rootView.getContext(),dayNumber);
                TimeTableActivity.d3recyclerView.setAdapter(TimeTableActivity.thursdayAdapter);
            }break;
            case 4:{
                TimeTableActivity.fridayAdapter = new MyDayAdapter(daySubjects,rootView.getContext(),dayNumber);
                TimeTableActivity.d4recyclerView.setAdapter(TimeTableActivity.fridayAdapter);
            }break;
        }
        return rootView;
    }

    public DayTimeTableFragment newInstance(int dayNumber){
        DayTimeTableFragment dayTimeTableFragment = new DayTimeTableFragment();
        Bundle args = new Bundle();
        args.putInt(DAY_NUMBER, dayNumber);
        dayTimeTableFragment.setArguments(args);
        return dayTimeTableFragment;
    }
}
