package com.example.average_app;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class GS_RecyclerViewAdapter extends RecyclerView.Adapter<GS_RecyclerViewAdapter.MyViewHolder> {
    Context context;
    ArrayList<SubjectGradeModel> gradeSubjects;

    public GS_RecyclerViewAdapter(Context context, ArrayList<SubjectGradeModel> gradeSubjects){
        this.context=context;
        this.gradeSubjects=gradeSubjects;
    }

    @NonNull
    @Override
    public GS_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_row,parent,false);
        return new GS_RecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GS_RecyclerViewAdapter.MyViewHolder holder, int position) {
        //holder.subjectName.setText(gradeSubjects.get(position).toString());
        holder.subjectName.setText(gradeSubjects.get(holder.getAdapterPosition()).getName().toString());
        Log.d("binding",gradeSubjects.get(holder.getAdapterPosition()).getName().toString());


        //przypisanie podmienianych danych
        RadioGroup radioGroup = holder.itemView.findViewById(R.id.gradeRadioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = group.findViewById(checkedId);
                String selectedGrade = radioButton.getText().toString();
                gradeSubjects.get(holder.getAdapterPosition()).setValue(selectedGrade);
            }
        });


    }

    @Override
    public int getItemCount() {
        return gradeSubjects.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView subjectName;
        RadioGroup gradeValue;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            subjectName = itemView.findViewById(R.id.label);
            gradeValue = itemView.findViewById(R.id.gradeRadioGroup);
        }
    }

    public ArrayList<SubjectGradeModel> getResultArray(){
        /*
        //mogę w taki sposób nadpisać pola zawierające oceny
        for(int a=0;a<gradeSubjects.size();a++){
            gradeSubjects.get(a).setName("blelele");
        }
         */
        return gradeSubjects;
    }
}
