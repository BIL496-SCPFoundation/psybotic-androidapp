package com.scpfoundation.psybotic.app.ui.psychologychat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
            LinearLayout templinearlayout = null;
            for(int i = 0; i < arr.length(); i++){
                if(cardview_count % 2 == 0) {
                    LinearLayout newlayout = new LinearLayout(this.getApplicationContext());
                    newlayout.setOrientation(LinearLayout.HORIZONTAL);
                    newlayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.
                            LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    newlayout.layout(0,20,0,0);
                    CardView cardView = new CardView(this.getApplicationContext());


                    cardView.setLayoutParams(new LinearLayout.LayoutParams(0, 150,
                            0.3f));
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

                    LinearLayout innLayout = new LinearLayout(c);
                    innLayout.setPadding(0,0,0,20);
                    innLayout.setOrientation(LinearLayout.VERTICAL);
                    innLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.
                            LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));

                    ImageView imageView = new ImageView(c);
                    imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.
                            LayoutParams.MATCH_PARENT,0,0.8f));

                    TextView textView = new TextView(c);
                    imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.
                            LayoutParams.MATCH_PARENT,0,0.2f));
                    textView.setGravity(Gravity.CENTER);
                    textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    textView.setTextSize(15);
                    textView.setText(("Dr."+arr.getJSONObject(i).getString("firstName")+" "+
                            arr.getJSONObject(i).get("lastName")));

                    innLayout.addView(imageView);
                    innLayout.addView(textView);
                    cardView.addView(innLayout);
                    newlayout.addView(cardView);
                    templinearlayout = newlayout;

                }
                else{
                    CardView cardView = new CardView(this.getApplicationContext());

                    cardView.setLayoutParams(new LinearLayout.LayoutParams(0, 150,
                            0.3f));
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

                    LinearLayout innLayout = new LinearLayout(c);
                    innLayout.setPadding(0,0,0,20);
                    innLayout.setOrientation(LinearLayout.VERTICAL);
                    innLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.
                            LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));

                    ImageView imageView = new ImageView(c);
                    imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.
                            LayoutParams.MATCH_PARENT,0,0.8f));

                    TextView textView = new TextView(c);
                    imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.
                            LayoutParams.MATCH_PARENT,0,0.2f));
                    textView.setGravity(Gravity.CENTER);
                    textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    textView.setTextSize(15);
                    textView.setText(("Dr."+arr.getJSONObject(i).getString("firstName")+" "+
                            arr.getJSONObject(i).get("lastName")));

                    innLayout.addView(imageView);
                    innLayout.addView(textView);
                    cardView.addView(innLayout);
                    templinearlayout.addView(cardView);
                    ln.addView(templinearlayout);

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