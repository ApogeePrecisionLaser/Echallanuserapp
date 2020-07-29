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

public class Login extends AppCompatActivity {
    EditText mobile_no;
    ProgressDialog dialog;

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
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                number = mobile_no.getText().toString();
                caldata caldata=new caldata();
                caldata.execute();
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
