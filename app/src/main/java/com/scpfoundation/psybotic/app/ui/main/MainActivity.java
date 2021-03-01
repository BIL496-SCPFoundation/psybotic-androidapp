package com.scpfoundation.psybotic.app.ui.main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.scpfoundation.psybotic.app.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private GoogleSignInAccount account;
    private TextView greetingsTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        greetingsTextView= findViewById(R.id.greetings_text);
        account = (GoogleSignInAccount) getIntent().getExtras().get("account");
        String greetingText = (account != null) ?  "Welcome " + account.getDisplayName() : "Welcome dear guest";
        if (greetingsTextView != null) {
            greetingsTextView.setText(greetingText);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.profile_view:
                // go to profile page
                break;
            case R.id.ai_chat_view:
                // go to ai chat page
                break;
            case R.id.psychologist_chat_view:
                // go to psychologist chat page
                break;
            case R.id.notifications_view:
                // go to notifications view
                break;
        }
    }
}