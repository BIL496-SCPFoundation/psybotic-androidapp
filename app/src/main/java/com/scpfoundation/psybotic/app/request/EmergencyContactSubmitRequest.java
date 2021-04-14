package com.scpfoundation.psybotic.app.request;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.scpfoundation.psybotic.app.data.EmergencyContact;
import com.scpfoundation.psybotic.app.data.FamilyMember;
import com.scpfoundation.psybotic.app.ui.profile.EmergencyContactAdapter;
import com.scpfoundation.psybotic.app.ui.profile.FamilyMemberAdapter;

import org.json.JSONObject;

public class EmergencyContactSubmitRequest
    implements View.OnClickListener, Response.ErrorListener, Response.Listener<JSONObject>{

    private EmergencyContact emergencyContact;
    private Context context;
    private Dialog dialog;
    private final String HOST = "https://limitless-lake-96203.herokuapp.com";
    private RequestQueue requestQueue;
    private ConstraintLayout submitDialog;
    private EmergencyContactAdapter adapter;

    public EmergencyContactSubmitRequest(EmergencyContact emergencyContact,
                                     Context context,
                                     ConstraintLayout submitDialog,
                                         EmergencyContactAdapter adapter) {

        this.emergencyContact = emergencyContact;
        this.context = context;
        this.submitDialog = submitDialog;
        this.adapter = adapter;
        requestQueue = Volley.newRequestQueue(this.context);
    }


    private void submit() {
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST,
                HOST + "/emergencyContacts/insert",null, this, this )
        {
            @Override
            public byte[] getBody() {
                Gson gson = new Gson();
                return gson.toJson(emergencyContact).getBytes();
            }
        };
        dialog = ProgressDialog.show(this.context, "",
                "Loading. Please wait...", true);
        requestQueue.add(req);
    }


    @Override
    public void onClick(View v) {
        this.submit();
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast toast = Toast.makeText(this.context, error.getMessage(), Toast.LENGTH_SHORT);
        toast.show();
        dialog.cancel();

    }

    @Override
    public void onResponse(JSONObject response) {
        Toast toast = Toast.makeText(this.context, "Emergency contact added", Toast.LENGTH_SHORT);
        toast.show();
        dialog.cancel();
        adapter.insert(emergencyContact);
        this.submitDialog.setVisibility(View.GONE);

    }

    public void setAdapter(EmergencyContactAdapter adapter) {
        this.adapter = adapter;
    }

}
