package com.spk.questionnaire.questions.questionmodels;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class QuestionsItem implements Parcelable
{
    public static final Creator<QuestionsItem> CREATOR = new Creator<QuestionsItem>()
    {
        @Override
        public QuestionsItem createFromParcel(Parcel in)
        {
            QuestionsItem questionItem = new QuestionsItem();
            questionItem.questionTypeName = in.readString();
            questionItem.questionName = in.readString();
            questionItem.id = in.readInt();
            Bundle b = in.readBundle(AnswerOptions.class.getClassLoader());
            questionItem.answerOptions = b.getParcelableArrayList("q_items");
            questionItem.questionTypeId = in.readInt();

            return questionItem;
        }

        @Override
        public QuestionsItem[] newArray(int size)
        {
            return new QuestionsItem[size];
        }
    };
    @SerializedName("question_type_name")
    private String questionTypeName;
    @SerializedName("question_name")
    private String questionName;
    @SerializedName("id")
    private int id;
    @SerializedName("question_item")
    private List<AnswerOptions> answerOptions;

    @SerializedName("question_type_id")
    private int questionTypeId;

    public String getQuestionTypeName()
    {
        return questionTypeName;
    }

    public void setQuestionTypeName(String questionTypeName)
    {
        this.questionTypeName = questionTypeName;
    }

    public String getQuestionName()
    {
        return questionName;
    }

    public void setQuestionName(String questionName)
    {
        this.questionName = questionName;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public List<AnswerOptions> getAnswerOptions()
    {
        return answerOptions;
    }

    public void setAnswerOptions(List<AnswerOptions> answerOptions)
    {
        this.answerOptions = answerOptions;
    }

    public int getQuestionTypeId()
    {
        return questionTypeId;
    }

    public void setQuestionTypeId(int questionTypeId)
    {
        this.questionTypeId = questionTypeId;
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(questionTypeName);
        dest.writeString(questionName);
        dest.writeInt(id);
        Bundle b = new Bundle();
        b.putParcelableArrayList("items", (ArrayList<? extends Parcelable>) answerOptions);
        dest.writeBundle(b);
        dest.writeInt(questionTypeId);
    }
}