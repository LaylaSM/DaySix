package mii.laylasm.daysix;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import static mii.laylasm.daysix.LoginUser.ln;

public class SubmitSuccess extends AppCompatActivity {
    Button btnSuccessOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_success);
        btnSuccessOk = (Button) findViewById(R.id.btnSubmitToLogin);
        btnSuccessOk.setText(Language.text.SUCCESS_OK[ln.activeLang()]);
        findViewById(R.id.btnSubmitToLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubmitSuccess.this, Login.class);
                startActivity(intent);
            }
        });
    }
}
