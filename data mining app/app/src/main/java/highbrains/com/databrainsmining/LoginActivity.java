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

public class LoginActivity extends AppCompatActivity {
    Button btnlogin;
    Button btncadastro;
    AlertDialog.Builder dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setTitle("Data Brains - Login");
        ActionBar actionBar = getSupportActionBar();

        setContentView(R.layout.activity_login);
        btnlogin=(Button) findViewById(R.id.btn_login);
        btncadastro=(Button) findViewById(R.id.btnCadastroAct);

        btncadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, Cadastro_Activity.class));
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
                dialog=new AlertDialog.Builder(LoginActivity.this);//CRIAR NOVO
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
