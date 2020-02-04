package mud.per.iw.pcom;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;

import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.security.MessageDigest;


public class actfeedback extends AppCompatActivity {
    private static final String TAG = "FeedActivity";
    private static final int REQUEST_SIGNUP = 0;
    private String name;
    private int svcid;
    String[] menuArray;
    private String responseString;
    private String initials;
    private String Rnkname;
    private Menu menu;
    private String inBedMenuTitle = "Login";
    private String outOfBedMenuTitle = "Logout";
    private boolean inBed = false;
    private String smonth;
    private String yrval;
    EditText _emailText;
    EditText _passwordText;
    Button _loginButton;
    TextView _signupLink;
    String changeres;
    private static MessageDigest md;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actfeedback);
        _emailText=(EditText)findViewById(R.id.input_head);
        _passwordText=(EditText)findViewById(R.id.input_feed);
        _loginButton=(Button)findViewById(R.id.btn_feed);
       // _signupLink=(TextView)findViewById(R.id.link_signup);

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    login();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


    }

    public void login() throws Exception {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        // _loginButton.setEnabled(false);

//        final ProgressDialog progressDialog = new ProgressDialog(login.this,
//                R.style.AppTheme);
//        progressDialog.setIndeterminate(true);
//        progressDialog.setMessage("Authenticating...");
//        progressDialog.show();

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();
        SharedPreferences prefs = getSharedPreferences("userinfo", MODE_PRIVATE);
        String restoredText = prefs.getString("UserID", null);
        if (restoredText != null) {

            svcid = Integer.parseInt(prefs.getString("UserID", "no")); //0 is the default value.
        }
        isertsp(email,password," ",svcid);



    }
    public String isertsp(String ft ,String fd ,String fi ,int  fu){
        String url = this.getResources().getString( R.string.weburl29)+"?ft="+ft+"&fd="+fd+"&fi="+fi+"&fu="+fu+"";



        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        Toast.makeText(actfeedback.this, "Feedback Sent!",
                                Toast.LENGTH_LONG).show();

                        Intent intentl = new Intent(actfeedback.this, MainActivity.class);
                        startActivity(intentl);

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.wtf("nwe1", error.toString());
                        Toast.makeText(actfeedback.this, "Error!",
                                Toast.LENGTH_LONG).show();


                    }
                });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                3000,
                5,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        singletongm.getInstance(this).addToRequestQueue(jsonObjectRequest);


        return changeres;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);

        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Error!", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() ) {
            _emailText.setError("Enter Subject");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() ) {
            _passwordText.setError("Enter Feedback");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }









}
