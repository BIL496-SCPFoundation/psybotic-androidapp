package com.scpfoundation.psybotic.app.ui.psychologychat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.scpfoundation.psybotic.app.R;

import org.w3c.dom.Text;

public class PsychologyInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_psychology_info);
        String title = getIntent().getExtras().get("psychology_name") + " Information";
        TextView textView = findViewById(R.id.empt_title);
        textView.setText(title);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.profile_back_button:
                finish();
                break;
            case R.id.chat_container:
                Intent intent = new Intent(this.getApplicationContext(), PsychologyActivity.class);
                intent.putExtra("psychology_name", ( getIntent().getExtras().
                        get("psychology_name")) +" Chat");
                startActivity(intent);
                break;
            default:
                System.err.println("Button is not defined");
        }
    }

}