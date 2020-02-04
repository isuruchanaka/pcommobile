//import android.app.Dialog;
//import android.content.DialogInterface;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;
//import android.support.v7.app.AlertDialog;
//import android.text.method.PasswordTransformationMethod;
//import android.util.Log;
//import android.view.View;
//import android.support.design.widget.NavigationView;
//import android.support.v4.view.GravityCompat;
//import android.support.v4.widget.DrawerLayout;
//import android.support.v7.app.ActionBarDrawerToggle;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.LinearLayout;
//import android.widget.Spinner;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.android.volley.Request;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.JsonObjectRequest;
//
//import org.json.JSONArray;
//import org.json.JSONObject;
//
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//public class MainActivity extends AppCompatActivity
//        implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemSelectedListener {
//
//    private String name;
//    private String svcid;
//    String[] menuArray;
//    private String responseString;
//    private String initials;
//    private String Rnkname;
//    private Menu menu;
//    private String inBedMenuTitle = "Login";
//    private String outOfBedMenuTitle = "Logout";
//    private boolean inBed = false;
//    private String smonth;
//    private String yrval;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//
//
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();
//        Calendar c = Calendar.getInstance();
//        int year = c.get(Calendar.YEAR);
//        int month = c.get(Calendar.MONTH);
//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);
//        menu = navigationView.getMenu();
//        final Spinner spinner = (Spinner) findViewById(R.id.planets_spinner);
//// Create an ArrayAdapter using the string array and a default spinner layout
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
//                R.array.planets_array, android.R.layout.simple_spinner_item);
//// Specify the layout to use when the list of choices appears
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//// Apply the adapter to the spinner
//
//        spinner.setAdapter(adapter);
//        spinner.setSelection(month);
//        Log.wtf("testpass", String.valueOf(year));
//        spinner.setOnItemSelectedListener(this);
//
//        final  Spinner spinner1 = (Spinner) findViewById(R.id.planets_spinner1);
//// Create an ArrayAdapter using the string array and a default spinner layout
//        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
//                R.array.planets_array1, android.R.layout.simple_spinner_item);
//// Specify the layout to use when the list of choices appears
//        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//// Apply the adapter to the spinner
//        spinner1.setAdapter(adapter1);
//        menuArray = getResources().getStringArray(R.array.planets_array1);
//        for(int i = 0;i<menuArray.length; i++)
//        {
//            if(menuArray[i].contains(String.valueOf(year)))
//            {
//
//                spinner1.setSelection(i);
//            }
//
//        }
//        Button button = (Button) findViewById(R.id.button);
//        button.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                if(svcid==null||svcid.equals("no")||svcid.equals("")){
//                    loginu();
//
//                }
//                else {
//                    findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
//                    yrval = spinner1.getSelectedItem().toString();
//                    String mnval = String.valueOf(spinner.getSelectedItemPosition()+1);
//                    smonth=spinner.getSelectedItem().toString();
//                    loaddata(svcid,yrval,mnval);
//                }
//            }
//        });
//        SharedPreferences prefs = getSharedPreferences("userinfo", MODE_PRIVATE);
//        String restoredText = prefs.getString("SVCID", null);
//        if (restoredText != null) {
//            name = prefs.getString("Surname", "no");//"No name defined" is the default value.
//            svcid = prefs.getString("SVCID", "no"); //0 is the default value.
//            initials = prefs.getString("Initials", "no");
//            Rnkname = prefs.getString("Rnkname", "no");
//            NavigationView navigationView1 = (NavigationView) findViewById(R.id.nav_view);
//
//            View headerView1 = navigationView1.getHeaderView(0);
//            TextView b1 = (TextView)headerView1.findViewById(R.id.txtname);
//            b1.setText(initials+" "+ name);
//            TextView b2 = (TextView)headerView1.findViewById(R.id.txtrank);
//            b2.setText(Rnkname);
//            inBed=true;
//
//            Log.wtf("testpass",cryptWithMD5("password"));
//        }
//
//        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
//    }
//    public void onItemSelected(AdapterView<?> parent, View view,
//                               int pos, long id) {
//        // An item was selected. You can retrieve the selected item using
//        // parent.getItemAtPosition(pos)
//    }
//
//    public void onNothingSelected(AdapterView<?> parent) {
//        // Another interface callback
//    }
//    @Override
//    public void onBackPressed() {
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//
//        return super.onOptionsItemSelected(item);
//    }
//
//    @SuppressWarnings("StatementWithEmptyBody")
//    @Override
//    public boolean onNavigationItemSelected(MenuItem item) {
//        // Handle navigation view item clicks here.
//        int id = item.getItemId();
//        Log.wtf("testpass", String.valueOf(id));
//        if (id == R.id.nav_camera) {
//
//            if (inBed) {
//                signOut();
//            } else {
//
//                loginu();
//            }
//
//
//        }
//        if (id == R.id.nav_camera1) {
//
//
//            changepass();
//
//        }
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
//        return true;
//    }
//
//    public  void signOut(){
//
//        SharedPreferences settings = getSharedPreferences("userinfo", MODE_PRIVATE);
//        settings.edit().clear().commit();
//        name ="no";//"No name defined" is the default value.
//        svcid = "no"; //0 is the default value.
//        initials ="no";
//        Rnkname ="no";
//        cleard();
//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//
//        View headerView = navigationView.getHeaderView(0);
//        TextView b1 = (TextView)headerView.findViewById(R.id.txtname);
//        b1.setText("");
//        TextView b2 = (TextView)headerView.findViewById(R.id.txtrank);
//        b2.setText("");
//        Log.wtf("testpass",cryptWithMD5("password"));
//        inBed=false;
//        updateMenuTitles();
//
//
//    }
//
//    private static MessageDigest md;
//    public  void changepass(){
//        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
//        alertDialog.setMessage("Change Password?");
//        LinearLayout layout = new LinearLayout(MainActivity.this);
//        layout.setOrientation(LinearLayout.VERTICAL);
//
//// Add a TextView here for the "Title" label, as noted in the comments
//        final EditText titleBox = new EditText(MainActivity.this);
//        titleBox.setHint("New Password");
//        titleBox.setTransformationMethod(PasswordTransformationMethod.getInstance());
//        layout.addView(titleBox); // Notice this is an add method
//
//// Add another TextView here for the "Description" label
//        final EditText descriptionBox = new EditText(MainActivity.this);
//        descriptionBox.setHint("Confirm Password");
//        descriptionBox.setTransformationMethod(PasswordTransformationMethod.getInstance());
//        layout.addView(descriptionBox); // Another add method
//        alertDialog.setView(layout);
//
//        alertDialog.setIcon(R.drawable.ic_menu_manage);
//        alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Change password", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int which) {
//
//                String emnttext = titleBox.getText().toString();
//                String descr = descriptionBox.getText().toString();
//                if(!emnttext.equals(descr)){
//                    Toast.makeText(MainActivity.this, "Please Make Sure New Password and Confirm Password Same!",
//                            Toast.LENGTH_LONG).show();
//                }
//                else {
//                    findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
//                    chnguser(emnttext, cryptWithMD5(descr));
//                }
//
//            }
//        });
//
//        alertDialog.show();
//
//    }
//    public  void loginu(){
//        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
//        alertDialog.setMessage("Login");
//        LinearLayout layout = new LinearLayout(MainActivity.this);
//        layout.setOrientation(LinearLayout.VERTICAL);
//
//// Add a TextView here for the "Title" label, as noted in the comments
//        final EditText titleBox = new EditText(MainActivity.this);
//        titleBox.setHint("Service No");
//        layout.addView(titleBox); // Notice this is an add method
//
//// Add another TextView here for the "Description" label
//        final EditText descriptionBox = new EditText(MainActivity.this);
//        descriptionBox.setHint("Password");
//        descriptionBox.setTransformationMethod(PasswordTransformationMethod.getInstance());
//        layout.addView(descriptionBox); // Another add method
//        alertDialog.setView(layout);
//
//        alertDialog.setIcon(R.drawable.ic_menu_manage);
//        alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Login", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int which) {
//
//                String emnttext = titleBox.getText().toString();
//                String descr = descriptionBox.getText().toString();
//                findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
//                loaduser(emnttext,cryptWithMD5(descr));
//
//
//            }
//        });
//
//        alertDialog.show();
//
//    }
//
//    public void  cleard(){
//        TextView b1 = (TextView)findViewById(R.id.txtmesamt);
//        b1.setText("");
//        TextView b2 = (TextView)findViewById(R.id.txtbar);
//        b2.setText("");
//
//        TextView b3 = (TextView)findViewById(R.id.txttot);
//        b3.setText("");
//        TextView b4 = (TextView)findViewById(R.id.textView3);
//        b4.setText("");
//    }
//    public void  setdata(){
//
//        try {
//            JSONObject jsonObject = null;
//
//            jsonObject = new JSONObject(responseString);
//
//            JSONArray we = null;
//
//            we = jsonObject.getJSONArray("Table");
//
//
//            JSONObject we1 = we.getJSONObject(0);
//
//            String s1 = we1.get("SVCID").toString();
//            String t1 = we1.get("Rnkname").toString();
//            String u1 = we1.get("Surname").toString();
//            String v1 = we1.get("Initials").toString();
//
//            SharedPreferences.Editor editor = getSharedPreferences("userinfo", MODE_PRIVATE).edit();
//            editor.putString("SVCID", s1);
//            editor.putString("Rnkname", t1);
//            editor.putString("Surname", u1);
//            editor.putString("Initials", v1);
//            editor.apply();
//            SharedPreferences prefs = getSharedPreferences("userinfo", MODE_PRIVATE);
//            String restoredText = prefs.getString("SVCID", null);
//            if (restoredText != null) {
//                name = prefs.getString("Surname", "no");//"No name defined" is the default value.
//                svcid = prefs.getString("SVCID", "no"); //0 is the default value.
//                initials = prefs.getString("Initials", "no");
//                Rnkname = prefs.getString("Rnkname", "no");
//                NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//
//                View headerView = navigationView.getHeaderView(0);
//                TextView b1 = (TextView)headerView.findViewById(R.id.txtname);
//                b1.setText(initials+" "+ name);
//                TextView b2 = (TextView)headerView.findViewById(R.id.txtrank);
//                b2.setText(Rnkname);
//                Log.wtf("testpass",cryptWithMD5("password"));
//                inBed=true;
//                updateMenuTitles();
//                findViewById(R.id.loadingPanel).setVisibility(View.GONE);
//                Toast.makeText(MainActivity.this, "Login Successful!",
//                        Toast.LENGTH_LONG).show();
//            }else {
//                Toast.makeText(MainActivity.this, "Incorrect Username or Password!",
//                        Toast.LENGTH_LONG).show();
//            }
//
//
//
//        }catch (Exception ex){
//
//
//            Toast.makeText(MainActivity.this, "Incorrect Username or Password!",
//                    Toast.LENGTH_LONG).show();
//        }
//
//
//    }
//
//    public void  setbilldata(){
//
//        try {
//            cleard();
//            JSONObject jsonObject = null;
//
//            jsonObject = new JSONObject(responseString);
//
//            JSONArray we = null;
//
//            we = jsonObject.getJSONArray("Table");
//
//
//            JSONObject we1 = we.getJSONObject(0);
//
//            String s1 = we1.get("MessAmnt").toString();
//            String t1 = we1.get("BarAmnt").toString();
//            String u1 = we1.get("TotalAmnt").toString();
//            String v1 = we1.get("BillDate").toString();
//            Log.wtf("eee", v1);
//            v1= "Last Updated: "+v1.split("T")[0];
////            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
////            DateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
//            // formatter1.format(formatter.parse(v1))
//            TextView b5 = (TextView)findViewById(R.id.textView);
//            b5.setText(v1);
//
//            TextView b1 = (TextView)findViewById(R.id.txtmesamt);
//            b1.setText(s1);
//            TextView b2 = (TextView)findViewById(R.id.txtbar);
//            b2.setText(t1);
//
//            TextView b3 = (TextView)findViewById(R.id.txttot);
//            b3.setText(u1);
//
//            TextView b4 = (TextView)findViewById(R.id.textView3);
//            b4.setText("Mess Bill for "+smonth+"  "+yrval);
//            b4.setTextSize(25);
//
//            Log.wtf("testpass",cryptWithMD5("password"));
//            findViewById(R.id.loadingPanel).setVisibility(View.GONE);
//
//
//
//        }catch (Exception ex){
//            findViewById(R.id.loadingPanel).setVisibility(View.GONE);
//            Toast.makeText(MainActivity.this, "No Data!",
//                    Toast.LENGTH_LONG).show();
//
//        }
//
//
//    }
//    @Override
//    public void onStart() {
//        super.onStart();
//        updateMenuTitles();
//    }
//
//    private void updateMenuTitles() {
//
//        MenuItem bedMenuItem = menu.findItem(R.id.nav_camera);
//        if (inBed) {
//            bedMenuItem.setTitle(outOfBedMenuTitle);
//        } else {
//            bedMenuItem.setTitle(inBedMenuTitle);
//        }
//    }
//    public String loaddata(String  svcid,String  ym, String mnth){
//        final String url = "http://203.115.24.227:8888/mbs/getbill.ashx?svcid="+svcid+"&ym="+ym+"&mnt="+mnth+"";
//        Log.wtf("eee", url);
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
//                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
//
//                    @Override
//                    public void onResponse(JSONObject response) {
//
//                        responseString = response.toString();
//                        setbilldata();
//
//
//                    }
//                }, new Response.ErrorListener() {
//
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                        Log.wtf("eee", "ww");
//                        cleard();
//                        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
//                        Toast.makeText(MainActivity.this, "No Data!",
//                                Toast.LENGTH_LONG).show();
//                        // TODO: Handle error
//
//                    }
//                });
//
//// Access the RequestQueue through your singleton class.
//        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
//        return  responseString;
//    }
//
//    public String loaduser(String  svcid,String  pass){
//        String url = "http://203.115.24.227:8888/mbs/getuser.ashx?pass="+pass+"&svcid="+svcid+"";
//        Log.wtf("eee", url);
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
//                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
//
//                    @Override
//                    public void onResponse(JSONObject response) {
//
//                        responseString = response.toString();
//                        setdata();
//
//
//                    }
//                }, new Response.ErrorListener() {
//
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                        Log.wtf("eee", "ww2");
//
//                        // TODO: Handle error
//
//                    }
//                });
//
//// Access the RequestQueue through your singleton class.
//        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
//        return  responseString;
//    }
//    public String chnguser(String  scid,String  pass){
//        String url = "http://203.115.24.227:8888/mbs/chngpass.ashx?pass="+pass+"&svcid="+svcid+"";
//        Log.wtf("eee", url);
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
//                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
//
//                    @Override
//                    public void onResponse(JSONObject response) {
//
//                        responseString = response.toString();
//                        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
//                        Toast.makeText(MainActivity.this, "Password Changed!",
//                                Toast.LENGTH_LONG).show();
//
//
//                    }
//                }, new Response.ErrorListener() {
//
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                        Log.wtf("eee", "ww2");
//                        Toast.makeText(MainActivity.this, "Error Changing Password!",
//                                Toast.LENGTH_LONG).show();
//                        // TODO: Handle error
//
//                    }
//                });
//
//// Access the RequestQueue through your singleton class.
//        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
//        return  responseString;
//    }
//    public static String cryptWithMD5(String pass){
//        try {
//            md = MessageDigest.getInstance("MD5");
//            byte[] passBytes = pass.getBytes();
//            md.reset();
//            byte[] digested = md.digest(passBytes);
//            StringBuffer sb = new StringBuffer();
//            for(int i=0;i<digested.length;i++){
//                sb.append(Integer.toHexString(0xff & digested[i]));
//            }
//            return sb.toString();
//        } catch (NoSuchAlgorithmException ex) {
//            Logger.getLogger(MainActivity.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return null;
//
//
//    }
//
//
//}
