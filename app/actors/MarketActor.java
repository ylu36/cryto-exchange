package actors;
import java.util.*;
import akka.actor.AbstractActor;
import akka.actor.Props;
import play.Logger;
import play.Logger.ALogger;
import java.util.Timer;
import java.util.TimerTask;
import actors.MarketActorProtocol.*;
public class MarketActor extends AbstractActor {
    private static final ALogger logger = Logger.of(MarketActor.class);
    private List<String> waitingForConfirm;
    public Map<String, List<Integer>> orderbook = new HashMap<>();

    public MarketActor() {        
        waitingForConfirm = new ArrayList<>();
    }
    public static Props getProps() {
        return Props.create(MarketActor.class);
    }
    @Override
	public Receive createReceive() {
        return receiveBuilder().match(Hold.class, hold -> {
            logger.info("Hold request received...");
            System.out.println("in hold");
            // boolean trial = canHold(hold.offerId, hold.amount);
            sender().tell(hold.message, self());
        }).match(Confirm.class, confirm -> {
            logger.info("Confirm request received...");
            System.out.println("in confirm");
        }).match(GetSellOfferById.class, getSellOfferById -> {
            int rate = getSellOfferById.rate;
            int amount = getSellOfferById.amount;
            String message = getSellOfferById.message;
            sender().tell(Integer.toString(rate)+" "+Integer.toString(amount)+ " " +message, self());
        }).match(GetSellOffers.class, getSellerOffers -> {
            logger.info("Confirm request received...");
            String reply = getSellerOffers.offerIDs.toString();
            sender().tell(reply, self());
        }).match(GetTransactions.class, getTransactions -> {
            logger.info("Confirm request received...");
            String reply = getTransactions.transactions.toString();
            sender().tell(reply, self());
        }).match(GetTransactionById.class, getTransactionById -> {
            logger.info("Confirm request received...");
            int amount = getTransactionById.amount;
            int rate = getTransactionById.rate;
            String message = getTransactionById.message;
            sender().tell(Integer.toString(rate)+" "+Integer.toString(amount)+" " +message, self());
        }).build();
    }

    // public boolean canHold(String offerId, int amount) {
    //     logger.info("try to hold an offer " + offerId + "with amount " + amount);
    //     System.out.println("try to hold an offer " + offerId + "with amount " + amount);
    //     if(amount > offerbook.get(offerId).get(1)) {
    //         System.out.println("error: not enough balance!");
    //         return false;
    //     }
    //     int numLeft = offerbook.get(offerId).get(1) - amount;
    //     List<Integer> list = Arrays.asList(offerbook.get(offerId).get(0), numLeft);
    //     offerbook.put(offerId, list);
    //     return true;
    // }
}