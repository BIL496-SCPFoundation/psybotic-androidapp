package com.scpfoundation.psybotic.app.listener;

import android.text.Editable;
import android.text.TextWatcher;

import com.scpfoundation.psybotic.app.data.FamilyMember;

public class FamilyMemberSubmitListener {

    FamilyMember familyMember;

    public FamilyMemberSubmitListener(FamilyMember familyMember) {
        this.familyMember = familyMember;
    }

    public class FirstNameListener implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            familyMember.setFirstName(s.toString());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
    public class LastNameListener implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            familyMember.setLastName(s.toString());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
    public class EmailListener implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            familyMember.setEmail(s.toString());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
    public class PhoneListener implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            familyMember.setPhone(s.toString());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
}
