import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeliveryCostTests {

    public static Object[][] testData() {
        return new Object[][]{
                {2, false, true, DeliveryCost.Workload.VERY_HIGH, 720.0},
                {10, true, false, DeliveryCost.Workload.HIGN, 420.0},
                {30, false, true, DeliveryCost.Workload.ELEVATED, 720.0},
                {31, true, false, DeliveryCost.Workload.OTHERS, 500.0}
        };
    }

    @ParameterizedTest
    @MethodSource("testData")
    void correctPriceIsEvaluatedIfCorrectDataEntered(int distance, boolean is, boolean isFragile, DeliveryCost.Workload workload, double result) throws DeliveryCost.ImpossibleToDeliverException {
        assertEquals(DeliveryCost.evaluateDeliveryCost(distance, is, isFragile, workload), result);
    }

    @Test
    void impossibleToDeliverFragileForLongDistance() throws DeliveryCost.ImpossibleToDeliverException {
        DeliveryCost.ImpossibleToDeliverException thrown = Assertions.assertThrows(DeliveryCost.ImpossibleToDeliverException.class, () -> {
            DeliveryCost.evaluateDeliveryCost(31, false, true, DeliveryCost.Workload.OTHERS);
        });
        Assertions.assertEquals("It's impossible to deliver fragile cargo to more than 30 km", thrown.getMessage());
    }

    @Test
    void impossibleToDeliverIfIncorrectDistance() throws DeliveryCost.ImpossibleToDeliverException {
        DeliveryCost.ImpossibleToDeliverException thrown = Assertions.assertThrows(DeliveryCost.ImpossibleToDeliverException.class, () -> {
            DeliveryCost.evaluateDeliveryCost(0, false, true, DeliveryCost.Workload.OTHERS);
        });
        Assertions.assertEquals("Distance is too short to order delivery", thrown.getMessage());
    }

    @Test
    void minPriceIsDisplayedIfDeliveryCostIsLow() throws DeliveryCost.ImpossibleToDeliverException {
        assertEquals(DeliveryCost.evaluateDeliveryCost(1, false, false, DeliveryCost.Workload.OTHERS), 400);
    }

}
