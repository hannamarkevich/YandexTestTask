public class Runner {

    public static void main(String[] args){
        try {
            System.out.println("Delivery cost is " + DeliveryCost.evaluateDeliveryCost(1, false, false, DeliveryCost.Workload.OTHERS));
        } catch (DeliveryCost.ImpossibleToDeliverException e) {
            System.out.println("Cannot deliver fragile cargo to more than 30 km");
        }
    }
}
