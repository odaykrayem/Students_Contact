package com.example.shaqrastudentscontact.professor.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shaqrastudentscontact.R;
import com.example.shaqrastudentscontact.models.ProfessorQuestion;
import com.example.shaqrastudentscontact.models.Question;
import com.example.shaqrastudentscontact.professor.adapters.ProfessorRequestedQuestionsAdapter;

import java.util.ArrayList;


public class ProfessorRequestedQuestionsFragment extends Fragment {

    Context ctx;
    RecyclerView mList;
    ArrayList<ProfessorQuestion> list;
    ProfessorRequestedQuestionsAdapter adapter;
    ProgressDialog pDialog;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.ctx = context;
    }

    public ProfessorRequestedQuestionsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_professor_requested_questions, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mList = view.findViewById(R.id.rv);
        list = new ArrayList<ProfessorQuestion>(){{
            add(new ProfessorQuestion(1, 1, "tahani", "Questio about soething", "wht is ?", "new", "19-3-2022"));
        }};
        adapter = new ProfessorRequestedQuestionsAdapter(ctx, list);
        mList.setAdapter(adapter);


    }
}