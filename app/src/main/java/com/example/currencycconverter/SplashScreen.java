package com.example.currencycconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;


import com.example.currencycconverter.data.DatabaseHandler;
import com.example.currencycconverter.model.Currency;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;

import currencyconverter.R;

public class SplashScreen extends AppCompatActivity{

    public String val = "";
    public String[] currencies;
    public String nume = "";
    public String[] numecomplet;
    public String valori = "";
    public String[] valorile;
    public ArrayList<String> buy = new ArrayList<String>();
    public ArrayList<String> sell = new ArrayList<String>();
    public Instant instant = null;
    public ZoneId zoneId = null;
    public ZonedDateTime zdt = null;
    public String time;
    final DatabaseHandler db = new DatabaseHandler(SplashScreen.this);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().setHomeButtonEnabled(true);

        webscrape ws = new webscrape();
        ws.execute();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

            @Override
            public void run() {


                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(intent);

                // close this activity
                finish();
            }
        }, 4000);





    }

    private class webscrape extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                //Connect to website

                Document doc = Jsoup.connect("https://www.luxor-exchange.ro/arad").get();
                Elements body = doc.select("tbody");
                for (Element valuta : body) {
                    val = valuta.select("td.nowrap strong").text();
                    nume = valuta.select("span.is-hidden-mobile").text().replaceAll("- ", "2").replace("2Euro", "Euro");
                    valori = valuta.select("td.has-text-right-mobile").text().replaceAll(" Lei", "").replaceAll(",", ".");
                }

        } catch(Exception e)

        {
            e.printStackTrace();
            Intent intent = new Intent(SplashScreen.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
            return null;
    }


        @Override
        protected void onPostExecute(Void aVoid) {

            super.onPostExecute(aVoid);

         //   db.deleteCurrency();

            currencies = val.split(" ");


            numecomplet = nume.split("2");
            valorile = valori.split(" ");


            for (int i = 0; i < valorile.length; i++) {
                if (i % 2 == 0) buy.add(valorile[i]);
                else sell.add(valorile[i]);
            }

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                instant = Instant.now();
            }

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                zoneId = ZoneId.of("Europe/Bucharest");
            }

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                zdt = ZonedDateTime.ofInstant(instant, zoneId);
            }

            time = zdt.toString().replaceAll("T", " ");

            Currency a = new Currency(0,time.substring(0,19)," ", " ", " ");
            db.addCurrency(a);

            setContentView(R.layout.activity_splash_screen);
            for (int i = 0; i < buy.size(); i++) {
                Currency curr = new Currency(i, currencies[i], numecomplet[i], buy.get(i), sell.get(i));
            /*    curr.setAbbreviation(currencies[i]);
                curr.setName(numecomplet[i]);
                curr.setBuy(buy.get(i));
                curr.setSell(sell.get(i));*/
                db.addCurrency(curr);
            }
        }
    }
    //}
}