package com.spk.questionnaire.questions.database;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

import com.spk.questionnaire.questions.qdb.QuestionChoicesDao;
import com.spk.questionnaire.questions.qdb.QuestionDao;
import com.spk.questionnaire.questions.qdb.QuestionEntity;
import com.spk.questionnaire.questions.qdb.QuestionWithChoicesEntity;

@Database(entities = {QuestionWithChoicesEntity.class, QuestionEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase
{
    private static final String DB_NAME = "question_db";

    private static AppDatabase INSTANCE;

    public static synchronized AppDatabase getAppDatabase(Context context)
    {
        if (INSTANCE == null)
        {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DB_NAME)

                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }

    public abstract QuestionChoicesDao getQuestionChoicesDao();
    public abstract QuestionDao getQuestionDao();
}
