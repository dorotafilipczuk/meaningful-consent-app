package distribution;

import java.util.Random;

public class BinaryDistribution implements RewardDistribution {
    private double f9p;
    private double f10x;

    public BinaryDistribution(double x, double p) {
        this.f10x = x;
        this.f9p = p;
    }

    public double getSample() {
        if (new Random().nextDouble() < this.f9p) {
            return this.f10x;
        }
        return 0.0d;
    }

    public double computeReservationPrice(double c) {
        return this.f10x - (c / this.f9p);
    }

    public String toString() {
        return "" + this.f10x + " with probability " + this.f9p + "";
    }
}
