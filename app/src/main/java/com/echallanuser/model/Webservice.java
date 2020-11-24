package com.echallanuser.model;
import android.content.Context;
import android.net.Uri;
import android.os.StrictMode;
import android.util.Log;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;


public class Webservice {
    private static final String TAG = "Echallan";
    Context context;
    String server_ip;
    String port;
    public Webservice(Context context) {
        this.context = context;
    }

    public String getdata(JSONObject jsonObject) {
        String data = "";
        HttpResponse response = null;
        JSONArray jsonArray=new JSONArray();
        JSONObject receivedJsonObj = null;
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            HttpPost httppost = null;
            if(server_ip!=null){
                httppost = new HttpPost("http://" +server_ip + ":" + port + "/TrafficPoliceNew/api/shift/getOffenderdata");
            }else{
                httppost = new HttpPost("http://" + "120.138.10.251" + ":" + "8084" + "/api/shift/getOffenderdata");
            }

            HttpParams httpParameters = new BasicHttpParams();
            HttpClient httpClient = new DefaultHttpClient(httpParameters);
            httppost.setHeader("Content-type", "application/json");
            httppost.setEntity(new StringEntity(jsonObject.toString(), "UTF-8"));
            try {

               response = httpClient.execute(httppost);
               receivedJsonObj = processHttpResponse(response);
//                result = EntityUtils.toString(response.getEntity());
               data = receivedJsonObj.toString();

                httpClient.getConnectionManager().shutdown();
               } catch (Exception e) {
                Log.e(TAG, "Error in http data " + e.toString());
               }
               }
              catch (Exception e) {
                  Log.e(TAG, "Error in http connection " + e.toString());
            }
            return data;
    }


    public String mobilenumber(String no) {
        String result = "";
        HttpResponse response = null;

        JSONObject receivedJsonObj = null;
        String data = "";
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            HttpPost httppost = null;
            if(server_ip!=null){
                httppost = new HttpPost("http://" + server_ip + ":" + port + "/TrafficPoliceNew/api/shift/verify_Offendermobileno");
            }else{
                httppost = new HttpPost("http://" + "120.138.10.251" + ":" + "8084" + "/api/shift/verify_Offendermobileno");
            }

            HttpParams httpParameters = new BasicHttpParams();
            HttpClient httpClient = new DefaultHttpClient(httpParameters);
            httppost.setHeader("Content-type", "application/json");
            httppost.setEntity(new StringEntity(no.toString(), "UTF-8"));
            try {
                // response = httpClient.execute(httppost);
                response = httpClient.execute(httppost);
                // receivedJsonObj = processHttpResponse(response);
                result = EntityUtils.toString(response.getEntity());
                // data = receivedJsonObj.getString("result");

                httpClient.getConnectionManager().shutdown();
            } catch (Exception e) {
                Log.e(TAG, "Error in http data " + e.toString());
            }
        } catch (Exception e) {
            Log.e(TAG, "Error in http connection " + e.toString());
        }
        return result;
    }


    public String sendOtp(String mobile_no)
    {
        long result = 0;
       // ip=server_ip;
        HttpResponse response = null;
        JSONObject receivedJsonObj=null;
        String data="";
        try
        {
//   804207
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            HttpPost httppost=null;
            if(server_ip!=null)
            {
                httppost = new HttpPost("http://"+server_ip+":"+"8080"+"/TrafficPoliceNew/api/shift/verifyOffenderOTP");
            }
            else {
                httppost = new HttpPost("http://" + "120.138.10.251" + ":" + "8084" + "/api/shift/verifyOffenderOTP");
            }
//            httppost = new HttpPost("http://122.176.75.92:8079/AppointmentWebService/api/appointment/login");
            HttpParams httpParameters = new BasicHttpParams();
            HttpClient httpClient = new DefaultHttpClient(httpParameters);
            httppost.setHeader("Content-type", "application/json");
            httppost.setEntity(new StringEntity(mobile_no.toString(), "UTF-8"));
            try
            {
                response = httpClient.execute(httppost);
                receivedJsonObj = processHttpResponse(response);
//                int s=receivedJsonObj.length();
                data=receivedJsonObj.toString();
            }
            catch (Exception e)
            {
                Log.e(TAG, "Error in http execute " + e);
            }
            httpClient.getConnectionManager().shutdown();
        } catch (Exception e)
        {
            Log.e(TAG, "Error in http connection " + e.toString());
        }
        return data;

    }
    public String re_SendOTP(String mobile_no)
    {
        long result = 0;
        //ip=server_ip;
        String pdfname="";
        HttpResponse response = null;
        JSONObject receivedJsonObj=null;
        String data="";
        try
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            HttpPost httppost=null;
            if(port.equalsIgnoreCase("8085"))
            {
                httppost = new HttpPost("http://"+server_ip+":" + "8085"+  "/api/shift/reSendOTP");
            }
            else {
                httppost = new HttpPost("http://" + "120.138.10.251" + ":" + "8080" + "/TrafficPoliceNew/api/shift/reSendOTP");
            }
//            httppost = new HttpPost("http://122.176.75.92:8079/AppointmentWebService/api/appointment/login");
            HttpParams httpParameters = new BasicHttpParams();

            HttpClient httpClient = new DefaultHttpClient(httpParameters);
            httppost.setHeader("Content-type", "application/json");
            httppost.setEntity(new StringEntity(mobile_no.toString(), "UTF-8"));
            try
            {
                response = httpClient.execute(httppost);
//                receivedJsonObj = processHttpResponse(response);
                data=EntityUtils.toString(response.getEntity());
            }
            catch (Exception e)
            {
                Log.e(TAG, "Error in http execute " + e);
            }
            httpClient.getConnectionManager().shutdown();
        } catch (Exception e)
        {
            Log.e(TAG, "Error in http connection " + e.toString());
        }
        return data;
    }

    public static JSONObject processHttpResponse(HttpResponse response)
            throws UnsupportedEncodingException, IllegalStateException, IOException, JSONException {
        JSONObject top = null;
        StringBuilder builder = new StringBuilder();
        String dec = "";
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
            for (String line = null; (line = reader.readLine()) != null; ) {
                builder.append(line).append("\n");
            }
            String decoded = new String(builder.toString().getBytes(), "UTF-8");
            Log.d(TAG, "decoded http response: " + decoded);
            JSONTokener tokener = new JSONTokener(Uri.decode(builder.toString()));
            top = new JSONObject(tokener);
        } catch (JSONException t) {
            Log.w(TAG, "<processHttpResponse> caught: " + t + ", handling as string...");
        } catch (IOException e) {
            Log.e(TAG, "caught processHttpResponse IOException : " + e, e);
        } catch (Throwable t) {
            Log.e(TAG, "caught processHttpResponse Throwable : " + t, t);
        }
        return top;
    }
}
