package fr.utt.bulat.entities;

import java.util.ArrayList;
import java.util.List;

public class ScoreObject {

    private int score;

    private List<ResultObject> allResult;

    public ScoreObject(){
        allResult = new ArrayList<ResultObject>();
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = this.score + score;
    }

    public void addNewQuizResult(ResultObject resultObject){
        allResult.add(resultObject);
    }

    public List<ResultObject> getQuizResultObject(){
        return this.allResult;
    }
}
