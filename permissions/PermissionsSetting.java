package permissions;

import com.meaningful.ata1g11.meaningfulconsent.Helper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class PermissionsSetting {
    private String description;
    Set<Collectible> enabledPermissions;

    public PermissionsSetting(Set<Collectible> enabledPermissions) {
        this.enabledPermissions = enabledPermissions;
    }

    public PermissionsSetting(String TFTString) {
        this.enabledPermissions = new HashSet();
        int i = 0;
        for (Collectible c : Collectible.values()) {
            if (TFTString.substring(i, i + 1).equals("T")) {
                this.enabledPermissions.add(c);
            }
            i++;
        }
    }

    private PermissionsSetting(int Ts) {
        List<String> random = makeRandomPermissionSetting(Ts);
        this.enabledPermissions = new HashSet();
        int i = 0;
        for (Collectible c : Collectible.values()) {
            if (((String) random.get(i)).equals("T")) {
                this.enabledPermissions.add(c);
            }
            i++;
        }
    }

    public PermissionsSetting(long seed) {
        this(getRandomNumberofTs(seed));
    }

    private static int getRandomNumberofTs(long seed) {
        Random random = new Random();
        random.setSeed(seed);
        return random.nextInt(Collectible.values().length + 1);
    }

    private List<String> makeRandomPermissionSetting(int Ts) {
        List<String> s = new ArrayList();
        int permissions = Collectible.values().length;
        for (int i = 0; i < permissions; i++) {
            if (i < Ts) {
                s.add("T");
            } else {
                s.add("F");
            }
        }
        Collections.shuffle(s);
        return s;
    }

    public static Set<PermissionsSetting> getAllPossiblePermissionSettings() {
        Set<Set<Collectible>> allCollectibleSubsets = powerSet(new HashSet(Arrays.asList(Collectible.values())));
        Set<PermissionsSetting> allPermissionsSettings = new HashSet();
        for (Set cs : allCollectibleSubsets) {
            allPermissionsSettings.add(new PermissionsSetting(cs));
        }
        return allPermissionsSettings;
    }

    public static <T> Set<Set<T>> powerSet(Set<T> originalSet) {
        Set<Set<T>> sets = new HashSet();
        if (originalSet.isEmpty()) {
            sets.add(new HashSet());
        } else {
            List<T> list = new ArrayList(originalSet);
            T head = list.get(0);
            for (Set<T> set : powerSet(new HashSet(list.subList(1, list.size())))) {
                Set<T> newSet = new HashSet();
                newSet.add(head);
                newSet.addAll(set);
                sets.add(newSet);
                sets.add(set);
            }
        }
        return sets;
    }

    public String toString() {
        return this.enabledPermissions.toString();
    }

    public String toTFString() {
        String TFSTring = "";
        for (Collectible c : Collectible.values()) {
            TFSTring = TFSTring + (this.enabledPermissions.contains(c) ? "T" : "F");
        }
        return TFSTring;
    }

    public int size() {
        return this.enabledPermissions.size();
    }

    public int hashCode() {
        return (this.enabledPermissions == null ? 0 : this.enabledPermissions.hashCode()) + 31;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        PermissionsSetting other = (PermissionsSetting) obj;
        if (this.enabledPermissions == null) {
            if (other.enabledPermissions != null) {
                return false;
            }
            return true;
        } else if (this.enabledPermissions.equals(other.enabledPermissions)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean hasEnabled(Collectible c) {
        return this.enabledPermissions.contains(c);
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getValue(int max, PrivacyType privacyType, Helper helper) {
        int totalValue = 0;
        for (Collectible c : this.enabledPermissions) {
            totalValue += c.getValue(max, privacyType);
        }
        return totalValue;
    }
}
