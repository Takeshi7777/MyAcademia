package com.taki.myacademia1;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;


public class AddNotices extends AppCompatActivity {

    Spinner Stream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notices);
        Stream      = findViewById(R.id.spinner);

        ArrayAdapter<String> Myadap1 = new ArrayAdapter<String>(AddNotices.this,android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.streamdrop));
        Myadap1.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        Stream.setAdapter(Myadap1);
    }

}
