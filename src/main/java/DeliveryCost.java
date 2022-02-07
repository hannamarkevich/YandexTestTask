public class DeliveryCost {
    private static final int MIN_DELEVERY_COST = 400;

    public static double evaluateDeliveryCost(int distance, boolean isBigSize, boolean isFragile, Workload workload) throws ImpossibleToDeliverException {
        double result = 0;
        if (distance > 30) {
            if (isFragile) {
                throw new ImpossibleToDeliverException("It's impossible to deliver fragile cargo to more than 30 km");
            }
            result += 300;
        }
        else if (distance > 10) {
            result += 200;
        }
        else if (distance > 2) {
            result += 100;
        }
        else if (distance <= 0) {
            throw new ImpossibleToDeliverException("Distance is too short to order delivery");
        }
        else {
            result += 50;
        }

        result = isBigSize ? result + 200 : result + 100;

        if (isFragile) { result += 300; }

        result = Math.ceil(result * workload.getFactor() * 100) / 100;

        return result > MIN_DELEVERY_COST ? result : MIN_DELEVERY_COST;
    }

    public enum Workload {
        VERY_HIGH (1.6),
        HIGN (1.4),
        ELEVATED(1.2),
        OTHERS(1);

        private final double factor;

        Workload(double factor) {
            this.factor = factor;
        }

        public double getFactor() {
            return factor;
        }
    }

    static class ImpossibleToDeliverException extends Exception {
        ImpossibleToDeliverException(String message) {super(message);}
    }
}
