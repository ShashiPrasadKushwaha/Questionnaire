package com.spk.questionnaire.questions.qdb;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface QuestionChoicesDao
{
    @Insert
    void insertAllChoicesOfQuestion(List<QuestionWithChoicesEntity> choices);

    @Query("UPDATE answer_choices SET  ans_choice_state = :selectState WHERE question_id = :questionId AND ans_choice_pos =:optionId")
    void updateQuestionWithChoice(String selectState, String questionId, String optionId);

    @Query("SELECT ans_choice_state FROM answer_choices WHERE question_id = :questionId AND ans_choice_pos =:optionId")
    String isChecked(String questionId, String optionId);

    @Query("SELECT * FROM answer_choices WHERE ans_choice_state =:selected")
    List<QuestionWithChoicesEntity> getAllQuestionsWithChoices(String selected);

    @Query("DELETE FROM answer_choices")
    void deleteAllChoicesOfQuestion();
}
