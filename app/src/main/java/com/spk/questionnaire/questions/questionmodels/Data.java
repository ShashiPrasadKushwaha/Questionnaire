package com.spk.questionnaire.questions.questionmodels;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data
{
    @SerializedName("questions")
    private List<QuestionsItem> questions;

    public List<QuestionsItem> getQuestions()
    {
        return questions;
    }

    public void setQuestions(List<QuestionsItem> questions)
    {
        this.questions = questions;
    }
}