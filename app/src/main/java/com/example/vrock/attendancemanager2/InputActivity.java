package com.example.vrock.attendancemanager2;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.MissingFormatArgumentException;

public class InputActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = (EditText) findViewById(R.id.subjectName);
                Editable editable1 = editText.getText();
                editText = (EditText) findViewById(R.id.classesAttended);
                Editable editable2 = editText.getText();
                editText = (EditText) findViewById(R.id.totalClasses);
                Editable editable3 = editText.getText();

                Intent intent = new Intent(InputActivity.this, MainActivity.class);
                if (editable1.toString().matches("") | editable2.toString().matches("") | editable3.toString().matches("")) {
                    Toast toast = new Toast(InputActivity.this);
                    toast.makeText(InputActivity.this, "Enter Data in all fields",Toast.LENGTH_SHORT).show();
                } else {
                    String subject = editable1.toString();
                    int classesAttended = Integer.parseInt(editable2.toString());
                    int total = Integer.parseInt(editable3.toString());
                    if((classesAttended>total)) {
                        Toast toast = new Toast(InputActivity.this);
                        toast.makeText(InputActivity.this, "Invalid Input",Toast.LENGTH_SHORT).show();
                    } else if(subject.length()>40) {
                        Toast toast = new Toast(InputActivity.this);
                        toast.makeText(InputActivity.this, "Class name cannot be more than 40 characters",Toast.LENGTH_SHORT).show();
                    } else if (MainActivity.subjectNameList.contains(subject)){
                        Toast toast = new Toast(InputActivity.this);
                        toast.makeText(InputActivity.this, "Class already exists",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        intent.putExtra("SubjectName", subject);
                        intent.putExtra("ClassesAttended", classesAttended);
                        intent.putExtra("Total", total);
                        Log.v("TAGINPUT", subject);
                        setResult(Activity.RESULT_OK, intent);
                        finish();
                    }
                }
            }
        });
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }
}
