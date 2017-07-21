package baskara.bankatmjsonpostpythongae;

import android.content.Intent;
import android.support.annotation.Nullable;
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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText usernameLogin;
    private EditText pinLogin;
    private Button buttonLogin;
    private Button changeActivity;
    private String TAG = "login";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usernameLogin = (EditText)findViewById(R.id.loginUserUsername);
        pinLogin = (EditText)findViewById(R.id.loginPinUsername);
        buttonLogin = (Button)findViewById(R.id.buttonLogin);
        changeActivity = (Button)findViewById(R.id.buttonChangeToAdmin);
        buttonLogin.setOnClickListener(this);
        changeActivity.setOnClickListener(this);
    }

    private void login() throws JSONException {
            String username = usernameLogin.getText().toString();
            String pin = pinLogin.getText().toString();
            // Create JSONObject
            final JSONObject item = new JSONObject();

            // add the items to JSONObject
            item.put("username", username);
            item.put("pin", pin);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    URL url = null;

                    try {
                        url = new URL("http://192.168.11.5:8080/loginHandlerAndroid");
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
                                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                                intent.putExtra("username", str);
                                startActivity(intent);
                            }
                            else {
                                failText();
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
    private void failText(){
        Toast.makeText(this, "Username or PIN is WRONG", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
        if (v == buttonLogin){
            try {
                login();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (v == changeActivity){
            startActivity(new Intent(this, RegisterActivity.class));
        }
    }
}
