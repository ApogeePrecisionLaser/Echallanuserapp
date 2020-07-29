package com.echallanuser.paytmgateway;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.echallanuser.R;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


/**
* Created by Somya.
 */

public class checksum extends AppCompatActivity implements PaytmPaymentTransactionCallback {
 LinearLayout linearLayout;
    String custid="", orderId="", mid="",amount;
    TextView amt,status,bnkname,date,paymentmode,gatwayname,response;
    ProgressBar simpleProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.checksum);
       amt=findViewById(R.id.txnamount);
       status=findViewById(R.id.status);
       bnkname=findViewById(R.id.bankname);
       date=findViewById(R.id.txndate);
       paymentmode=findViewById(R.id.paymentmode);
       gatwayname=findViewById(R.id.gatewayname);
       response=findViewById(R.id.rspmsg);
       linearLayout=findViewById(R.id.mainlayout);
        simpleProgressBar=(ProgressBar) findViewById(R.id.simpleProgressBar3); // initiate the progress bar
        simpleProgressBar.setMax(100);

        //initOrderId();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

     Intent i=getIntent();
     orderId=i.getStringExtra("echallanno");
    amount =i.getStringExtra("amount");
       // orderId = "ORDER_" + System.currentTimeMillis();
        custid = "CUST123";
        simpleProgressBar.setVisibility(View.VISIBLE);
        //mid = "eNnjXe00637647587210"; /// your marchant id
        mid="BtgxwM61526972362584";
        sendUserDetailTOServerdd dl = new sendUserDetailTOServerdd();
        dl.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        // vollye , retrofit, asynch

    }

    public class sendUserDetailTOServerdd extends AsyncTask<ArrayList<String>, Void, String> {

        private ProgressDialog dialog = new ProgressDialog(checksum.this);

        //private String orderId , mid, custid, amt;
        String url ="http://120.138.10.146:8080/TrafficPoliceNew/api/shift/checksum";
        String varifyurl = "https://pguat.paytm.com/paytmchecksum/paytmCallback.jsp";
    //String varifyurl = "https://securegw-stage.paytm.in/theia/paytmCallback?ORDER_ID="+orderId+"";
                           // "https://securegw-stage.paytm.in/theia/paytmCallback?ORDER_ID"+orderId;
        String CHECKSUMHASH ="";

                @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Please wait");
            this.dialog.show();
        }

        protected String doInBackground(ArrayList<String>... alldata) {
            JSONParser jsonParser = new JSONParser(checksum.this);



            String param=
                     mid +"&"+ orderId +"&"+custid+"&"+ "WAP"+"&"+amount+"&"+"WEBSTAGING"+
                                        "&"+ varifyurl+"&"+"Retail"+"&"+"8840590504"+"&"+"shubham.srivastava243@gmail.com";
                    /*"MID="+mid+
                    "&ORDER_ID="+orderId+
                    "&CUST_ID="+custid+
                            "&CHANNEL_ID=WAP&TXN_AMOUNT=1&WEBSITE=WEBSTAGING"+"&CALLBACK_URL="+varifyurl+"&INDUSTRY_TYPE_ID=Retail"+
                    "&MOBILE_NO="+"8840590504"+
                    "&EMAIL="+"shubham.srivastava243@gmail.com";*/

            JSONObject jsonObject = jsonParser.makeHttpRequest(url,"POST",param);
            // yaha per checksum ke saht order id or status receive hoga..
          Log.e("CheckSum result >>",jsonObject.toString());
            if(jsonObject != null){
                Log.e("CheckSum result >>",jsonObject.toString());
                try {

                    CHECKSUMHASH=jsonObject.has("CHECKSUMHASH")?jsonObject.getString("CHECKSUMHASH"):"";
                    //CHECKSUMHASH=jsonObject;
                    Log.e("CheckSum result >>",CHECKSUMHASH);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return CHECKSUMHASH;
        }

        @Override
        protected void onPostExecute(String result) {
            Log.e(" setup acc ","  signup result  " + result);
            if (dialog.isShowing()) {
                dialog.dismiss();
            }

            PaytmPGService Service = PaytmPGService.getStagingService("");
           // when app is ready to publish use production service
            // PaytmPGService  Service = PaytmPGService.getProductionService();

            // now call paytm service here
            //below parameter map is required to construct PaytmOrder object, Merchant should replace below map values with his own values
            HashMap<String, String> paramMap = new HashMap<String, String>();
            //these are mandatory parameters
            paramMap.put("MID", mid); //MID provided by paytm
            paramMap.put("ORDER_ID", orderId);
            paramMap.put("CUST_ID", custid);
            paramMap.put("INDUSTRY_TYPE_ID", "Retail");
            paramMap.put("CHANNEL_ID", "WAP");
            paramMap.put("TXN_AMOUNT", amount);
            paramMap.put("WEBSITE", "WEBSTAGING");
            paramMap.put( "EMAIL" , "shubham.srivastava243@gmail.com");   // no need
            paramMap.put( "MOBILE_NO" , "8840590504");  // no need
            paramMap.put("CALLBACK_URL" ,varifyurl);
            paramMap.put("CHECKSUMHASH" ,CHECKSUMHASH);
            //paramMap.put("PAYMENT_TYPE_ID" ,"CC");    // no need


            PaytmOrder Order = new PaytmOrder(paramMap);
            Log.e("checksum ", "param "+ paramMap.toString());
            Service.initialize(Order,null);
            // start payment service call here

            Service.startPaymentTransaction(checksum.this, true, true,
                    checksum.this  );


        }

    }


    @Override
    public void onTransactionResponse(Bundle bundle) {
        Log.e("checksum ", " respon true " + bundle.toString());
        linearLayout.setVisibility(View.VISIBLE);
        String amount=bundle.getString("TXNAMOUNT");
        String status1=bundle.getString("STATUS");
        String response1=bundle.getString("RESPMSG");
        String gatewayname1=bundle.getString("GATEWAYNAME");
        String paymentmode1=bundle.getString("PAYMENTMODE");
        String date1=bundle.getString("TXNDATE");
        String bnkname1=bundle.getString("BANKNAME");
        amt.setText(amount);
        bnkname.setText(bnkname1);
        response.setText(response1);
        status.setText(status1);
        paymentmode.setText(paymentmode1);
        date.setText(date1);
        gatwayname.setText(gatewayname1);
        Toast.makeText(checksum.this,"Transcation successfull",Toast.LENGTH_LONG).show();
    }

    @Override
    public void networkNotAvailable() {

    }

    @Override
    public void clientAuthenticationFailed(String s) {

    }

    @Override
    public void someUIErrorOccurred(String s) {

        Log.e("checksum ", " ui fail respon  "+ s );
        Toast.makeText(checksum.this," ui fail respon ",Toast.LENGTH_LONG).show();

    }

    @Override
    public void onErrorLoadingWebPage(int i, String s, String s1) {
        Log.e("checksum ", " error loading pagerespon true "+ s + "  s1 " + s1);
        Toast.makeText(checksum.this,"error loading pagerespon true",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressedCancelTransaction() {
        Log.e("checksum ", " cancel call back respon  " );
        Toast.makeText(checksum.this,"Transcation cancel call back response",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onTransactionCancel(String s, Bundle bundle) {
        Log.e("checksum ", "  transaction cancel " );
        Toast.makeText(checksum.this,"Transcation cancel",Toast.LENGTH_LONG).show();
    }


}
