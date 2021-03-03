package com.scpfoundation.psybotic.app.ui.chatbot;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.scpfoundation.psybotic.app.R;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.messages.MessageInput;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;

import java.util.ArrayList;
import java.util.List;

public class ChatBotActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbot);
        String senderId = "muhammed";
        ImageLoader imageLoader = null;
        MessagesListAdapter<Message> adapter = new MessagesListAdapter<>(senderId, imageLoader);
        MessagesList m = new MessagesList(this,null);
        m.setAdapter(adapter);
        MessageInput inputView = new MessageInput(this,null);
        adapter.addToStart(new Message(),true);
        List<Message> messages = new ArrayList<>();
        Message j = new Message();
        messages.add(j);
        adapter.addToEnd(messages,true);
        inputView.setInputListener(new MessageInput.InputListener() {
            @Override
            public boolean onSubmit(CharSequence input) {
                //validate and send message
                Message message = new Message();
                adapter.addToStart(message, true);
                return true;
            }
        });
    }

}