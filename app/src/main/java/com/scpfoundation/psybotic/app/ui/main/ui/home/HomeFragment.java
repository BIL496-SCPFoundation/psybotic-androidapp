package com.scpfoundation.psybotic.app.ui.main.ui.home;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.scpfoundation.psybotic.app.R;
import com.scpfoundation.psybotic.app.ui.main.MainActivity;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MainActivity activity = (MainActivity) getActivity();
        GoogleSignInAccount account = activity.getAccount();
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        textView.setText(account.getDisplayName());
//        homeViewModel.getData().observe(getViewLifecycleOwner(), new Observer<GoogleSignInAccount>() {
//            @Override
//            public void onChanged(@Nullable GoogleSignInAccount account) {
//                if (account != null) {
//                    textView.setText(account.getDisplayName());
//                } else {
//                    textView.setText("Guest Mode");
//                }
//            }
//        });
        return root;
    }
}