package com.example.brom.listviewjsonapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MountainDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mountaindetails);

        TextView test = (TextView) findViewById(R.id.test);
        Intent intent = getIntent();
        int height = intent.getIntExtra("HEIGHT", 0);
        test.setText("The height is: " + height);

        TextView test2 = (TextView) findViewById(R.id.test2);
        String location = intent.getStringExtra("LOCATION");
        test2.setText("The location is: " + location);

        TextView test3 = (TextView) findViewById(R.id.test3);
        String name = intent.getStringExtra("NAME");
        test3.setText("The name is: " + name);
    }
}
