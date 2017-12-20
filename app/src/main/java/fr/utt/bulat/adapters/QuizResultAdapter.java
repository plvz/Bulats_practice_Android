package fr.utt.bulat.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import fr.utt.bulat.R;
import fr.utt.bulat.entities.ResultObject;

import java.util.List;

public class QuizResultAdapter extends RecyclerView.Adapter<QuizResultViewHolder>{

    private static final String TAG = QuizResultAdapter.class.getSimpleName();

    private Context context;

    private List<ResultObject> resultAnalysis;


    public QuizResultAdapter(Context context, List<ResultObject> resultAnalysis) {
        this.context = context;
        this.resultAnalysis = resultAnalysis;
    }

    @Override
    public QuizResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_quiz_result_analysis, parent, false);
        QuizResultViewHolder quizResultHolder = new QuizResultViewHolder(layoutView);
        return quizResultHolder;
    }

    @Override
    public void onBindViewHolder(QuizResultViewHolder holder, int position) {
        ResultObject resultData = resultAnalysis.get(position);

        if(resultData != null){
            holder.questionNum.setText("QUESTION " + resultData.getQuestionNumber());
            holder.mainQuestion.setText(resultData.getQuestionTitle().trim());
            holder.yourAnswer.setText("Your answer: " + resultData.getSelectedAnswer().trim());
            holder.correctAnswer.setText("Correct answer: " + resultData.getCollectAnswer().trim());
            if(resultData.isCorrect()){
                holder.imageMark.setImageResource(R.drawable.goodmark);
            }else{
                holder.imageMark.setImageResource(R.drawable.badmark);
            }
        }else{
            Toast.makeText(context, "Error!! Quiz result analysis missing", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public int getItemCount() {
        return resultAnalysis.size();
    }
}
