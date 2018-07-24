package pandora;

import distribution.RewardDistribution;

public class Box implements Comparable<Box> {
    private double cost;
    protected boolean open = false;
    protected Double reward = null;
    private RewardDistribution rewardDistribution;
    private Double f3z = null;

    public Box(double cost, RewardDistribution rewardDistribution) {
        this.cost = cost;
        this.rewardDistribution = rewardDistribution;
    }

    public void open(int max) {
        if (isOpen()) {
            throw new IllegalStateException("Box already open.");
        }
        this.reward = Double.valueOf(this.rewardDistribution.getSample());
        this.open = true;
    }

    public void computeReservationPrice() {
        if (isOpen()) {
            throw new IllegalStateException("Box already open.");
        }
        this.f3z = Double.valueOf(this.rewardDistribution.computeReservationPrice(this.cost));
    }

    public boolean isOpen() {
        return this.open;
    }

    public boolean isClosed() {
        return !this.open;
    }

    public Double getReward() {
        if (!isClosed()) {
            return this.reward;
        }
        throw new IllegalStateException("Box not opened yet.");
    }

    public double getCost() {
        return this.cost;
    }

    public Double getReservationPrice() {
        if (this.f3z == null) {
            computeReservationPrice();
        }
        return this.f3z;
    }

    public Double getValue() {
        if (isOpen()) {
            return getReward();
        }
        return getReservationPrice();
    }

    public int compareTo(Box o) {
        return o.getValue().compareTo(getValue());
    }

    public String toString() {
        if (this.open) {
            return "Open box containing " + this.reward;
        }
        return "Closed box with reward distr. " + this.rewardDistribution + ", with rv = " + this.f3z + ", cost to open = " + this.cost;
    }
}
