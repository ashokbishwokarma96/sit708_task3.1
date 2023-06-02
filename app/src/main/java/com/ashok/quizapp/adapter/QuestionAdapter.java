package com.ashok.quizapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ashok.quizapp.Final;
import com.ashok.quizapp.MainActivity;
import com.ashok.quizapp.QuestionActivity;
import com.ashok.quizapp.R;
import com.ashok.quizapp.model.QuestionModel;

import java.util.ArrayList;
import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder> {

    private List<QuestionModel> questionList;
    private RecyclerView recyclerView;
    private int currentQuestionIndex = 0;
    private int totalQS = 0;
    private Context context;
    int pressed = 0;
    int point = 0;

    public QuestionAdapter(List<QuestionModel> questionList, RecyclerView recyclerView, Context context) {
        this.questionList = questionList;
        this.recyclerView = recyclerView;
        this.context = context;

    }


    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_question_card, parent, false);
        return new QuestionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {
        totalQS=questionList.size();
        if (position != currentQuestionIndex) {
            holder.itemView.setVisibility(View.GONE);
            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
        } else {
            int count = position + 1;
            holder.questionatm.setText("Question no. " + count + " of " + totalQS);
            holder.progressBar.setProgress(position + 1);
            holder.progressBar.setMax(totalQS);
            holder.progressBar.setContentDescription("completed");
            holder.itemView.setVisibility(View.VISIBLE);
            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(
                    RecyclerView.LayoutParams.MATCH_PARENT,
                    RecyclerView.LayoutParams.WRAP_CONTENT
            ));
            QuestionModel question = questionList.get(position);
            SharedPreferences sharedPreferences = context.getSharedPreferences("ashok", Context.MODE_PRIVATE);
            String UserName = sharedPreferences.getString("name", "default");
            holder.userName.setText("Welcome " + UserName);
            holder.questionTitleTextView.setText(question.getTitle());
            holder.questionTextView.setText(question.getQuestion());
            holder.choice1RadioButton.setText(question.getAnswers().get(0));
            holder.choice2RadioButton.setText(question.getAnswers().get(1));
            holder.choice3RadioButton.setText(question.getAnswers().get(2));
            holder.choice4RadioButton.setText(question.getAnswers().get(3));

            List<Button> btnList = new ArrayList<Button>();
            btnList.add(holder.choice1RadioButton);
            btnList.add(holder.choice2RadioButton);
            btnList.add(holder.choice3RadioButton);
            btnList.add(holder.choice4RadioButton);
            checkANS(holder.choice1RadioButton, question, btnList);
            checkANS(holder.choice2RadioButton, question, btnList);
            checkANS(holder.choice3RadioButton, question, btnList);
            checkANS(holder.choice4RadioButton, question, btnList);


            holder.submitAnswerButton.setOnClickListener(view -> {
                if (this.pressed == 1) {
                    currentQuestionIndex = currentQuestionIndex + 1;
                    this.pressed = 0;

                    if (currentQuestionIndex == questionList.size()) {

                        Toast.makeText(view.getContext(), "Quiz completed!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, Final.class);
                        intent.putExtra("name", UserName);
                        intent.putExtra("point", point);
                        intent.putExtra("total", totalQS);
                        context.startActivity(intent);
                        return;
                    }
                    recyclerView.scrollToPosition(currentQuestionIndex);
                    notifyDataSetChanged();

                }

            });
        }
    }

    private void checkANS(Button Btn, QuestionModel question, List<Button> btnList) {
        Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ansIndex = "";
                if (pressed == 0) {
                    String buttonText = ((Button) view).getText().toString();
                    for (int i = 0; i < question.getAnswers().size(); i++) {
                        if (question.getAnswers().get(i).equals(buttonText)) {
                            Log.d("ansIndex", "onClick: " + ansIndex);
                            ansIndex = i + "";
                            break;
                        }
                    }
                    if (ansIndex.equals(question.getCorrectAnswerIndex() + "")) {
                        Btn.setBackgroundColor(Color.rgb(0, 255, 0));
                        for (Button btn_list : btnList) {
                            if (btn_list != Btn) {
                                btn_list.setBackgroundColor(Color.rgb(255, 0, 0));
                            }

                        }
                        point++;

                        Toast.makeText(view.getContext(), "Correct!", Toast.LENGTH_SHORT).show();
                    } else {
                        for (Button btn_list : btnList) {
                            int index = btnList.indexOf(btn_list);
                            Log.d("index", ":" + index);
                            Log.d("index", ":" + question.getCorrectAnswerIndex());
                            if (index == question.getCorrectAnswerIndex()) {
                                btn_list.setBackgroundColor(Color.rgb(0, 255, 0));
                            } else {
                                btn_list.setBackgroundColor(Color.rgb(255, 0, 0));
                            }


                        }

                        Toast.makeText(view.getContext(), "Incorrect!", Toast.LENGTH_SHORT).show();

                    }
                    pressed++;
                } else {
                    // do nothing if button is already pressed
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return questionList.size();
    }

    public void setCurrentQuestionIndex(int questionIndex) {
        // set the current question index
        if (questionIndex >= 0 && questionIndex < questionList.size()) {
            currentQuestionIndex = questionIndex;
            notifyDataSetChanged();
        }
    }

    public class QuestionViewHolder extends RecyclerView.ViewHolder {
        TextView questionTitleTextView;
        TextView questionatm;
        TextView userName;
        TextView questionTextView;
        Button choice1RadioButton;
        Button choice2RadioButton;
        Button choice3RadioButton;
        Button choice4RadioButton;
        Button submitAnswerButton;
        ProgressBar progressBar;

        public QuestionViewHolder(View view) {
            super(view);
            questionTitleTextView = itemView.findViewById(R.id.question_title);
            questionatm = itemView.findViewById(R.id.question_atm);
            userName = itemView.findViewById(R.id.userName);
            questionTextView = itemView.findViewById(R.id.question_text);
            choice1RadioButton = itemView.findViewById(R.id.answer_1);
            choice2RadioButton = itemView.findViewById(R.id.answer_2);
            choice3RadioButton = itemView.findViewById(R.id.answer_3);
            choice4RadioButton = itemView.findViewById(R.id.answer_4);
            submitAnswerButton = itemView.findViewById(R.id.submitBtn);
            progressBar = itemView.findViewById(R.id.question_progress_bar);
        }
    }
}