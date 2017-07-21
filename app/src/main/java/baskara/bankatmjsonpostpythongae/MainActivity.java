package baskara.bankatmjsonpostpythongae;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button buttonSubmit;
    private EditText usernameLogin;
    private EditText pinLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonSubmit = (Button)findViewById(R.id.buttonSubmit);
        usernameLogin = (EditText)findViewById(R.id.usernameLogin);
        pinLogin = (EditText)findViewById(R.id.pinLogin);
        buttonSubmit.setOnClickListener(this);
    }

    private void login(){
        String username = usernameLogin.getText().toString();
        String pin = pinLogin.getText().toString();
        if (username.equals("admin") && pin.equals("123")){
            Log.d("id", usernameLogin.getText().toString());
            startActivity(new Intent(this, RegisterActivity.class));
        }
    }
    @Override
    public void onClick(View v) {
        if (v == buttonSubmit){
            login();
            Log.d("tes","button");
        }
    }
}
