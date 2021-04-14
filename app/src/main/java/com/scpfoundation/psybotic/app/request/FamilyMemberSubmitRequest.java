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
import com.scpfoundation.psybotic.app.data.FamilyMember;
import com.scpfoundation.psybotic.app.ui.profile.FamilyMemberAdapter;

import org.json.JSONObject;

public class FamilyMemberSubmitRequest
        implements View.OnClickListener, Response.ErrorListener, Response.Listener<JSONObject> {

    private FamilyMember familyMember;
    private Context context;
    private Dialog dialog;
    private final String HOST = "https://limitless-lake-96203.herokuapp.com";
    private RequestQueue requestQueue;
    private ConstraintLayout submitDialog;
    private FamilyMemberAdapter adapter;

    private void submit() {
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST,
                HOST + "/familyMembers/insert",null, this, this )
        {
            @Override
            public byte[] getBody() {
                Gson gson = new Gson();
                return gson.toJson(familyMember).getBytes();
            }
        };
        dialog = ProgressDialog.show(this.context, "",
                "Loading. Please wait...", true);
        requestQueue.add(req);
    }

    public FamilyMemberSubmitRequest(FamilyMember familyMember,
                                     Context context,
                                     ConstraintLayout submitDialog,
                                     FamilyMemberAdapter adapter) {

        this.familyMember = familyMember;
        this.context = context;
        this.submitDialog = submitDialog;
        this.adapter = adapter;
        requestQueue = Volley.newRequestQueue(this.context);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast toast = Toast.makeText(this.context, error.getMessage(), Toast.LENGTH_SHORT);
        toast.show();
        dialog.cancel();
    }

    @Override
    public void onResponse(JSONObject response) {
        Toast toast = Toast.makeText(this.context, "Family member added", Toast.LENGTH_SHORT);
        toast.show();
        dialog.cancel();
        adapter.insert(familyMember);
        this.submitDialog.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        submit();
    }

    public void setAdapter(FamilyMemberAdapter adapter) {
        this.adapter = adapter;
    }
}
