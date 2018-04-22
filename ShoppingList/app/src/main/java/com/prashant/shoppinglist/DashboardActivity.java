package com.prashant.shoppinglist;



import android.content.Intent;
import android.content.SyncStatusObserver;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;



public class DashboardActivity extends AppCompatActivity {


    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Bundle b=getIntent().getExtras();
        String email=b.getString("emailId");

        Button surveyBtn = (Button)findViewById(R.id.open_survey_button);

        surveyBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent survey =new Intent(getApplicationContext(),SurveyActivity.class);
            survey.putExtra("emailId", email);
            startActivity(survey);
        }
    });

}
}
