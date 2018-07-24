package pandora;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PandoraProblem {
    List<Box> allBoxes;
    int max;
    public double searchCosts = 0.0d;

    public PandoraProblem(List<Box> allBoxes, int max) {
        this.allBoxes = allBoxes;
        this.max = max;
    }

    public Box applyPandorasRule() {
        for (Box box : this.allBoxes) {
            if (box.isClosed()) {
                box.computeReservationPrice();
            }
        }
        System.out.println(this);
        ArrayList<Box> sortedBoxes = new ArrayList(this.allBoxes);
        while (true) {
            Collections.sort(sortedBoxes);
            Box bestBox = (Box) sortedBoxes.get(0);
            if (!bestBox.isClosed()) {
                return bestBox;
            }
            System.out.println("Best box is " + bestBox + ", opening...");
            bestBox.open(this.max);
            this.searchCosts += bestBox.getCost();
            System.out.println(this);
        }
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("Total costs incurred: " + this.searchCosts + "\n");
        int i = 0;
        for (Box b : this.allBoxes) {
            int i2 = i + 1;
            s.append("#" + i + ": " + b.toString() + "\n");
            i = i2;
        }
        return s.toString();
    }
}
