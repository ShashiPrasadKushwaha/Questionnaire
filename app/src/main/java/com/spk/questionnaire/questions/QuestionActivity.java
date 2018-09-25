package com.spk.questionnaire.questions;

import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.widget.TextView;

import com.google.gson.Gson;
import com.spk.questionnaire.R;
import com.spk.questionnaire.questions.adapters.ViewPagerAdapter;
import com.spk.questionnaire.questions.database.AppDatabase;
import com.spk.questionnaire.questions.fragments.CheckBoxesFragment;
import com.spk.questionnaire.questions.fragments.RadioBoxesFragment;
import com.spk.questionnaire.questions.qdb.QuestionEntity;
import com.spk.questionnaire.questions.qdb.QuestionWithChoicesEntity;
import com.spk.questionnaire.questions.questionmodels.AnswerOptions;
import com.spk.questionnaire.questions.questionmodels.QuestionDataModel;
import com.spk.questionnaire.questions.questionmodels.QuestionsItem;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class QuestionActivity extends AppCompatActivity
{
    final ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
    List<QuestionsItem> questionsItems = new ArrayList<>();
    private AppDatabase appDatabase;
    //private TextView questionToolbarTitle;
    private TextView questionPositionTV;
    private String totalQuestions = "1";
    private Gson gson;
    private ViewPager questionsViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        toolBarInit();

        appDatabase = AppDatabase.getAppDatabase(QuestionActivity.this);
        gson = new Gson();

        if (getIntent().getExtras() != null)
        {
            Bundle bundle = getIntent().getExtras();
            parsingData(bundle);
        }
    }

    private void toolBarInit()
    {
        Toolbar questionToolbar = findViewById(R.id.questionToolbar);
        questionToolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        questionToolbar.setNavigationOnClickListener(v -> onBackPressed());

        //questionToolbarTitle = questionToolbar.findViewById(R.id.questionToolbarTitle);
        questionPositionTV = questionToolbar.findViewById(R.id.questionPositionTV);

        //questionToolbarTitle.setText("Questions");
    }

    /*This method decides how many Question-Screen(s) will be created and
    what kind of (Multiple/Single choices) each Screen will be.*/
    private void parsingData(Bundle bundle)
    {
        QuestionDataModel questionDataModel = new QuestionDataModel();

        questionDataModel = gson.fromJson(bundle.getString("json_questions"), QuestionDataModel.class);

        questionsItems = questionDataModel.getData().getQuestions();

        totalQuestions = String.valueOf(questionsItems.size());
        String questionPosition = "1/" + totalQuestions;
        setTextWithSpan(questionPosition);

        preparingQuestionInsertionInDb(questionsItems);
        preparingInsertionInDb(questionsItems);

        for (int i = 0; i < questionsItems.size(); i++)
        {
            QuestionsItem question = questionsItems.get(i);

            if (question.getQuestionTypeName().equals("CheckBox"))
            {
                CheckBoxesFragment checkBoxesFragment = new CheckBoxesFragment();
                Bundle checkBoxBundle = new Bundle();
                checkBoxBundle.putParcelable("question", question);
                checkBoxBundle.putInt("page_position", i);
                checkBoxesFragment.setArguments(checkBoxBundle);
                fragmentArrayList.add(checkBoxesFragment);
            }

            if (question.getQuestionTypeName().equals("Radio"))
            {
                RadioBoxesFragment radioBoxesFragment = new RadioBoxesFragment();
                Bundle radioButtonBundle = new Bundle();
                radioButtonBundle.putParcelable("question", question);
                radioButtonBundle.putInt("page_position", i);
                radioBoxesFragment.setArguments(radioButtonBundle);
                fragmentArrayList.add(radioBoxesFragment);
            }
        }

        questionsViewPager = findViewById(R.id.pager);
        questionsViewPager.setOffscreenPageLimit(1);
        ViewPagerAdapter mPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragmentArrayList);
        questionsViewPager.setAdapter(mPagerAdapter);
    }

    public void nextQuestion()
    {
        int item = questionsViewPager.getCurrentItem() + 1;
        questionsViewPager.setCurrentItem(item);

        String currentQuestionPosition = String.valueOf(item + 1);

        String questionPosition = currentQuestionPosition + "/" + totalQuestions;
        setTextWithSpan(questionPosition);
    }

    public int getTotalQuestionsSize()
    {
        return questionsItems.size();
    }

    private void preparingQuestionInsertionInDb(List<QuestionsItem> questionsItems)
    {
        List<QuestionEntity> questionEntities = new ArrayList<>();

        for (int i = 0; i < questionsItems.size(); i++)
        {
            QuestionEntity questionEntity = new QuestionEntity();
            questionEntity.setQuestionId(questionsItems.get(i).getId());
            questionEntity.setQuestion(questionsItems.get(i).getQuestionName());

            questionEntities.add(questionEntity);
        }
        insertQuestionInDatabase(questionEntities);
    }

    private void insertQuestionInDatabase(List<QuestionEntity> questionEntities)
    {
        Observable.just(questionEntities)
                .map(this::insertingQuestionInDb)
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    /*First, clear the table, if any previous data saved in it. Otherwise, we get repeated data.*/
    private String insertingQuestionInDb(List<QuestionEntity> questionEntities)
    {
        appDatabase.getQuestionDao().deleteAllQuestions();
        appDatabase.getQuestionDao().insertAllQuestions(questionEntities);
        return "";
    }

    private void preparingInsertionInDb(List<QuestionsItem> questionsItems)
    {
        ArrayList<QuestionWithChoicesEntity> questionWithChoicesEntities = new ArrayList<>();

        for (int i = 0; i < questionsItems.size(); i++)
        {
            List<AnswerOptions> answerOptions = questionsItems.get(i).getAnswerOptions();

            for (int j = 0; j < answerOptions.size(); j++)
            {
                QuestionWithChoicesEntity questionWithChoicesEntity = new QuestionWithChoicesEntity();
                questionWithChoicesEntity.setQuestionId(String.valueOf(questionsItems.get(i).getId()));
                questionWithChoicesEntity.setAnswerChoice(answerOptions.get(j).getName());
                questionWithChoicesEntity.setAnswerChoicePosition(String.valueOf(j));
                questionWithChoicesEntity.setAnswerChoiceId(answerOptions.get(j).getAnswerId());
                questionWithChoicesEntity.setAnswerChoiceState("0");

                questionWithChoicesEntities.add(questionWithChoicesEntity);
            }
        }

        insertQuestionWithChoicesInDatabase(questionWithChoicesEntities);
    }

    private void insertQuestionWithChoicesInDatabase(List<QuestionWithChoicesEntity> questionWithChoicesEntities)
    {
        Observable.just(questionWithChoicesEntities)
                .map(this::insertingQuestionWithChoicesInDb)
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    /*First, clear the table, if any previous data saved in it. Otherwise, we get repeated data.*/
    private String insertingQuestionWithChoicesInDb(List<QuestionWithChoicesEntity> questionWithChoicesEntities)
    {
        appDatabase.getQuestionChoicesDao().deleteAllChoicesOfQuestion();
        appDatabase.getQuestionChoicesDao().insertAllChoicesOfQuestion(questionWithChoicesEntities);
        return "";
    }

    @Override
    public void onBackPressed()
    {
        if (questionsViewPager.getCurrentItem() == 0)
        {
            super.onBackPressed();
        } else
        {
            int item = questionsViewPager.getCurrentItem() - 1;
            questionsViewPager.setCurrentItem(item);

            String currentQuestionPosition = String.valueOf(item + 1);

            String questionPosition = currentQuestionPosition + "/" + totalQuestions;
            setTextWithSpan(questionPosition);
        }
    }

    private void setTextWithSpan(String questionPosition)
    {
        int slashPosition = questionPosition.indexOf("/");

        Spannable spanText = new SpannableString(questionPosition);
        spanText.setSpan(new RelativeSizeSpan(0.7f), slashPosition, questionPosition.length(), 0);
        questionPositionTV.setText(spanText);
    }
}