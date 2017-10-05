package mii.laylasm.daysix;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.SharedPreferencesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.MenuInflater;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import mii.laylasm.daysix.Utils.JsonUtil;
import mii.laylasm.daysix.Utils.Properties;

import static mii.laylasm.daysix.Utils.Properties.ROOT_URL;

public class LoginUser extends AppCompatActivity {

    boolean Registered;
    public static Language ln = new Language(Language.ENGLISH);
    EditText editCardNumber, editMobilePin;
    TextView txtWelcome, txtPlsActivate;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_user);
        this.setTitle("");
        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        Registered = sharedPref.getBoolean(Properties.REGISTERED, false);

        txtWelcome = (TextView) findViewById(R.id.txtWelcome);
        txtWelcome.setText(Language.text.WELCOME[ln.activeLang()]);

        txtPlsActivate = (TextView) findViewById(R.id.txtPlsActivate);
        txtPlsActivate.setText(Language.text.PLS_ACTV_ACC[ln.activeLang()]);

        editCardNumber = (EditText) findViewById(R.id.EdtActCardNumb);
        editCardNumber.setHint(Language.text.CARD_NUMBER[ln.activeLang()]);

        editMobilePin = (EditText) findViewById(R.id.EdtActMobPin);
        editMobilePin.setHint(Language.text.MOBILE_PIN[ln.activeLang()]);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        if (!Registered) {
            //belum terdaftar menu aktivasi
            btnLogin.setText(Language.text.VERIFY[ln.activeLang()]);
            txtPlsActivate.setVisibility(View.VISIBLE);
            editCardNumber.setVisibility(View.VISIBLE);
            editMobilePin.setVisibility(View.VISIBLE);

        } else {
            //sudah terdaftar menu login
            btnLogin.setText(Language.text.LOGIN[ln.activeLang()]);
            txtPlsActivate.setVisibility(View.GONE);
            editCardNumber.setVisibility(View.GONE);
            editMobilePin.setHint(Language.text.TYPE_YOUR_ACCESS_CODE[ln.activeLang()]);


        }
        findViewById(R.id.btnLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!Registered) {
                    userActivation();
                } else {
                    userLogin();


                }


            }
        });
    }

    private void userLogin() {
        Intent intent = new Intent(LoginUser.this, Home.class);
        startActivity(intent);

    }


    private void userActivation() {
        final String cardnumber = editCardNumber.getText().toString();
        final String mobilepin = editMobilePin.getText().toString();
        String url = ROOT_URL + "verification?card_number=" + cardnumber + "&pin=" + mobilepin;

        JsonUtil ju = new JsonUtil(LoginUser.this);
        String returns = ju.get_data(url, LoginUser.this);

        JSONObject jobj = null;
        String rsp_code = "";
        if (returns != null) {

            try {
                jobj = new JSONObject(returns);
                rsp_code = (jobj.get("rsp_code").toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        boolean dispFailed = true;
        String rsp_message = "";
        Bundle bundle = new Bundle();
        //Toast.makeText(LoginUser.this, returns, Toast.LENGTH_SHORT).show();
        if (rsp_code.equals("00")) {
            dispFailed = false;
            Intent intent = new Intent(LoginUser.this, SubmitAccess.class);
            bundle.putString(Properties.CARD_NUMBER, cardnumber);
            bundle.putString(Properties.MOBILE_PIN, mobilepin);
            intent.putExtras(bundle);
            startActivity(intent);
        } else if (!rsp_code.equals("")) {

            try {
                rsp_message = (jobj.get("rsp_message").toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        if (dispFailed) {
            Intent intent = new Intent(LoginUser.this, FailedAccess.class);
            if (returns == null || rsp_code.equals("")) {
                bundle.putString(Properties.RSP_CODE, Properties.RSP_CONNECTION_PROBLEM);
                //Toast.makeText(LoginUser.this, "NULLLLLL", Toast.LENGTH_SHORT).show();
            } else
                bundle.putString(Properties.RSP_CODE, rsp_code);
            bundle.putString(Properties.RSP_MSG, rsp_message);
            intent.putExtras(bundle);
            startActivity(intent);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuLanguage:
                Toast.makeText(getApplicationContext(), "Language are clicked!!", Toast.LENGTH_LONG).show();
                return true;
            case R.id.menuClear:
                Toast.makeText(getApplicationContext(), "Clear are clicked!!", Toast.LENGTH_SHORT).show();
                final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.clear();
                editor.commit();
                return true;
            case R.id.menuHelp:
                Toast.makeText(getApplicationContext(), "Help are clicked!!", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menuLogout:
                Toast.makeText(getApplicationContext(), "Logout are clicked!!", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
