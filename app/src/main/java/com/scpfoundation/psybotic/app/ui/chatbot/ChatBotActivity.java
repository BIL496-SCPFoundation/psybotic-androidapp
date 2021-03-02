package com.scpfoundation.psybotic.app.ui.chatbot;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.scpfoundation.psybotic.app.R;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;

public class ChatBotActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbot);
        String senderId = "";
        ImageLoader imageLoader = null;
        MessagesListAdapter<Message> adapter = new MessagesListAdapter<>(senderId, imageLoader);
        MessagesList m = new MessagesList(null,null);
        m.setAdapter(adapter);
    }
}