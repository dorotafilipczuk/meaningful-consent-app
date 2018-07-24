package permissions;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class PricingFunction {
    private String description;
    Map<PermissionsSetting, Integer> pricingFunction = new HashMap();
    private int range;

    public PricingFunction(long seed, int max) {
        this.range = max / 5;
        Random random = new Random();
        random.setSeed(seed);
        for (PermissionsSetting permissionSetting : PermissionsSetting.getAllPossiblePermissionSettings()) {
            setPoints(permissionSetting, constructPoints(random, permissionSetting));
        }
    }

    private int constructPoints(Random random, PermissionsSetting permissionSetting) {
        int numberOfPermissions = permissionSetting.size();
        if (numberOfPermissions == 0) {
            return 0;
        }
        return (this.range * (numberOfPermissions - 1)) + random.nextInt(this.range);
    }

    private void setPoints(PermissionsSetting permissionSetting, int points) {
        this.pricingFunction.put(permissionSetting, Integer.valueOf(points));
    }

    private int getPoints(PermissionsSetting permissionSetting) {
        return ((Integer) this.pricingFunction.get(permissionSetting)).intValue();
    }

    public static void main(String[] args) {
        int i;
        new PricingFunction(1, 100).setDescription("H1");
        new PricingFunction(1, 100).setDescription("H2");
        new PricingFunction(1, 50).setDescription("M1");
        new PricingFunction(1, 50).setDescription("M2");
        new PricingFunction(1, 25).setDescription("L1");
        new PricingFunction(1, 25).setDescription("L2");
        PricingFunction m3 = new PricingFunction(1, 50);
        m3.setDescription("M3");
        pricings = new PricingFunction[6][];
        pricings[0] = new PricingFunction[]{h1, h2, l1, m1, l2, m2};
        pricings[1] = new PricingFunction[]{h2, m1, h1, m2, l1, l2};
        pricings[2] = new PricingFunction[]{m1, m2, h2, l2, h1, l1};
        pricings[3] = new PricingFunction[]{m2, l2, m1, l1, h2, h1};
        pricings[4] = new PricingFunction[]{l2, l1, m2, h1, m1, h2};
        pricings[5] = new PricingFunction[]{l1, h1, l2, h2, m2, m1};
        PermissionsSetting[][] r = (PermissionsSetting[][]) Array.newInstance(PermissionsSetting.class, new int[]{6, 6});
        for (i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                r[i][j] = new PermissionsSetting((long) ((i * 6) + j));
                r[i][j].setDescription("R" + i + "" + j);
            }
        }
        PermissionsSetting[] s = new PermissionsSetting[6];
        for (i = 0; i < 6; i++) {
            s[i] = new PermissionsSetting((long) (i + 314));
            s[i].setDescription("S" + i);
        }
        Scenario[][] all = (Scenario[][]) Array.newInstance(Scenario.class, new int[]{6, 8});
        for (int p = 0; p < 6; p++) {
            int sc = 0;
            while (sc < 8) {
                if (sc == 0 || sc == 7) {
                    all[p][sc] = new Scenario(m3, s[p]);
                } else {
                    all[p][sc] = new Scenario(pricings[p][sc - 1], r[p][sc - 1]);
                }
                System.out.println(all[p][sc]);
                System.out.println("-----\n");
                sc++;
            }
        }
    }

    public int calculatePoints(Set<Collectible> setOfCollectiblesTurnedOn) {
        return ((Integer) this.pricingFunction.get(setOfCollectiblesTurnedOn)).intValue();
    }

    public String toString() {
        return this.pricingFunction.toString();
    }

    public String toTFString() {
        String TFSTring = "";
        for (PermissionsSetting p : this.pricingFunction.keySet()) {
            TFSTring = TFSTring + pricingLineFormat(p);
        }
        return TFSTring;
    }

    private String pricingLineFormat(PermissionsSetting p) {
        return p.toTFString() + " " + getPoints(p) + "\n";
    }

    public String toTFString(PermissionsSetting defaultSetting) {
        String TFSTring = pricingLineFormat(defaultSetting);
        for (PermissionsSetting p : this.pricingFunction.keySet()) {
            if (!p.equals(defaultSetting)) {
                TFSTring = TFSTring + pricingLineFormat(p);
            }
        }
        return TFSTring;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
