package com.example.android_bannani_mohamed_fares_loumi_mohamed.ui.logOut;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.android_bannani_mohamed_fares_loumi_mohamed.HomeActivity;
import com.example.android_bannani_mohamed_fares_loumi_mohamed.R;


public class LogOutFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_log_out, container, false);
        getActivity().finish();
        Intent intent = new Intent(getActivity(), HomeActivity.class);
        startActivity(intent);
        return root;
    }
}