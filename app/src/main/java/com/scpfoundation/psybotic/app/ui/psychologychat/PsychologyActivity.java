package com.scpfoundation.psybotic.app.ui.psychologychat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.scpfoundation.psybotic.app.R;
import com.scpfoundation.psybotic.app.ui.chatbot.Message;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.messages.MessageInput;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;

public class PsychologyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_psychology);
        String senderId = "o";
        ImageLoader imageLoader = null;
        MessagesListAdapter<com.scpfoundation.psybotic.app.ui.chatbot.Message> adapter = new MessagesListAdapter<>(senderId, imageLoader);
        ((MessagesList) findViewById(R.id.messagesList)).setAdapter(adapter);
        MessageInput minput = findViewById(R.id.input);
        minput.setInputListener(new MessageInput.InputListener() {
            @Override
            public boolean onSubmit(CharSequence input) {
                //validate and send message
                com.scpfoundation.psybotic.app.ui.chatbot.Message message = new Message(input.toString());
                adapter.addToStart(message,true);
                return true;
            }
        });
        minput.setAttachmentsListener(new MessageInput.AttachmentsListener() {
            @Override
            public void onAddAttachments() {
                //select attachments
            }
        });
    }
}