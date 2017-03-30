package highbrains.com.databrainsmining;

import android.app.FragmentBreadCrumbs;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class TrendsActivity extends AppCompatActivity {
     public ListView ListaTRENDS;
    String[] list_command;
    FloatingActionButton FAB1;
    //String[] list_desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trends);
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
    }
}

class CustomAdapterPage extends ArrayAdapter<String>
{
    Context context;
  // String[] Descricao;
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
      //  mydesc.setText(Descricao[position]);

        return row;


    }
}

