package com.meaningful.ata1g11.meaningfulconsent;

import android.content.Context;
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

public class ServerJobParticipant extends AsyncTask<String, Void, String> {
    private Context mContext;

    public ServerJobParticipant(Context context) {
        this.mContext = context;
    }

    protected String doInBackground(String[] params) {
        try {
            HttpURLConnection con = (HttpURLConnection) new URL("http://consent.ecs.soton.ac.uk/mobile/participant/").openConnection();
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestMethod("POST");
            JSONObject each = new JSONObject();
            each.put("uuid", params[0]);
            each.put("age", params[1]);
            each.put("gender", params[2]);
            each.put("job", params[3]);
            each.put("nationality", params[4]);
            each.put("answer1", params[5]);
            each.put("answer2", params[6]);
            each.put("answer3", params[7]);
            each.put("brand", params[8]);
            each.put("model", params[9]);
            each.put("version", params[10]);
            each.put("number", params[11]);
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
                Helper helper = new Helper(this.mContext);
                String[] parts = sb.toString().replaceAll("\\s+", "").split("-");
                helper.setTreatment(parts[0]);
                helper.setUserNumber(parts[1]);
                helper.setUserType(parts[2]);
                helper.setScenarioType(parts[3]);
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
