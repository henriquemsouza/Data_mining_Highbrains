package highbrains.com.databrainsmining;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Cadastro_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_);

        getSupportActionBar().setTitle("Data Brains - Cadastro");
        ActionBar actionBar = getSupportActionBar();
    }
}
