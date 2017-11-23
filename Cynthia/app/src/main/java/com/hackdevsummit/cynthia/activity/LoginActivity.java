package com.hackdevsummit.cynthia.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.hackdevsummit.cynthia.MainActivity;
import com.hackdevsummit.cynthia.R;
import com.hackdevsummit.cynthia.helper.AppController;
import com.hackdevsummit.cynthia.helper.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private static String TAG = LoginActivity.class.getSimpleName();
    private static final String UPLOAD_URL = "https://devsum.herokuapp.com/sendsms";

    private EditText editTextPhone;
    private EditText editTextPassword;
    private Button buttonLogin;

    private String phone = "";
    private String password = "";

    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initializeData();
        initializeListener();
    }

    private void initializeData() {
        editTextPhone = (EditText) findViewById(R.id.tiet_phoneInput);
        editTextPassword = (EditText) findViewById(R.id.tiet_passwordInput);
        buttonLogin = (Button) findViewById(R.id.btn_login);

        sessionManager = new SessionManager(getApplicationContext());
    }

    private void initializeListener() {
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generateDataForm();
                if (phone.isEmpty() && password.isEmpty()) {
                    showToast("Mohon isi form dengan lengkap");
                } else {
                    loginToServer();
                }
            }
        });
    }

    private void loginSuccess() {
        sessionManager.setLogin(true);
        showToast("Login Berhasil");
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void loginToServer() {
        try {

            JSONObject jsonBody = new JSONObject();
            jsonBody.put("to", phone);
            jsonBody.put("msg", password);
            final String requestBody = jsonBody.toString();

            Log.d(TAG,"requestBody : " + requestBody);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d(TAG,"response : " + response.toString());
                    try {
//                        JSONObject jObj = new JSONObject(response);
                        loginSuccess();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d(TAG,"responseError : " + error.toString());
                }
            }) {

                @Override
                protected Map<String, String> getParams() {

                    // Posting parameters to login url
                    Map<String, String> params = new HashMap<String, String>();
//                    String mypass = md5(password);
//                    String mypass1 = md5(mypass);

                    params.put("to", phone);
                    params.put("msg", password);

                    return params;
                }

            };
            AppController.getInstance().addToRequestQueue(stringRequest, "send_img");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void generateDataForm() {
        phone = editTextPhone.getText().toString().trim();
        password = editTextPassword.getText().toString().trim();
    }

    /**
     * method ini untuk menampilkan toast agar pemanggilan lebih sederhana
     * @param s pesan
     */
    private void showToast(String s) {
        Toast.makeText(LoginActivity.this,s,Toast.LENGTH_LONG).show();
    }
}
