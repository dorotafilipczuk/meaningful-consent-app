package permissions;

import android.content.Context;
import com.meaningful.ata1g11.meaningfulconsent.Helper;

public enum Collectible {
    CONTACTS,
    MESSAGES,
    APPS,
    PHOTOS,
    BROWSINGHISTORY;
    
    Context appContext;
    Helper helper;

    public int getValue(int max, PrivacyType privacyType) {
        if (max == 25) {
            return getValueLow(this.helper);
        }
        if (max == 50) {
            return getValueMed(this.helper);
        }
        if (max == 100) {
            return getValueHigh(this.helper);
        }
        throw new IllegalStateException("Error: max value undefined. " + this + ", " + max + ", " + privacyType);
    }

    public static void main(String[] args) {
        String s = "";
        for (int max : new int[]{25, 50, 100}) {
            for (PrivacyType p : PrivacyType.values()) {
                for (Collectible c : values()) {
                    s = s + c.getValue(max, p) + ", ";
                }
                s = s + "\n";
            }
        }
        System.out.println(s);
    }

    private int getValueLow(Helper helper) {
        switch (this) {
            case CONTACTS:
                return helper.getLowContacts().intValue();
            case MESSAGES:
                return helper.getLowMessages().intValue();
            case APPS:
                return helper.getLowApps().intValue();
            case PHOTOS:
                return helper.getLowPhotos().intValue();
            case BROWSINGHISTORY:
                return helper.getLowHistory().intValue();
            default:
                throw new IllegalStateException("Error: enum exhausted: " + this + ", ");
        }
    }

    private int getValueMed(Helper helper) {
        switch (this) {
            case CONTACTS:
                return helper.getMediumContacts().intValue();
            case MESSAGES:
                return helper.getMediumMessages().intValue();
            case APPS:
                return helper.getMediumApps().intValue();
            case PHOTOS:
                return helper.getMediumPhotos().intValue();
            case BROWSINGHISTORY:
                return helper.getMediumHistory().intValue();
            default:
                throw new IllegalStateException("Error: enum exhausted: " + this + ", ");
        }
    }

    private int getValueHigh(Helper helper) {
        switch (this) {
            case CONTACTS:
                return helper.getHighContacts().intValue();
            case MESSAGES:
                return helper.getHighMessages().intValue();
            case APPS:
                return helper.getHighApps().intValue();
            case PHOTOS:
                return helper.getHighPhotos().intValue();
            case BROWSINGHISTORY:
                return helper.getHighHistory().intValue();
            default:
                throw new IllegalStateException("Error: enum exhausted: " + this + ", ");
        }
    }
}
