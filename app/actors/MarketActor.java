// package actors;
// import java.util.*;
// import akka.actor.AbstractActor;
// import akka.actor.Props;
// import play.Logger;
// import play.Logger.ALogger;
// import java.util.Timer;
// import java.util.TimerTask;
// import actors.MarketActorProtocol.*;
// public class MarketActor extends AbstractActor {
//     private static final ALogger logger = Logger.of(MarketActor.class);
//     public String offerId;
//     public int amount;
//     private List<String> waitingForConfirm;
//     public Map<String, List<Integer>> orderbook = new HashMap<>();

//     public MarketActor(String offerId, int amount) {
//         List<Integer> list1 = Arrays.asList(100, 5);
//         List<Integer> list2 = Arrays.asList(80, 2);
//         List<Integer> list3 = Arrays.asList(50, 12);
//         orderbook.put("431671cb", list1);
//         orderbook.put("16b961ed", list2);
//         orderbook.put("1e06381d", list3);
//         this.offerId = offerId;
//         this.amount = amount;
//         waitingForConfirm = new ArrayList<>();
//     }
//     public static Props getProps() {
//         return Props.create(marketActor.class);
//     }
//     @Override
// 	public Receive createReceive() {
//         return receiveBuilder().match(Hold.class, hold -> {
//             logger.info("Hold request received...");
//             System.out.println("in hold");
//             boolean trial = canHold(hold.offerId, hold.amount);
//             sender().tell(trial, self());
//         }).match(Confirm.class, confirm -> {
//             logger.info("Confirm request received...");
//             System.out.println("in confirm");

//         }).build();
//     }

//     public boolean canHold(String offerId, int amount) {
//         logger.info("try to hold an offer " + offerId + "with amount " + amount);
//         System.out.println("try to hold an offer " + offerId + "with amount " + amount);
//         if(amount > offerbook.get(offerId).get(1)) {
//             System.out.println("error: not enough balance!");
//             return false;
//         }
//         int numLeft = offerbook.get(offerId).get(1) - amount;
//         List<Integer> list = Arrays.asList(offerbook.get(offerId).get(0), numLeft);
//         offerbook.put(offerId, list);
//         return true;
//     }
// }