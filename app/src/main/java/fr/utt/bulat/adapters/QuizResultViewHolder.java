package fr.utt.bulat.adapters;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import fr.utt.bulat.R;


public class QuizResultViewHolder extends RecyclerView.ViewHolder{

    public ImageView imageMark;

    public TextView questionNum;

    public TextView mainQuestion;

    public TextView yourAnswer;

    public TextView correctAnswer;


    public QuizResultViewHolder(View itemView) {
        super(itemView);

        imageMark = (ImageView)itemView.findViewById(R.id.question_icon);
        questionNum = (TextView)itemView.findViewById(R.id.question_number);
        mainQuestion = (TextView)itemView.findViewById(R.id.real_question);
        yourAnswer = (TextView)itemView.findViewById(R.id.your_answer);
        correctAnswer = (TextView)itemView.findViewById(R.id.correct_answer);
    }
}
