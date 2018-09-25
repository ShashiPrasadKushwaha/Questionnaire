package com.spk.questionnaire.questions.qdb;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface QuestionDao
{
    @Insert
    void insertAllQuestions(List<QuestionEntity> questions);

    //@Query("UPDATE questions SET  q_option_state = :selectState WHERE question_id = :questionId AND q_option_id =:optionId")
    //void updateQuestionWithChoice(String selectState, String questionId, String optionId);

    //@Query("SELECT q_option_state FROM questions WHERE question_id = :questionId AND q_option_id =:optionId")
    //String isChecked(String questionId, String optionId);

    @Query("SELECT * FROM questions")
    List<QuestionEntity> getAllQuestions();

    @Query("DELETE FROM questions")
    void deleteAllQuestions();
}
