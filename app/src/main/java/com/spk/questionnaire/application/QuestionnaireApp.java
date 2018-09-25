package com.spk.questionnaire.application;

import android.app.Application;

import com.facebook.stetho.Stetho;

public class QuestionnaireApp extends Application
{
    private static QuestionnaireApp sInstance;

    public static synchronized QuestionnaireApp getInstance()
    {
        return sInstance;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();

        sInstance = this;

        //Stetho is used to view the structure and values in Tables of Database.
        //Connect a device/emulator, run the app and complete the Questionnaire
        //then open Chrome browser and type this in address bar "chrome://inspect/#devices",
        //you will find connected device/emulator in below section of screen.
        Stetho.initializeWithDefaults(this);
    }
}
