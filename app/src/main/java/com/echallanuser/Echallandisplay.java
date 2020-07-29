package com.echallanuser;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.echallanuser.model.Webservice;
import com.echallanuser.paytmgateway.checksum;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Echallandisplay extends AppCompatActivity {
 TextView t1,t2,t3,t4,t5,t6,t7,t8,t9;
 Button btnpay;
 String keypersonid;
    TableLayout tableLayout;

    String challanno,amount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_echallandisplay);
        tableLayout =  findViewById(R.id.maintable);

//        t1=findViewById(R.id.txt1);
//        t3=findViewById(R.id.txt3);
//        t4=findViewById(R.id.txt4);
//        t5=findViewById(R.id.txt5);
//        t6=findViewById(R.id.txt6);
//        t7=findViewById(R.id.txt7);
//        t8=findViewById(R.id.txt8);
//        t9=findViewById(R.id.txt9);
        btnpay=findViewById(R.id.btnpaay);
        Intent i=getIntent();
        keypersonid=i.getStringExtra("code");

        btnpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Echallandisplay.this, checksum.class);
                intent.putExtra("echallanno",challanno);
                intent.putExtra("amount",amount);

                startActivity(intent);

            }
        });
        caldata caldata=new caldata();
        caldata.execute();
    }
    private class caldata extends AsyncTask<String,String,String> {
        ProgressDialog dialog;
    String result="";

        @Override
        protected String doInBackground(String... params) {

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = null;
            try {
                final Webservice genericModel = new Webservice(Echallandisplay.this);
                result = genericModel.getdata(keypersonid);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                if (!result.isEmpty()) {

                    JSONObject jsonObject = new JSONObject(result);

                    JSONArray jsonArray2 = jsonObject.getJSONArray("OffenceData");

                    for (int i = 0; i < jsonArray2.length(); i++) {
       //                 {"OffenceData":[{"Challan_no":"ORDER_138964","key_person_name":"श्री आर.एन त्रिपाठी","vehicle_no":"MP08R8868","offender_name":"","location":"ikuh dh Vadh frjkgk","offencedate":"24-07-2020 17:54:36","city_name":"","offender_age":"","vehicle_type":"LMV","act":"81\/177","offence_type":"मालयानो में पशुओ को  ले  जाना ","amount":"500","act_origin":"Madhya pradesh Moter vehicle Rules 1994","offence_code":"156"}]}
                       JSONObject jsonObject1 = jsonArray2.getJSONObject(i);

                        amount=jsonObject1.getString("amount");
                        challanno=jsonObject1.getString("Challan_no");
                        try {
                            dialog.dismiss();
                            showTable(jsonObject);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
            @Override
        protected void onPreExecute()
        {
            dialog =  ProgressDialog.show(Echallandisplay.this, "", "Proccessing....Please wait");
            dialog.show();
            // Things to be done before execution of long running operation. For
            // example showin ProgessDialog
        }


    }
    public void showTable(JSONObject jsonObject) {

        ScrollView scrollview =  findViewById(R.id.scroll_table);
        HorizontalScrollView horizontalScrollView = findViewById(R.id.horizon_table);
        JSONArray jsonArray = null;
        tableLayout.removeAllViews();
        try {
            jsonArray = jsonObject.getJSONArray("OffenceData");

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            int i = 0;
            if (i == 0) {
                TableRow row = new TableRow(this);
                row.removeAllViews();
                createHeaderColumnView(row, new TextView(this), "S.No");
                createHeaderColumnView(row, new TextView(this), "Challan_no");
                createHeaderColumnView(row, new TextView(this), "offender_name");
                createHeaderColumnView(row, new TextView(this), "vehicle_no");
                createHeaderColumnView(row, new TextView(this), "location");
                createHeaderColumnView(row, new TextView(this), "offence_code");
                createHeaderColumnView(row, new TextView(this), "offence_type");
                createHeaderColumnView(row, new TextView(this), "act");
                createHeaderColumnView(row, new TextView(this), "act_origin");
                createHeaderColumnView(row, new TextView(this), "Officer_name");
                createHeaderColumnView(row, new TextView(this), "offencedate");
                createHeaderColumnView(row, new TextView(this), "amount");
                tableLayout.addView(row);
            }

            for (int ii = 0; ii < jsonArray.length(); ii++) {
                try {
                    ++i;
                    JSONObject jsonObject1 = jsonArray.getJSONObject(ii);
                    TableRow row = new TableRow(this);
                    TextView sno = new TextView(this);
                    createColumnView(row, sno, i + "", false);
                    TextView Challan_no = new TextView(this);

                    createColumnView(row, Challan_no, jsonObject1.getString("Challan_no").toString(), false);
                    TextView offender_name = new TextView(this);
                    createColumnView(row, offender_name, jsonObject1.getString("offender_name").toString(), false);
                    TextView vehicle_no = new TextView(this);
                    createColumnView(row, vehicle_no, jsonObject1.getString("vehicle_no").toString(), false);
                    TextView location = new TextView(this);
                    createColumnView(row, location, jsonObject1.getString("location").toString(), false);
                    final TextView offence_code = new TextView(this);
                    createColumnView(row, offence_code, jsonObject1.getString("offence_code").toString(), false);
                    final TextView offence_type=new TextView(this);
                    createColumnView(row, offence_type,jsonObject1.getString("offence_type").toString(), false);
                    final TextView act=new TextView(this);
                    createColumnView(row, act,jsonObject1.getString("act").toString(), false);
                    final TextView act_origin=new TextView(this);
                    createColumnView(row, act_origin,jsonObject1.getString("act_origin").toString(), false);
                    final TextView key_person_name=new TextView(this);
                    createColumnView(row, key_person_name,jsonObject1.getString("key_person_name").toString(), false);
                    final TextView offencedate=new TextView(this);
                    createColumnView(row, offencedate,jsonObject1.getString("offencedate").toString(), false);
                    final TextView amount=new TextView(this);
                    createColumnView(row, amount,jsonObject1.getString("amount").toString(), false);
                    tableLayout.addView(row);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //TableRow row=new TableRow(this);


            }

            horizontalScrollView.addView(tableLayout);
            scrollview.addView(horizontalScrollView);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void createHeaderColumnView(TableRow tr, TextView t, String viewdata) {
        t.setText(viewdata == null ? "" : viewdata.trim());
        t.setTextSize(20);
        t.setBackgroundColor(Color.GRAY);
        t.setTextColor(Color.WHITE);
        t.setMaxLines(5);
        t.setPadding(12, 0, 0, 0);
        tr.setPadding(0, 1, 0, 1);
        tr.addView(t);
    }

    public void createColumnView(TableRow tr, TextView t, String viewdata, boolean hyperLink) {
        t.setText(viewdata == null ? "" : viewdata.trim());
        t.setTextSize(20);
        t.setBackgroundColor(Color.LTGRAY);
        t.setTextColor(hyperLink ? Color.BLUE : Color.BLACK);
        t.setMaxLines(5);
        t.setPadding(12, 0, 0, 0);
        tr.setPadding(0, 5, 0, 1);
        tr.addView(t);
    }

    public void createColumnView2(TableRow tr, TextView t, String viewdata, boolean hyperLink) {
        t.setText("Min-Maxlevel");
        t.setTextSize(20);
        t.setBackgroundColor(Color.LTGRAY);
        t.setMaxLines(5);
        t.setTextColor(hyperLink ? Color.BLUE : Color.BLACK);
        t.setPadding(12, 0, 0, 0);
        tr.setPadding(0, 5, 0, 1);
        tr.addView(t);
    }
    public void createColumnView3(TableRow tr, TextView t, String viewdata, boolean hyperLink) {
        t.setText("FullView");
        t.setTextSize(20);
        t.setBackgroundColor(Color.LTGRAY);
        t.setMaxLines(5);
        t.setTextColor(hyperLink ? Color.BLUE : Color.BLACK);
        t.setPadding(12, 0, 0, 0);
        tr.setPadding(0, 5, 0, 1);
        tr.addView(t);
    }
    public void createColumnView4(TableRow tr, TextView t, String viewdata, boolean hyperLink) {
        t.setText("ChartView");
        t.setTextSize(20);
        t.setBackgroundColor(Color.LTGRAY);
        t.setMaxLines(5);
        t.setTextColor(hyperLink ? Color.BLUE : Color.BLACK);
        t.setPadding(12, 0, 0, 0);
        tr.setPadding(0, 5, 0, 1);
        tr.addView(t);
    }
}