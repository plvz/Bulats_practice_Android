package fr.utt.bulat.helper;


import android.content.Context;
import android.content.SharedPreferences;

public class MySharedPreference {

    private SharedPreferences prefs;

    private Context context;

    public MySharedPreference(Context context){
        this.context = context;
        prefs = context.getSharedPreferences(Helper.SHARED_PREF, Context.MODE_PRIVATE);
    }

    public void saveQuizHighestQuizScore(int score){
        SharedPreferences.Editor edits = prefs.edit();
        edits.putInt(Helper.QUIZ_SCORE, score);
        edits.apply();
    }

    public int getCurrentHighestQuizScore(){
        return prefs.getInt(Helper.QUIZ_SCORE, 0);
    }

    public boolean isHighestScore(int newScore){
        int highestScore = getCurrentHighestQuizScore();
        if(highestScore > newScore){
            return true;
        }
        return false;
    }

    public void clearAllSubscriptions(){
        prefs.edit().clear().apply();
    }
}
