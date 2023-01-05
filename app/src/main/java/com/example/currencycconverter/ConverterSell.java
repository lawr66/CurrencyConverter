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
import java.util.List;

import currencyconverter.R;

public class ConverterSell extends AppCompatActivity {

    final DatabaseHandler db = new DatabaseHandler(com.example.currencycconverter.ConverterSell.this);

    double sellvalfrom;
    double sellvalto;
    double result;
    String name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_converter_sell);

        EditText et = findViewById(R.id.valueFrom2);
        Spinner spinnerfrom = findViewById(R.id.spnrfrom2);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.currenciues, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerfrom.setAdapter(adapter);

        Spinner spinnerto = findViewById(R.id.spnrto2);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerto.setAdapter(adapter);

        List<Currency> valute = db.getAllCurrencies();
        int nr_valute = db.getCount();
        String sell = "";
        for (int i = nr_valute-23; i < nr_valute; i++) {
                if(!String.valueOf(valute.get(i).getBuy()).equals(" ")) sell = sell + String.valueOf(valute.get(i).getBuy()) + " ";
            }
        String[] sellvalue = sell.split(" ");


        spinnerfrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedtext = parent.getSelectedItem().toString();
                if (!selectedtext.equals("RON")) sellvalfrom = Double.valueOf(sellvalue[position]);
                else if (selectedtext.equals("JPY") || selectedtext.equals("HUF") || selectedtext.equals("RUB"))
                    sellvalfrom = Double.valueOf(sellvalue[position]) * 100;
                else sellvalfrom = Double.valueOf("1");
                System.out.println(sellvalfrom);
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
                if (!selectedtext.equals("RON")) sellvalto = Double.valueOf(sellvalue[position]);
                else if (selectedtext.equals("JPY") || selectedtext.equals("HUF") || selectedtext.equals("RUB"))
                    sellvalto = Double.valueOf(sellvalue[position]) * 100;
                else sellvalto = Double.valueOf("1");
                name = selectedtext;
                System.out.println(sellvalto);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        TextView tvresult = findViewById(R.id.textView6);
        Button btnconvert = findViewById(R.id.btnConvert2);
        btnconvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DecimalFormat precision = new DecimalFormat("0.0000");
                System.out.println(String.valueOf(et.getText()));
                System.out.println(sellvalfrom);
                System.out.println(sellvalto);
                result = Double.valueOf(String.valueOf(et.getText())) * sellvalfrom / sellvalto;
                tvresult.setText("Result: " + precision.format(result) + "  " + name);
                result = 0;
            }
        });
    }
}
