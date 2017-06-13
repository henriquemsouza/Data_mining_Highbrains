package com.example.guilherme.consumirapi;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ListViewActivity extends AppCompatActivity {

    Button buttonBox2;

    private String TAG = ListViewActivity.class.getSimpleName();
    private ListView lv;

    ArrayList<HashMap<String, String>> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        itemList = new ArrayList<>();
        lv = (ListView) findViewById(R.id.list);
        buttonBox2 = (Button)findViewById(R.id.buttonBox2);

        String url1 = "http://monitwi.herokuapp.com/monitoramentos/1/1.json";
        new GetItens().execute(url1);
    }

    private class GetItens extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            Toast.makeText(ListViewActivity.this, "Json Data is downloading", Toast.LENGTH_LONG).show();
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
                        final String quali = resultado.getString("quali");
                        final String data_pub = resultado.getString("data_pub");
                        final String texto = resultado.getString("texto");

                        HashMap<String, String> item = new HashMap<>();
                        item.put("id", id);
                        item.put("quali", quali);
                        item.put("data_pub", data_pub);
                        item.put("texto", texto);

                        itemList.add(item);
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
            ListAdapter adapter = new SimpleAdapter(ListViewActivity.this, itemList, R.layout.list_item, new String[]{"nome_twi", "quali", "data_pub", "texto"}, new int[]{R.id.nome_twi, R.id.quali, R.id.texto, R.id.data_pub});
            lv.setAdapter(adapter);
        }
    }


}
