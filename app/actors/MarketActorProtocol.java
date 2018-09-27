package actors;
public class MarketActorProtocol {
    public static class Hold {
        public final String offerId;
        public final int amount;
        public Hold(String offerId, int amount) {
            this.offerId = offerId;
            this.amount = amount;
        }
    }

    public static class Confirm {
        public final String offerId;
        public final int amount;
        public Confirm(String offerId, int amount) {
            this.offerId = offerId;
            this.amount = amount;
        }
    }
}