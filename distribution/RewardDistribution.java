package distribution;

public interface RewardDistribution {
    double computeReservationPrice(double d);

    double getSample();
}
