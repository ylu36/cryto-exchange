package actors;
import java.util.*;
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

    public static class Offerbook {
        public Map<String, List<Integer>> orderbook = new HashMap<>();
        public Offerbook() {
            List<Integer> list1 = Arrays.asList(100, 5);
            List<Integer> list2 = Arrays.asList(80, 2);
            List<Integer> list3 = Arrays.asList(50, 12);
            orderbook.put("431671cb", list1);
            orderbook.put("16b961ed", list2);
            orderbook.put("1e06381d", list3);
        }
        
        public String getSellOffers() {
            String res = "";
            Iterator it = orderbook.keySet().iterator();

            while(it.hasNext()) {
            String offerId = (String) it.next();
            if(orderbook.get(offerId).get(1) > 0)
                res += offerId + '\t';
            }
            return res.trim();
        }
    }
}