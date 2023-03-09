package com.example.myapplication.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;


import com.example.myapplication.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class TermDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);
        /*
        final String termTitle = getString(R.string.termTitle);
        final String termStartDate = getString(R.string.termStartDate);
        final String termEndDate = getString(R.string.termEndDate);
        final String saveTermButtonText = getString(R.string.saveTermButton);

        final TextView termTitleTextView = findViewById(R.id.term_Title);
        final TextView termStartDateTextView = findViewById(R.id.term_StartDate);
        final TextView termEndDateTextView = findViewById(R.id.term_EndDate);
        final Button saveTermButton = findViewById(R.id.save_TermButton);

        EditText termTitleInput = findViewById(R.id.term_TitleText);
        EditText termStartInput = findViewById(R.id.term_StartDateText);
        EditText termEndInput = findViewById(R.id.term_EndDateText);

        List<TextView> termDisplayList = new ArrayList<>();

        termTitleTextView.setText(termTitle);
        termStartDateTextView.setText(termStartDate);
        termEndDateTextView.setText(termEndDate);
        saveTermButton.setText(saveTermButtonText);


        saveTermButton.setOnClickListener(view -> {
            // get the text from each EditText
            String termTitleInputText = termTitleInput.getText().toString();
            String termStartInputText = termStartInput.getText().toString();
            String termEndInputText = termEndInput.getText().toString();

            //Terms object to insert into the database
            Terms newTerm = new Terms(0, termTitleInputText, termStartInputText, termEndInputText);


            // insert the new term into the database
            ScheduleDatabaseBuilder db = ScheduleDatabaseBuilder.getDatabase(TermList.this);
            db.termsDao().insert(newTerm);

            // update the termDisplayList to reflect the new state of the database
            List<Terms> allTerms = db.termsDao().getAllTerms();
            LinearLayout linearLayoutTerm = findViewById(R.id.linearLayoutTermDisplay);
            linearLayoutTerm.removeAllViews();
            termDisplayList.clear();
            for (Terms term : allTerms) {
                TextView newTextView = new TextView(TermList.this);
                String finalTermDisplayText = term.getTermTitle() + " " + term.getTermStart() + " " + term.getTermEnd();
                newTextView.setText(finalTermDisplayText);
                newTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                linearLayoutTerm.addView(newTextView);
                termDisplayList.add(newTextView);
            }

            // create a new TextView to display the term data
            TextView newTextView = new TextView(TermList.this);
            String finalTermDisplayText = termTitleInputText + " " + termStartInputText + " " + termEndInputText;
            newTextView.setText(finalTermDisplayText);
            newTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);

            // add the new TextView to the LinearLayout

            linearLayoutTerm.addView(newTextView);

            // add the new TextView to the termDisplayList
            termDisplayList.add(newTextView);

            // concatenate the text of each TextView in termDisplayList
            StringBuilder builder = new StringBuilder();
            for (TextView textView : termDisplayList) {
                builder.append(textView.getText().toString()).append("\n");
            }
            String allTermDisplayText = builder.toString();

            // clear the EditText fields
            termTitleInput.setText("");
            termStartInput.setText("");
            termEndInput.setText("");


            // hide the soft keyboard
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        });
*/
        FloatingActionButton fab = findViewById(R.id.termListFloatingActionButton);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(TermDetails.this, CourseList.class);
            startActivity(intent);
        });

    }

}