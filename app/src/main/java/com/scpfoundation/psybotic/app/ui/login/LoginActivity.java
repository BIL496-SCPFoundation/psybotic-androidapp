package com.scpfoundation.psybotic.app.ui.login;

import android.app.Activity;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.scpfoundation.psybotic.app.ui.data.User;
import com.scpfoundation.psybotic.app.ui.handler.LoginHandler;
import com.scpfoundation.psybotic.app.ui.main.MainActivity;
import com.scpfoundation.psybotic.app.R;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener,
        Response.Listener<JSONObject>, Response.ErrorListener{

    private static final int RC_SIGN_IN = 100;
    private GoogleSignInClient mGoogleSignInClient;
    RequestQueue requestQueue;
    private final String HOST = "http://192.168.0.12:8080";
    private ProgressDialog dialog;
    private GoogleSignInAccount account;

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onStart() {
        super.onStart();
        /*
         * Check if user already signed in via a google account, if yes go to MainActivity
         */
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            updateUI(account);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            final User user = new User();
            user.setEmail(account.getEmail());
            user.setFirstName(account.getGivenName());
            user.setLastName(account.getFamilyName());
            user.setGoogleId(account.getId());
            JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, HOST + "/users/login",
                    null, this, this) {
                @Override
                public byte[] getBody() {
                    Gson gson = new Gson();
                    String body = gson.toJson(user);
                    return body.getBytes();
                }
            };
            dialog = ProgressDialog.show(this, "",
                    "Loading. Please wait...", true);
            requestQueue.add(req);
            requestQueue.start();
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
//            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            e.printStackTrace();
        }
    }

    private void updateUI(GoogleSignInAccount account) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("account", account);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        System.out.println("Oluştu");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        requestQueue = Volley.newRequestQueue(this.getApplicationContext());
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        findViewById(R.id.google_login_button).setOnClickListener(this);
        findViewById(R.id.guest_login_button).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.google_login_button:
                signIn();
                break;
            case R.id.guest_login_button:
                updateUI(null);
                break;
            default:
                Toast.makeText(this, "Button not defined in onClickListener", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        dialog.cancel();
    }

    @Override
    public void onResponse(JSONObject response) {
        dialog.cancel();
        updateUI(account);
    }
}