package com.example.currencycconverter;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.currencycconverter.data.DatabaseHandler;
import com.example.currencycconverter.model.Currency;

import java.util.List;

import currencyconverter.R;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        Button btnbuy = findViewById(R.id.btnbuy);
        Button btnsell = findViewById(R.id.btnsell);
        Button btndb = findViewById(R.id.btndb);
        Button btnabt = findViewById(R.id.btnAbout);
        final DatabaseHandler db = new DatabaseHandler(MainActivity.this);

        btnbuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Converter.class);
                startActivity(intent);
            }
        });

        btnsell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ConverterSell.class);
                startActivity(intent);
            }
        });

        btndb.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                List<Currency> valute = db.getAllCurrencies();
                Intent intent = new Intent(MainActivity.this, Vizualizare.class);
                int i = 0;
                int nr_valute = db.getCount();
                String s = "";
                for (i = 0; i < nr_valute; i++) {
                    s = s + String.valueOf(valute.get(i).getName()) + " "
                            + String.valueOf(valute.get(i).getAbbreviation()) + " "
                            + String.valueOf(valute.get(i).getBuy()) + " "
                            + String.valueOf(valute.get(i).getSell()) + ";";
                }
                intent.putExtra("date", s);
                startActivity(intent);
            }
        });

        btnabt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,About.class);
                startActivity(intent);
            }
        });
    }
}
//}