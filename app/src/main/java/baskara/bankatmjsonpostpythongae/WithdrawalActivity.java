package baskara.bankatmjsonpostpythongae;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class WithdrawalActivity extends AppCompatActivity implements View.OnClickListener{
    private Button buttonWithdrawal;
    private EditText editWithdrawal;
    private String TAG = "Withdrawal";
    private String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdrawal);
        buttonWithdrawal = (Button)findViewById(R.id.buttonSubmitWithdrawal);
        editWithdrawal = (EditText)findViewById(R.id.editWithdrawal);
        buttonWithdrawal.setOnClickListener(this);
        setValue();
    }
    private void setValue(){
        Bundle extras = getIntent().getExtras();
        if (extras!=null){
            username = extras.getString("username");
            Log.d("intent", username);
        }
    }
    private void deposit() throws JSONException {
        String withdrawal = editWithdrawal.getText().toString();
        // Create JSONObject
        final JSONObject item = new JSONObject();

        // add the items to JSONObject
        item.put("username", username);
        item.put("withdrawal", withdrawal);

        new Thread(new Runnable() {
            @Override
            public void run() {
                URL url = null;

                try {
                    url = new URL("http://192.168.11.5:8080/withdrawalHandlerAndroid");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    String ret = "";
                    try {
                        connection = (HttpURLConnection) url.openConnection();

                        // set parameter to http request
                        connection.setRequestMethod("POST");
                        connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
                        connection.setDoOutput(true);

                        connection.connect();

                        // set json data to send data
                        OutputStream outputStream = connection.getOutputStream();
                        PrintStream ps = new PrintStream(connection.getOutputStream());
                        ps.print(item.toString());
                        Log.d("JSON", item.toString());
                        ps.close();
                        outputStream.close();

                        // parse response get the data after post
                        String str = inputStreamToString(connection.getInputStream());
                        Log.d(TAG, str);
                        if (!str.equals("fail")){
                            Log.d(TAG, "mantap");
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (connection != null) {
                            connection.disconnect();
                        }
                    }

                } catch (Exception ex) {
                    System.out.println(ex);
                }

            }
        }).start();
    }
    // InputStream -> String
    static String inputStreamToString(InputStream is) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();

        try {
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    @Override
    public void onClick(View v) {
        if (v == buttonWithdrawal){
            Toast.makeText(getApplicationContext(), "Withdrawal Success", Toast.LENGTH_LONG).show();
            try {
                deposit();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
