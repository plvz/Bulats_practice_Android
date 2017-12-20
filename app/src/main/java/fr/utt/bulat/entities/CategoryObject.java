package fr.utt.bulat.entities;

public class CategoryObject {

    private int id;

    private String quizCategoryName;

    private String quizCategoryImagePath;

    public CategoryObject(String quizCategoryName, String quizCategoryImagePath) {
        this.quizCategoryName = quizCategoryName;
        this.quizCategoryImagePath = quizCategoryImagePath;
    }

    public CategoryObject(int id, String quizCategoryName, String quizCategoryImagePath) {
        this.id = id;
        this.quizCategoryName = quizCategoryName;
        this.quizCategoryImagePath = quizCategoryImagePath;
    }

    public int getId(){
        return id;
    }

    public String getQuizCategoryName() {
        return quizCategoryName;
    }

    public String getQuizCategoryImagePath() {
        return quizCategoryImagePath;
    }
}
