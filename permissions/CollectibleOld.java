package permissions;

public enum CollectibleOld {
    CONTACTS,
    MESSAGES,
    APPS,
    PHOTOS,
    BROWSINGHISTORY;

    public int getValue(int max, PrivacyType privacyType) {
        if (max == 25) {
            return getValueLow(privacyType);
        }
        if (max == 50) {
            return getValueMed(privacyType);
        }
        if (max == 100) {
            return getValueHigh(privacyType);
        }
        throw new IllegalStateException("Error: max value undefined. " + this + ", " + max + ", " + privacyType);
    }

    public static void main(String[] args) {
        String s = "";
        for (int max : new int[]{25, 50, 100}) {
            for (PrivacyType p : PrivacyType.values()) {
                for (CollectibleOld c : values()) {
                    s = s + c.getValue(max, p) + ", ";
                }
                s = s + "\n";
            }
        }
        System.out.println(s);
    }

    private int getValueLow(PrivacyType privacyType) {
        switch (privacyType) {
            case FUNDAMENTALIST:
                switch (this) {
                    case CONTACTS:
                    case PHOTOS:
                        return 7;
                    case MESSAGES:
                        return 6;
                    case APPS:
                        return 4;
                    case BROWSINGHISTORY:
                        return 2;
                    default:
                        break;
                }
            case PRAGMATIST:
                switch (this) {
                    case CONTACTS:
                        return 6;
                    case MESSAGES:
                        return 6;
                    case APPS:
                        return 4;
                    case PHOTOS:
                        return 5;
                    case BROWSINGHISTORY:
                        return 2;
                    default:
                        break;
                }
            case UNCONCERNED:
                switch (this) {
                    case CONTACTS:
                        return 5;
                    case MESSAGES:
                        return 5;
                    case APPS:
                        return 2;
                    case PHOTOS:
                        return 8;
                    case BROWSINGHISTORY:
                        return 3;
                    default:
                        break;
                }
        }
        throw new IllegalStateException("Error: enum exhausted: " + this + ", " + privacyType);
    }

    private int getValueMed(PrivacyType privacyType) {
        switch (privacyType) {
            case FUNDAMENTALIST:
                switch (this) {
                    case CONTACTS:
                        return 9;
                    case MESSAGES:
                        return 14;
                    case APPS:
                        return 10;
                    case PHOTOS:
                        return 15;
                    case BROWSINGHISTORY:
                        return 6;
                    default:
                        break;
                }
            case PRAGMATIST:
                switch (this) {
                    case CONTACTS:
                        return 11;
                    case MESSAGES:
                        return 11;
                    case APPS:
                        return 10;
                    case PHOTOS:
                        return 13;
                    case BROWSINGHISTORY:
                        return 0;
                    default:
                        break;
                }
            case UNCONCERNED:
                switch (this) {
                    case CONTACTS:
                        return 1;
                    case MESSAGES:
                        return 2;
                    case APPS:
                        return 15;
                    case PHOTOS:
                        return 17;
                    case BROWSINGHISTORY:
                        return 2;
                    default:
                        break;
                }
        }
        throw new IllegalStateException("Error: enum exhausted: " + this + ", " + privacyType);
    }

    private int getValueHigh(PrivacyType privacyType) {
        switch (privacyType) {
            case FUNDAMENTALIST:
                switch (this) {
                    case CONTACTS:
                        return 18;
                    case MESSAGES:
                        return 20;
                    case APPS:
                        return 19;
                    case PHOTOS:
                        return 24;
                    case BROWSINGHISTORY:
                        return 14;
                    default:
                        break;
                }
            case PRAGMATIST:
                switch (this) {
                    case CONTACTS:
                        return 17;
                    case MESSAGES:
                        return 16;
                    case APPS:
                        return 8;
                    case PHOTOS:
                        return 20;
                    case BROWSINGHISTORY:
                        return 6;
                    default:
                        break;
                }
            case UNCONCERNED:
                switch (this) {
                    case CONTACTS:
                        return 0;
                    case MESSAGES:
                        return 1;
                    case APPS:
                        return 1;
                    case PHOTOS:
                        return 14;
                    case BROWSINGHISTORY:
                        return 0;
                    default:
                        break;
                }
        }
        throw new IllegalStateException("Error: enum exhausted: " + this + ", " + privacyType);
    }
}
