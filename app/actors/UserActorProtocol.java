package actors;
public class UserActorProtocol {
    public static class PlaceOffer {
        public final String offerId;
        public final int amount;
        public PlaceOffer(String offerid, int amount) {
            this.offerId = offerid;
            this.amount = amount;
        }
    }
}