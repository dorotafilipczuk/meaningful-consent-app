package com.meaningful.ata1g11.meaningfulconsent;

import android.content.Context;
import android.os.AsyncTask;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.json.JSONException;
import org.json.JSONObject;

public class ServerJobScenario extends AsyncTask<String, Void, String> {
    private Context mContext;

    public ServerJobScenario(Context context) {
        this.mContext = context;
    }

    protected String doInBackground(String[] params) {
        int ind = 0;
        while (ind < params.length) {
            try {
                String day = "day" + params[ind] + ".txt";
                HttpURLConnection con = (HttpURLConnection) new URL("http://consent.ecs.soton.ac.uk/mobile/scenario/").openConnection();
                con.setDoOutput(true);
                con.setDoInput(true);
                con.setRequestProperty("Content-Type", "application/json");
                con.setRequestProperty("Accept", "application/json");
                con.setRequestMethod("POST");
                JSONObject each = new JSONObject();
                each.put("day", params[ind]);
                each.put("uuid", new Helper(this.mContext).getUserID());
                OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
                wr.write(each.toString());
                wr.flush();
                StringBuilder sb = new StringBuilder();
                if (con.getResponseCode() == 200) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
                    while (true) {
                        String line = br.readLine();
                        if (line == null) {
                            break;
                        }
                        sb.append(line + "\n");
                    }
                    br.close();
                    File file = this.mContext.getFileStreamPath(day);
                    if (file.exists()) {
                        file.delete();
                    }
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(this.mContext.openFileOutput(day, 0));
                    outputStreamWriter.write(sb.toString());
                    outputStreamWriter.flush();
                    outputStreamWriter.close();
                }
                con.disconnect();
                ind++;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            } catch (JSONException e3) {
                e3.printStackTrace();
            }
        }
        return null;
    }
}
