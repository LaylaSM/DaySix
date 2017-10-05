package mii.laylasm.daysix.Utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.renderscript.ScriptGroup;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;
import android.view.View;
import android.content.SharedPreferences.Editor;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutionException;

import okhttp3.internal.cache.DiskLruCache;

/**
 * Created by Layla Siti Mardhiyah on 19/09/2017.
 */

public class JsonUtil extends AppCompatActivity {

    SharedPreferences pref;
    Editor editor;
    Context appContext;
    int PRIVAETE_MODE = 0;

    public JsonUtil(Context ap) {
        appContext = ap;
    }



    public String get_data(String url, Activity app) {
        getDataAsyncTask sda = new getDataAsyncTask(app);
        try {
            return sda.execute(new String[]{url}).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        } catch (ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String put_data(String url) {
        putDataAsyncTask sda = this.new putDataAsyncTask();
        //sda.applicationContext =  this.appContext;
        try {
            return sda.execute(new String[]{url}).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        } catch (ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

    private final int HEADERMAX = 10;
    private final int BODYMAX = 20;
    private String[] headerName = new String[HEADERMAX];
    private String[] headerValue = new String[HEADERMAX];
    private int cHeader = 0;

    public void addHeader(String name, String value) {
        if (cHeader == HEADERMAX)
            return;
        this.headerName[cHeader] = name;
        this.headerValue[cHeader] = value;
        cHeader++;
    }

    private String[] bodyName = new String[BODYMAX];
    private String[] bodyValue = new String[BODYMAX];
    private int cBody = 0;

    public void addBody(String name, String value) {
        if (cBody == BODYMAX)
            return;
        this.bodyName[cBody] = name;
        this.bodyValue[cBody] = value;
        cBody++;
    }


    protected class getDataAsyncTask extends AsyncTask<String, Void, String> {

        private Context applicationContext;
        ProgressDialog dialog;

        public getDataAsyncTask(Context context) {
            applicationContext = context;
            dialog = new ProgressDialog(applicationContext);
        }

        protected void onPreExecute() {
            dialog.setTitle("Please wait..");
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String result = null;

            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpPut = new HttpGet(params[0]);

            try {
                HttpResponse httpResponse = httpClient.execute(httpPut);
                InputStream inputStream = httpResponse.getEntity().getContent();

                BufferedReader streamReader;
                streamReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

                String line = "";
                StringBuilder text = new StringBuilder();
                while ((line = streamReader.readLine()) != null) {
                    text.append(line).append(" ");
                }

                try {
                    result = text.toString();
                } catch (Exception e) {
                    e.printStackTrace();
                    result = e.toString();
                }
            } catch (IOException e) {
                e.printStackTrace();
                result = e.toString();
            }

            return result;
        }

        protected void onPostExecute(String data) {
        }

    }


    protected class putDataAsyncTask extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... params) {

            String result = "";
            HttpClient httpClient = new DefaultHttpClient();
            HttpPut httpPut = new HttpPut(params[0]);
            for (int i = 0; i < cHeader; i++) {
                httpPut.addHeader(JsonUtil.this.headerName[i], JsonUtil.this.headerValue[i]);
            }

            String json = "";
            JSONObject jsonObject = new JSONObject();
            try {

                for (int i = 0; i < cBody; i++) {
                    jsonObject.put(JsonUtil.this.bodyName[i], JsonUtil.this.bodyValue[i]);
                }
                json = jsonObject.toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }


            StringEntity se = null;
            try {
                se = new StringEntity(json);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            httpPut.setEntity(se);
            try {
                HttpResponse httpResponse = httpClient.execute(httpPut);
                InputStream inputStream = httpResponse.getEntity().getContent();

                BufferedReader streamReader;
                streamReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

                String line = "";
                StringBuilder text = new StringBuilder();
                while ((line = streamReader.readLine()) != null) {
                    text.append(line).append(" ");
                }

                try {
                    result = text.toString();
                } catch (Exception e) {
                    e.printStackTrace();
                    result = e.toString();
                }
            } catch (IOException e) {
                e.printStackTrace();
                result = e.toString();
            }

            return result;

        }


    }

}

