package com.example.currencycconverter;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableRow;
import android.widget.TextView;

import org.w3c.dom.Text;

import currencyconverter.R;


public class Vizualizare extends AppCompatActivity {
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_vizualizare);

        Bundle extra = getIntent().getExtras();
        final ScrollView lista=findViewById(R.id.scrollView2);

        int i=0;

        if (extra != null) {
            String s1=extra.getString("date");

            //se despart datele in functie de ";"
            String[] s2=s1.split("\\;");

            while (i<s2.length){
                String text= "  ";
                text=text+s2[i] + "\n ----------------------------------------------------------";
                LinearLayout ll = (LinearLayout) findViewById(R.id.linear);
                TextView tv1 = new TextView(this);
                tv1.setText(text);
                ll.addView(tv1);
                i++;
            }
        }

    }
}