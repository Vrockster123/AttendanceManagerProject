package com.example.vrock.attendancemanager2;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.lzyzsd.circleprogress.DonutProgress;

import org.w3c.dom.Text;

import java.util.List;

import io.paperdb.Paper;

/**
 * Created by vvvro on 2/25/2016.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<Subject> mDataset;
    private Context mContext;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView subjectName;
        public TextView classesAttended;
        public TextView AttendanceRequired;
        public DonutProgress percent;
        public Button incrementAttendance;
        public Button decrementAttendance;
        public Button undoAttendance;

        public ViewHolder(View itemView) {
            super(itemView);
            subjectName = (TextView) itemView.findViewById(R.id.subject_name);
            classesAttended = (TextView) itemView.findViewById(R.id.classes_Attended);
            AttendanceRequired = (TextView) itemView.findViewById(R.id.total_attendance);
            percent = (DonutProgress) itemView.findViewById((R.id.donut_progress));
            incrementAttendance = (Button) itemView.findViewById(R.id.attendance_increase);
            decrementAttendance = (Button) itemView.findViewById(R.id.attendance_decrease);
            undoAttendance = (Button) itemView.findViewById(R.id.attendance_undo);
        }
    }

    public MyAdapter(List<Subject> myDataset, Context myContext) {
        mDataset = myDataset;
        mContext = myContext;
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.cardviewlayout, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.v("The Data is ", mDataset.get(position).subjectName);
        holder.subjectName.setText(mDataset.get(position).subjectName);
        holder.classesAttended.setText(Integer.toString(mDataset.get(position).ClassesAttended));
        holder.AttendanceRequired.setText(Integer.toString(mDataset.get(position).total));
        holder.percent.setProgress((int) mDataset.get(position).percent);
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
                alertDialogBuilder.setMessage("Delete the Class?");
                alertDialogBuilder.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.dbHelper.deleteSubject(MainActivity.subjectList.get(position).subjectName);
                        MainActivity.monday.remove(MainActivity.subjectList.get(position).subjectName);
                        Paper.book().write("monday", MainActivity.monday);
                        MainActivity.tuesday.remove(MainActivity.subjectList.get(position).subjectName);
                        Paper.book().write("tuesday", MainActivity.tuesday);
                        MainActivity.wednesday.remove(MainActivity.subjectList.get(position).subjectName);
                        Paper.book().write("wednesday", MainActivity.wednesday);
                        MainActivity.thursday.remove(MainActivity.subjectList.get(position).subjectName);
                        Paper.book().write("thursday", MainActivity.thursday);
                        MainActivity.friday.remove(MainActivity.subjectList.get(position).subjectName);
                        Paper.book().write("friday", MainActivity.friday);
                        MainActivity.subjectNameList.remove(MainActivity.subjectList.get(position).subjectName);
                        MainActivity.subjectList.remove(position);
                        MainActivity.mAdapter.notifyDataSetChanged();
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
                return false;
            }
        });
        holder.incrementAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.subjectStackArrayList.get(position).push(1);
                mDataset.get(position).ClassesAttended++;
                mDataset.get(position).total++;
                mDataset.get(position).percent = ((float) mDataset.get(position).ClassesAttended / mDataset.get(position).total) * 100;
                MainActivity.changeAttendance(mDataset.get(position).subjectName,
                        mDataset.get(position).ClassesAttended,
                        mDataset.get(position).total,
                        mDataset.get(position).percent);
            }
        });
        holder.decrementAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.subjectStackArrayList.get(position).push(0);
                mDataset.get(position).total++;
                mDataset.get(position).percent = ((float) mDataset.get(position).ClassesAttended / mDataset.get(position).total) * 100;
                MainActivity.changeAttendance(mDataset.get(position).subjectName,
                        mDataset.get(position).ClassesAttended,
                        mDataset.get(position).total,
                        mDataset.get(position).percent);
            }
        });
        holder.undoAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.subjectStackArrayList.get(position).isEmpty() == true) {
                    Toast toast = new Toast(mContext);
                    toast.makeText(mContext, "Nothing to Undo", Toast.LENGTH_SHORT).show();
                } else {
                    if(MainActivity.subjectStackArrayList.get(position).pop()==1) {
                        mDataset.get(position).ClassesAttended--;
                        mDataset.get(position).total--;
                    }
                    else {
                        mDataset.get(position).total--;
                    }
                    mDataset.get(position).percent = ((float) mDataset.get(position).ClassesAttended / mDataset.get(position).total) * 100;
                    MainActivity.changeAttendance(mDataset.get(position).subjectName,
                            mDataset.get(position).ClassesAttended,
                            mDataset.get(position).total,
                            mDataset.get(position).percent);
                }
            }
        });
    }
}
