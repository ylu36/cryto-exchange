package controllers;
import java.util.*;
import javax.inject.Inject;
import javax.inject.Singleton;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.pattern.Patterns;
import play.Logger;
import play.Logger.ALogger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
public class ExchangeController extends Controller {
    public Map<String, List<Integer>> orderbook = new HashMap<>();
    final ActorSystem system;
    private static final ALogger logger = Logger.of(ExchangeController.class);
    private static int balanceUSD, balanceBTC;

    @Inject
	public ExchangeController(ActorSystem system) {
        List<Integer> list1 = Arrays.asList(100, 5);
        List<Integer> list2 = Arrays.asList(80, 2);
        List<Integer> list3 = Arrays.asList(50, 12);
        orderbook.put("431671cb", list1);
        orderbook.put("16b961ed", list2);
        orderbook.put("1e06381d", list3);
        logger.info("initializing exchange controller...");
        this.system = system;
        this.balanceUSD = 0;
        this.balanceBTC = 0;
    }

    public Result addbalance(Integer amount) {
        balanceUSD += amount;
        ObjectNode result = Json.newObject();
        result.put("status", "success");
        return ok(result);
    }

    public Result getbalance() {
        ObjectNode result = Json.newObject();
        result.put("status", "success");
        result.put("USD", balanceUSD);
        result.put("BTC", balanceBTC);
        return ok(result);
    }

    public Result gettranactions() {
        ObjectNode result = Json.newObject();
        result.put("status", "success");
        return ok(result);
    }
}
