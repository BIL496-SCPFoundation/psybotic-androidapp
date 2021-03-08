package com.scpfoundation.psybotic.app.ui.profile;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.scpfoundation.psybotic.app.R;
import com.scpfoundation.psybotic.app.data.EmergencyContact;
import com.scpfoundation.psybotic.app.data.FamilyMember;
import com.wrapp.floatlabelededittext.FloatLabeledEditText;

import org.json.JSONObject;

import java.util.List;

public class EmergencyContactAdapter extends RecyclerView.Adapter<EmergencyContactAdapter.ViewHolder> {

    private static final String HOST = "https://limitless-lake-96203.herokuapp.com";
    private ProgressDialog dialog;
    private List<EmergencyContact> emergencyContactList;

    public void insert(EmergencyContact emergencyContact) {
        emergencyContactList.add(emergencyContact);
        notifyDataSetChanged();
    }

    public EmergencyContactAdapter(List<EmergencyContact> emergencyContactList) {
        this.emergencyContactList = emergencyContactList;
    }

    @NonNull
    @Override
    public EmergencyContactAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.fragment_emergency_contact, parent, false);
        return new ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull EmergencyContactAdapter.ViewHolder holder, int position) {

        final EmergencyContact emergencyContact = emergencyContactList.get(position);

        final TextView fnTextView = holder.fnTextView;
        fnTextView.setText(emergencyContact.getFirstName() == null ? "Name unknown" : emergencyContact.getFirstName());
        final TextView lnTextView = holder.lnTextView;
        lnTextView.setText(emergencyContact.getLastName() == null ? "Last name unknown" : emergencyContact.getLastName());
        final TextView emailTextView = holder.emailTextView;
        emailTextView.setText(emergencyContact.getEmail() == null ? "Email unknown" : emergencyContact.getEmail());
        final TextView phoneTextView = holder.phoneTextView;
        phoneTextView.setText(emergencyContact.getPhone() == null ? "Phone unknown" : emergencyContact.getPhone());

        EditText fnEditText = holder.fnEditText;
        fnEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                emergencyContact.setFirstName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        fnEditText.setText(emergencyContact.getFirstName());

        EditText lnEditText = holder.lnEditText;
        lnEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                emergencyContact.setLastName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        lnEditText.setText(emergencyContact.getLastName());

        EditText emailEditTex = holder.emailEditText;
        emailEditTex.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                emergencyContact.setEmail(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        emailEditTex.setText(emergencyContact.getEmail());

        EditText phoneEditText = holder.phoneEditText;
        phoneEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                emergencyContact.setPhone(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        phoneEditText.setText(emergencyContact.getPhone());

        final FloatLabeledEditText fnEdit = holder.fnEdit;
        final FloatLabeledEditText lnEdit = holder.lnEdit;
        final FloatLabeledEditText emailEdit = holder.emailEdit;
        final FloatLabeledEditText phoneEdit = holder.phoneEdit;

        final ImageView editImage = holder.editImage;

        final CardView editButton = holder.editButton;
        final CardView applyButton = holder.applyButton;
        final CardView deleteButton = holder.deleteButton;

        applyButton.setOnClickListener(v -> {
            RequestQueue requestQueue = Volley.newRequestQueue(emailTextView.getContext());;
            String url = HOST + "/emergencyContacts/update";
            JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url,
                    null, response -> {
                        dialog.cancel();
                        int index = emergencyContactList.indexOf(emergencyContact);
                        applyButton.setVisibility(View.GONE);
                        deleteButton.setVisibility(View.VISIBLE);

                        holder.editMode = false;

                        fnEdit.setVisibility(View.GONE);
                        emailEdit.setVisibility(View.GONE);
                        lnEdit.setVisibility(View.GONE);
                        phoneEdit.setVisibility(View.GONE);

                        fnTextView.setVisibility(View.VISIBLE);
                        lnTextView.setVisibility(View.VISIBLE);
                        emailTextView.setVisibility(View.VISIBLE);
                        phoneTextView.setVisibility(View.VISIBLE);

                        editImage.setImageResource(R.drawable.ic_pencil);
                        notifyItemChanged(position);
                    }, error -> {
                if (error instanceof TimeoutError) {
                    Toast.makeText(editButton.getContext(), "Timeout", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(editButton.getContext(), error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
                dialog.cancel();
                applyButton.setVisibility(View.GONE);
                deleteButton.setVisibility(View.VISIBLE);

                holder.editMode = false;

                fnEdit.setVisibility(View.GONE);
                emailEdit.setVisibility(View.GONE);
                lnEdit.setVisibility(View.GONE);
                phoneEdit.setVisibility(View.GONE);

                fnTextView.setVisibility(View.VISIBLE);
                lnTextView.setVisibility(View.VISIBLE);
                emailTextView.setVisibility(View.VISIBLE);
                phoneTextView.setVisibility(View.VISIBLE);

                editImage.setImageResource(R.drawable.ic_pencil);
            }) {
                @Override
                public byte[] getBody() {
                    Gson gson = new Gson();
                    String body = gson.toJson(emergencyContact);
                    return body.getBytes();
                }
            };
            dialog = ProgressDialog.show(editButton.getContext(), "",
                    "Loading. Please wait...", true);

            requestQueue.add(req);
        });
        editButton.setOnClickListener(v -> {
            //Show edit views
            if (holder.editMode) {
                applyButton.setVisibility(View.GONE);
                deleteButton.setVisibility(View.VISIBLE);

                holder.editMode = false;

                fnEdit.setVisibility(View.GONE);
                emailEdit.setVisibility(View.GONE);
                lnEdit.setVisibility(View.GONE);
                phoneEdit.setVisibility(View.GONE);

                fnTextView.setVisibility(View.VISIBLE);
                lnTextView.setVisibility(View.VISIBLE);
                emailTextView.setVisibility(View.VISIBLE);
                phoneTextView.setVisibility(View.VISIBLE);

                editImage.setImageResource(R.drawable.ic_pencil);

            } else { // hide edit views
                applyButton.setVisibility(View.VISIBLE);
                deleteButton.setVisibility(View.GONE);
                holder.editMode = true;

                fnTextView.setVisibility(View.GONE);
                lnTextView.setVisibility(View.GONE);
                emailTextView.setVisibility(View.GONE);
                phoneTextView.setVisibility(View.GONE);

                fnEdit.setVisibility(View.VISIBLE);
                emailEdit.setVisibility(View.VISIBLE);
                lnEdit.setVisibility(View.VISIBLE);
                phoneEdit.setVisibility(View.VISIBLE);

                editImage.setImageResource(R.drawable.ic_close);
            }

        });
        deleteButton.setOnClickListener(v -> new AlertDialog.Builder(emailTextView.getContext())
                .setTitle("Delete family member")
                .setMessage("Are you sure you want to delete this family member?")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface _dialog, int which) {
                        RequestQueue requestQueue = Volley.newRequestQueue(emailTextView.getContext());;
                        String url = HOST + "/emergencyContacts/deleteById?id=" + emergencyContact.getId();
                        JsonObjectRequest req = new JsonObjectRequest(Request.Method.DELETE, url,
                                null, response -> {
                            dialog.cancel();
                            int index = emergencyContactList.indexOf(emergencyContact);
                            notifyItemRemoved(index);
                            emergencyContactList.remove(emergencyContact);
                            notifyDataSetChanged();
                        }, error -> {
                            if (error instanceof TimeoutError) {
                                Toast.makeText(editButton.getContext(), "Timeout", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(editButton.getContext(), error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                            dialog.cancel();
                        });
                        dialog = ProgressDialog.show(editButton.getContext(), "",
                                "Loading. Please wait...", true);

                        requestQueue.add(req);
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show()
        );

    }

    @Override
    public int getItemCount() {
        return emergencyContactList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView fnTextView;
        public TextView lnTextView;
        public TextView emailTextView;
        public TextView phoneTextView;

        public EditText fnEditText;
        public EditText lnEditText;
        public EditText emailEditText;
        public EditText phoneEditText;

        public FloatLabeledEditText fnEdit;
        public FloatLabeledEditText lnEdit;
        public FloatLabeledEditText emailEdit;
        public FloatLabeledEditText phoneEdit;

        public CardView editButton;
        public CardView deleteButton;
        public CardView applyButton;
        public ImageView editImage;

        public boolean editMode;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            fnTextView =  itemView.findViewById(R.id.ec_first_name);
            lnTextView = itemView.findViewById(R.id.ec_last_name);
            emailTextView = itemView.findViewById(R.id.ec_email);
            phoneTextView = itemView.findViewById(R.id.ec_phone);

            fnEditText = itemView.findViewById(R.id.ec_fn_edit_text);
            lnEditText = itemView.findViewById(R.id.ec_ln_edit_text);
            emailEditText = itemView.findViewById(R.id.ec_email_edit_text);
            phoneEditText = itemView.findViewById(R.id.ec_phone_edit_text);

            fnEdit = itemView.findViewById(R.id.ec_fn_edit);
            lnEdit = itemView.findViewById(R.id.ec_ln_edit);
            emailEdit = itemView.findViewById(R.id.ec_email_edit);
            phoneEdit = itemView.findViewById(R.id.ec_phone_edit);

            editButton = itemView.findViewById(R.id.ec_edit_icon);
            deleteButton = itemView.findViewById(R.id.ec_delete_icon);
            applyButton = itemView.findViewById(R.id.ec_apply_icon);
            editImage = itemView.findViewById(R.id.ec_edit_image);

            editMode = false;

        }
    }
}