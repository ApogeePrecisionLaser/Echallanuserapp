package com.echallanuser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.echallanuser.database.DatabaseOperation;
import com.echallanuser.model.Webservice;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Login extends AppCompatActivity {
    EditText mobile_no;
    ProgressDialog dialog;
      SessionManager sessionManager;
    Button verify;
    String number = "";
    String ip = "", port = "";
    DatabaseOperation db = new DatabaseOperation(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        db.open();

        mobile_no = findViewById(R.id.number);
        verify = findViewById(R.id.submit);
        sessionManager=new SessionManager(this);
        boolean islogin=sessionManager.checkLogin();
        if(islogin){
            HashMap<String,String> userDetail=sessionManager.getUserDetails();
            String ph=userDetail.get(SessionManager.KEY_MOBILE);
            Intent i=new Intent(this,MainActivity.class);
            i.putExtra("mobile_no",ph);
            finish();
            startActivity(i);
        }

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(!mobile_no.getText().toString().isEmpty()) {
                   number = mobile_no.getText().toString();
                   sessionManager.createLoginSession(number);
                   /*number verfication from server registered or not*/
                   caldata caldata = new caldata();
                   caldata.execute();
               }else{
                  Toast.makeText(Login.this,"Enter Mobile number ",Toast.LENGTH_LONG).show();
               }
            }
        });
    }

    private class caldata extends AsyncTask<String, String, String> {
        ProgressDialog dialog;
        String data = "";

        @Override
        protected String doInBackground(String... params) {

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = null;
            try {
                final Webservice webservice = new Webservice(Login.this);
                data = webservice.mobilenumber(number);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                if (!result.isEmpty())
                    if (result.equals("success")) {
                        dialog.dismiss();
                        Intent intent = new Intent(Login.this, OtpActivity.class);
                        intent.putExtra("mobile_no", number);
                        finish();
                        startActivity(intent);
                    } else {
                        dialog.dismiss();
                        Toast.makeText(Login.this, "Error in response!!!!Please try Later", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(Login.this, Login.class);
                        finish();
                        startActivity(intent);
                    }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onPreExecute() {
            dialog = ProgressDialog.show(Login.this, "", "Proccessing....Please wait");
            dialog.show();
            // Things to be done before execution of long running operation. For
            // example showin ProgessDialog
        }
    }
}
