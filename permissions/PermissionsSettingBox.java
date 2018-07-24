package permissions;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.meaningful.ata1g11.meaningfulconsent.Helper;
import distribution.RewardDistribution;
import distribution.UniformDistribution;
import pandora.Box;

public class PermissionsSettingBox extends Box {
    static Helper helper;
    Context context;
    PermissionsSetting permissionsSetting;
    PrivacyType privacyType;

    public PermissionsSettingBox(int max, PermissionsSetting permissionsSetting, PrivacyType privacyType, Context con) {
        super(10.0d, calculateRewardDistribution(max, permissionsSetting, privacyType));
        this.permissionsSetting = permissionsSetting;
        this.privacyType = privacyType;
        this.context = con;
        helper = new Helper(this.context, Boolean.valueOf(true));
    }

    private static RewardDistribution calculateRewardDistribution(int max, PermissionsSetting p, PrivacyType privacyType) {
        int range = max / 5;
        int numberOfPermissions = p.size();
        if (numberOfPermissions == 0) {
            return new UniformDistribution(0.0d, 0.0d);
        }
        int leftbound = range * (numberOfPermissions - 1);
        int rightbound = leftbound + range;
        int value = p.getValue(max, privacyType, helper);
        return new UniformDistribution((double) (leftbound - value), (double) (rightbound - value));
    }

    public String toString() {
        return "(Setting: " + this.permissionsSetting.toTFString() + ") " + super.toString();
    }

    public void open(int max) {
        if (isOpen()) {
            throw new IllegalStateException("Box already open.");
        }
        SharedPreferences sharedPrefs = this.context.getSharedPreferences("Settings", 0);
        Editor prefEditor = sharedPrefs.edit();
        this.reward = Double.valueOf(Double.parseDouble(getPrefs("day" + Integer.toString(sharedPrefs.getInt("Day", 0)) + ".txt", this.permissionsSetting.toTFString())[1]) - ((double) this.permissionsSetting.getValue(max, this.privacyType, helper)));
        prefEditor.putInt("AgentQuote", Integer.valueOf(Integer.valueOf(sharedPrefs.getInt("AgentQuote", 0)).intValue() + 1).intValue());
        prefEditor.commit();
        this.open = true;
    }

    public String toTFString() {
        return this.permissionsSetting.toTFString();
    }

    public void setReward(Double customReward) {
        this.reward = customReward;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String[] getPrefs(java.lang.String r9, java.lang.String r10) {
        /*
        r8 = this;
        r6 = 2;
        r4 = new java.lang.String[r6];
        r6 = r8.context;	 Catch:{ FileNotFoundException -> 0x0045, IOException -> 0x0043 }
        r1 = r6.openFileInput(r9);	 Catch:{ FileNotFoundException -> 0x0045, IOException -> 0x0043 }
        if (r1 == 0) goto L_0x0042;
    L_0x000b:
        r2 = new java.io.InputStreamReader;	 Catch:{ FileNotFoundException -> 0x0045, IOException -> 0x0043 }
        r2.<init>(r1);	 Catch:{ FileNotFoundException -> 0x0045, IOException -> 0x0043 }
        r0 = new java.io.BufferedReader;	 Catch:{ FileNotFoundException -> 0x0045, IOException -> 0x0043 }
        r0.<init>(r2);	 Catch:{ FileNotFoundException -> 0x0045, IOException -> 0x0043 }
        r3 = 0;
    L_0x0016:
        r3 = r0.readLine();	 Catch:{ FileNotFoundException -> 0x0045, IOException -> 0x0043 }
        if (r3 == 0) goto L_0x003f;
    L_0x001c:
        r6 = "";
        r6 = r3.equals(r6);	 Catch:{ FileNotFoundException -> 0x0045, IOException -> 0x0043 }
        if (r6 != 0) goto L_0x0016;
    L_0x0024:
        r6 = "\\s+";
        r5 = r3.split(r6);	 Catch:{ FileNotFoundException -> 0x0045, IOException -> 0x0043 }
        r6 = 0;
        r6 = r5[r6];	 Catch:{ FileNotFoundException -> 0x0045, IOException -> 0x0043 }
        r6 = r6.equals(r10);	 Catch:{ FileNotFoundException -> 0x0045, IOException -> 0x0043 }
        if (r6 == 0) goto L_0x0016;
    L_0x0033:
        r6 = 0;
        r7 = 0;
        r7 = r5[r7];	 Catch:{ FileNotFoundException -> 0x0045, IOException -> 0x0043 }
        r4[r6] = r7;	 Catch:{ FileNotFoundException -> 0x0045, IOException -> 0x0043 }
        r6 = 1;
        r7 = 1;
        r7 = r5[r7];	 Catch:{ FileNotFoundException -> 0x0045, IOException -> 0x0043 }
        r4[r6] = r7;	 Catch:{ FileNotFoundException -> 0x0045, IOException -> 0x0043 }
    L_0x003f:
        r1.close();	 Catch:{ FileNotFoundException -> 0x0045, IOException -> 0x0043 }
    L_0x0042:
        return r4;
    L_0x0043:
        r6 = move-exception;
        goto L_0x0042;
    L_0x0045:
        r6 = move-exception;
        goto L_0x0042;
        */
        throw new UnsupportedOperationException("Method not decompiled: permissions.PermissionsSettingBox.getPrefs(java.lang.String, java.lang.String):java.lang.String[]");
    }
}
