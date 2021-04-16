package com.scpfoundation.psybotic.app.ui.notifications;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.scpfoundation.psybotic.app.R;
import com.scpfoundation.psybotic.app.data.Notification;
import com.scpfoundation.psybotic.app.ui.chatbot.ChatBotActivity;
import com.scpfoundation.psybotic.app.ui.profile.ProfileActivity;
import com.scpfoundation.psybotic.app.ui.psychologychat.PsychologyActivity;

import org.json.JSONObject;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder>  {
    private static final String HOST = "https://limitless-lake-96203.herokuapp.com";
    private ProgressDialog dialog;

    @NonNull
    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.notification, parent, false);
        return new NotificationAdapter.ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.ViewHolder holder, int position) {
        final Notification notification = notificationList.get(position);
        final TextView header_text = holder.Header;
        System.out.println(notification.getTextHeader());
        System.out.println(notification.getSendingDate());
        header_text.setText(notification.getTextHeader());
        final TextView notification_text = holder.NotificationText;

        notification_text.setText(notification.getText());
        final TextView notification_date=holder.NotificationDate;
        notification_date.setText(notification.getSendingDate()+"");
        notification_date.setMovementMethod(new ScrollingMovementMethod());
        final TextView bildirim_icin_kalansure=holder.KalanSure;
        bildirim_icin_kalansure.setMovementMethod(new ScrollingMovementMethod());
        Date date = new Date(System.currentTimeMillis());
        String kalansure;
        if(notification.getSendingDate()!=null) {
             kalansure= getDateDiff(date,notification.getSendingDate(), TimeUnit.HOURS) + " Saat Gecti. \n"+"Senin Icin Endiseliyiz,Sevdiklerine Haber verecegiz/verdik";
        }
        else
            kalansure="Null Geldi";
        bildirim_icin_kalansure.setText(kalansure);
        final ImageButton iyiyim=holder.right;
        final ImageButton hastayim=holder.emergency;
        final ImageView logo=holder.logo;
        iyiyim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(notification.toString());
                String url = HOST + "/notifications/update";
                deleteItem(position,holder);



            }
        });
        hastayim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //RequestQueue requestQueue = Volley.newRequestQueue(iyiyim.getContext());;
                String url = HOST + "/notifications/update";
                Intent intent = new Intent(v.getContext(), ChatBotActivity.class);
                deleteItem(position,holder);
                v.getContext().startActivity(intent);

            }
        });
    }
    public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
    }
    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView Header;
        public TextView KalanSure;
        public TextView NotificationText;
        public TextView NotificationDate;
        public ImageButton right;
        public ImageButton emergency;
        public ImageView logo;
        public TextView size;
        public ViewHolder(View itemView) {
            super(itemView);
            Header =  itemView.findViewById(R.id.notification_Header);
            KalanSure=itemView.findViewById(R.id.due_date);
            NotificationDate=itemView.findViewById(R.id.sending_date);
            NotificationText=itemView.findViewById(R.id.NotificationText);
            right=itemView.findViewById(R.id.Allright);
            emergency=itemView.findViewById(R.id.NoIamBad);
            size=itemView.findViewById(R.id.Notification_sizes);
        }
    }
    private List<Notification> notificationList;

    public NotificationAdapter(List<Notification> notificationList) {
        this.notificationList = notificationList;
    }
    private void deleteItem(int position,ViewHolder holder) {
        notificationList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, notificationList.size());
        holder.itemView.setVisibility(View.GONE);
    }


}
