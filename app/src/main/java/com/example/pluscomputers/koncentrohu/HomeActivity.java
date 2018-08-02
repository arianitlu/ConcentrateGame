package com.example.pluscomputers.koncentrohu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {

    private Button btnSinglePlayer,btnMultiPlayer,btnSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnSinglePlayer = findViewById(R.id.btnSinglePlayer);
        btnMultiPlayer = findViewById(R.id.btnMultiPlayer);
        btnSettings = findViewById(R.id.btnSettings);

        btnSinglePlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(HomeActivity.this,MainActivity.class);
                intent.putExtra("singlePlayer",true);
                startActivity(intent);
            }
        });

        btnMultiPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(HomeActivity.this,MainActivity.class);
                intent.putExtra("multiPlayer",true);
                startActivity(intent);
            }
        });

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(HomeActivity.this,SettingsActivity.class);
                intent.putExtra("singlePlayer",true);
                startActivity(intent);
            }
        });
    }
}
