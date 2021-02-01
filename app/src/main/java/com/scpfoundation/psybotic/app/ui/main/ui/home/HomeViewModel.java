package com.scpfoundation.psybotic.app.ui.main.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<GoogleSignInAccount> mAccount;

    public HomeViewModel() {
        mAccount = new MutableLiveData<>();
        mAccount.setValue(null);
    }

    public void updateData(GoogleSignInAccount account) {
        this.mAccount.setValue(account);
    }

    public LiveData<GoogleSignInAccount> getData() {
        return mAccount;
    }
}