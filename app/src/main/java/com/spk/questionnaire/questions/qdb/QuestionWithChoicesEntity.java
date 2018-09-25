package com.spk.questionnaire.questions.qdb;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "answer_choices")
public class QuestionWithChoicesEntity
{
    @PrimaryKey(autoGenerate = true)
    private long id;
    @ColumnInfo(name = "question_id")
    private String questionId;
    @ColumnInfo(name = "ans_choice")
    private String answerChoice;
    @ColumnInfo(name = "ans_choice_pos")
    private String answerChoicePosition;
    @ColumnInfo(name = "ans_choice_id")
    private String answerChoiceId;
    @ColumnInfo(name = "ans_choice_state")
    private String answerChoiceState;

    public String getAnswerChoiceId()
    {
        return answerChoiceId;
    }

    public void setAnswerChoiceId(String answerChoiceId)
    {
        this.answerChoiceId = answerChoiceId;
    }

    public String getAnswerChoiceState()
    {
        return answerChoiceState;
    }

    public void setAnswerChoiceState(String answerChoiceState)
    {
        this.answerChoiceState = answerChoiceState;
    }

    public String getAnswerChoicePosition()
    {
        return answerChoicePosition;
    }

    public void setAnswerChoicePosition(String answerChoicePosition)
    {
        this.answerChoicePosition = answerChoicePosition;
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getQuestionId()
    {
        return questionId;
    }

    public void setQuestionId(String questionId)
    {
        this.questionId = questionId;
    }

    public String getAnswerChoice()
    {
        return answerChoice;
    }

    public void setAnswerChoice(String answerChoice)
    {
        this.answerChoice = answerChoice;
    }
}
