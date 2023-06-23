package com.example.average_app;

import static com.example.average_app.GradesActivity.RESULT_AVERAGE;
import static com.example.average_app.GradesActivity.RESULT_GRADE_COUNT;
import static com.example.average_app.GradesActivity.RESULT_NAME;
import static com.example.average_app.GradesActivity.RESULT_SURNAME;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //key names for resuming activity
    public static final String KEY_NAME = "name";
    public static final String KEY_SURNAME = "surname";
    public static final String KEY_GRADE_COUNT = "grade_count";
    public String name_str,surname_str,grade_count_str;


    EditText name,surname,grade_count;

    TextView average;
    Button submit,exit;
    boolean submit_visible=false;
    boolean exit_status=false;
    boolean exit_visible=false;
    private ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent intent = result.getData();
                        if(intent != null){
                            //przygotowanie pól nadpisywanych
                            name = findViewById(R.id.nameEditText);
                            surname = findViewById(R.id.surnameEditText);
                            grade_count = findViewById(R.id.gradeCountEditText);

                            average=findViewById(R.id.averageTextView);
                            exit = findViewById(R.id.exitButton);

                            //wyłuskanie wartości
                            String res_name = intent.getStringExtra(RESULT_NAME);
                            String res_surname = intent.getStringExtra(RESULT_SURNAME);
                            String res_grade_count = intent.getStringExtra(RESULT_GRADE_COUNT);
                            double res_average = Double.parseDouble(intent.getStringExtra(RESULT_AVERAGE));

                            //ustawienie pól z wartościami uzupełnionymi
                            name.setText(res_name);
                            surname.setText(res_surname);
                            grade_count.setText(res_grade_count);

                            //wyłączenie możliwości edytowania
                                /*
                                name.setEnabled(false);
                                surname.setEnabled(false);
                                grade_count.setEnabled(false);
                                */

                            Log.d("srednia","srednia.double:"+res_average);
                            Log.d("srednia","czy zaliczone?:"+((res_average>=3.0)?true:false));

                            if(res_average>=3.0){
                                exit.setText(R.string.activ1_exitButton_positive);
                                exit_status=true;
                            }else{
                                exit.setText(R.string.activ1_exitButton_negative);
                                exit_status=false;
                            }

                            //String wartosc_wyodrebniona = intent.getStringExtra(RESULT_AVERAGE);
                            Log.d("srednia","wyodrebniona:"+res_average);//wartosc_wyodrebniona
                            average.setText("Średnia ocen:"+res_average);//jezeli jest string to kaze konwertowac na int
                            exit.setVisibility(View.VISIBLE);
                            exit_visible=true;
                        }
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.nameEditText);
        surname = findViewById(R.id.surnameEditText);
        grade_count = findViewById(R.id.gradeCountEditText);
        submit = findViewById(R.id.submitButton);

        average = findViewById(R.id.averageTextView);
        exit = findViewById(R.id.exitButton);

        //submit.setVisibility(submit_visible ? View.VISIBLE : View.INVISIBLE);   << krótka wersja if
        if(submit_visible){
            submit.setVisibility(View.VISIBLE);
        }
        else {
            submit.setVisibility(View.INVISIBLE);
        }

        exit.setVisibility(View.INVISIBLE);


        //private jezeli poza onCreate
        TextWatcher buttonEnabler = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                name_str = name.getText().toString().trim();
                surname_str = surname.getText().toString().trim();
                grade_count_str = grade_count.getText().toString();


                if(!name_str.isEmpty() && !surname_str.isEmpty() && !grade_count_str.isEmpty()){
                    int grade_count_value = Integer.parseInt(grade_count_str);

                    //submit_visible=(grade_count_value>=5 && grade_count_value<=15)?true:false;
                    if(grade_count_value>=5 && grade_count_value<=15) {
                        submit_visible=true;
                    }
                    else{
                        submit_visible=false;
                    }
                }
                else{
                    submit_visible=false;
                }

                //submit.setVisibility(submit_visible ? View.VISIBLE : View.INVISIBLE);   << krótka wersja if
                if(submit_visible){
                    submit.setVisibility(View.VISIBLE);
                }
                else {
                    submit.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };

        name.addTextChangedListener(buttonEnabler);
        surname.addTextChangedListener(buttonEnabler);
        grade_count.addTextChangedListener(buttonEnabler);



        name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus && name.getText().toString().trim().length()==0){//trim - ignorowanie spacji
                    name.setError(getString(R.string.toast_name_not_empty));
                    Toast.makeText(MainActivity.this, R.string.toast_name_not_empty,Toast.LENGTH_SHORT).show();
                }
            }
        });

        surname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus && surname.getText().toString().trim().length()==0){//trim - ignorowanie spacji
                    surname.setError(getString(R.string.toast_surname_not_empty));
                    Toast.makeText(MainActivity.this, R.string.toast_surname_not_empty,Toast.LENGTH_SHORT).show();
                }
            }
        });

        grade_count.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){//utrata focusa

                    //trzeba sprawdzić czy pole jest puste, żeby przy konwersji na int nie wywalało wyjątku
                    if(grade_count.getText().toString().length()==0){//brak trim, bo numeryczna nie pozwala spacji wstawiać
                        grade_count.setError(getString(R.string.toast_grade_count_not_empty));
                        Toast.makeText(MainActivity.this, R.string.toast_grade_count_not_empty, Toast.LENGTH_SHORT).show();
                    }
                    else{
                        //trzeba wykonać dopiero jak wyjdzie sie z pola i jest uzupelnione, bo inaczej ma Integer.ParseInt(null)
                        String grade_text = grade_count.getText().toString();
                        int grade_value = Integer.parseInt(grade_text);

                        if(grade_value<5 || grade_value>15) {
                            grade_count.setError(getString(R.string.toast_grade_count_alert));
                            Toast.makeText(MainActivity.this, R.string.toast_grade_count_alert, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startSecondActivity();
            }
        });

        exit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                exitApp(exit_status);
            }
        });


    }

    private void startSecondActivity(){
        name = findViewById(R.id.nameEditText);
        surname = findViewById(R.id.surnameEditText);
        grade_count = findViewById(R.id.gradeCountEditText);

        Intent intent = new Intent(MainActivity.this,GradesActivity.class);//MainActivity.this

        intent.putExtra(KEY_NAME,name.getText().toString());
        intent.putExtra(KEY_SURNAME,surname.getText().toString());
        intent.putExtra(KEY_GRADE_COUNT,grade_count.getText().toString());

        mActivityResultLauncher.launch(intent);
    }

    private void exitApp(boolean status){
        if(status){
            Toast.makeText(MainActivity.this, R.string.exit_positive,Toast.LENGTH_SHORT).show();
            finish();
        }else{
            Toast.makeText(MainActivity.this, R.string.exit_negative,Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //exit_status <<zmienna boolean ktora trzeba odtworzyc
        //exit_visible <<zmienna boolean ktora przetrzymuje czy exit button jest widoczny
        average = findViewById(R.id.averageTextView);
        exit = findViewById(R.id.exitButton);


        outState.putBoolean("exit_status",exit_status);
        outState.putBoolean("exit_visible",exit_visible);
        outState.putString("average",average.getText().toString());
        outState.putString("exit",exit.getText().toString());

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        boolean exit_vis;

        average.findViewById(R.id.averageTextView);
        exit = findViewById(R.id.exitButton);

        average.setText(savedInstanceState.getString("average"));
        exit.setText(savedInstanceState.getString("exit"));

        exit_status=savedInstanceState.getBoolean("exit_status");
        exit_vis=savedInstanceState.getBoolean("exit_visible");

        if(exit_vis || exit_status){
            exit.setVisibility(View.VISIBLE);
        }else{
            exit.setVisibility(View.INVISIBLE);
        }
    }

}