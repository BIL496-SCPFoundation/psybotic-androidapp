package com.scpfoundation.psybotic.app.ui.profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.gson.Gson;
import com.scpfoundation.psybotic.app.R;
import com.scpfoundation.psybotic.app.ui.data.User;
import com.scpfoundation.psybotic.app.ui.util.DownloadImageTask;

import org.json.JSONObject;

public class ProfileActivity
        extends AppCompatActivity
        implements View.OnClickListener, Response.ErrorListener, Response.Listener<JSONObject> {

    private User curUser;
    private GoogleSignInAccount account;
    private final String HOST = "https://limitless-lake-96203.herokuapp.com/";
    RequestQueue requestQueue;
    private ProgressDialog dialog;
    private ImageView profileImage;
    private ProgressBar imageLoading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        // initializing
        account = (GoogleSignInAccount) getIntent().getExtras().get("account");
        requestQueue = Volley.newRequestQueue(this.getApplicationContext());
        imageLoading = findViewById(R.id.image_loading);
        profileImage = findViewById(R.id.profile_image);
        new DownloadImageTask(profileImage, imageLoading)
                .execute(account.getPhotoUrl().toString());

        //find the user data
        String url = HOST + "/users/findById?id=" + account.getId();
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url,
                null, this, this);
        requestQueue.add(req);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.profile_back_button:
                finish();
                break;
            default:
                System.err.println("Button is not defined");
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(JSONObject response) {
        Gson gson = new Gson();
        User u = gson.fromJson(response.toString(), User.class);

        TextView firstName = findViewById(R.id.first_name_text);
        firstName.setText(u.getFirstName());

        TextView lastName = findViewById(R.id.last_name_text);
        lastName.setText(u.getLastName());

        TextView email = findViewById(R.id.email_text);
        email.setText(u.getEmail());

        TextView city = findViewById(R.id.city_text);
        city.setText(u.getCity());

    }
}