package actors;
public class UserActorProtocol {

    public static class PlaceOffer {
        public final String offerId;
        public final int amount;

        public PlaceOffer(String offerId, int amount) {
            this.offerId = offerId;
            this.amount = amount;
        }
    }
}