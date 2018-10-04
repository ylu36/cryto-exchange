package actors;
import java.util.*;
import akka.actor.AbstractActor;
import akka.actor.Props;
import java.util.Timer;
import java.util.TimerTask;
import actors.MarketActorProtocol.*;
public class MarketActor extends AbstractActor {
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
            sender().tell(hold.message, self());
        }).match(GetSellOfferById.class, getSellOfferById -> {
            int rate = getSellOfferById.rate;
            int amount = getSellOfferById.amount;
            String message = getSellOfferById.message;
            sender().tell(Integer.toString(rate)+" "+Integer.toString(amount)+ " " +message, self());
        }).match(GetSellOffers.class, getSellerOffers -> {
            String reply = getSellerOffers.offerIDs.toString();
            sender().tell(reply, self());
        }).match(GetTransactions.class, getTransactions -> {
            String reply = getTransactions.transactions.toString();
            sender().tell(reply, self());
        }).match(GetTransactionById.class, getTransactionById -> {
            String message = getTransactionById.message;
            // String totalCost = Integer.toString(getTransactionById.totalCost);
            // String totalAmount = Integer.toString(getTransactionById.totalAmount);
            String rate = Integer.toString(getTransactionById.rate);
            String amount = Integer.toString(getTransactionById.amount);
            sender().tell(message + " " + rate + " " + amount, self());
        }).build();
    }    
}