package com.example.android_bannani_mohamed_fares_loumi_mohamed;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

import com.example.android_bannani_mohamed_fares_loumi_mohamed.domain.Rdv;
import com.example.android_bannani_mohamed_fares_loumi_mohamed.repository.MySqlLiteDB;
import com.lamudi.phonefield.PhoneEditText;

import java.util.Calendar;

public class NewRDVActivity extends Activity implements LocationListener{

    private static final String TAG = NewRDVActivity.class.getSimpleName();
    private static final int REQUEST_CODE = 1;
    private Uri uriContact;
    public TextView txtLat;
    public EditText txtNumber;
    private String contactID;     // contacts unique ID
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    String lat;
    String provider;
    protected String latitude, longitude;
    protected boolean gps_enabled, network_enabled;
    private Rdv rdv=new Rdv();
    private PhoneEditText phoneEditText;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private EditText editTextDate;
    SmsManager sms = SmsManager.getDefault();
    private String username;
    private String localisation;
    MySqlLiteDB db;


    /**
     * Called when the activity is first created.
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db=new MySqlLiteDB(this);
        username = getIntent().getExtras().getString("username");
        localisation = getIntent().getExtras().getString("localisation");
        setContentView(R.layout.activity_new_rdv);
        txtNumber = findViewById(R.id.idTxtNumber);
        txtLat = (TextView) findViewById(R.id.lat);
        phoneEditText = (PhoneEditText) findViewById(R.id.editTextPhoneNumber);
        phoneEditText.setDefaultCountry("FR");
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) this);

        editTextDate = findViewById(R.id.editTextDate);
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = day + "/" + month + "/" + year;
                editTextDate.setText(date);
            }
        };
    }

    @Override
    public void onLocationChanged(Location location) {

        if (localisation!=null){
            String[] tab=localisation.split(",");
            rdv.setLatitude(Float.valueOf(tab[0]));
            rdv.setLongitude(Float.valueOf(tab[1]));
            txtLat.setText("Latitude:" + rdv.getLatitude() + ", Longitude:" + rdv.getLongitude());

        }
        else {
            rdv.setLatitude(location.getLatitude());
            rdv.setLongitude(location.getLongitude());
            txtLat.setText("Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude());
        }
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("Latitude","disable");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("Latitude","enable");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude","status");
    }

    public void onClickSelectContact(View btnSelectContact) {

        // using native contacts selection
        // Intent.ACTION_PICK = Pick an item from the data, returning what was selected.
        startActivityForResult(new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI), REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            Log.d(TAG, "Response: " + data.toString());
            uriContact = data.getData();
            retrieveContactName();
            retrieveContactNumber();
            //retrieveContactPhoto();

        }
    }
/*
    private void retrieveContactPhoto() {

        Bitmap photo = null;

        try {
            InputStream inputStream = ContactsContract.Contacts.openContactPhotoInputStream(getContentResolver(),
                    ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, new Long(contactID)));

            if (inputStream != null) {
                photo = BitmapFactory.decodeStream(inputStream);
                ImageView imageView = (ImageView) findViewById(R.id.img_contact);
                imageView.setImageBitmap(photo);
            }

            assert inputStream != null;
            inputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }*/

    private void retrieveContactNumber() {

        String contactNumber = String.valueOf(txtNumber.getText());

        // getting contacts ID
        Cursor cursorID = getContentResolver().query(uriContact,
                new String[]{ContactsContract.Contacts._ID},
                null, null, null);

        if (cursorID.moveToFirst()) {

            contactID = cursorID.getString(cursorID.getColumnIndex(ContactsContract.Contacts._ID));
        }

        cursorID.close();

        Log.d(TAG, "Contact ID: " + contactID);

        // Using the contact ID now we will get contact phone number
        Cursor cursorPhone = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER},

                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? AND " +
                        ContactsContract.CommonDataKinds.Phone.TYPE + " = " +
                        ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE,

                new String[]{contactID},
                null);

        if (cursorPhone.moveToFirst()) {
            Log.d("rdv1","|"+contactNumber+"|");
            if(contactNumber.length()>2)  contactNumber = contactNumber +";"+ cursorPhone.getString(cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            else contactNumber = cursorPhone.getString(cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
        }

        cursorPhone.close();
        txtNumber.setText(""+contactNumber);

        Log.d(TAG, "Contact Phone Number: " + contactNumber);
    }

    private void retrieveContactName() {

        String contactName = null;

        // querying contact data store
        Cursor cursor = getContentResolver().query(uriContact, null, null, null, null);

        if (cursor.moveToFirst()) {

            // DISPLAY_NAME = The display name for the contact.
            // HAS_PHONE_NUMBER =   An indicator of whether this contact has at least one phone number.

            contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
        }

        cursor.close();

        Log.d(TAG, "Contact Name: " + contactName);

    }
    public void onClickAdd(View view) {
        String contactNumber = String.valueOf(txtNumber.getText());
        if (!phoneEditText.isValid()) {
            Toast.makeText(getApplicationContext(),"Invalid Phone Number !!",Toast.LENGTH_SHORT).show();
        }
        else{
            Log.d("rdv1","|"+contactNumber+"|");
            if(contactNumber!=null)  contactNumber = contactNumber +";"+ phoneEditText.getPhoneNumber();
            else contactNumber = phoneEditText.getPhoneNumber();
            Log.d("rdv2",contactNumber);
            txtNumber.setText(""+contactNumber);
        }

    }


    public void onClickSend(View view) {
        String numeros = txtNumber.getText().toString();
        String[] tableau = numeros.split(";");

        for (int i = 0; i < tableau.length; i++) {
            Rdv x=new Rdv(editTextDate.getText().toString(),rdv.getLatitude(),rdv.getLongitude(),tableau[i]);
            sms.sendTextMessage(tableau[i], null,  " hi there, "+username+" want to meet you on "+editTextDate.getText()  , null, null);
            sms.sendTextMessage(tableau[i], null,  ", please check your app 'Rendez-vous' ! Meeting will take place at "+ txtLat.getText().toString() , null, null);
            db.insertRdv(x);
        }
        Intent i = new Intent(this, MyAccountActivity.class);
        i.putExtra("username", username);
        startActivity(i);

    }

    public void selectDate(View view) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateSetListener, year, month, day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    public void onClickGetFromMap(View view) {
        Intent i = new Intent(this, MapActivity.class);
        i.putExtra("username", username);
        startActivity(i);
    }
}