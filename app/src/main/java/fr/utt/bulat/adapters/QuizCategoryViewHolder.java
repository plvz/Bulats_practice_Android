package fr.utt.bulat.adapters;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import fr.utt.bulat.R;

public class QuizCategoryViewHolder extends RecyclerView.ViewHolder{

    public TextView categoryName;

    public ImageView categoryImage;

    public View mItemView;

    public QuizCategoryViewHolder(View itemView) {
        super(itemView);
        mItemView = itemView;
        categoryName = (TextView)itemView.findViewById(R.id.category_name);
        categoryImage = (ImageView)itemView.findViewById(R.id.category_image);
    }
}
