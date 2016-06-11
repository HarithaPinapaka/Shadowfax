package com.shadowfax.getlocationofdelivery;

import android.app.IntentService;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.net.SocketTimeoutException;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class SendDetailsService extends IntentService {

    private String mJsonStr;

    public SendDetailsService() {
        super("SendDetailsService");
    }



    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("SendDetails", "inside SendDetailsService.....");
        mJsonStr = intent.getExtras().getString("json_str");
        sendDetails getAsync = new sendDetails();
        getAsync.execute();
    }

    class sendDetails extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            String url = "http://fbb27d94.ngrok.io/ROOT/postLatLongData";
            String response;

            try {
                System.out.println("json sending is.." + mJsonStr);

                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost  = new HttpPost(url);
                httpPost.setHeader("Content-Type", "application/json");
                httpPost.setEntity(new StringEntity(mJsonStr.toString()));
                httpClient.execute(httpPost);

                return null;
            } catch (SocketTimeoutException e) {

                return "socketexception";
            } catch (Exception e) {
                e.printStackTrace();

            }
            return null;

        }

    }

}
