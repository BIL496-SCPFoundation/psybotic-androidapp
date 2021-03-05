package com.scpfoundation.psybotic.app.ui.profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scpfoundation.psybotic.app.R;
import com.scpfoundation.psybotic.app.data.FamilyMember;
import com.scpfoundation.psybotic.app.data.User;
import com.scpfoundation.psybotic.app.listener.FamilyMemberSubmitListener;
import com.scpfoundation.psybotic.app.request.FamilyMemberSubmitRequest;
import com.scpfoundation.psybotic.app.ui.util.DownloadImageTask;
import com.wrapp.floatlabelededittext.FloatLabeledEditText;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ProfileActivity
        extends AppCompatActivity
        implements  View.OnClickListener, Response.ErrorListener, Response.Listener<JSONObject> {

    private User curUser;
    private User editedUser;
    private GoogleSignInAccount account;
    private final String HOST = "https://limitless-lake-96203.herokuapp.com";
    RequestQueue requestQueue;
    private ProgressDialog dialog;
    private ImageView profileImage;
    private ProgressBar imageLoading;
    private boolean editMode = false;
    RecyclerView familyMemberList;
    FamilyMemberAdapter familyMemberAdapter;
    ProgressBar loadingFamilyMembers;
    private FamilyMember newFamilyMember = new FamilyMember();
    FamilyMemberSubmitRequest submitRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        // initializing
        familyMemberList = findViewById(R.id.family_members_list);
        loadingFamilyMembers = findViewById(R.id.loading_family_members);
        account = (GoogleSignInAccount) getIntent().getExtras().get("account");
        requestQueue = Volley.newRequestQueue(this.getApplicationContext());
        imageLoading = findViewById(R.id.image_loading);
        profileImage = findViewById(R.id.profile_image);
        //new DownloadImageTask(profileImage, imageLoading)
                //.execute(account.getPhotoUrl().toString());

        //find the user data
        String url = HOST + "/users/findById?id=" + account.getId();
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url,
                null, this, this);
        requestQueue.add(req);
        getFamilyMembers();

        newFamilyMember.setSuperId(account.getId());
        FamilyMemberSubmitListener listener = new FamilyMemberSubmitListener(newFamilyMember);

        EditText fnCreate = findViewById(R.id.fm_fn_create_text);
        fnCreate.addTextChangedListener(listener.new FirstNameListener());

        EditText lnCreate = findViewById(R.id.fm_ln_create_text);
        lnCreate.addTextChangedListener(listener.new LastNameListener());

        EditText emailCreate = findViewById(R.id.fm_email_create_text);
        emailCreate.addTextChangedListener(listener.new EmailListener());

        EditText phoneCreate = findViewById(R.id.fm_phone_create_text);
        phoneCreate.addTextChangedListener(listener.new PhoneListener());

        final ConstraintLayout constraintLayout = findViewById(R.id.fm_create_dialog);

        submitRequest =
                new FamilyMemberSubmitRequest(newFamilyMember,
                        this,
                        constraintLayout,
                        familyMemberAdapter);

        ImageButton submitButton = findViewById(R.id.fm_submit);
        submitButton.setOnClickListener(submitRequest);

        ImageButton cancelSubmitButton = findViewById(R.id.fm_cancel_submit);
        cancelSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                constraintLayout.setVisibility(View.GONE);
            }
        });

    }

    public void showToast(String msg) {
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.profile_back_button:
                finish();
                break;
            case R.id.edit_image_card:
                toggleEdit(!editMode);
                break;
            case R.id.apply_edit_card:
                sendUpdateUserRequest();
                break;
            case R.id.add_fm_button:
                ConstraintLayout cl = findViewById(R.id.fm_create_dialog);
                cl.setVisibility(View.VISIBLE);
                break;
            default:
                System.err.println("Button is not defined");
        }
    }



    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e("Request Error", error.getMessage(), error);
        loadingFamilyMembers.setVisibility(View.GONE);
        if (error instanceof TimeoutError) {
            showToast("Timeout");
        } else {
            showToast(error.getMessage());

        }
    }

    private void sendUpdateUserRequest() {
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, HOST + "/users/update",
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                dialog.cancel();
                showToast("User updated successfully");
                curUser = editedUser;
                updateUserCard();
                toggleEdit(false);
            }
        }, this) {
            @Override
            public byte[] getBody() {
                Gson gson = new Gson();
                String body = gson.toJson(editedUser);
                return body.getBytes();
            }
        };
        dialog = ProgressDialog.show(this, "",
                "Loading. Please wait...", true);
        requestQueue.add(req);

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
        curUser = u;
        editedUser = u;
    }

    private void toggleEdit(boolean willShow) {
        editMode = willShow;
        ((ImageView)findViewById(R.id.toggle_edit_button)).
                setImageResource(editMode ? R.drawable.ic_close : R.drawable.ic_pencil);
        ((CardView)findViewById(R.id.apply_edit_card)).setVisibility(editMode ? View.VISIBLE : View.GONE);
        FloatLabeledEditText firstNameEdit = findViewById(R.id.first_name_edit);
        EditText firstNameEditText = findViewById(R.id.first_name_edit_text);
        firstNameEditText.setText(curUser.getFirstName());
        firstNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editedUser.setFirstName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        TextView firstName = findViewById(R.id.first_name_text);
        firstNameEdit.setVisibility(willShow ? View.VISIBLE : View.GONE);
        firstName.setVisibility(willShow ? View.GONE : View.VISIBLE);

        FloatLabeledEditText lastNameEdit = findViewById(R.id.last_name_edit);
        EditText lastNameEditText = findViewById(R.id.last_name_edit_text);
        lastNameEditText.setText(curUser.getLastName());
        lastNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editedUser.setLastName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        TextView lastName = findViewById(R.id.last_name_text);
        lastNameEdit.setVisibility(willShow ? View.VISIBLE : View.GONE);
        lastName.setVisibility(willShow ? View.GONE : View.VISIBLE);

        FloatLabeledEditText emailEdit = findViewById(R.id.e_mail_edit);
        EditText emailEditText = findViewById(R.id.e_mail_edit_text);
        emailEditText.setText(curUser.getEmail());
        emailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editedUser.setEmail(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        TextView email = findViewById(R.id.email_text);
        emailEdit.setVisibility(willShow ? View.VISIBLE : View.GONE);
        email.setVisibility(willShow ? View.GONE : View.VISIBLE);

        FloatLabeledEditText cityEdit = findViewById(R.id.city_edit);
        EditText cityEditText = findViewById(R.id.city_edit_text);
        cityEditText.setText(curUser.getCity());
        cityEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editedUser.setCity(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        TextView city = findViewById(R.id.city_text);
        cityEdit.setVisibility(willShow ? View.VISIBLE : View.GONE);
        city.setVisibility(willShow ? View.GONE : View.VISIBLE);

    }

    public void updateUserCard() {
        ((TextView) findViewById(R.id.first_name_text)).setText(curUser.getFirstName());
        ((TextView) findViewById(R.id.last_name_text)).setText(curUser.getLastName());
        ((TextView) findViewById(R.id.email_text)).setText(curUser.getEmail());
        ((TextView) findViewById(R.id.city_text)).setText(curUser.getCity());

    }

    private Context getAppContext() {
        return this.getApplicationContext();
    }

    private void getFamilyMembers() {
        String url = HOST + "/users/familyMembers?userId=" + account.getId();
        loadingFamilyMembers.setVisibility(View.VISIBLE);
        JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                loadingFamilyMembers.setVisibility(View.GONE);
                        Gson gson = new Gson();
                Type type = new TypeToken<ArrayList<FamilyMember>>(){}.getType();

                List<FamilyMember> familyMembers =
                        (List<FamilyMember>) gson.fromJson(response.toString(), type);
                familyMemberAdapter = new FamilyMemberAdapter(familyMembers);
                submitRequest.setAdapter(familyMemberAdapter);
                familyMemberList.setAdapter(familyMemberAdapter);
                familyMemberList.setLayoutManager(new LinearLayoutManager(getAppContext()));
//                familyMemberList.setNestedScrollingEnabled(false);
            }
        }, this);
        requestQueue.add(req);
    }
}
