package mud.per.iw.pcom;

import android.content.SharedPreferences;
import android.net.Uri;
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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.security.MessageDigest;


public class login extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    private String name;
    private String svcid;
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
    private static MessageDigest md;
    private String changeres;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        _emailText=(EditText)findViewById(R.id.input_email);
        _passwordText=(EditText)findViewById(R.id.input_password);
        _loginButton=(Button)findViewById(R.id.btn_login);
        _signupLink=(TextView)findViewById(R.id.link_signup);
        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    getver1();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        getver();
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
        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        loaduser(email,generateSHA1Hash(password));
//        Intent intent = new Intent(login.this, MainActivity.class);
//        startActivity(intent);
        // TODO: Implement your own authentication logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        //onLoginSuccess();
                        // onLoginFailed();
                       // progressDialog.dismiss();
                    }
                }, 1000);
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
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() ) {
            _emailText.setError("enter a Username");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 3 || password.length() > 10) {
            _passwordText.setError("between 3 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
    public void  setdata(){

        try {
            JSONObject jsonObject = null;

            jsonObject = new JSONObject(responseString);

            JSONArray we = null;

            we = jsonObject.getJSONArray("Table");


            JSONObject we1 = we.getJSONObject(0);

            String s1 = we1.get("UserID").toString();
            String t1 = we1.get("Description").toString();
            String u1 = we1.get("LName").toString();
            String v1 = we1.get("FName").toString();
            String x1 = we1.get("RoleID").toString();
            String z1 = we1.get("Password").toString();
            String p1 = we1.get("Country").toString();

            SharedPreferences.Editor editor = getSharedPreferences("userinfo", MODE_PRIVATE).edit();
            editor.putString("UserID", s1);
            editor.putString("Description", t1);
            editor.putString("LName", u1);
            editor.putString("FName", v1);
            editor.putString("RoleID", x1);
            editor.putString("Password", z1);
            editor.putString("Country", p1);
            editor.apply();
            SharedPreferences prefs = getSharedPreferences("userinfo", MODE_PRIVATE);
            String restoredText = prefs.getString("UserID", null);
            if (restoredText != null) {
                name = prefs.getString("LName", "no");//"No name defined" is the default value.
                svcid = prefs.getString("UserID", "no"); //0 is the default value.
                initials = prefs.getString("FName", "no");
                Rnkname = prefs.getString("Description", "no");


               // Log.wtf("testpass",cryptWithMD5("isuru"));
                inBed=true;
                findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                //findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                Toast.makeText(login.this, "Login Successful!",
                        Toast.LENGTH_LONG).show();
                Intent intentl = new Intent(this, MainActivity.class);
                startActivity(intentl);
            }else {
                Toast.makeText(login.this, "Incorrect Username or Password!",
                        Toast.LENGTH_LONG).show();
            }



        }catch (Exception ex){
             Log.wtf("testpass",ex.toString());

            Toast.makeText(login.this, "Incorrect Username or Password!",
                    Toast.LENGTH_LONG).show();
        }


    }

    public String getver1(){
        String url = this.getApplicationContext().getResources().getString( R.string.weburl34);



        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        String  suresb = response.toString();
                        // Log.wtf("CameraDemo1", suresb);
                        try{

                            JSONObject dbovh = null;

                            dbovh = new JSONObject(suresb);

                            JSONArray we = null;

                            we = dbovh.getJSONArray("Table");



                            int b=0;

                            for (int row = 0; row < (we.length()); row++) {

                                JSONObject we1 = we.getJSONObject(b);

                                String Code = we1.get("VersionNo").toString();
                                //String SiteUId = we1.get("UId").toString();
//                                //String StationTypeUId = we1.get("StationTypeUId").toString();
//                                String Description = we1.get("sitedesp").toString();
//                                String Lat = we1.get("Lat").toString();
//                                String Long = we1.get("Long").toString();
//                                String stype = we1.get("sttype").toString();
//                                String UId = we1.get("UId").toString();
//                                String stypedes = we1.get("stdesp").toString();

                                if(!Code.equals(login.this.getResources().getString( R.string.weburl35))) {
                                    final String appPackageName =login.this.getPackageName(); // getPackageName() from Context or Activity object
                                    try {
                                        Toast.makeText(login.this, "Please Update Your App!", Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                                    } catch (android.content.ActivityNotFoundException anfe) {
                                        Toast.makeText(login.this, "Please Update Your App!", Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                                    }
                                }
                                else { login();}
                                b++;
                            }




                        } catch (NullPointerException ex){
                            Log.wtf("CameraDemo", ex.toString());
                        } catch (Exception e){
                            Log.wtf("CameraDemo", e.toString());
                        }


                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {




                    }
                });


        singletongm.getInstance(login.this).addToRequestQueue(jsonObjectRequest);



        return changeres;
    }
    public String getver(){
        String url = this.getApplicationContext().getResources().getString( R.string.weburl34);



        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        String  suresb = response.toString();
                        // Log.wtf("CameraDemo1", suresb);
                        try{

                            JSONObject dbovh = null;

                            dbovh = new JSONObject(suresb);

                            JSONArray we = null;

                            we = dbovh.getJSONArray("Table");



                            int b=0;

                            for (int row = 0; row < (we.length()); row++) {

                                JSONObject we1 = we.getJSONObject(b);

                                String Code = we1.get("VersionNo").toString();
                                //String SiteUId = we1.get("UId").toString();
//                                //String StationTypeUId = we1.get("StationTypeUId").toString();
//                                String Description = we1.get("sitedesp").toString();
//                                String Lat = we1.get("Lat").toString();
//                                String Long = we1.get("Long").toString();
//                                String stype = we1.get("sttype").toString();
//                                String UId = we1.get("UId").toString();
//                                String stypedes = we1.get("stdesp").toString();

                                if(!Code.equals(login.this.getResources().getString( R.string.weburl35))) {
                                    final String appPackageName =login.this.getPackageName(); // getPackageName() from Context or Activity object
                                    try {
                                        Toast.makeText(login.this, "Please Update Your App!", Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                                    } catch (android.content.ActivityNotFoundException anfe) {
                                        Toast.makeText(login.this, "Please Update Your App!", Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                                    }
                                }
                                b++;
                            }




                        } catch (NullPointerException ex){
                            Log.wtf("CameraDemo", ex.toString());
                        } catch (Exception e){
                            Log.wtf("CameraDemo", e.toString());
                        }


                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {




                    }
                });


        singletongm.getInstance(login.this).addToRequestQueue(jsonObjectRequest);



        return changeres;
    }
       public static String generateSHA1Hash(String input) throws Exception {

        MessageDigest messagedigest = MessageDigest.getInstance("SHA");
        messagedigest.update(input.getBytes("UTF-8"));
        byte digest[] = messagedigest.digest();

        StringBuffer s = new StringBuffer(digest.length);
        int length = digest.length;
        for (int n = 0; n < length; n++) {
            int number = digest[n];
            number = (number < 0) ? (number + 256) : number;
            s.append(Integer.toString(number));
        }
        Log.wtf("testpass",s.toString());
        return s.toString();
    }

    public String loaduser(String  svcid,String  pass){

        String url = this.getApplicationContext().getResources().getString( R.string.weburl2)+"?pass="+pass+"&svcid="+svcid+"";
        Log.wtf("eee", url);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        responseString = response.toString();
                        setdata();


                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.wtf("eee", "ww2");

                        // TODO: Handle error

                    }
                });

// Access the RequestQueue through your singleton class.
        singletongm.getInstance(this).addToRequestQueue(jsonObjectRequest);
        return  responseString;
    }



}
