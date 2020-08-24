package com.echallanuser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.echallanuser.utility.CustomGridView;
import com.echallanuser.utility.Item;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    GridView gridView;
    ArrayList<Item> gridArray = new ArrayList<Item>();
    CustomGridView customGridView;
//    SessionManager sessionManager=new SessionManager(this);

    // String trafficpoliceid;
  @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);
      Intent i = getIntent();
      // trafficpoliceid=i.getStringExtra("trafficpoliceid");
      Bitmap echallan = BitmapFactory.decodeResource(this.getResources(), R.drawable.echaalan);
      Bitmap help = BitmapFactory.decodeResource(this.getResources(), R.drawable.helpicon);
      gridArray.add(new Item(echallan, "Echallan"));
      gridArray.add(new Item(help, "Help"));
      gridView = (GridView) findViewById(R.id.gridView1);
      customGridView = new CustomGridView(this, R.layout.grid_single, gridArray);
      gridView.setAdapter(customGridView);

      gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              if (position == 0) {
                  Intent intent = new Intent(MainActivity.this, Echallandisplay.class);
                  // intent.putExtra("trafficpoliceid",trafficpoliceid);
                  startActivity(intent);
              }
          }
      });
  }
      @Override
      public boolean onOptionsItemSelected(MenuItem item) {

          // Handle item selection
          switch (item.getItemId()) {
              case R.id.iddtl:
               //   sessionManager.logoutUser();
                  return true;

              default:
                  return super.onOptionsItemSelected(item);
          }
      }


}