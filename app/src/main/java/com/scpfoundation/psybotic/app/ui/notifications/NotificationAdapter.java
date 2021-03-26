package com.scpfoundation.psybotic.app.ui.notifications;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.scpfoundation.psybotic.app.R;
import com.scpfoundation.psybotic.app.data.Notification;

import org.json.JSONObject;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
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
        header_text.setText(notification.getTextHeader());
        final TextView notification_text = holder.NotificationText;
        notification_text.setText(notification.getText());
        final TextView notification_date=holder.NotificationDate;
        notification_date.setText(notification.getSendingDate()+"");
        final TextView bildirim_icin_kalansure=holder.KalanSure;
        Date date = new Date(System.currentTimeMillis());
        String kalansure;
        if(notification.getSendingDate()!=null) {
             kalansure= getDateDiff(date, notification.getSendingDate(), TimeUnit.HOURS) + "";
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
               // RequestQueue requestQueue = Volley.newRequestQueue(iyiyim.getContext());;
                String url = HOST + "/notifications/update";
                /*JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url,
                        null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        dialog.cancel();
                        int index = notificationList.indexOf(notification);
                        notifyItemChanged(position);
//                        notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error instanceof TimeoutError) {
                            Toast.makeText(iyiyim.getContext(), "Timeout", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(hastayim.getContext(), error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                        dialog.cancel();
                    }
                }) {
                    @Override
                    public byte[] getBody() {
                        Gson gson = new Gson();
                        String body = gson.toJson(notification);
                        return body.getBytes();
                    }
                };
                dialog = ProgressDialog.show(iyiyim.getContext(), "",
                        "Loading. Please wait...", true);

                requestQueue.add(req);
                */

            }
        });
        hastayim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //RequestQueue requestQueue = Volley.newRequestQueue(iyiyim.getContext());;
                String url = HOST + "/notifications/update";
                /*
                JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url,
                        null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        dialog.cancel();
                        int index = notificationList.indexOf(notification);

                        notifyItemChanged(position);
//                        notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error instanceof TimeoutError) {
                            Toast.makeText(hastayim.getContext(), "Timeout", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(hastayim.getContext(), error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                        dialog.cancel();

                    }
                }) {
                    @Override
                    public byte[] getBody() {
                        Gson gson = new Gson();
                        String body = gson.toJson(notification);
                        return body.getBytes();
                    }
                };
                dialog = ProgressDialog.show(hastayim.getContext(), "",
                        "Loading. Please wait...", true);

                requestQueue.add(req);
                */;
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
        public ViewHolder(View itemView) {
            super(itemView);
            Header =  itemView.findViewById(R.id.notification_Header);
            KalanSure=itemView.findViewById(R.id.LastDate);
            NotificationDate=itemView.findViewById(R.id.NotificationDate);
            NotificationText=itemView.findViewById(R.id.NotificationText);
            right=itemView.findViewById(R.id.Allright);
            emergency=itemView.findViewById(R.id.NoIamBad);
            logo=itemView.findViewById(R.id.warning);
        }
    }
    private List<Notification> notificationList;

    public NotificationAdapter(List<Notification> notificationList) {
        this.notificationList = notificationList;
    }


}
