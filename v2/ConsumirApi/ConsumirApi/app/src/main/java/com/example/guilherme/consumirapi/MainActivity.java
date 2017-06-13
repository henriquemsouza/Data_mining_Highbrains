package com.example.guilherme.consumirapi;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.os.StrictMode;

import android.content.Intent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    EditText genreName;
    Button buttonBox;
    Button buttonBox2;
    Button buttonBox3;
    Button buttonBox4;
    Button buttonBox5;
    AlertDialog.Builder dialog;
    private String TAG = MainActivity.class.getSimpleName();
    private ListView lv;
    private ListView lv2;

    ArrayList<HashMap<String, String>> genreList;
    ArrayList<HashMap<String, String>> monitList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        genreList = new ArrayList<>();
        monitList = new ArrayList<>();
        lv = (ListView) findViewById(R.id.list);
        //
        getSupportActionBar().setTitle("Data Brains");
        ActionBar actionBar = getSupportActionBar();
        //

        genreName = (EditText)findViewById(R.id.textBox);
        buttonBox = (Button)findViewById(R.id.buttonBox);
        buttonBox2 = (Button)findViewById(R.id.buttonBox2);
        buttonBox3 = (Button)findViewById(R.id.buttonBox3);
        buttonBox4 = (Button)findViewById(R.id.buttonBox4);
        buttonBox5 = (Button)findViewById(R.id.buttonBox5);

        GetMonits mGetMonits;



        String url1 = "http://monitwi.herokuapp.com/monitoramentos/1.json";
        mGetMonits = new GetMonits();


        mGetMonits.execute(url1);
    }
    //
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_proximo:
                // Toast.makeText(this, "Menu Item 1 selected", Toast.LENGTH_SHORT).show();
                dialog=new AlertDialog.Builder(MainActivity.this);//CRIAR NOVO
                dialog.setTitle("About");
                dialog.setMessage("Desenvolvido por High Brains \nFrom the best to the best\n2017©");
                dialog.setIcon(android.R.drawable.ic_menu_info_details);
                dialog.create();
                dialog.show();


                break;

        }

        return true;
    }
    //

    private class GetGenres extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            Toast.makeText(MainActivity.this, "Json Data is downloading", Toast.LENGTH_LONG).show();
        }

        @Override
        protected Void doInBackground(String... params)
        {
            HttpHandler sh = new HttpHandler();
            String url = params[0];
            final String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null)
            {
                try
                {
                    final JSONObject jsonObj = new JSONObject(jsonStr);
                    final JSONArray results = jsonObj.getJSONArray("results");
                    genreList.clear();
                    for (int i = 0; i < results.length(); i++) {
                        final JSONObject resultado = results.getJSONObject(i);
                        final String nome_twi = resultado.getString("nome_twi");
                        final String quali = resultado.getString("quali");
                        final String data_pub = resultado.getString("data_pub");
                        final String texto = resultado.getString("texto");
                        HashMap<String, String> genre = new HashMap<>();
                        genre.put("nome_twi", nome_twi);
                        genre.put("quali", quali);
                        genre.put("data_pub", data_pub);
                        genre.put("texto", texto);

                        genreList.add(genre);
                    }
                }
                catch (final JSONException e)
                {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Json parsing error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
            else
            {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Couldn't get json from server. Check LogCat" + jsonStr, Toast.LENGTH_LONG).show();
                    }
                });
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            ListAdapter adapter = new SimpleAdapter(MainActivity.this, genreList, R.layout.list_item, new String[]{"nome_twi", "quali", "data_pub", "texto"}, new int[]{R.id.nome_twi, R.id.quali, R.id.texto, R.id.data_pub});
            lv.setAdapter(adapter);
        }
    }

    private class GetMonits extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            Toast.makeText(MainActivity.this, "Checking", Toast.LENGTH_LONG).show();
        }

        @Override
        protected Void doInBackground(String... params)
        {
            HttpHandler sh = new HttpHandler();
            String url = params[0];
            final String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null)
            {
                try
                {
                    final JSONObject jsonObj = new JSONObject(jsonStr);
                    final JSONArray results = jsonObj.getJSONArray("results");
                    for (int i = 0; i < results.length(); i++) {
                        final JSONObject resultado = results.getJSONObject(i);
                        final String id = resultado.getString("id");
                        final String palavra = resultado.getString("palavra");

                        HashMap<String, String> monit = new HashMap<>();
                        monit.clear();

                        monit.put("id", id);
                        monit.put("palavra", palavra);

                        monitList.clear();
                        monitList.add(monit);
                    }
                }
                catch (final JSONException e)
                {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Json parsing error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
            else
            {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Couldn't get json from server. Check LogCat" + jsonStr, Toast.LENGTH_LONG).show();
                    }
                });
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            ListAdapter adapter = new SimpleAdapter(MainActivity.this, monitList, R.layout.list_item, new String[]{"id", "palavra"}, new int[]{R.id.data_pub, R.id.quali});
            lv.setAdapter(adapter);
        }
    }

    public  void itemListView(View v)
    {
        lv.setAdapter(null);
        buttonBox.setVisibility(View.INVISIBLE);
        buttonBox2.setVisibility(View.VISIBLE);
        buttonBox3.setVisibility(View.VISIBLE);
        buttonBox4.setVisibility(View.VISIBLE);
        buttonBox5.setVisibility(View.VISIBLE);
        String url2 = "http://monitwi.herokuapp.com/monitoramentos/1/1.json";
        GetGenres mGetGenres;
        mGetGenres = new GetGenres();
        mGetGenres.execute(url2);
    }

    public void itemListViewSemanal(View v)
    {
        lv.setAdapter(null);
        String url3 = "http://monitwi.herokuapp.com/monitoramentos/item/semanal/1/1.json";
        GetGenres mGetGenres;
        mGetGenres = new GetGenres();
        mGetGenres.execute(url3);
    }

    public void itemListViewMensal (View v)
    {
        lv.setAdapter(null);
        String url4 = "http://monitwi.herokuapp.com/monitoramentos/item/mensal/1/1.json";
        GetGenres mGetGenres;
        mGetGenres = new GetGenres();
        mGetGenres.execute(url4);
    }

    public void monitsList (View v)
    {
        lv.setAdapter(null);
        buttonBox.setVisibility(View.VISIBLE);
        buttonBox2.setVisibility(View.INVISIBLE);
        buttonBox3.setVisibility(View.INVISIBLE);
        buttonBox4.setVisibility(View.INVISIBLE);
        buttonBox5.setVisibility(View.INVISIBLE);
        String url1 = "http://monitwi.herokuapp.com/monitoramentos/1.json";
        GetMonits m2GetMonits;
        m2GetMonits = new GetMonits();
        m2GetMonits.execute(url1);
    }



    private class PostGenre extends AsyncTask<String, Void, Void>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            Toast.makeText(MainActivity.this, "Json Data is posting", Toast.LENGTH_LONG).show();
        }
        @Override
        protected Void doInBackground(String... name)
        {
            HttpHandler sh = new HttpHandler();
            String resposta = "";

            String url = "http://aptwiapi.herokuapp.com/aptwi/";
            try
            {
                resposta = sh.requestPost(url, name[0]);
            }
            catch (final Exception e)
            {
                Log.e(TAG, "Couldn't get json from server." + e.toString());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Couldn't get json from server. Check LogCat" + e.toString(), Toast.LENGTH_LONG).show();
                    }
                });
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);
            Toast.makeText(MainActivity.this, "Json Data is finished", Toast.LENGTH_LONG).show();
        }
    }
}
