package com.ashok.quizapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.ashok.quizapp.databinding.ActivityFinalBinding;

public class Final extends AppCompatActivity {

  private Button btn_cong;
    private Button btn_fin;
    private TextView tv_cong;
    private TextView tv_point;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_final);
      btn_cong=findViewById(R.id.restart);
      btn_fin=findViewById(R.id.Finish);
      tv_cong=findViewById(R.id.cong);
      tv_point=findViewById(R.id.point);
      Intent intent = getIntent();
      int point = intent.getIntExtra("point",0);
      int total = intent.getIntExtra("total",0);
      String userName = intent.getStringExtra("name");
tv_cong.setText("Congratulation "+userName);
      tv_point.setText("Your Score:\n"+point+"/"+total);

      btn_cong.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          Intent intent = new Intent(Final.this, MainActivity.class);
          intent.putExtra("name",userName);
          startActivity(intent);

        }
      });
      btn_fin.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finishAffinity();
        }
      });
    }


}