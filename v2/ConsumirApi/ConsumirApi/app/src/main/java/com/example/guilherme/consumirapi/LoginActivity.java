package com.example.guilherme.consumirapi;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class LoginActivity extends AppCompatActivity {
    AlertDialog.Builder dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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
                dialog=new AlertDialog.Builder(LoginActivity.this);//CRIAR NOVO
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
    public void callSC(View view)
    {
        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(i);
    }
}
