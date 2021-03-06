package com.scpfoundation.psybotic.app.ui.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.gson.Gson;
import com.scpfoundation.psybotic.app.R;
import com.scpfoundation.psybotic.app.data.User;
import com.scpfoundation.psybotic.app.ui.login.LoginActivity;
import com.scpfoundation.psybotic.app.ui.notifications.NotificationActivity;
import com.scpfoundation.psybotic.app.ui.profile.ProfileActivity;
import com.scpfoundation.psybotic.app.ui.chatbot.ChatActivity;
import com.scpfoundation.psybotic.app.ui.realUserChat.DialogList;
import com.scpfoundation.psybotic.app.util.RequestManager;
import com.scpfoundation.psybotic.app.util.Utility;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, OnCompleteListener<Void> {

    private GoogleSignInAccount account;
    private TextView greetingsTextView;
    private GoogleSignInClient mGoogleSignInClient;
    private User user;
    private RequestManager requestManager;
    private String HOST = "https://limitless-lake-96203.herokuapp.com/";
    private final Context CTX = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseApp.initializeApp(this.getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestManager = RequestManager.getInstance(this);
        greetingsTextView = findViewById(R.id.greetings_text);
        account = (GoogleSignInAccount) getIntent().getExtras().get("account");
        String greetingText = (account != null) ? "Welcome " + account.getDisplayName() : "Welcome dear guest";
        if (greetingsTextView != null) {
            greetingsTextView.setText(greetingText);
        }
        Map<String, String> params = new HashMap<>();
        if (account != null) {
            params.put("id", account.getId());
        }
        requestManager.getRequest(HOST + "/users/findById", params, response -> {
            this.user = new Gson().fromJson(response.toString(), User.class);
        }, error -> {
            Utility.showToast(CTX, "Request error");
        });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.profile_view:
                if (account != null) {
                    Intent intent = new Intent(this.getApplicationContext(), ProfileActivity.class);
                    intent.putExtra("account", account);
                    startActivity(intent);
                }
                break;
            case R.id.ai_chat_view:
                if (account != null) {
                    Intent intentForChat = new Intent(this.getApplicationContext(), ChatActivity.class);
                    startActivity(intentForChat);
                }
                // go to ai chat page
                break;
            case R.id.psychologist_chat_view:
                // go to psychologist chat page
                if (account != null) {
                    Intent intentForPsychology = new Intent(this.getApplicationContext(), DialogList.class);
                    intentForPsychology.putExtra("isPsychologist", user.isPsychologist());
                    startActivity(intentForPsychology);
                }
                break;
            case R.id.notifications_view:
                if (account != null) {
                    Intent intent = new Intent(this.getApplicationContext(), NotificationActivity.class);
                    intent.putExtra("account", account);
                    startActivity(intent);
                }
                break;
            case R.id.logout_icon:
                signOut();
                break;
            default:
                System.err.println("Button not defined");
        }
    }

    private void signOut() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, this);
    }

    @Override
    public void onComplete(@NonNull Task<Void> task) {
        Intent intent = new Intent(this.getApplicationContext(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}