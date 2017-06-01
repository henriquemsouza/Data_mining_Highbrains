package highbrains.com.databrainsmining;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import twitter4j.Status;//lib from twitter4j
import twitter4j.Twitter; //lib from twitter4j
import twitter4j.TwitterFactory;//lib from twitter4j

public class MainActivity extends AppCompatActivity {
    AlertDialog.Builder dialog;
    Button btnlogin;
    Button btncadbtnloginastro;
    Button btntrends;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("Data Brains");
        ActionBar actionBar = getSupportActionBar();



        btnlogin=(Button) findViewById(R.id.btnLogin);
        btncadbtnloginastro=(Button) findViewById(R.id.btnCADASTRO);
        btntrends=(Button) findViewById(R.id.btnTrends);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });
        btntrends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TrendsActivity.class));
            }
        });
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
                dialog.setMessage("Desenvolvido por \nFrom the best to the best\nHigh Brains @2017");
                dialog.setIcon(android.R.drawable.ic_menu_info_details);
                dialog.create();
                dialog.show();


                break;

        }

        return true;
    }
    //




}
