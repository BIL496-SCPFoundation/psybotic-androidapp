package com.scpfoundation.psybotic.app.ui.profile;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.scpfoundation.psybotic.app.R;
import com.scpfoundation.psybotic.app.data.FamilyMember;
import com.wrapp.floatlabelededittext.FloatLabeledEditText;

import org.json.JSONObject;

import java.util.List;

// Create the basic adapter extending from RecyclerView.Adapter
// Note that we specify the custom ViewHolder which gives us access to our views
public class FamilyMemberAdapter extends RecyclerView.Adapter<FamilyMemberAdapter.ViewHolder>{
    private static final String HOST = "https://limitless-lake-96203.herokuapp.com";
    private ProgressDialog dialog;

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView firstNameTextView;
        public TextView lastNameTextView;
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

        public ImageView editImage;

        public boolean editMode;
        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            firstNameTextView =  itemView.findViewById(R.id.family_member_first_name);
            lastNameTextView = itemView.findViewById(R.id.family_member_last_name);
            emailTextView = itemView.findViewById(R.id.family_member_email);
            phoneTextView = itemView.findViewById(R.id.family_member_phone);

            fnEditText = itemView.findViewById(R.id.fm_fn_edit_text);
            lnEditText = itemView.findViewById(R.id.fm_ln_edit_text);
            emailEditText = itemView.findViewById(R.id.fm_email_edit_text);
            phoneEditText = itemView.findViewById(R.id.fm_phone_edit_text);

            fnEdit = itemView.findViewById(R.id.fm_fn_edit);
            lnEdit = itemView.findViewById(R.id.fm_ln_edit);
            emailEdit = itemView.findViewById(R.id.fm_email_edit);
            phoneEdit = itemView.findViewById(R.id.fm_phone_edit);

            editButton = itemView.findViewById(R.id.fm_edit_icon);
            deleteButton = itemView.findViewById(R.id.fm_delete_icon);

            editImage = itemView.findViewById(R.id.fm_edit_image);

            editMode = false;
        }
    }

    private List<FamilyMember> familyMemberList;

    public FamilyMemberAdapter(List<FamilyMember> familyMemberList) {
        this.familyMemberList = familyMemberList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.fragment_family_member, parent, false);
        return new ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        final FamilyMember familyMember = familyMemberList.get(position);

        final TextView fnTextView = holder.firstNameTextView;
        fnTextView.setText(familyMember.getFirstName() == null ? "Name unknown" : familyMember.getFirstName());
        final TextView lnTextView = holder.lastNameTextView;
        lnTextView.setText(familyMember.getLastName() == null ? "Last name unknown" : familyMember.getLastName());
        final TextView emailTextView = holder.emailTextView;
        emailTextView.setText(familyMember.getEmail() == null ? "Email unknown" : familyMember.getEmail());
        final TextView phoneTextView = holder.phoneTextView;
        phoneTextView.setText(familyMember.getPhone() == null ? "Phone unknown" : familyMember.getPhone());

        EditText fnEditText = holder.fnEditText;
        fnEditText.setText(familyMember.getFirstName());
        EditText lnEditText = holder.lnEditText;
        lnEditText.setText(familyMember.getLastName());
        EditText emailEditTex = holder.emailEditText;
        emailEditTex.setText(familyMember.getEmail());
        EditText phoneEditText = holder.phoneEditText;
        phoneEditText.setText(familyMember.getPhone());

        final FloatLabeledEditText fnEdit = holder.fnEdit;
        final FloatLabeledEditText lnEdit = holder.lnEdit;
        final FloatLabeledEditText emailEdit = holder.emailEdit;
        final FloatLabeledEditText phoneEdit = holder.phoneEdit;

        final ImageView editImage = holder.editImage;

        final boolean editMode = holder.editMode;

        final CardView editButton = holder.editButton;
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Show edit views
                if (holder.editMode) {
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


            }
        });
        CardView deleteButton = holder.deleteButton;
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(emailTextView.getContext())
                        .setTitle("Delete family member")
                        .setMessage("Are you sure you want to delete this family member?")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface _dialog, int which) {
                                RequestQueue requestQueue = Volley.newRequestQueue(emailTextView.getContext());;
                                String url = HOST + "/familyMembers/deleteById?id=" + familyMember.getId();
                                JsonObjectRequest req = new JsonObjectRequest(Request.Method.DELETE, url,
                                        null, new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        dialog.cancel();
                                        familyMemberList.remove(familyMember);
                                        notifyItemRemoved(position);
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        if (error instanceof TimeoutError) {
                                            Toast.makeText(editButton.getContext(), "Timeout", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(editButton.getContext(), error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                        dialog.cancel();
                                    }
                                });
                                dialog = ProgressDialog.show(editButton.getContext(), "",
                                        "Loading. Please wait...", true);

                                requestQueue.add(req);
                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return familyMemberList.size();
    }

}
