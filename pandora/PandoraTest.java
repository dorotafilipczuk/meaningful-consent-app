package pandora;

import distribution.UniformDistribution;
import java.util.ArrayList;
import java.util.List;

public class PandoraTest {
    public static void main(String[] args) {
        List<Box> allBoxes = new ArrayList();
        allBoxes.add(new Box(0.2d, new UniformDistribution(0.0d, 1.0d)));
        PandoraProblem pandoraProblem = new PandoraProblem(allBoxes, 0);
        System.out.println(pandoraProblem);
        System.out.println("Selected box: " + pandoraProblem.applyPandorasRule());
    }
}
