package highbrains.com.databrainsmining;

import android.app.FragmentBreadCrumbs;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class TrendsActivity extends AppCompatActivity {
     public ListView ListaTRENDS;
    String[] list_command;
    FloatingActionButton FAB1;
    //String[] list_desc;
//--------------galera way-----------------------------------------------------------------------------------
    EditText genreName;
    Button buttonBox;

    private String TAG = MainActivity.class.getSimpleName();
    private ListView lv;
    ArrayList<HashMap<String, String>> genreList;
    //--------------galera way-----------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trends);
        //--------------galera way-----------------------------------------------------------------------------------
        genreList = new ArrayList<>();
        lv = (ListView) findViewById(R.id.ListView);

        genreName = (EditText)findViewById(R.id.textBox);
        buttonBox = (Button)findViewById(R.id.buttonBox);

        new GetGenres().execute();
        //--------------galera way-----------------------------------------------------------------------------------

        FAB1=(FloatingActionButton) findViewById(R.id.fbtNewSearch);
        FAB1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TrendsActivity.this, KeyWordActivity.class));
            }
        });

        ActionBar actionBar = getSupportActionBar(); //Sets the name of Action bar
        getSupportActionBar().setTitle("Trends"); //Sets the name of Action bar


        GraphView graph = (GraphView) findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        graph.addSeries(series);
/*
        ListaTRENDS=(ListView) findViewById(R.id.ListView);
        //listView1=(ListView) findViewById(R.id.ListViewPage);
        Resources res=getResources();
        list_command=res.getStringArray(R.array.ComandoAnStu);
        //list_desc=res.getStringArray(R.array.DescricaoAnStu);

        CustomAdapterPage adapter=new CustomAdapterPage(this,list_command);
        //
        ListaTRENDS.setAdapter(adapter);

        ListaTRENDS.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                switch (position) {
                    case 0:
                        Toast.makeText(getApplicationContext(), "CODE == 0", Toast.LENGTH_LONG).show();
                        // startActivity( new Intent( MainActivity.this, androidstudActivity.class ));
                    case 1:
                        Toast.makeText(getApplicationContext(), "Hello", Toast.LENGTH_LONG).show();

                }

            }


        });
        */
    }
    //-----------------------------------------------------------------------------------------------
    private class GetGenres extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            Toast.makeText(TrendsActivity.this, "Json Data is downloading", Toast.LENGTH_LONG).show();
        }

        @Override
        protected Void doInBackground(Void... arg0)
        {
            HttpHandler sh = new HttpHandler();
            String url = "http://aptwiapi.herokuapp.com/aptwi.json";
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
                        final String name = resultado.getString("name");

                        HashMap<String, String> genre = new HashMap<>();
                        genre.put("id", id);
                        genre.put("name", name);

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
            ListAdapter adapter = new SimpleAdapter(TrendsActivity.this, genreList, R.layout.list_item, new String[]{"id", "name"}, new int[]{R.id.email, R.id.mobile});
            lv.setAdapter(adapter);
        }
    }

    public  void executePost(View v)
    {
        String name  = genreName.getText().toString();

        new PostGenre().execute(name);
    }

    private class PostGenre extends AsyncTask<String, Void, Void>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            Toast.makeText(TrendsActivity.this, "Json Data is posting", Toast.LENGTH_LONG).show();
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
            Toast.makeText(TrendsActivity.this, "Json Data is finished", Toast.LENGTH_LONG).show();
        }
    }

    //-----------------------------------------------------------------------------------------------
}

class CustomAdapterPage extends ArrayAdapter<String>
{
    Context context;
  //String[] Descricao;
    String[] Comando;

    public CustomAdapterPage(Context context, String[] com)
    {
        super(context, R.layout.line_trends,com);
        this.context=context;
     // this.Descricao=descricao;
        this.Comando=com;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row=inflater.inflate(R.layout.line_trends, parent, false);

        TextView mycom= (TextView) row.findViewById(R.id.txtCom);
        TextView mydesc= (TextView) row.findViewById(R.id.txtDesc);
        mycom.setText(Comando[position]);
      //mydesc.setText(Descricao[position]);

        return row;

    }
}

