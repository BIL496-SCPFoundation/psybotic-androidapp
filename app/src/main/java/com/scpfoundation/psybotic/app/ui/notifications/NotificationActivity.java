package com.scpfoundation.psybotic.app.ui.notifications;

import androidx.appcompat.app.AppCompatActivity;
import com.scpfoundation.psybotic.app.R;
import com.scpfoundation.psybotic.app.data.User;

import android.os.Bundle;

public class NotificationActivity extends AppCompatActivity {
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

    }

}