package distribution;

import java.util.Random;

public class UniformDistribution implements RewardDistribution {
    private double f11a;
    private double f12b;

    public UniformDistribution(double a, double b) {
        this.f11a = a;
        this.f12b = b;
    }

    public double getSample() {
        return this.f11a + ((this.f12b - this.f11a) * new Random().nextDouble());
    }

    public double computeReservationPrice(double c) {
        if (this.f11a + (2.0d * c) <= this.f12b || c == 0.0d) {
            return this.f12b - Math.sqrt((2.0d * c) * (this.f12b - this.f11a));
        }
        return (0.5d * (this.f11a + this.f12b)) - c;
    }

    public String toString() {
        return "U(" + this.f11a + ", " + this.f12b + ")";
    }

    public static void main(String[] args) {
        UniformDistribution d = new UniformDistribution(60.0d, 80.0d);
        System.out.println(d.computeReservationPrice(10.0d));
        System.out.println(d.toString());
    }
}
