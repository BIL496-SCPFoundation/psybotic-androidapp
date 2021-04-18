package com.scpfoundation.psybotic.app.ui.info;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.scpfoundation.psybotic.app.R;
import com.scpfoundation.psybotic.app.data.Psychologist;
import com.squareup.picasso.Picasso;

public class PsychologistInfoActivity extends AppCompatActivity {

    private TextView tooalbarTitle;
    private ImageView imageView;
    private TextView title;
    private TextView name;
    private TextView expertise;
    private TextView ageOfInterest;
    private TextView highSchool;
    private TextView university;
    private TextView master;
    private TextView bio;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_psychologist_info);
        Psychologist psy = (Psychologist) getIntent().getExtras().get("psychologist");
        imageView = findViewById(R.id.psychologist_image);

        tooalbarTitle = findViewById(R.id.psychologist_info_title);
        tooalbarTitle.setText(psy.getFirstName());
        if (psy.getImageURL() != null && !psy.getImageURL().equals("")) {
            Picasso.get().load(psy.getImageURL()).into(imageView);
        }

        title = findViewById(R.id.title);
        StringBuilder titles = new StringBuilder();
        if (psy.getTitles() != null) {
            for (String title :
                    psy.getTitles()) {
                titles.append(title);
            }
        }
        title.setText(titles.toString());

        name = findViewById(R.id.name);
        String str = psy.getFirstName() + " " + psy.getLastName();
        name.setText(str);

        expertise = findViewById(R.id.expertise);
        expertise.setText(psy.getExpertise());

        ageOfInterest = findViewById(R.id.age_of_interest);
        ageOfInterest.setText(psy.getAgeOfInterest());

        highSchool = findViewById(R.id.high_school);
        university = findViewById(R.id.university);
        master = findViewById(R.id.master);
        if (psy.getEducations() != null) {
            highSchool.setText(psy.getEducations()[0]);
            university.setText(psy.getEducations()[1]);
            master.setText(psy.getEducations()[2]);

        }

        bio = findViewById(R.id.bio);
        bio.setText(psy.getBiography());
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.psychologist_info_back_button:
                finish();
                break;
            default:
                break;
        }
    }
}