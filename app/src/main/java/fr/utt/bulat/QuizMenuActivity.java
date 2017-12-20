package fr.utt.bulat;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class QuizMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_menu);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        ActionBar actionBar = getSupportActionBar();
        if(null != actionBar){
            actionBar.hide();
        }

        Button selectQuiz = (Button)findViewById(R.id.text_quiz_button);
        assert selectQuiz != null;
        selectQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent quizCategoryIntent = new Intent(QuizMenuActivity.this, QuizCategoryActivity.class);
                quizCategoryIntent.putExtra("QUIZ_TYPE", 1);
                startActivity(quizCategoryIntent);
            }
        });

        Button bestQuiz = (Button)findViewById(R.id.extra_word_button);
        assert bestQuiz != null;
        bestQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent extraWordQuizCategory = new Intent(QuizMenuActivity.this, ExtraWordCategoryActivity.class);
                extraWordQuizCategory.putExtra("QUIZ_TYPE", 2);
                startActivity(extraWordQuizCategory);
            }
        });

        Button quizInstruction = (Button)findViewById(R.id.quiz_instruction_button);
        assert quizInstruction != null;
        quizInstruction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent instructionIntent = new Intent(QuizMenuActivity.this, QuizInstructionActivity.class);
                startActivity(instructionIntent);
            }
        });
    }
}
