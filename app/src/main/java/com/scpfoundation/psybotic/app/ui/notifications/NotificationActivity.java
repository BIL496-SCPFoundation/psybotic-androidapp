package com.scpfoundation.psybotic.app.ui.notifications;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scpfoundation.psybotic.app.R;
import com.scpfoundation.psybotic.app.data.FamilyMember;
import com.scpfoundation.psybotic.app.data.Notification;
import com.scpfoundation.psybotic.app.data.User;
import com.scpfoundation.psybotic.app.request.FamilyMemberSubmitRequest;
import com.scpfoundation.psybotic.app.ui.profile.FamilyMemberAdapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class NotificationActivity extends AppCompatActivity   implements  View.OnClickListener, Response.ErrorListener, Response.Listener<JSONObject>{
    RecyclerView notifitcanList;
    NotificationAdapter notificationAdapter;
    ProgressBar loadingNotifications;
    private GoogleSignInAccount account;
    private Notification newNotification = new Notification();
    private final String HOST = "https://limitless-lake-96203.herokuapp.com";
    private Notification curNotification;
    private Notification editedNotification;
    RequestQueue requestQueue;
    private ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        notifitcanList = findViewById(R.id.notification_lists);
        loadingNotifications = findViewById(R.id.loading_notifications);
        account = (GoogleSignInAccount) getIntent().getExtras().get("account");
        requestQueue = Volley.newRequestQueue(this.getApplicationContext());
        getNotifications();
        final ConstraintLayout constraintLayout = findViewById(R.id.fm_create_dialog);
    }

    private Context getAppContext() {
        return this.getApplicationContext();
    }

    private void getNotifications() {
        String url = HOST + "/users/notifications?userId=" + account.getId();
        loadingNotifications.setVisibility(View.VISIBLE);
        JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                loadingNotifications.setVisibility(View.GONE);
                Gson gson = new Gson();
                Type type = new TypeToken<ArrayList<Notification>>() {
                }.getType();

                List<Notification> notifications =
                        (List<Notification>) gson.fromJson(response.toString(), type);
                notificationAdapter = new NotificationAdapter(notifications);
                notifitcanList.setAdapter(notificationAdapter);
                notifitcanList.setLayoutManager(new LinearLayoutManager(getAppContext()));
//                familyMemberList.setNestedScrollingEnabled(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //to do something..
            }
        });
        requestQueue.add(req);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e("Error",error.getMessage());
    }
    private void sendUpdateNotification() {
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, HOST + "/notifications/update",
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                dialog.cancel();
                curNotification = editedNotification;
            }
        }, this) {
            @Override
            public byte[] getBody() {
                Gson gson = new Gson();
                String body = gson.toJson(editedNotification);
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
        Notification u = gson.fromJson(response.toString(), Notification.class);



        TextView header_Text = findViewById(R.id.notification_Header);
        header_Text.setText(u.getTextHeader());

        TextView notification_Text = findViewById(R.id.NotificationText);
        notification_Text.setText(u.getText());

        TextView notification_Date = findViewById(R.id.NotificationDate);
        notification_Date.setText(u.getSendingDate()+"");

        TextView notification_last_time = findViewById(R.id.GerceklesmeTarihi);
        Date date = new Date(System.currentTimeMillis());
        String kalansure=getDateDiff(date,u.getSendingDate(), TimeUnit.HOURS)+"";
        notification_last_time.setText(kalansure);

        ImageButton iyiyim=findViewById(R.id.Allright);
        ImageButton hastayim=findViewById(R.id.NoIamBad);

        curNotification = u;
        editedNotification = u;
    }


    public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
    }
}
