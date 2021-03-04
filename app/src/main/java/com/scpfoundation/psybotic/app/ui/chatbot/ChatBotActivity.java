package com.scpfoundation.psybotic.app.ui.chatbot;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

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
        ((MessagesList) findViewById(R.id.messagesList)).setAdapter(adapter);
        MessageInput minput = findViewById(R.id.input);
        minput.setInputListener(new MessageInput.InputListener() {
            @Override
            public boolean onSubmit(CharSequence input) {
                //validate and send message
                System.out.println("LO   "+input);
                Message message = new Message(input.toString());
                List<Message> lis = new ArrayList<>();
                lis.add(message);
                adapter.addToStart(message,true);
                if(adapter != null)
                    System.out.println("asdq");
                return true;
            }
        });

    }


}