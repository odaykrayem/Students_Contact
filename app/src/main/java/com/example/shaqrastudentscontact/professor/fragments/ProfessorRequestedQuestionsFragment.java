package com.example.shaqrastudentscontact.professor.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.shaqrastudentscontact.R;
import com.example.shaqrastudentscontact.models.ProfessorQuestion;
import com.example.shaqrastudentscontact.professor.adapters.ProfessorRequestedQuestionsAdapter;
import com.example.shaqrastudentscontact.utils.SharedPrefManager;
import com.example.shaqrastudentscontact.utils.Urls;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;

public class ProfessorRequestedQuestionsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    Context context;
    RecyclerView mList;
    ArrayList<ProfessorQuestion> list;
    ProfessorRequestedQuestionsAdapter adapter;
    ProgressDialog pDialog;
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public ProfessorRequestedQuestionsFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_professor_requested_questions, container, false);
        mSwipeRefreshLayout = view.findViewById(R.id.swipe);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.secondary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
        mSwipeRefreshLayout.post(() -> {
            mSwipeRefreshLayout.setRefreshing(true);
            getQuestionsForProfessor();
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mList = view.findViewById(R.id.rv);

        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Processing please wait...");
        pDialog.setCancelable(false);

    }

    private void getQuestionsForProfessor() {
        pDialog.show();
        list = new ArrayList<ProfessorQuestion>();

        String url = Urls.GET_PROFESSOR_QUESTIONS;
        String profId = String.valueOf(SharedPrefManager.getInstance(context).getUserId());
        AndroidNetworking.get(url)
                .setPriority(Priority.MEDIUM)
                .addQueryParameter("professor_id", String.valueOf(profId))
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String messageGot = "founded";
                            String message = response.getString("message");
                            if (message.toLowerCase().contains(messageGot.toLowerCase())) {
                                JSONArray jsonArray = response.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject obj = jsonArray.getJSONObject(i);
//                                    JSONObject student_data = obj.getJSONObject("student");
                                    list.add(new ProfessorQuestion(
                                            Integer.parseInt(obj.getString("id")),
                                            Integer.parseInt(obj.getString("user_id")),
//                                            student_data.has("name") ? student_data.getString("name"):"null",
                                            "student name",
                                            obj.getString("title"),
                                            obj.getString("content"),
                                            obj.getString("answer").equals("null")?"":obj.getString("answer"),
                                            obj.getString("created_at").split(" ")[0]
                                    ));

                                }
                                adapter = new ProfessorRequestedQuestionsAdapter(context, list);

                                mList.setAdapter(adapter);
                            } else {
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                            }
                            mSwipeRefreshLayout.setRefreshing(false);
                            pDialog.dismiss();
                        } catch (Exception e) {
                            pDialog.dismiss();
                            mSwipeRefreshLayout.setRefreshing(false);
                            e.printStackTrace();
                            Log.e("cquestions catch", e.getMessage());
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        pDialog.dismiss();
                        mSwipeRefreshLayout.setRefreshing(false);
                        Log.e("cquestions anerror", error.getErrorBody());
                    }
                });
    }

    @Override
    public void onRefresh() {
        getQuestionsForProfessor();
    }
}