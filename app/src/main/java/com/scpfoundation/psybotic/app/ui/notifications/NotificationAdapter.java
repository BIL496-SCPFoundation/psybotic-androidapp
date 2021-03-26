package com.scpfoundation.psybotic.app.ui.notifications;

import android.media.Image;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.scpfoundation.psybotic.app.ui.profile.FamilyMemberAdapter;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {


    @NonNull
    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView Header;
        public TextView KalanSure;
        public TextView NotificationDate;
        public TextView LastDate;
        public ImageButton right;
        public ImageButton emergency;
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }


}
