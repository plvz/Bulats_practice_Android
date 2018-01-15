package fr.utt.bulat.adapters;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.utt.bulat.MainExtraWordActivity;
import fr.utt.bulat.MainPictureActivity;
import fr.utt.bulat.MainQuizActivity;
import fr.utt.bulat.R;
import fr.utt.bulat.entities.CategoryObject;

import java.util.List;

public class QuizCategoryAdapter extends RecyclerView.Adapter<QuizCategoryViewHolder>{

    private Context context;

    private List<CategoryObject> quizCategoryObject;

    private int quizType;

    public QuizCategoryAdapter(Context context, List<CategoryObject> quizCategoryObject, int quizType) {
        this.context = context;
        this.quizCategoryObject = quizCategoryObject;
        this.quizType = quizType;
    }

    @Override
    public QuizCategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.quiz_category_list, parent, false);
        QuizCategoryViewHolder quizCategoryHolder = new QuizCategoryViewHolder(layoutView);
        return quizCategoryHolder;
    }

    @Override
    public void onBindViewHolder(final QuizCategoryViewHolder holder, int position) {
        CategoryObject catObject = quizCategoryObject.get(position);
        final String catName = quizCategoryObject.get(position).getQuizCategoryName();
        final int id = catObject.getId();
        final String customCatnam= catName+""+(position+1);
        holder.categoryName.setText(customCatnam);

        int imageResource = getResourseId(context, catObject.getQuizCategoryImagePath(), "drawable", context.getPackageName());
        holder.categoryImage.setImageResource(imageResource);

        holder.mItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent quizTypeIntent = null;
                if(quizType == 1){
                    quizTypeIntent = new Intent(context, MainQuizActivity.class);
                }else if(quizType == 2){
                    quizTypeIntent = new Intent(context, MainExtraWordActivity.class);
                }
                quizTypeIntent.putExtra("QUIZ_CATEGORY_NAME", customCatnam);
                quizTypeIntent.putExtra("QUIZ_CATEGORY_ID", id);
                context.startActivity(quizTypeIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return quizCategoryObject.size();
    }

    public static int getResourseId(Context context, String pVariableName, String pResourcename, String pPackageName) throws RuntimeException {
        try {
            return context.getResources().getIdentifier(pVariableName, pResourcename, pPackageName);
        } catch (Exception e) {
            throw new RuntimeException("Error getting Resource ID.", e);
        }
    }
}
