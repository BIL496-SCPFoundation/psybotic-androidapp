package com.scpfoundation.psybotic.app.ui.chatbot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.scpfoundation.psybotic.app.R;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.messages.MessageInput;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;

import java.util.ArrayList;
import java.util.List;

public class ChatBotActivity extends AppCompatActivity {

    GoogleSignInAccount account;
    MessagesListAdapter<Message> adapter;
    List<Message> messages;
    private final BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Author author = new Author();
            //author.setId(intent.getExtras().getString("senderId"));
            //author.setName(intent.getExtras().getString("firstName"));
            Message message = new Message("Hey there");
            messages.add(message);
            message.setAuthor(author);
            //message.setText(intent.getExtras().getString("message"));
            adapter.addToEnd(messages, true);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbot);

        LocalBroadcastManager.getInstance(this).registerReceiver((mMessageReceiver),
                new IntentFilter("messageData"));
        Context c = this;
        account = GoogleSignIn.getLastSignedInAccount(this);
        Author self = new Author();
        self.setName(account.getDisplayName());
        self.setId(account.getId());
        ImageLoader imageLoader = null;
        MessagesListAdapter.HoldersConfig holdersConfig = new MessagesListAdapter.HoldersConfig();
        holdersConfig.setIncomingLayout(R.layout.item_incoming_text_message);
        holdersConfig.setOutcomingLayout(R.layout.item_outcoming_text_message);
        MessagesListAdapter<Message> adapter = new MessagesListAdapter<>(account.getId(), holdersConfig,
                imageLoader);
        ((MessagesList) findViewById(R.id.messagesList)).setAdapter(adapter);
        MessageInput minput = findViewById(R.id.input);
        Intent tintent = getIntent();
        messages = new ArrayList<>();
        minput.setInputListener(new MessageInput.InputListener() {
            @Override
            public boolean onSubmit(CharSequence input) {
                //validate and send message

                Message message = new Message(input.toString());
                Message message2 = new Message("Heyoo!");
                message.setAuthor(self);
                messages.add(message);
                //messages.add(message2);
                //adapter.addToStart(message,true);
                tintent.putExtra("senderId",  self.getId());
                tintent.putExtra("firstName",  self.getName());
                tintent.putExtra("message",  message.getText());
                //adapter.addToStart(message,true);
                mMessageReceiver.onReceive(c,getIntent());
                //adapter.addToEnd(messages,true);
                //adapter.addToStart(message2,false);
                return true;
            }
        });









    }

    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);

    }
}