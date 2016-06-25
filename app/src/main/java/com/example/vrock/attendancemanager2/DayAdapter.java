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

import com.github.lzyzsd.circleprogress.DonutProgress;

import java.util.HashMap;
import java.util.List;

import io.paperdb.Paper;

/**
 * Created by vvvro on 6/24/2016.
 */
public class DayAdapter extends RecyclerView.Adapter<DayAdapter.ViewHolder>{
    private List<String> mDataset;
    private Context mContext;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView dayName;
        public Button incrementDayAttendance;
        public Button decrementDayAttendance;

        public ViewHolder(View itemView) {
            super(itemView);
            dayName = (TextView) itemView.findViewById(R.id.day_name);
            incrementDayAttendance = (Button) itemView.findViewById(R.id.attendance_day_increase);
            decrementDayAttendance = (Button) itemView.findViewById(R.id.attendance_day_decrease);
        }
    }

    public DayAdapter(List<String> myDataset, Context  myContext) {
        mDataset = myDataset;
        mContext = myContext;
    }

    @Override
    public int getItemCount() {
        Log.v("AdapterTag ",String.valueOf(mDataset.size()));
        return mDataset.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.dayrecyclerview, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.dayName.setText(mDataset.get(position));
        holder.incrementDayAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
                alertDialogBuilder.setMessage("Did you attend class on "+mDataset.get(position)+"?");
                alertDialogBuilder.setPositiveButton("ATTENDED", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for (int i = 0; i < MainActivity.subjectList.size(); i++) {
                            String subjectName = MainActivity.subjectList.get(i).subjectName;
                            int n = 0;
                            switch (position) {
                                case 0: {
                                    n = MainActivity.monday.get(subjectName);
                                }
                                break;
                                case 1: {
                                    n = MainActivity.tuesday.get(subjectName);
                                }
                                break;
                                case 2: {
                                    n = MainActivity.wednesday.get(subjectName);
                                }
                                break;
                                case 3: {
                                    n = MainActivity.thursday.get(subjectName);
                                }
                                break;
                                case 4: {
                                    n = MainActivity.friday.get(subjectName);
                                }
                                break;

                            }
                            if (n != 0) {
                                int classesAttended = MainActivity.subjectList.get(i).ClassesAttended;
                                classesAttended+=n;
                                int total = MainActivity.subjectList.get(i).total;
                                total+=n;
                                float percent = ((float) classesAttended / total) * 100;
                                MainActivity.changeAttendance(subjectName,
                                        classesAttended,
                                        total,
                                        percent);
                            }
                        }
                    }
                });
                alertDialogBuilder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }
                );
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
        holder.decrementDayAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
                alertDialogBuilder.setMessage("Did you skip class on "+mDataset.get(position)+"?");
                alertDialogBuilder.setPositiveButton("SKIPPED", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for (int i = 0; i < MainActivity.subjectList.size(); i++) {
                            String subjectName = MainActivity.subjectList.get(i).subjectName;
                            int n = 0;
                            switch (position) {
                                case 0: {
                                    n = MainActivity.monday.get(subjectName);
                                }
                                break;
                                case 1: {
                                    n = MainActivity.tuesday.get(subjectName);
                                }
                                break;
                                case 2: {
                                    n = MainActivity.wednesday.get(subjectName);
                                }
                                break;
                                case 3: {
                                    n = MainActivity.thursday.get(subjectName);
                                }
                                break;
                                case 4: {
                                    n = MainActivity.friday.get(subjectName);
                                }
                                break;

                            }
                            if (n != 0) {
                                int classesAttended = MainActivity.subjectList.get(i).ClassesAttended;
                                int total = MainActivity.subjectList.get(i).total;
                                total+=n;
                                float percent = ((float) classesAttended / total) * 100;
                                MainActivity.changeAttendance(subjectName,
                                        classesAttended,
                                        total,
                                        percent);
                            }
                        }
                    }
                });
                alertDialogBuilder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }
                );
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
    }
}
