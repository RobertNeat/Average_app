package com.example.average_app;

import static com.example.average_app.MainActivity.KEY_GRADE_COUNT;
import static com.example.average_app.MainActivity.KEY_NAME;
import static com.example.average_app.MainActivity.KEY_SURNAME;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class GradesActivity extends AppCompatActivity {
    public final static String RESULT_NAME = "result_name";
    public final static String RESULT_SURNAME = "result_surname";
    public final static String RESULT_GRADE_COUNT = "result_grade_count";
    public final static String RESULT_AVERAGE = "result_average";
    Button calculate_button;

    public double sum = 0;//sum of the grades from the list
    public String count="";//count of the grades
    private ArrayList<SubjectGradeModel> gradeSubjects = new ArrayList<>();//tablica przechowująca listę przedmiotów

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grades);

        //pobranie ilości ocen
        Intent intent = getIntent();
        count = intent.getStringExtra(KEY_GRADE_COUNT);

        //inicjalizacja przycisku, żeby można było listenera kliknięcie podpiąć
        calculate_button=findViewById(R.id.button_count_average);


        RecyclerView recyclerView = findViewById(R.id.gradesPicker);
        setUpGradeSubjects();
        GS_RecyclerViewAdapter adapter = new GS_RecyclerViewAdapter(this,gradeSubjects);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        //button to send activity return values
        calculate_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                ArrayList<SubjectGradeModel> temp=adapter.getResultArray();
                for(int a=0;a<temp.size();a++){
                    Log.d("temp", temp.get(a).getName()+"---"+temp.get(a).getValue());
                }
                */
                returnToMain(adapter.getResultArray());
            }
        });
    }

    private void returnToMain(ArrayList<SubjectGradeModel> modifiedList){
        //wartości pól z MainActivity
        Intent Origin_intent = getIntent();
        String name = Origin_intent.getStringExtra(KEY_NAME);
        String surname = Origin_intent.getStringExtra(KEY_SURNAME);
        String grade_count = Origin_intent.getStringExtra(KEY_GRADE_COUNT);

        //policzenie średniej i przekazanie do pierwszej
        //String average = "0.15"; (testowe - działa)

        //zsumowanie wszystkich zmodyfikowanych ocen
        for(int a=0;a<modifiedList.size();a++){
            sum+=Double.parseDouble(modifiedList.get(a).getValue());
        }

        String average = Double.toString(sum/Double.parseDouble(count));
        //String average = Double.toString(sum);
        Log.d("srednia","suma:"+Double.toString(sum));
        Log.d("srednia","licznik:"+count);
        Log.d("srednia","srednia:"+average);

        Intent Result_intent = new Intent();

        Result_intent.putExtra(RESULT_NAME,name);
        Result_intent.putExtra(RESULT_SURNAME,surname);
        Result_intent.putExtra(RESULT_GRADE_COUNT,grade_count);

        Result_intent.putExtra(RESULT_AVERAGE,average);//.toString()
        setResult(RESULT_OK,Result_intent);
        finish();//zniszczenie aktywności
    }

    private void setUpGradeSubjects(){
        String[] subjectNames = getResources().getStringArray(R.array.subjectArray);

        for(int a=0;a<Integer.parseInt(count);a++){
            //Log.d("subject",subjectNames[a]);
            gradeSubjects.add(new SubjectGradeModel(subjectNames[a],"3"));
        }
    }
}