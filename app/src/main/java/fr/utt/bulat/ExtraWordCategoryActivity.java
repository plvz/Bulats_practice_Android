package fr.utt.bulat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import fr.utt.bulat.adapters.QuizCategoryAdapter;
import fr.utt.bulat.database.DatabaseQuery;
import fr.utt.bulat.entities.CategoryObject;

public class ExtraWordCategoryActivity extends AppCompatActivity {

    private static final String TAG = ExtraWordCategoryActivity.class.getSimpleName();

    private RecyclerView quizRecyclerView;

    private static int quizType = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_category);

        setTitle(getString(R.string.extra_word_category));

        DatabaseQuery dbQuery = new DatabaseQuery(ExtraWordCategoryActivity.this);
        List<CategoryObject> categoryData = dbQuery.getAllQuizCategory(1);

        quizRecyclerView = (RecyclerView)findViewById(R.id.quiz_category);
        GridLayoutManager mGrid = new GridLayoutManager(this, 2);
        quizRecyclerView.setLayoutManager(mGrid);
        quizRecyclerView.setHasFixedSize(true);

        QuizCategoryAdapter mAdapter = new QuizCategoryAdapter(ExtraWordCategoryActivity.this, categoryData, quizType);
        quizRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onBackPressed() {
        Intent backIntent = new Intent(ExtraWordCategoryActivity.this, QuizMenuActivity.class);
        startActivity(backIntent);
    }
}
