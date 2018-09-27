package actors;

import java.util.*;
import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.pattern.Patterns;
import play.Logger;
import play.Logger.ALogger;
import scala.compat.java8.FutureConverters;
import javax.inject.*;
import java.util.concurrent.CompletionStage;
import actors.UserActorProtocol.*;

public class UserActor extends AbstractActor {
    private static final ALogger logger = Logger.of(UserActor.class);
    public UserActor() {

    }
    public static Props getProps() {
        return Props.create(UserActor.class);
    }
    @Override
	public Receive createReceive() {
        return receiveBuilder()
        .match(PlaceOffer.class, placeOffer->{
            logger.info("");
            System.out.println("in user actor");
            String reply = "hello " + placeOffer.offerId + " = " + placeOffer.amount;
            sender().tell(reply, self());
        }).build();
    }
}