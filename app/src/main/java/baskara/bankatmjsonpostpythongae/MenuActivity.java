package baskara.bankatmjsonpostpythongae;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener{
    private String username;
    private Button buttonDeposit;
    private Button buttonWithdrawal;
    private Button buttonTransfer;
    private Button buttonLogout;
    private Button buttonInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        setValue();
        buttonDeposit = (Button)findViewById(R.id.buttonDeposit);
        buttonWithdrawal = (Button)findViewById(R.id.buttonWithdrawal);
        buttonTransfer = (Button)findViewById(R.id.buttonTransfer);
        buttonLogout = (Button)findViewById(R.id.buttonLogout);
        buttonInfo = (Button)findViewById(R.id.buttonInfo);
        buttonDeposit.setOnClickListener(this);
        buttonWithdrawal.setOnClickListener(this);
        buttonTransfer.setOnClickListener(this);
        buttonLogout.setOnClickListener(this);
        buttonInfo.setOnClickListener(this);
    }
    private void setValue(){
        Bundle extras = getIntent().getExtras();
        if (extras!=null){
            username = extras.getString("username");
            Log.d("intent", username);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == buttonDeposit){
            Intent intent = new Intent(getApplicationContext(), DepositActivity.class);
            intent.putExtra("username", username);
            startActivity(intent);
        }
        if (v == buttonWithdrawal){
            Intent intent = new Intent(getApplicationContext(), WithdrawalActivity.class);
            intent.putExtra("username", username);
            startActivity(intent);
        }
        if (v == buttonTransfer){
            Intent intent = new Intent(getApplicationContext(), TransferActivity.class);
            intent.putExtra("username", username);
            startActivity(intent);
        }
        if (v == buttonLogout){
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }
        if (v == buttonInfo){
            Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
            intent.putExtra("username", username);
            startActivity(intent);

        }
    }
}
