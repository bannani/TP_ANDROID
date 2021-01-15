package com.example.android_bannani_mohamed_fares_loumi_mohamed.ui.account;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.android_bannani_mohamed_fares_loumi_mohamed.Accept_Refuse_RDV;
import com.example.android_bannani_mohamed_fares_loumi_mohamed.MaprRdvActivity;
import com.example.android_bannani_mohamed_fares_loumi_mohamed.R;
import com.example.android_bannani_mohamed_fares_loumi_mohamed.SharedViewModel;
import com.example.android_bannani_mohamed_fares_loumi_mohamed.NewRDVActivity;
import com.lamudi.phonefield.PhoneEditText;


public class AccountFragment extends Fragment {

    private SharedViewModel viewModel;
    String name;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_account, container, false);
        name = getActivity().getIntent().getExtras().getString("username");
        final TextView username = root.findViewById(R.id.username);
        viewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
        viewModel.setText(name);
        final Button rdv = root.findViewById(R.id.buttonShowRDV);
        rdv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), MaprRdvActivity.class);
                i.putExtra("username", name);
                startActivity(i);
            }
        });
        final Button start = root.findViewById(R.id.buttonNewRdv);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), NewRDVActivity.class);
                i.putExtra("username", name);
                startActivity(i);
            }
        });
        final Button accept = root.findViewById(R.id.buttonAcceptRDV);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), Accept_Refuse_RDV.class);
                i.putExtra("username", name);
                startActivity(i);
            }
        });

        username.setText(name);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
    }

}