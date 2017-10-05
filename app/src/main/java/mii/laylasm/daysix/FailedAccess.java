package mii.laylasm.daysix;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import mii.laylasm.daysix.Utils.Properties;

import static mii.laylasm.daysix.LoginUser.ln;

public class FailedAccess extends AppCompatActivity {
    Button btnFailedBack;
    TextView txtFailed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_failed_access);
        txtFailed = (TextView) findViewById(R.id.txtFailed);
        btnFailedBack = (Button) findViewById(R.id.btnFailedBack);
        btnFailedBack.setText(Language.text.FAILED_BACK[ln.activeLang()]);
        Bundle bundle = getIntent().getExtras();
        String rsp_code = bundle.getString(Properties.RSP_CODE);
        String rsp_msg = bundle.getString(Properties.RSP_MSG);
        if (Properties.RSP_CONNECTION_PROBLEM.equals(rsp_code)) {
            txtFailed.setText("Sorry, your data can't be verified.\n" +
                    "Please check again.");
        } else {
            txtFailed.setText(rsp_msg + "(" + rsp_code + ")");
        }
        btnFailedBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FailedAccess.this, LoginUser.class);
                startActivity(intent);
            }
        });

    }
}
