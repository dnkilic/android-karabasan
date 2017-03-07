package com.dnkilic.karabasan;

public class Dialog {

    public static final int ACTION_GET_NAME = 0;
    public static final int ACTION_GET_AGE = 1;
    public static final int ACTION_GET_HEIGHT = 2;
    public static final int ACTION_MILK = 3;
    public static final int ACTION_VOTE = 4;
    public static final int ACTION_GET_WEIGHT = 5;
    public static final int ACTION_EYES = 6;
    public static final int ACTION_FIFTY = 7;
    public static final int ACTION_NAMING = 8;
    public static final int ACTION_SELECT_NUMBER = 9;
    public static final int ACTION_YOUR_NAME = 10;
    public static final int ACTION_HOW = 11;
    public static final int ACTION_YOUR_COUNTRY = 12;
    public static final int ACTION_STUDENT = 13;

    private String question;
    private int inputType;
    private boolean yesNoQuestion = false;
    private String answer;

    public Dialog(String question, int inputType) {
        this.question = question;
        this.inputType = inputType;
        this.yesNoQuestion = false;
    }

    public Dialog(String question, int inputType, boolean yesNoQuestion) {
        this.question = question;
        this.inputType = inputType;
        this.yesNoQuestion = yesNoQuestion;
    }

    public String getQuestion() {
        return question;
    }

    public int getInputType() {
        return inputType;
    }

    public boolean isYesNoQuestion() {
        return yesNoQuestion;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {

        this.answer = answer;
    }
}