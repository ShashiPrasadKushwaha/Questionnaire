# Questionnaire
This is a demo application which will help to make **Questionnaire** *(Survey, Feedback, Poll, Filter, Exam, Questions)* for users in your App.

This project got inspiration from this repo -> https://github.com/AndreiD/surveylib

![alt text](https://github.com/ShashiPrasadKushwaha/Questionnaire/raw/master/app/questionnaire.gif "Questionnaire Gif")
## Let see what you will learn from this Project.

 - How to use Room *(part of Android Architecture Components )*.
 - How to use simple Background process with RxJava *(RxAndroid)* or we say how to replace Async task with RxJava.
 - Parcelable with Nested Model (as List of Model inside Model) in Pojo.
 - Other various logic building.

#### Situations: (When its helpful to use/refer this Project)

 - [x] This project **only** works with situations where You have Multiple/Single Choice(s) based questions.**(If more variation needed, refer [AndreiD's work](https://github.com/AndreiD/surveylib))**

 - [x] If you want persistence with the previous selection of choice(s), means user can go back and modify the selection and previous selections will not lose there state.**(This is addition feature in this Project)**
 - [x] What if you don't want or submit the result of *Questionnaire* as soon the completion. Means if there is other work(s) before sending the data to back-end, and also you don't want to hanging with result in between Activities. See the [AnswerActivity](https://github.com/ShashiPrasadKushwaha/Questionnaire/blob/master/app/src/main/java/com/spk/questionnaire/questions/AnswersActivity.java) , how to retrieve the result from database and display/send. **(This is addition feature in this Project)**

###### Want to send result in Json format see the [AnswerActivity.java](https://github.com/ShashiPrasadKushwaha/Questionnaire/blob/master/app/src/main/java/com/spk/questionnaire/questions/AnswersActivity.java)
```java
/*Here,JSON got created and send to make Result View as per Project requirement.
* Alternatively, in your case, you make Network-call to send the result to back-end.*/
private void makeJsonDataToMakeResultView()
{
  try
  {
     JSONArray questionAndAnswerArray = new JSONArray();
     int questionsSize = questionsList.size();
     if (questionsSize > 0)
	  {
		  for (int i = 0; i < questionsSize; i++)
		  {
             JSONObject questionName = new JSONObject();
             questionName.put("question",questionsList.get(i).getQuestion());
            //questionName.put("question_id",String.valueOf(questionsList.get(i).getQuestionId()));
             String questionId = String.valueOf(questionsList.get(i).getQuestionId());

            JSONArray answerChoicesList = new JSONArray();
            int selectedChoicesSize = questionsWithAllChoicesList.size();

            for (int k = 0; k < selectedChoicesSize; k++)
             {
                String questionIdOfChoice = questionsWithAllChoicesList.get(k).getQuestionId();
                if (questionId.equals(questionIdOfChoice))
                 {
                     JSONObject selectedChoice = new JSONObject();
                     selectedChoice.put("answer_choice", questionsWithAllChoicesList.get(k).getAnswerChoice());
                    //selectedChoice.put("answer_id", questionsWithAllChoicesList.get(k).getAnswerChoiceId());
                     answerChoicesList.put(selectedChoice);
                 }
             }

            questionName.put("selected_answer", answerChoicesList);

            questionAndAnswerArray.put(questionName);
          }
      }
		  questionsAnswerView(questionAndAnswerArray);

  } catch (JSONException e)
	 {
		  e.printStackTrace();
	 }
}
```
#### If you found helpful and learnt something new from this work, please star this project.

## License

~~~~
Copyright 2018 Shashi Prasad Kushwaha

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
~~~~