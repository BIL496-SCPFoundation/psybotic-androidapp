package com.scpfoundation.psybotic.app.ui.realUserChat;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scpfoundation.psybotic.app.R;
import com.scpfoundation.psybotic.app.data.FamilyMember;
import com.scpfoundation.psybotic.app.data.Psychologist;
import com.scpfoundation.psybotic.app.data.User;
import com.scpfoundation.psybotic.app.ui.chatbot.Author;
import com.scpfoundation.psybotic.app.ui.chatbot.ChatActivity;
import com.scpfoundation.psybotic.app.util.RequestManager;
import com.scpfoundation.psybotic.app.util.Utility;
import com.squareup.picasso.Picasso;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.commons.models.IUser;
import com.stfalcon.chatkit.dialogs.DialogsList;
import com.stfalcon.chatkit.dialogs.DialogsListAdapter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class DialogList extends AppCompatActivity {

    private DialogsListAdapter dialogsListAdapter;
    private DialogsList dialogsListView;
    private RequestManager requestManager;
    private final String HOST = "https://limitless-lake-96203.herokuapp.com";
    private final String CHATROOMS = HOST + "/chatMessages/chatRooms";
    private final String PSYCHOLOGISTS = HOST + "/psychologists/verifiedPsychologists";
    private GoogleSignInAccount account;
    private ProgressBar loadingConversations;
    private final Context CTX = this;
    private HashMap<String, User> conversations = new HashMap<>();
    private RecyclerView psychologistList;
    private PsychologistAdapter psychologistAdapter;
    private ProgressBar loadingPsychologists;
    private boolean isPsychologist = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialogs);
        dialogsListView = findViewById(R.id.dialogsList);
        Map<String, String> params = new HashMap<>();
        psychologistList = findViewById(R.id.psychologist_list);
        requestManager = RequestManager.getInstance(this);
        account = GoogleSignIn.getLastSignedInAccount(this);
        loadingPsychologists = findViewById(R.id.loading_psychologists);
        params.put("userId", Objects.requireNonNull(account).getId());
        dialogsListAdapter = new DialogsListAdapter((imageView, url, payload) ->
                Picasso.get().load((url == null || url.equals("")) ? "https://freepikpsd.com/wp-content/uploads/2019/11/Man_Person_Avatar_Computer_93949-Transparent-Png-Images.png" : url).into(imageView));
        dialogsListAdapter.setOnDialogClickListener(dialog -> {
            Intent intent = new Intent(CTX, ChatActivity.class);
            User u = conversations.get(((IUser)dialog.getUsers().get(0)).getId());
            intent.putExtra("receiver", u);
            intent.putExtra("chatRoomId", dialog.getId());
            startActivity(intent);
        });
        if (getIntent().hasExtra("isPsychologist")) {
            isPsychologist = (boolean) getIntent().getExtras().get("isPsychologist");
            if (isPsychologist) {
                psychologistList.setVisibility(View.GONE);
                loadingPsychologists.setVisibility(View.GONE);
            }
        }
        dialogsListView.setAdapter(dialogsListAdapter);
        loadingConversations = findViewById(R.id.on_goings_progressbar);
        requestManager.getArrayRequest(CHATROOMS, params, response -> {
            ChatroomResponse[] chatrooms = new Gson().fromJson(response.toString(), ChatroomResponse[].class);
            for (ChatroomResponse res :
                    chatrooms) {
                conversations.put(res.getUser().getId(), res.getUser());
                DefaultDialog dialog = new DefaultDialog();
                dialog.setDialogName(res.getUser().getFirstName());
                dialog.setId(res.getLastMessage().getChatRoomId());
                dialog.setDialogPhoto(res.getUser().getImageUrl());
                Message message = new Message(res.getLastMessage().getMessage());
                Author author = new Author();
                author.setId(res.getUser().getId());
                author.setName(res.getUser().getFirstName());
                message.setCreatedAt(res.getLastMessage().getDate());
                message.setAuthor(author);
                dialog.setLastMessage(message);
                List<IUser> users = new ArrayList<>();
                users.add(author);
                dialog.setUsers(users);
                dialogsListAdapter.addItem(dialog);
            }
            loadingConversations.setVisibility(View.GONE);
        }, error -> {
            loadingConversations.setVisibility(View.GONE);
            Utility.showToast(CTX, "Request error");
            Log.e("Chatrooms request error", error.getMessage());
        });
        requestManager.getArrayRequest(PSYCHOLOGISTS, null, response -> {
            Type type = new TypeToken<ArrayList<Psychologist>>(){}.getType();
            List<Psychologist> psychologists = new Gson().fromJson(response.toString(), type);
            psychologistAdapter = new PsychologistAdapter(psychologists);
            psychologistList.setAdapter(psychologistAdapter);
            loadingPsychologists.setVisibility(View.GONE);
        }, error -> {
            Utility.showToast(CTX, "Request error");
            loadingPsychologists.setVisibility(View.GONE);
        });
    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialogs_back_button:
                finish();
                break;
            default:
                break;
        }
    }
}