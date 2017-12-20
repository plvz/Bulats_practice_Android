package fr.utt.bulat;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import fr.utt.bulat.database.DatabaseQuery;
import fr.utt.bulat.entities.QuestionObject;
import fr.utt.bulat.entities.ResultObject;
import fr.utt.bulat.entities.ScoreObject;
import fr.utt.bulat.helper.MySharedPreference;

public class MainExtraWordActivity extends AppCompatActivity {

    private static final String TAG = MainExtraWordActivity.class.getSimpleName();

    private TextView questionNumber, question;

    private EditText userAnswer;


    private List<QuestionObject> quizObject;

    private QuestionObject allQuestions;

    private int totalQuizCount;

    private int questionCount = 0;

    private ScoreObject mScore;

    private Button nextQuestionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extra_word);

        String categoryName = getIntent().getExtras().getString("QUIZ_CATEGORY_NAME");
        if(!TextUtils.isEmpty(categoryName)){
            setTitle("Quiz on " + categoryName);
        }

        mScore = new ScoreObject();

        questionNumber = (TextView)findViewById(R.id.question_number);
        question = (TextView)findViewById(R.id.question);


        int quizCategoryId = getIntent().getExtras().getInt("QUIZ_CATEGORY_ID");
        DatabaseQuery query = new DatabaseQuery(MainExtraWordActivity.this);

        quizObject = query.getQuizQuestionsById(quizCategoryId);

        nextQuestionButton = (Button)findViewById(R.id.next_quiz);

        if(quizObject.size() > 0){
            totalQuizCount = quizObject.size();
            allQuestions = quizObject.get(questionCount);
            displayQuizQuestions();

            assert nextQuestionButton != null;
            nextQuestionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    userAnswer = (EditText)findViewById(R.id.answer);
                    String userSelectedAnswer = userAnswer.getText().toString();
                    if(userSelectedAnswer.equals("")){
                        Toast.makeText(MainExtraWordActivity.this, "You must set an answer " + userSelectedAnswer, Toast.LENGTH_LONG).show();
                    }else{
                        //check for the correct answer
                        Log.d(TAG, "Match answers " + allQuestions.getAnswer() + " select " + userSelectedAnswer);
                        if((allQuestions.getAnswer().trim()).toLowerCase().equals((userSelectedAnswer.trim()).toLowerCase())){
                            //set new score
                            mScore.setScore(1);
                            //set the result
                            mScore.addNewQuizResult(new ResultObject(""+allQuestions.getId(), allQuestions.getQuestion(), userSelectedAnswer, allQuestions.getAnswer(), true));
                        }else{
                            mScore.addNewQuizResult(new ResultObject(""+allQuestions.getId(), allQuestions.getQuestion(), userSelectedAnswer, allQuestions.getAnswer(), false));
                        }
                        Log.d(TAG, "Quiz Result " + mScore.getQuizResultObject().size());
                        questionCount++;

                        // check if there is more question
                        if(questionCount >= totalQuizCount){
                            // Quiz over
                            Intent quizOverIntent = new Intent(MainExtraWordActivity.this, QuizResultActivity.class);

                            GsonBuilder builder = new GsonBuilder();
                            Gson gson = builder.create();
                            final String scoreString = gson.toJson(mScore);
                            quizOverIntent.putExtra("RESULT_OBJECT", scoreString);

                            double percentageScore = (mScore.getScore() * 100) / totalQuizCount ;
                            quizOverIntent.putExtra("TOTAL_SCORE", String.valueOf(percentageScore));

                            // compare score and save
                            MySharedPreference sharedPreference = new MySharedPreference(MainExtraWordActivity.this);
                            Double mDouble = new Double(percentageScore);
                            int presentScore = mDouble.intValue();
                            if(!sharedPreference.isHighestScore(presentScore)){
                                sharedPreference.saveQuizHighestQuizScore(presentScore);
                            }

                            startActivity(quizOverIntent);

                        }else{
                            // display new questions
                            allQuestions = quizObject.get(questionCount);
                            displayQuizQuestions();
                        }
                    }
                }
            });
        }else{

            nextQuestionButton.setVisibility(View.GONE);
            Toast.makeText(MainExtraWordActivity.this, getString(R.string.no_quiz_in_category), Toast.LENGTH_LONG).show();
        }
    }

    private void displayQuizQuestions(){
        if(allQuestions != null){
            int currentQuestion = questionCount + 1;
            questionNumber.setText("Question " + currentQuestion);
            question.setText(allQuestions.getQuestion());

            String answerOption = allQuestions.getOptions();
            String[] allAnswerOptions = allQuestions.convertOptionsToStringArray(answerOption);


        }
    }




}
