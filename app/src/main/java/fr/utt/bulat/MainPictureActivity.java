package fr.utt.bulat;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.utt.bulat.database.DatabaseQuery;
import fr.utt.bulat.entities.QuestionObject;
import fr.utt.bulat.entities.ResultObject;
import fr.utt.bulat.entities.ScoreObject;
import fr.utt.bulat.helper.MySharedPreference;

import java.util.List;

public class MainPictureActivity extends AppCompatActivity implements Button.OnClickListener{

    private static final String TAG = MainPictureActivity.class.getSimpleName();

    private TextView pictureQuestionNum;
    private TextView pictureQuestion;
    private ImageView imageQuestion;
    private Button answerOne, answerTwo, answerThree, answerFour;

    private DatabaseQuery query;
    private List<QuestionObject> quizQuestionList;
    private int totalQuizQuestion;
    private QuestionObject quizQuestion;
    private int quizStartingNumber = 0;

    private ScoreObject mScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_picture);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        String categoryName = getIntent().getExtras().getString("QUIZ_CATEGORY_NAME");
        if(!TextUtils.isEmpty(categoryName)){
            setTitle("Quiz on " + categoryName);
        }

        mScore = new ScoreObject();

        pictureQuestionNum = (TextView)findViewById(R.id.question_number);
        pictureQuestion = (TextView)findViewById(R.id.question);
        imageQuestion = (ImageView)findViewById(R.id.question_image);
        answerOne = (Button)findViewById(R.id.answer_one);
        answerTwo = (Button)findViewById(R.id.answer_two);
        answerThree = (Button)findViewById(R.id.answer_three);
        answerFour = (Button)findViewById(R.id.answer_four);

        answerOne.setOnClickListener(this);
        answerTwo.setOnClickListener(this);
        answerThree.setOnClickListener(this);
        answerFour.setOnClickListener(this);

        // hide all view and load quiz content
        hideAllViews();

        // get quiz data from database
        query = new DatabaseQuery(MainPictureActivity.this);
        int pictureQuizId = getIntent().getExtras().getInt("QUIZ_CATEGORY_ID");
        Log.d(TAG, "Quiz category " + pictureQuizId);
        quizQuestionList = query.getQuizQuestionsById(pictureQuizId);

        if(quizQuestionList.size() <= 0){
            Toast.makeText(this, "This quiz category has not yet been created. Please contact the admin", Toast.LENGTH_LONG).show();
            return;
        }else {
            totalQuizQuestion = quizQuestionList.size();
            quizQuestion = quizQuestionList.get(quizStartingNumber);
            showAllViews();
            displayCurrentQuizQuestion();
        }
    }

    private void hideAllViews(){
        pictureQuestionNum.setVisibility(View.GONE);
        pictureQuestion.setVisibility(View.GONE);
        imageQuestion.setVisibility(View.GONE);
        answerOne.setVisibility(View.GONE);
        answerTwo.setVisibility(View.GONE);
        answerThree.setVisibility(View.GONE);
        answerFour.setVisibility(View.GONE);
    }

    private void showAllViews(){
        pictureQuestionNum.setVisibility(View.VISIBLE);
        pictureQuestion.setVisibility(View.VISIBLE);
        imageQuestion.setVisibility(View.VISIBLE);
        answerOne.setVisibility(View.VISIBLE);
        answerTwo.setVisibility(View.VISIBLE);
        answerThree.setVisibility(View.VISIBLE);
        answerFour.setVisibility(View.VISIBLE);
    }

    private void displayCurrentQuizQuestion(){
        pictureQuestionNum.setText("Question " + (quizStartingNumber + 1));
        pictureQuestion.setText(quizQuestion.getQuestion());

        int imageId = getResourceId(quizQuestion.getImage(), "drawable", getPackageName());
        imageQuestion.setImageResource(imageId);

        String[] selectAnswer = getAnswerOptions();
        answerOne.setText(selectAnswer[0]);
        answerTwo.setText(selectAnswer[1]);
        answerThree.setText(selectAnswer[2]);
        answerFour.setText(selectAnswer[3]);
    }

    private String[] getAnswerOptions(){
        String answerOption = quizQuestion.getOptions();
        return quizQuestion.convertOptionsToStringArray(answerOption);
    }

    public int getResourceId(String variableName, String resourceName, String packageName) throws RuntimeException {
        try {
            return getResources().getIdentifier(variableName, resourceName, packageName);
        } catch (Exception e) {
            throw new RuntimeException("Error getting Resource ID.", e);
        }
    }

    @Override
    public void onClick(View v) {
        Button clickedButton = (Button)v;
        String userSelectedAnswer = clickedButton.getText().toString();
        if(userSelectedAnswer.trim().equals(quizQuestion.getAnswer().trim())){
            // answer is correct
            //set new score
            mScore.setScore(1);
            //set the result
            mScore.addNewQuizResult(new ResultObject(""+quizQuestion.getId(), quizQuestion.getQuestion(), userSelectedAnswer, quizQuestion.getAnswer(), true));
        }else{
            // answer is incorrect
            mScore.addNewQuizResult(new ResultObject(""+quizQuestion.getId(), quizQuestion.getQuestion(), userSelectedAnswer, quizQuestion.getAnswer(), false));
        }
        quizStartingNumber++;

        // check if there is more question
        if(quizStartingNumber >= totalQuizQuestion){
            // Quiz over
            Intent quizOverIntent = new Intent(MainPictureActivity.this, QuizResultActivity.class);

            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            final String scoreString = gson.toJson(mScore);
            quizOverIntent.putExtra("RESULT_OBJECT", scoreString);

            double percentageScore = (mScore.getScore() * 100) / totalQuizQuestion;
            quizOverIntent.putExtra("TOTAL_SCORE", String.valueOf(percentageScore));

            // compare score and save
            MySharedPreference sharedPreference = new MySharedPreference(MainPictureActivity.this);
            Double mDouble = new Double(percentageScore);
            int presentScore = mDouble.intValue();
            if(!sharedPreference.isHighestScore(presentScore)){
                sharedPreference.saveQuizHighestQuizScore(presentScore);
            }

            startActivity(quizOverIntent);

        }else{
            // display new questions
            quizQuestion = quizQuestionList.get(quizStartingNumber);
            displayCurrentQuizQuestion();
        }
    }
}
