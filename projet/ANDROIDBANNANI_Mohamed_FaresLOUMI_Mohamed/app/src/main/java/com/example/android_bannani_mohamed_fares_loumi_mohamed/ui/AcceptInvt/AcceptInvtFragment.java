package com.example.android_bannani_mohamed_fares_loumi_mohamed.ui.AcceptInvt;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;


import com.example.android_bannani_mohamed_fares_loumi_mohamed.R;
import com.example.android_bannani_mohamed_fares_loumi_mohamed.SharedViewModel;
import com.example.android_bannani_mohamed_fares_loumi_mohamed.domain.Rdv;
import com.example.android_bannani_mohamed_fares_loumi_mohamed.repository.MySqlLiteDB;
import com.lamudi.phonefield.PhoneEditText;

import java.util.Calendar;
import java.util.List;

public class AcceptInvtFragment extends Fragment implements View.OnClickListener {



    MySqlLiteDB dB;
    String name;
    private SharedViewModel viewModel;
    private PhoneEditText phoneEditText;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private EditText editTextDate;
    private EditText laltitude;
    private EditText longitude;
    SmsManager sms = SmsManager.getDefault();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_accept_rdv, container, false);


        name = getActivity().getIntent().getExtras().getString("username");
        phoneEditText = (PhoneEditText) root.findViewById(R.id.editTextPhoneNumber);
        phoneEditText.setDefaultCountry("FR");
        editTextDate = root.findViewById(R.id.editTextDate);
        laltitude = root.findViewById(R.id.idTxtLatitude);
        longitude = root.findViewById(R.id.idLongitude);
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = day + "/" + month + "/" + year;
                editTextDate.setText(date);
            }
        };

        return root;
    }

    public void onClickAccept(View view){
        if (!phoneEditText.isValid()) {
            Toast.makeText(getActivity(),"Invalid Phone Number !!",Toast.LENGTH_SHORT).show();
        }
        else{
            Rdv x=new Rdv(editTextDate.getText().toString(),Double.parseDouble(laltitude.getText().toString()),Double.parseDouble(longitude.getText().toString()),phoneEditText.getPhoneNumber());
            editTextDate.getText().toString();
            sms.sendTextMessage(phoneEditText.getPhoneNumber(), null,  " hi there, "+name+" accept the RDV !"  , null, null);
            (new MySqlLiteDB(getContext())).insertRdv(x);
            Toast.makeText(getActivity(),"Message sen",Toast.LENGTH_SHORT).show();
        }
    }

    public void selectDate(View view) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(getActivity(), android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateSetListener, year, month, day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
        viewModel.getText().observe(getViewLifecycleOwner(), new Observer<CharSequence>() {
            @Override
            public void onChanged(@Nullable CharSequence charSequence) {
                name = charSequence.toString();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.buttonAcceptRDV:
                Toast.makeText(getActivity(),"Invalid Phone Number11 !!",Toast.LENGTH_SHORT).show();
                onClickAccept(v);
            case R.id.editTextDate:
                selectDate(v);
                Toast.makeText(getActivity(),"Invalid Phone Number !!",Toast.LENGTH_SHORT).show();
        }
    }
}