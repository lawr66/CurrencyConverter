package com.example.currencycconverter;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.currencycconverter.data.DatabaseHandler;
import com.example.currencycconverter.model.Currency;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import currencyconverter.R;

public class Converter extends AppCompatActivity {

    final DatabaseHandler db = new DatabaseHandler(Converter.this);
    double buyvalfrom;
    double buyvalto;
    double result;
    String name = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_converter);

        EditText et = findViewById(R.id.valueFrom);
        Spinner spinnerfrom = findViewById(R.id.spnrfrom);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.currenciues, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerfrom.setAdapter(adapter);

        Spinner spinnerto = findViewById(R.id.spnrto);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerto.setAdapter(adapter);

        List<Currency> valute = db.getAllCurrencies();
        int nr_valute = db.getCount();
        String buy = "";
        for (int i = nr_valute-23; i < nr_valute; i++) {
            if(!String.valueOf(valute.get(i).getBuy()).equals(" ")) buy = buy + String.valueOf(valute.get(i).getBuy()) + " ";
        }
        String[] buyvalue = buy.split(" ");


        spinnerfrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedtext = parent.getSelectedItem().toString();
                if(!selectedtext.equals("RON"))buyvalfrom = Double.valueOf(buyvalue[position]);
                else if(selectedtext.equals("JPY") || selectedtext.equals("HUF") || selectedtext.equals("RUB")) buyvalfrom = Double.valueOf(buyvalue[position])*100;
                else buyvalfrom = Double.valueOf("1");
                System.out.println(buyvalfrom);
                System.out.println(parent.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedtext = parent.getSelectedItem().toString();
                if(!selectedtext.equals("RON")) buyvalto = Double.valueOf(buyvalue[position]);
                else if(selectedtext.equals("JPY") || selectedtext.equals("HUF") || selectedtext.equals("RUB")) buyvalto = Double.valueOf(buyvalue[position])*100;
                else buyvalto = Double.valueOf("1");
                name = selectedtext;
                System.out.println(buyvalto);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        TextView tvresult = findViewById(R.id.textView3);
        Button btnconvert = findViewById(R.id.btnConvert);
        btnconvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DecimalFormat precision = new DecimalFormat("0.0000");
                System.out.println(String.valueOf(et.getText()));
                System.out.println(buyvalfrom);
                System.out.println(buyvalto);
                result = Double.valueOf(String.valueOf(et.getText()))*buyvalfrom/buyvalto;
                tvresult.setText("Result: " + precision.format(result) + "  " + name);
                result = 0;
            }
        });
    }
}