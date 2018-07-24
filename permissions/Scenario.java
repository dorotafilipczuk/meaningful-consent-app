package permissions;

public class Scenario {
    private PermissionsSetting defaultSetting;
    private String description;
    private PricingFunction pricingFunction;

    public Scenario(PricingFunction pricingFunction, PermissionsSetting permissionsSetting) {
        this.pricingFunction = pricingFunction;
        this.defaultSetting = permissionsSetting;
    }

    public String toTFString() {
        return this.pricingFunction.toTFString(this.defaultSetting);
    }

    public String toString() {
        return this.pricingFunction.getDescription() + "/" + this.defaultSetting.getDescription() + ". Default: " + this.defaultSetting.toTFString();
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
