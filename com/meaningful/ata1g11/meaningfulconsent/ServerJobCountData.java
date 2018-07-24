package com.meaningful.ata1g11.meaningfulconsent;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.Browser;
import android.provider.ContactsContract.Contacts;
import android.provider.MediaStore.Images.Media;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.json.JSONException;
import org.json.JSONObject;

public class ServerJobCountData extends AsyncTask<String, Void, String> {
    private Context mContext;

    public ServerJobCountData(Context context) {
        this.mContext = context;
    }

    protected String doInBackground(String[] params) {
        try {
            HttpURLConnection con = (HttpURLConnection) new URL("http://consent.ecs.soton.ac.uk/mobile/countdata/").openConnection();
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestMethod("POST");
            JSONObject each = new JSONObject();
            Integer cnt = getNumberOfContacts();
            Integer msg = getNumberOfMessages();
            Integer lct = getNumberOfApps();
            Integer pht = getNumberOfPhotos();
            Integer brw = getNumberOfBrowsers();
            each.put("uuid", params[0]);
            each.put("contact", cnt);
            each.put("message", msg);
            each.put("location", lct);
            each.put("photo", pht);
            each.put("browser", brw);
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

    private Integer getNumberOfBrowsers() {
        Integer total = Integer.valueOf(0);
        try {
            Cursor cur = this.mContext.getContentResolver().query(Browser.BOOKMARKS_URI, new String[]{"_id", "url", "date", "visits"}, "bookmark=0", null, null);
            if (cur.getCount() > 0) {
                return Integer.valueOf(cur.getCount());
            }
            return total;
        } catch (Exception e) {
            return Integer.valueOf(0);
        }
    }

    private Integer getNumberOfPhotos() {
        Integer total = Integer.valueOf(0);
        try {
            Uri images = Media.EXTERNAL_CONTENT_URI;
            Cursor cur = this.mContext.getContentResolver().query(images, new String[]{"_id", "_data"}, null, null, null);
            if (cur.getCount() > 0) {
                return Integer.valueOf(cur.getCount());
            }
            return total;
        } catch (Exception e) {
            return Integer.valueOf(0);
        }
    }

    private Integer getNumberOfLocations() {
        Integer total = Integer.valueOf(0);
        try {
            Cursor cur = this.mContext.getContentResolver().query(Media.EXTERNAL_CONTENT_URI, new String[]{"_id", "latitude", "longitude"}, "latitude IS NOT NULL AND longitude IS NOT NULL", null, null);
            if (cur.getCount() > 0) {
                return Integer.valueOf(cur.getCount());
            }
            return total;
        } catch (Exception e) {
            return Integer.valueOf(0);
        }
    }

    private Integer getNumberOfMessages() {
        Integer total = Integer.valueOf(0);
        Uri smsURI = Uri.parse("content://sms");
        try {
            Cursor cur = this.mContext.getContentResolver().query(smsURI, new String[]{"thread_id", "address", "date", "body"}, null, null, null);
            if (cur.getCount() > 0) {
                return Integer.valueOf(cur.getCount());
            }
            return total;
        } catch (Exception e) {
            return Integer.valueOf(0);
        }
    }

    private Integer getNumberOfContacts() {
        Integer total = Integer.valueOf(0);
        ContentResolver cr = this.mContext.getContentResolver();
        try {
            Cursor cur = cr.query(Contacts.CONTENT_URI, new String[]{"_id", "display_name", "has_phone_number"}, "has_phone_number>0 ", null, null);
            if (cur.getCount() > 0) {
                return Integer.valueOf(cur.getCount());
            }
            return total;
        } catch (Exception e) {
            return Integer.valueOf(0);
        }
    }

    private Integer getNumberOfApps() {
        Integer total = Integer.valueOf(0);
        try {
            String user_id = new Helper(this.mContext).getUserID();
            return Integer.valueOf(this.mContext.getPackageManager().getInstalledPackages(0).size());
        } catch (Exception e) {
            return Integer.valueOf(0);
        }
    }
}
