package fr.utt.bulat.entities;


public class ResultObject {

    private String questionNumber;

    private String questionTitle;

    private String selectedAnswer;

    private String collectAnswer;

    private boolean isCorrect;

    public ResultObject(String questionNumber, String questionTitle, String selectedAnswer, String collectAnswer, boolean isCorrect) {
        this.questionNumber = questionNumber;
        this.questionTitle = questionTitle;
        this.selectedAnswer = selectedAnswer;
        this.collectAnswer = collectAnswer;
        this.isCorrect = isCorrect;
    }

    public String getQuestionNumber() {
        return questionNumber;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public String getSelectedAnswer() {
        return selectedAnswer;
    }

    public String getCollectAnswer() {
        return collectAnswer;
    }

    public boolean isCorrect() {
        return isCorrect;
    }
}
