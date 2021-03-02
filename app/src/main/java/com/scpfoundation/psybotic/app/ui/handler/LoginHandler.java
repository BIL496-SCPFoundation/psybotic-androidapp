package com.scpfoundation.psybotic.app.ui.handler;

import android.app.ProgressDialog;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.scpfoundation.psybotic.app.ui.login.LoginActivity;

import org.json.JSONObject;

public class LoginHandler implements Response.Listener<JSONObject>, Response.ErrorListener {

    private AppCompatActivity activity;

    public LoginHandler(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onResponse(JSONObject response) {
        ProgressDialog dialog = ProgressDialog.show(activity, "",
                "Loading. Please wait...", true);

    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }
}
