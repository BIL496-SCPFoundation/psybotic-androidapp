package com.scpfoundation.psybotic.app.ui.chatbot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.scpfoundation.psybotic.app.R;
import com.scpfoundation.psybotic.app.data.ChatMessage;
import com.scpfoundation.psybotic.app.data.FireBaseSendMessageAO;
import com.scpfoundation.psybotic.app.data.User;
import com.scpfoundation.psybotic.app.service.MessagingService;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.messages.MessageInput;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;

import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChatBotActivity extends AppCompatActivity {

    GoogleSignInAccount account;
    MessagesListAdapter<Message> adapter;
    private ProgressDialog dialog;
    private final BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Author author = new Author();
            author.setId(intent.getExtras().getString("senderId"));
            author.setName(intent.getExtras().getString("firstName"));
            Message message = new Message("Hey there");
            message.setAuthor(author);
            message.setText(intent.getExtras().getString("message"));
            adapter.addToStart(message, true);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbot);
        RequestQueue requestQueue = Volley.newRequestQueue(this.getApplicationContext());
        LocalBroadcastManager.getInstance(this).registerReceiver((mMessageReceiver),
                new IntentFilter("messageData"));
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
        adapter.addToStart(new Message("Hello, how may i help you?"),true);
        minput.setInputListener(new MessageInput.InputListener() {
            @Override
            public boolean onSubmit(CharSequence input) {
                //validate and send message
                List<Message> messages = new ArrayList<>();
                Message message = new Message(input.toString());
                Message message2 = new Message("Heyoo!");
                message.setAuthor(self);
                messages.add(message);
                messages.add(message2);

                adapter.addToStart(message,true);

                adapter.addToStart(message2,false);
                // See documentation on defining a message payload.
                ChatMessage cm = new ChatMessage(input.toString(),"Oğuz",
                        "Andaş", DateFormat.getDateInstance().toString(),
                        "112508789811138107089","110841579040674635754");

                FireBaseSendMessageAO all_body = new FireBaseSendMessageAO("ChatBot",input.toString()
                        ,"EaseToBody","aa",cm,self.getId());

                JsonObjectRequest req = reqChat(all_body);
                dialog = ProgressDialog.show(minput.getContext(), "",
                        "Loading. Please wait...", true);
                requestQueue.add(req);
                System.out.println(req.toString());
                return true;
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);

    }

    protected JsonObjectRequest reqChat(FireBaseSendMessageAO cm) {
        String HOST = "https://limitless-lake-96203.herokuapp.com";
        String url = HOST + "/firebase/sendMessage";
        return new JsonObjectRequest(Request.Method.POST, url,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
            if (dialog != null) {
                dialog.cancel();
            }

        }}, error -> {
            if (dialog != null) {
                dialog.cancel();
            }
            System.err.println(error.getMessage());
        }) {
            @Override
            public byte[] getBody() {
                Gson gson = new Gson();
                String body = gson.toJson(cm);
                return body.getBytes();
            }
        };
    }
}