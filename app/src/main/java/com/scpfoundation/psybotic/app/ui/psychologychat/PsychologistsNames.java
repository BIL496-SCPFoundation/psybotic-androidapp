package com.scpfoundation.psybotic.app.ui.psychologychat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.scpfoundation.psybotic.app.R;



public class PsychologistsNames extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_psychologists_names);
    }


    public void onClick(View v) {
        Intent intent = new Intent(this.getApplicationContext(), PsychologyActivity.class);
        TextView textView = (TextView) v;
        intent.putExtra("psychology_name", textView.getText().toString());
        startActivity(intent);
    }
}