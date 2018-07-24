package com.meaningful.ata1g11.meaningfulconsent;

import android.os.AsyncTask;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.json.JSONException;
import org.json.JSONObject;

public class ServerJobAppList extends AsyncTask<String, Void, String> {
    protected String doInBackground(String[] params) {
        String[] newArray = new String[(params.length - 1)];
        System.arraycopy(params, 0, newArray, 0, params.length - 1);
        try {
            StringBuilder builder = new StringBuilder();
            for (String s : newArray) {
                builder.append(s + ", ");
            }
            builder.setLength(builder.length() - 2);
            HttpURLConnection con = (HttpURLConnection) new URL("http://consent.ecs.soton.ac.uk/mobile/applist/").openConnection();
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestMethod("POST");
            JSONObject each = new JSONObject();
            each.put("uuid", params[params.length - 1]);
            each.put("list", builder.toString());
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
            }
            con.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        } catch (JSONException e3) {
            e3.printStackTrace();
        }
        return null;
    }
}
