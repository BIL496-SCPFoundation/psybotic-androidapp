package com.scpfoundation.psybotic.app.ui.chatbot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.gson.Gson;
import com.scpfoundation.psybotic.app.R;
import com.scpfoundation.psybotic.app.data.ChatMessage;
import com.scpfoundation.psybotic.app.data.MessageRequest;
import com.scpfoundation.psybotic.app.data.User;
import com.scpfoundation.psybotic.app.util.RequestManager;
import com.scpfoundation.psybotic.app.util.Utility;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.messages.MessageInput;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {

    GoogleSignInAccount account;
    MessagesListAdapter<Message> adapter;
    private RequestManager requestManager;
    private Author self;
    private Author chatbot;
    private String receiverId = "chatbot";
    private final String HOST = "https://limitless-lake-96203.herokuapp.com";
    private final String SEND_MESSAGE_URL = HOST + "/firebase/sendMessage";
    private final String HISTORY_URL = HOST + "/chatMessages/history";
    private User receiver;
    private Author receiverAuthor;
    private final Context CTX = this;
    private ProgressBar loadingHistory;
    private TextView title;
    private String chatRoomId;
    // Indicates if the current user is a psychologist
    private boolean isPsychologist = false;
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.hasExtra("senderId") && intent.hasExtra("firstName") &&
                    intent.hasExtra("message")) {
                Message message = new Message((String) intent.getExtras().get("message"));
                Author author = (intent.getExtras().get("senderId").equals(account.getId())) ? self : receiverAuthor;
                message.setAuthor(author);
                message.setText(intent.getExtras().getString("message"));
                adapter.addToStart(message, true);
            }

        }
    };;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        requestManager = RequestManager.getInstance(this.getApplicationContext());
        RequestQueue requestQueue = Volley.newRequestQueue(this.getApplicationContext());
        LocalBroadcastManager.getInstance(this).registerReceiver((mMessageReceiver),
                new IntentFilter("messageData"));
        loadingHistory = findViewById(R.id.loading_history);
        account = GoogleSignIn.getLastSignedInAccount(this);
        title = findViewById(R.id.chat_title);
        if (getIntent().hasExtra("receiver")) {
            receiver = (User) getIntent().getExtras().get("receiver");
            title.setText(receiver.getFirstName());
            receiverAuthor = new Author();
            receiverAuthor.setId(receiver.getId());
            receiverAuthor.setName(receiver.getFirstName());
            receiverId = receiver.getId();
        } else {
            receiverAuthor = new Author();
            receiverAuthor.setId("chatbot");
            receiverAuthor.setName("Psybotic");
        }
        if (getIntent().hasExtra("isPsychologist")) {
            isPsychologist = (boolean) getIntent().getExtras().get("isPsychologist");
        }
        self = new Author();
        self.setName(account.getDisplayName());
        self.setId(account.getId());
        chatbot = new Author();
        chatbot.setName("Psybotic");
        chatbot.setId("chatbot");
        ImageLoader imageLoader = null;
        MessagesListAdapter.HoldersConfig holdersConfig = new MessagesListAdapter.HoldersConfig();
        holdersConfig.setIncomingLayout(R.layout.item_incoming_text_message);
        holdersConfig.setOutcomingLayout(R.layout.item_outcoming_text_message);
        adapter = new MessagesListAdapter<>(account.getId(), holdersConfig,
                imageLoader);

        ((MessagesList) findViewById(R.id.messagesList)).setAdapter(adapter);
        MessageInput minput = findViewById(R.id.input);
        Map<String, String> params = new HashMap<>();
        String chatRoomId = getChatRoomId(account.getId(), receiverId);
        params.put("chatRoomId", chatRoomId);
        requestManager.getArrayRequest(HISTORY_URL, params, response -> {
            loadingHistory.setVisibility(View.GONE);
            ChatMessage[] historyMessages = new Gson().fromJson(response.toString(), ChatMessage[].class);
            for (ChatMessage m :
                    historyMessages) {
                Message tmp = new Message(m.getMessage());
                tmp.setCreatedAt(m.getDate());
                Author author = (m.getSenderId().equals(account.getId())) ? self : receiverAuthor;
                tmp.setAuthor(author);
                adapter.addToStart(tmp, false);

            }
        }, error -> {
            loadingHistory.setVisibility(View.GONE);
            Log.e("Request error", error.getLocalizedMessage());
        });

        minput.setInputListener(input -> {
            //validate and send message
            if (!input.toString().equals("")) {
                Message message = new Message(input.toString());
                message.setAuthor(self);
                adapter.addToStart(message, true);
                String chatRoomId1 = getChatRoomId(account.getId(), receiverId);
                ChatMessage cm = new ChatMessage(input.toString(),
                        account.getDisplayName(), account.getFamilyName(),
                        receiverId, account.getId(), chatRoomId1);
                try {
                    MessageRequest messageRequest = new MessageRequest();
                    messageRequest.setData(cm);
                    JSONObject body = new JSONObject(new Gson().toJson(messageRequest));
                    requestManager.postRequest(SEND_MESSAGE_URL, body, response -> {
                        JSONObject chatbotResponse;
                        try {
                            chatbotResponse = response.getJSONObject("chatbotResponse");
                            Message responseMessage = new Message(chatbotResponse.getString("text"));
                            responseMessage.setAuthor(chatbot);
                            responseMessage.setCreatedAt(new Date(System.currentTimeMillis()));
                            adapter.addToStart(responseMessage, true);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }, error -> {
                        Utility.showToast(CTX, "Encountered an error while sending message");
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                    return false;
                }
                return true;
            } else return false;
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.chat_back_button:
                finish();
                break;
            default:
                break;
        }
    }

    public static String getChatRoomId(String senderId, String receiverId) {
        if (receiverId.equals("chatbot")) {
            return senderId + receiverId;
        } else if (senderId.equals("chatbot")) {
            return receiverId + senderId;
        } else {
            double senderIdValue = Double.parseDouble(senderId);
            double receiverIdValue = Double.parseDouble(receiverId);
            if (receiverIdValue < senderIdValue) {
                return receiverId + senderId;
            } else {
                return senderId + receiverId;
            }
        }
    }
}