package com.prashant.shoppinglist;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    Button signin,signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signin=(Button) findViewById(R.id.signin);
        signin.setOnClickListener(this);
        signup=(Button) findViewById(R.id.signup);
        signup.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.signin:
                // do your code
                EditText username=(EditText) findViewById(R.id.username);
                EditText password=(EditText) findViewById(R.id.password);
                //final ProgressBar progressBar=(ProgressBar)findViewById(R.id.sign_in_progress);
               // progressBar.setVisibility(View.VISIBLE);
                enableProgressBar();
                requestLogin(username,password);
                //progressBar.setVisibility(View.GONE);
                break;

            case R.id.signup:
                // do your code
                Intent signupIntent=new Intent(this,signupActivity.class);
                startActivity(signupIntent);
                break;
             default:
                break;
        }

    }

    public void enableProgressBar()
    {
        ProgressBar uploadProgressBar=(ProgressBar)findViewById(R.id.sign_in_progress);
        uploadProgressBar.setVisibility(View.VISIBLE);
    }

    public void disableProgressBar()
    {
        ProgressBar uploadProgressBar=(ProgressBar)findViewById(R.id.sign_in_progress);
        uploadProgressBar.setVisibility(View.INVISIBLE);
    }



    public void requestLogin(EditText username,EditText password)
    {

        //manage this stuff

        final String user=username.getText().toString();
        final String psw=password.getText().toString();


        //connecting in backgroud thread

       final String url="http://"+Server.serverAddress+"/Users/login?emailId="+user+"&password="+psw;


        JSONObject obj=new JSONObject();
        try {

            obj.put("emailId",user);
            obj.put("password",psw);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        StringRequest req=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
                disableProgressBar();
                if(response.equals("Allow login"))
                {
                    Intent dashboard=new Intent(getApplicationContext(),DashboardActivity.class);
                    dashboard.putExtra("emailId",user);
                    startActivity(dashboard);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                disableProgressBar();
                Toast.makeText(getApplicationContext(),"Invalid Credentials",Toast.LENGTH_LONG).show();
                //remove below code
                //Intent survey=new Intent(getApplicationContext(),SurveyActivity.class);
                //survey.putExtra("emailID","deepak@gmail.com");
                //
                error.printStackTrace();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();

                params.put("emailId",user);
                params.put("password",psw);
                return params;

            }
        };
        //check response

        RequestQueue queue=Volley.newRequestQueue(getApplicationContext());
        queue.add(req);


    }

    private void scheduleNotification(Notification notification) {

        Intent intent = new Intent(this, NotifyBroadcast.class);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 00);
        calendar.set(Calendar.SECOND, 00);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    private Notification getNotification(String content) {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("Scheduled Notification");
        builder.setContentText(content);
        builder.setSmallIcon(R.drawable.vegetarian_food);
        return builder.build();
    }

}
