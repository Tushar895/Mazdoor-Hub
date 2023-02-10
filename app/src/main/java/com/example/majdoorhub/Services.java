package com.example.majdoorhub;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class Services extends AppCompatActivity {
    Button appliance;
    Button cleaning;
    Button electrician;
    Button painter;
    Button maid;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);
        appliance = findViewById(R.id.appliance);
        cleaning = findViewById(R.id.cleaning);
        electrician = findViewById(R.id.electrician);
        painter = findViewById(R.id.painting);
        maid = findViewById(R.id.maid);


        appliance.setOnClickListener(e->

        {
            Intent i = new Intent(this,Appliances_Repair.class);
            startActivity(i);
        });

        cleaning.setOnClickListener(e->
        {
            Intent j = new Intent(this,Cleaning_and_Pestcontrol.class);
                    startActivity(j);
        });

        electrician.setOnClickListener(e->

        {
            Intent i = new Intent(this,Electrician.class);
            startActivity(i);
        });

        painter.setOnClickListener(e->

        {
            Intent i = new Intent(this,Painter.class);
            startActivity(i);
        });


        maid.setOnClickListener(e->

        {
            Intent i = new Intent(this,Maid.class);
            startActivity(i);
        });




    }
}