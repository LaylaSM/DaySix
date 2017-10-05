package mii.laylasm.daysix;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import mii.laylasm.daysix.Utils.JsonUtil;
import mii.laylasm.daysix.Utils.Properties;

import static mii.laylasm.daysix.LoginUser.ln;


public class SubmitAccess extends AppCompatActivity {
    TextView txtWelcome, txtPlsSetAccessCode;
    EditText editAccessCode, editAccessCodeConfirm;
    Button btnSubmit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_access);

        Bundle bundle = getIntent().getExtras();
        //Extract the data…
        final String cardnumber = bundle.getString(Properties.CARD_NUMBER);
        final String mobilepin = bundle.getString(Properties.MOBILE_PIN);
        //ln.activeLang();
        txtWelcome = (TextView) findViewById(R.id.txtWelcome);
        txtWelcome.setText(Language.text.WELCOME[ln.activeLang()]);
        txtPlsSetAccessCode =(TextView) findViewById(R.id.txtPlsSetAccessCode);
        txtPlsSetAccessCode.setText(Language.text.PLS_SET_ACCESS_CODE[ln.activeLang()]);
        editAccessCode = (EditText) findViewById(R.id.EdtAccessCode);
        editAccessCode.setHint(Language.text.ACCESS_CODE[ln.activeLang()]);
        editAccessCodeConfirm = (EditText) findViewById(R.id.EdtAccessCodeConfirm);
        editAccessCodeConfirm.setHint(Language.text.CONFIRM_ACCESS_CODE[ln.activeLang()]);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnSubmit.setText(Language.text.SUBMIT[ln.activeLang()]);
        findViewById(R.id.btnSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userSubmit(cardnumber, mobilepin);
            }
        });
    }

    private void userSubmit(String cardnumber, String mobilepin) {
        final String accesscode = editAccessCode.getText().toString();
        final String accesscodeconfirm = editAccessCodeConfirm.getText().toString();
        String url = Properties.ROOT_URL + "add";
        JsonUtil ju = new JsonUtil(SubmitAccess.this);

        ju.addHeader("Accept", "application/json");
        ju.addHeader("Content-Type", "application/json");

        ju.addBody("card_number", cardnumber);
        ju.addBody("pin", mobilepin);
        ju.addBody("access_code", accesscode);
        ju.addBody("confirm_access_code", accesscodeconfirm);

        String returns = ju.put_data(url);

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
        boolean disFailed = true;
        String rsp_message = "";
        Bundle bundle = new Bundle();
        if (rsp_code.equals("00")) {
            disFailed = false;
            Intent intent = new Intent(SubmitAccess.this, SubmitSuccess.class);
            if (returns == null || rsp_code.equals("")){
                bundle.putString(Properties.RSP_CODE,Properties.RSP_SUCCESSFULL);
            } else
            bundle.putString(Properties.ACCESS_CODE, accesscode);
            bundle.putString(Properties.CONFIRM_ACCESS_CODE, accesscodeconfirm);
            intent.putExtras(bundle);
            startActivity(intent);

            //save registrasi
            final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putBoolean(Properties.REGISTERED, true);
            editor.putString(Properties.CARD_NUMBER, cardnumber);

            editor.apply();

        } else if (!rsp_code.equals("")) {
            try {
                rsp_message = (jobj.get("rsp_message").toString());

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (disFailed) {
            Intent intent = new Intent(SubmitAccess.this, SubmitSuccess.class);
            if (returns == null || rsp_code.equals("")) {
                bundle.putString(Properties.RSP_CODE, Properties.RSP_CONNECTION_PROBLEM);
            } else
                bundle.putString(Properties.RSP_CODE, rsp_code);
            bundle.putString(Properties.RSP_MSG, rsp_message);
            intent.putExtras(bundle);
            startActivity(intent);


        }
    }
}