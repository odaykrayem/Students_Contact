package com.example.shaqrastudentscontact.professor.adapters;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shaqrastudentscontact.R;
import com.example.shaqrastudentscontact.models.ProfessorQuestion;

import java.util.ArrayList;
import java.util.List;

public class ProfessorRequestedQuestionsAdapter extends RecyclerView.Adapter<ProfessorRequestedQuestionsAdapter.ViewHolder> {

    Context context;
    private List<ProfessorQuestion> list;
    public NavController navController;

    public ProfessorRequestedQuestionsAdapter(Context context, ArrayList<ProfessorQuestion> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ProfessorRequestedQuestionsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.item_requested_question, parent, false);
        ProfessorRequestedQuestionsAdapter.ViewHolder viewHolder = new ProfessorRequestedQuestionsAdapter.ViewHolder(listItem);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull ProfessorRequestedQuestionsAdapter.ViewHolder holder, int position) {

        ProfessorQuestion question = list.get(position);

        holder.studentName.setText(question.getStudentName());
        holder.question.setText(question.getContent());
        holder.date.setText(question.getDate());
        holder.isReplied.setChecked(!question.getAnswer().isEmpty());
        holder.isReplied.setEnabled(false);

        holder.addReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController = Navigation.findNavController(holder.itemView);
                Bundle bundle = new Bundle();
                bundle.putString("question_id", String.valueOf(question.getId()));
                navController.navigate(R.id.action_menu_requested_questions_to_ReplyToQuestionFragment,bundle);
            }
        });

        holder.replies.setOnClickListener(v -> {
            LayoutInflater factory = LayoutInflater.from(context);
            final View view = factory.inflate(R.layout.dialog_question_reply, null);
            final AlertDialog questionReplyDialog = new AlertDialog.Builder(context).create();
            questionReplyDialog.setView(view);
            questionReplyDialog.setCanceledOnTouchOutside(true);

            TextView title = view.findViewById(R.id.title);
            TextView details = view.findViewById(R.id.question);
            TextView reply = view.findViewById(R.id.answer);

            Log.e("answer", question.getAnswer());

            title.setText(question.getStudentName());
            details.setText(question.getContent());
            if(question.getAnswer().isEmpty()){
                reply.setText(context.getResources().getString(R.string.no_answer_yet));
            }else {
                reply.setText(question.getAnswer());
            }
            questionReplyDialog.show();
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView studentName, question, date, replies, addReply;
        public CheckBox isReplied;


        public ViewHolder(View itemView) {
            super(itemView);
            this.studentName = itemView.findViewById(R.id.name);
            this.question = itemView.findViewById(R.id.question);
            this.date = itemView.findViewById(R.id.date);
            this.addReply = itemView.findViewById(R.id.reply);
            this.replies = itemView.findViewById(R.id.replies);
            this.isReplied = itemView.findViewById(R.id.is_replied);
        }
    }

}