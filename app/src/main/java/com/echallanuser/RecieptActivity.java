package com.echallanuser;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class RecieptActivity extends AppCompatActivity {
TextView name,amount,reciept;
Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reciept);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        name=(TextView)findViewById(R.id.name);
        amount=(TextView)findViewById(R.id.amount);
        back=(Button) findViewById(R.id.back);
        reciept=(TextView)findViewById(R.id.reciept);
        Intent intent=getIntent();
        try
        {
           String recieptval=intent.getStringExtra("reciept");
            String ammountst=intent.getStringExtra("amount");
            String dest=intent.getStringExtra("destination");
          //  name.setText("Thanks For Using Auto  Railwat Plateform no:1 to "+dest);
            name.setText("");
            amount.setText("Transaction amount:"+ammountst);
            reciept.setText("Reciept : "+recieptval);
        }catch (Exception e)
        {

        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(RecieptActivity.this, MainActivity.class);
//                MainActivity.isFirst=false;
                finish();
                startActivity(intent1);
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent intent1 = new Intent(RecieptActivity.this, MainActivity.class);
//        MainActivity.isFirst=false;
        finish();
        startActivity(intent1);
    }


}
