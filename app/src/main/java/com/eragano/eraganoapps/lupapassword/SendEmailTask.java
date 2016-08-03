package com.eragano.eraganoapps.lupapassword;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

/**
 * Created by Deztu on 6/30/2016.
 */
public class SendEmailTask extends AsyncTask {
    private ProgressDialog progressDialog= null;
    private Activity activity= null;

    public SendEmailTask(Activity activity){
        this.activity= activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        progressDialog= new ProgressDialog(activity);
        progressDialog.setMessage("Getting ready...");
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected Object doInBackground(Object[] params) {
        try{
            Gmail gmail= new Gmail(params[0].toString(), params[1].toString(), params[2].toString(), params[3].toString(), params[4].toString());
            gmail.createEmailMessage();
            gmail.senEmail();
            publishProgress("Email sent.");
        }catch(Exception e){
            publishProgress(e.getMessage());
        }

        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);

        progressDialog.dismiss();
    }
}
