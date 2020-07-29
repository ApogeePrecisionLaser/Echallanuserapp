package com.echallanuser;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.echallanuser.model.Webservice;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;


public class OtpActivity extends AppCompatActivity
{
    EditText editText_otp;
    Button button_submit,btnLinkToResendOtp;
    ProgressDialog dialog;
   // DatabaseOperation dbTask;
    JSONObject jsonObject;
    String type="";
    String otp="";
    String mobile_no="",password;
  //  SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        //-------------------------------------------------------------------------------------------
        try
        {
            Intent intent=getIntent();
            mobile_no=intent.getExtras().getString("mobile_no").toString();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        editText_otp=(EditText)findViewById(R.id.otp);
//        dbTask=new DatabaseOperation(this);
        button_submit=(Button)findViewById(R.id.submitotp);
        btnLinkToResendOtp=(Button)findViewById(R.id.btnLinkToResendOtp);
        button_submit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                        otp=editText_otp.getText().toString();
                        Callingservcie callingservcie = new Callingservcie();
                        callingservcie.execute();
            }
        });
        btnLinkToResendOtp.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                final Webservice webservice = new Webservice(OtpActivity.this);

                String s = webservice.re_SendOTP(mobile_no);
                if(s.equals("success"))
                {
                    Toast.makeText(OtpActivity.this,"OTP Send To "+mobile_no,Toast.LENGTH_LONG);
                }
                else
                {
                    Toast.makeText(OtpActivity.this,"Error On Server Side!!!!Please Try Later",Toast.LENGTH_LONG);
                }
            }
        });

        try
        {
            Intent intent=getIntent();
             jsonObject=new JSONObject(intent.getExtras().getString("jsonObject").toString());
             mobile_no=intent.getExtras().getString("mobile").toString();
        }catch (Exception e){
            System.out.println("otpActivity intent" + e);
        }

    }

    @Override
    public void onBackPressed() {

            super.onBackPressed();

    }







    private class Callingservcie extends AsyncTask<String,String,String> {

        String result="";
        @Override
        protected String doInBackground(String... params) {

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = null;
            try {
                final Webservice genericModel = new Webservice(OtpActivity.this);
              result = genericModel.sendOtp(mobile_no + "_" + otp);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result)
        {

            try {
                if(!result.equals("")) {
                    dialog.dismiss();
             JSONObject jsonObject=new JSONObject(result);
             String val=jsonObject.getString("key_person_id").toString();
                    Intent intent = new Intent(OtpActivity.this, MainActivity.class);
                    intent.putExtra("code", val);
                    startActivity(intent);
                    finish();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }



        }
        @Override
        protected void onPreExecute()
        {
            dialog =  ProgressDialog.show(OtpActivity.this, "", "Proccessing....Please wait");
            dialog.show();
            // Things to be done before execution of long running operation. For
            // example showing ProgessDialog
        }
        @Override
        protected void onProgressUpdate(String... text)
        {
        }
        private void showconnAlert() {
            AlertDialog alertDialog = new AlertDialog.Builder(getApplicationContext()).create();
            // Setting Dialog Title
            alertDialog.setTitle("Message");
            // Setting Dialog Message
            alertDialog.setMessage("Sorry! Not connected to internet");
            alertDialog.setButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // finish();
                }
            });
            // Showing Alert Message
            alertDialog.show();

        }
    }
}
