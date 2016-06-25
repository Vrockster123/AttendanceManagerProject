package com.example.vrock.attendancemanager2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

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

public class MainActivity extends AppCompatActivity {
    //Hello!
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    Subject temp;
    public static AttendanceDbHelper dbHelper;
    public static MyAdapter mAdapter;
    public static List<Subject> subjectList = new ArrayList<Subject>();
    public static HashMap<String,Integer> monday = new HashMap<String,Integer>();
    public static HashMap<String,Integer> tuesday = new HashMap<String,Integer>();
    public static HashMap<String,Integer> wednesday = new HashMap<String,Integer>();
    public static HashMap<String,Integer> thursday = new HashMap<String,Integer>();
    public static HashMap<String,Integer> friday = new HashMap<String,Integer>();
    public static ArrayList<String> subjectNameList = new ArrayList<String>();
    FileOutputStream fos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        dbHelper = new AttendanceDbHelper(this);
        Cursor cursor = dbHelper.getAllSubjects();
        String name;
        int classesAttended,total;
        float percent;
        while(cursor.moveToNext()){
            name = cursor.getString(cursor.getColumnIndex(AttendanceContract.SubjectEntry.COLUMN_NAME_SUBJECT_NAME));
            classesAttended = cursor.getInt(cursor.getColumnIndex(AttendanceContract.SubjectEntry.COLUMN_NAME_HOURS_ATTENDED));
            total = cursor.getInt(cursor.getColumnIndex(AttendanceContract.SubjectEntry.COLUMN_NAME_HOURS_TOTAL));
            percent = cursor.getFloat(cursor.getColumnIndex(AttendanceContract.SubjectEntry.COLUMN_NAME_ATTENDANCE_PERCENTAGE));
            //TO DO: Do not allow false values of Percent or define only one constructor
            int flag = 1;
            for(int i=0;i<MainActivity.subjectList.size();i++){  //Added Loop to check if Item is already present
                if(MainActivity.subjectList.get(i).subjectName.compareTo(name)==0){
                    flag = 0;
                    break;
                }
            }
            if(flag==1) MainActivity.subjectList.add(new Subject(name, classesAttended, total, percent));
        }
        cursor.close();
        if(subjectNameList.isEmpty()==true) {
            for (int i = 0; i < subjectList.size(); i++) {
                subjectNameList.add(subjectList.get(i).subjectName);
            }
        }
        if(monday.isEmpty()==true) {
            for(int i=0;i<subjectList.size();i++) {
                monday.put(subjectList.get(i).subjectName,0);
                tuesday.put(subjectList.get(i).subjectName, 0);
                wednesday.put(subjectList.get(i).subjectName, 0);
                thursday.put(subjectList.get(i).subjectName, 0);
                friday.put(subjectList.get(i).subjectName, 0);
            }
        }

        Paper.init(this);
        if(Paper.book().exist("monday")==true) {
            monday = Paper.book().read("monday");
        } else {
            Paper.book().write("monday",monday);
        }
        if(Paper.book().exist("tuesday")==true) {
            tuesday = Paper.book().read("tuesday");
        } else {
            Paper.book().write("tuesday",tuesday);
        }
        if(Paper.book().exist("wednesday")==true) {
            wednesday = Paper.book().read("wednesday");
        } else {
            Paper.book().write("wednesday",wednesday);
        }
        if(Paper.book().exist("thursday")==true) {
            thursday = Paper.book().read("thursday");
        } else {
            Paper.book().write("thursday",thursday);
        }
        if(Paper.book().exist("friday")==true) {
            friday = Paper.book().read("friday");
        } else {
            Paper.book().write("friday",friday);
        }
        mAdapter = new MyAdapter(subjectList,MainActivity.this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();
                Intent intent = new Intent(MainActivity.this, InputActivity.class);
                startActivityForResult(intent, 1);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1) {
            if (resultCode == Activity.RESULT_CANCELED) {
            }
            else if (resultCode == Activity.RESULT_OK){
                String name = data.getStringExtra("SubjectName");
                int classesAttended = data.getIntExtra("ClassesAttended", 1);
                int total = data.getIntExtra("Total", 1);
                    Log.v("LOLOL", String.valueOf(classesAttended) + " " + String.valueOf(total));
                    Log.v("LOL", name);
                    float percent = ((float) classesAttended / total) * 100;
                    dbHelper.insertSubject(name, classesAttended, total, percent);
                    subjectList.add(new Subject(name, classesAttended, total));
                    subjectNameList.add(name);
                    monday.put(name, 0);
                    Paper.book().write("monday", monday);
                    tuesday.put(name, 0);
                    Paper.book().write("tuesday", monday);
                    wednesday.put(name, 0);
                    Paper.book().write("wednesday", monday);
                    thursday.put(name, 0);
                    Paper.book().write("thursday", monday);
                    friday.put(name, 0);
                    Paper.book().write("friday", monday);
                    mAdapter.notifyDataSetChanged();

            }
        }
    }
    public static void dataChanged(String name, int attendance, int total, float percent){
        for(int i=0;i<subjectList.size();i++){
            if(subjectList.get(i).subjectName==name) {
                subjectList.get(i).ClassesAttended = attendance;
                subjectList.get(i).total = total;
            }
        }
        mAdapter.notifyDataSetChanged();
    }
    public static void changeAttendance(String name, int attendance, int total, float percent){
        dbHelper.updateSubject(name, attendance, total, percent);
        dataChanged(name, attendance, total, percent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_timetable) {
            Intent intent = new Intent(MainActivity.this, TimeTableActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            if(position==0) return new SubjectListFragment();
            else if(position == 1) return new DayFragment();
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "ATTENDANCE";
                case 1:
                    return "TIME TABLE";
            }
            return null;
        }
    }
}
