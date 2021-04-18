package com.scpfoundation.psybotic.app.ui.realUserChat;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.scpfoundation.psybotic.app.R;
import com.scpfoundation.psybotic.app.data.Psychologist;
import com.scpfoundation.psybotic.app.data.User;
import com.scpfoundation.psybotic.app.ui.chatbot.ChatActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import static androidx.core.content.ContextCompat.startActivity;

public class PsychologistAdapter extends RecyclerView.Adapter<PsychologistAdapter.ViewHolder> {
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.fragment_psychologist_card, parent, false);
        return new PsychologistAdapter.ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Psychologist psychologist = psychologistList.get(position);
        ImageView profileImage = holder.profileImage;
        if (psychologist.getImageURL() != null && !psychologist.getImageURL().equals("")) {
            Picasso.get().load(psychologist.getImageURL()).into(profileImage);
        }
        TextView name = holder.name;
        name.setText(psychologist.getFirstName() + " " + psychologist.getLastName());

        TextView titles = holder.titles;
        StringBuilder tmpTitles = new StringBuilder();
        if (psychologist.getTitles() != null) {
            for (String title :
                    psychologist.getTitles()) {
                tmpTitles.append(title);
            }
        }

        titles.setText(tmpTitles.toString());

        TextView bio = holder.bio;
        bio.setText(psychologist.getBiography());

        Button startChat = holder.startChat;
        startChat.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), ChatActivity.class);
            User u = new User();
            u.setFirstName(psychologist.getFirstName());
            u.setLastName(psychologist.getLastName());
            u.setId(psychologist.getId());
            u.setPsychologist(true);
            intent.putExtra("receiver", u);
            startActivity(v.getContext(), intent, null);

        });
        Button seeMore = holder.seeMore;
    }

    @Override
    public int getItemCount() {
        return psychologistList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView profileImage;
        private TextView name;
        private TextView titles;
        private TextView bio;
        private Button startChat;
        private Button seeMore;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            profileImage = itemView.findViewById(R.id.psychology_card_image);
            name = itemView.findViewById(R.id.psychology_card_name);
            titles = itemView.findViewById(R.id.psychology_card_titles);
            bio = itemView.findViewById(R.id.psychology_card_bio);
            startChat = itemView.findViewById(R.id.start_conversation);
            seeMore = itemView.findViewById(R.id.see_more);
        }
    }

    private List<Psychologist> psychologistList;

    public PsychologistAdapter(List<Psychologist> psychologistList) {
        this.psychologistList = psychologistList;
    }
}
