package com.scpfoundation.psybotic.app.ui.psychologychat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.scpfoundation.psybotic.app.R;
import com.scpfoundation.psybotic.app.ui.chatbot.Author;
import com.scpfoundation.psybotic.app.ui.chatbot.Message;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.messages.MessageInput;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;

import java.util.ArrayList;
import java.util.List;

public class PsychologyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_psychology);
        String senderId = "o";
        ImageLoader imageLoader = null;
        MessagesListAdapter.HoldersConfig holdersConfig = new MessagesListAdapter.HoldersConfig();
        holdersConfig.setIncomingLayout(R.layout.item_incoming_text_message);
        holdersConfig.setOutcomingLayout(R.layout.item_outcoming_text_message);
        MessagesListAdapter<Message> adapter = new MessagesListAdapter<>(senderId, holdersConfig,
                imageLoader);
        ((MessagesList) findViewById(R.id.messagesList)).setAdapter(adapter);
        MessageInput minput = findViewById(R.id.input);
        minput.setInputListener(new MessageInput.InputListener() {
            @Override
            public boolean onSubmit(CharSequence input) {
                //validate and send message
                //List<Message> messages = new ArrayList<>();
                Message message = new Message(input.toString());
                Message message2 = new Message("Hey dear!");
                Author ab = new Author();
                ab.setId(senderId);
                message.setAuthor(ab);

                //adapter.addToStart(message,true);
                adapter.addToStart(message,true);
                adapter.addToStart(message2,false);
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