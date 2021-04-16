package com.scpfoundation.psybotic.app.ui.psychologychat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.scpfoundation.psybotic.app.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Arrays;


public class PsychologistsNames extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_psychologists_names);
        String body = (String) getIntent().getExtras().get("psychologists");
        LinearLayout ln = findViewById(R.id.all_screen);
        Context c = this.getApplicationContext();
        int cardview_count = 0;
        try {
            JSONArray arr = new JSONArray(body);
            LinearLayout templinearlayout;
            for(int i = 0; i < arr.length(); i++){
                //System.out.println(arr.length() + " BANGA  "+arr.getJSONObject(i));
                if(cardview_count % 2 == 0) {
                    LinearLayout newlayout = new LinearLayout(this.getApplicationContext());
                    newlayout.setOrientation(LinearLayout.HORIZONTAL);
                    newlayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.
                            LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    newlayout.layout(0,20,0,0);
                    CardView cardView = new CardView(this.getApplicationContext());
                    CardView.MarginLayoutParams p = (CardView.MarginLayoutParams) cardView.getLayoutParams();
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)
                            cardView.getLayoutParams();
                    p.setMarginStart(20);
                    params.weight = 0.3f;
                    cardView.setLayoutParams(p);
                    cardView.setLayoutParams(params);
                    cardView.getLayoutParams().width=0;
                    cardView.getLayoutParams().height=150;
                    cardView.setClickable(true);
                    cardView.setFocusable(true);
                    cardView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(c, PsychologyInfo.class);
                            CardView cardView = (CardView) v;
                            TextView textView = (TextView) ((LinearLayout) cardView.
                                    getChildAt(0)).getChildAt(1);
                            intent.putExtra("psychology_name", textView.getText().toString());
                            startActivity(intent);
                        }
                    });
                    cardView.setRadius(50);



                }
                else{

                }

                cardview_count++;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void onClick(View v) {
        Intent intent = new Intent(this.getApplicationContext(), PsychologyInfo.class);
        CardView cardView = (CardView) v;
        TextView textView = (TextView) ((LinearLayout) cardView.getChildAt(0)).getChildAt(1);
        intent.putExtra("psychology_name", textView.getText().toString());
        startActivity(intent);
    }
}