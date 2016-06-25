package com.example.vrock.attendancemanager2;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import io.paperdb.Paper;

/**
 * Created by vvvro on 6/22/2016.
 */
public class MyDayAdapter extends RecyclerView.Adapter<MyDayAdapter.ViewHolder> {
        private ArrayList<String> mDataset;
        private Context mContext;
        int mDayNumber;
        public MyDayAdapter(ArrayList<String> myDataset, Context myContext, int myDayNumber) {
            mDataset = myDataset;
            mContext = myContext;
            mDayNumber = myDayNumber;
        }

        @Override
        public int getItemCount() {
            return mDataset.size();
        }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final int positionv = position;
        holder.subject.setText(mDataset.get(position));
        switch (mDayNumber) {
            case 0:
                holder.subjectCount.setText(Integer.toString(MainActivity.monday.get(mDataset.get(position))));
                break;
            case 1:
                holder.subjectCount.setText(Integer.toString(MainActivity.tuesday.get(mDataset.get(position))));
                break;
            case 2:
                holder.subjectCount.setText(Integer.toString(MainActivity.wednesday.get(mDataset.get(position))));
                break;
            case 3:
                holder.subjectCount.setText(Integer.toString(MainActivity.thursday.get(mDataset.get(position))));
                break;
            case 4:
                holder.subjectCount.setText(Integer.toString(MainActivity.friday.get(mDataset.get(position))));
                break;
        }
        holder.incrementSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (mDayNumber) {
                    case 0: {
                        int count = MainActivity.monday.get(mDataset.get(positionv));
                        count++;
                        MainActivity.monday.put(mDataset.get(positionv), count);
                        //To Update the File
                        Paper.book().write("monday", MainActivity.monday);
                    }
                    break;
                    case 1: {
                        int count = MainActivity.tuesday.get(mDataset.get(positionv));
                        count++;
                        MainActivity.tuesday.put(mDataset.get(positionv), count);
                        //To Update the File
                        Paper.book().write("tuesday", MainActivity.tuesday);
                    }
                    break;
                    case 2: {
                        int count = MainActivity.wednesday.get(mDataset.get(positionv));
                        count++;
                        MainActivity.wednesday.put(mDataset.get(positionv), count);
                        //To Update the File
                        Paper.book().write("wednesday", MainActivity.wednesday);
                    }
                    break;
                    case 3: {
                        int count = MainActivity.thursday.get(mDataset.get(positionv));
                        count++;
                        MainActivity.thursday.put(mDataset.get(positionv), count);
                        //To Update the File
                        Paper.book().write("thursday", MainActivity.thursday);
                    }
                    break;
                    case 4: {
                        int count = MainActivity.friday.get(mDataset.get(positionv));
                        count++;
                        MainActivity.friday.put(mDataset.get(positionv), count);
                        //To Update the File
                        Paper.book().write("friday", MainActivity.friday);
                    }
                    break;
                }
                switch (mDayNumber) {
                    case 0:
                        TimeTableActivity.mondayAdapter.notifyDataSetChanged();
                        break;
                    case 1:
                        TimeTableActivity.tuesdayAdapter.notifyDataSetChanged();
                        break;
                    case 2:
                        TimeTableActivity.wednesdayAdapter.notifyDataSetChanged();
                        break;
                    case 3:
                        TimeTableActivity.thursdayAdapter.notifyDataSetChanged();
                        break;
                    case 4:
                        TimeTableActivity.fridayAdapter.notifyDataSetChanged();
                        break;
                }
            }
        });
        holder.decrementSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (mDayNumber) {
                    case 0: {
                        int count = MainActivity.monday.get(mDataset.get(positionv));
                        count--;
                        if (count < 0) count = 0;
                        MainActivity.monday.put(mDataset.get(positionv), count);
                        //To Update the File
                        Paper.book().write("monday", MainActivity.monday);
                    }
                    break;
                    case 1: {
                        int count = MainActivity.tuesday.get(mDataset.get(positionv));
                        count--;
                        if (count < 0) count = 0;
                        MainActivity.tuesday.put(mDataset.get(positionv), count);
                        //To Update the File
                        Paper.book().write("tuesday", MainActivity.tuesday);
                    }
                    break;
                    case 2: {
                        int count = MainActivity.wednesday.get(mDataset.get(positionv));
                        count--;
                        if (count < 0) count = 0;
                        MainActivity.wednesday.put(mDataset.get(positionv), count);
                        //To Update the File
                        Paper.book().write("wednesday", MainActivity.wednesday);
                    }
                    break;
                    case 3: {
                        int count = MainActivity.thursday.get(mDataset.get(positionv));
                        count--;
                        if (count < 0) count = 0;
                        MainActivity.thursday.put(mDataset.get(positionv), count);
                        //To Update the File
                        Paper.book().write("thursday", MainActivity.thursday);
                    }
                    break;
                    case 4: {
                        int count = MainActivity.friday.get(mDataset.get(positionv));
                        count--;
                        if (count < 0) count = 0;
                        MainActivity.friday.put(mDataset.get(positionv), count);
                        //To Update the File
                        Paper.book().write("friday", MainActivity.friday);
                    }
                    break;
                }
                switch (mDayNumber) {
                    case 0:
                        TimeTableActivity.mondayAdapter.notifyDataSetChanged();
                        break;
                    case 1:
                        TimeTableActivity.tuesdayAdapter.notifyDataSetChanged();
                        break;
                    case 2:
                        TimeTableActivity.wednesdayAdapter.notifyDataSetChanged();
                        break;
                    case 3:
                        TimeTableActivity.thursdayAdapter.notifyDataSetChanged();
                        break;
                    case 4:
                        TimeTableActivity.fridayAdapter.notifyDataSetChanged();
                        break;
                }
            }
        });
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


            View v = LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.timetableentry, parent, false);
            ViewHolder viewHolder = new ViewHolder(v);
            return viewHolder;

    }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public TextView subject;
            public TextView subjectCount;
            public Button incrementSubject;
            public Button decrementSubject;

            public ViewHolder(View itemView) {
                super(itemView);
                subject = (TextView) itemView.findViewById(R.id.subjectDay);
                subjectCount = (TextView) itemView.findViewById(R.id.subjectCount);
                incrementSubject = (Button) itemView.findViewById(R.id.subject_increment);
                decrementSubject = (Button) itemView.findViewById(R.id.subject_decrement);
            }
        }
}
