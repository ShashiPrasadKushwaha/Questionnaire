package com.spk.questionnaire;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.spk.questionnaire.questions.AnswersActivity;
import com.spk.questionnaire.questions.QuestionActivity;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity
{
    private static final int QUESTIONNAIRE_REQUEST = 2018;
    Button resultButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpToolbar();

        Button questionnaireButton = findViewById(R.id.questionnaireButton);
        resultButton = findViewById(R.id.resultButton);

        questionnaireButton.setOnClickListener(v -> {
            resultButton.setVisibility(View.GONE);

            Intent questions = new Intent(MainActivity.this, QuestionActivity.class);
            //you have to pass as an extra the json string.
            questions.putExtra("json_questions", loadQuestionnaireJson("questions_example.json"));
            startActivityForResult(questions, QUESTIONNAIRE_REQUEST);
        });

        resultButton.setOnClickListener(v -> {
            Intent questions = new Intent(MainActivity.this, AnswersActivity.class);
            startActivity(questions);
        });
    }

    void setUpToolbar()
    {
        Toolbar mainPageToolbar = findViewById(R.id.mainPageToolbar);
        setSupportActionBar(mainPageToolbar);
        getSupportActionBar().setTitle("Questionnaire Demo");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == QUESTIONNAIRE_REQUEST)
        {
            if (resultCode == RESULT_OK)
            {
                resultButton.setVisibility(View.VISIBLE);
                Toast.makeText(this, "Questionnaire Completed!!", Toast.LENGTH_LONG).show();
            }
        }
    }

    //json stored in the assets folder. but you can get it from wherever you like.
    private String loadQuestionnaireJson(String filename)
    {
        try
        {
            InputStream is = getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            return new String(buffer, "UTF-8");
        } catch (IOException ex)
        {
            ex.printStackTrace();
            return null;
        }
    }
}