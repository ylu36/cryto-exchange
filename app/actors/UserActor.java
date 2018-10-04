package actors;

import java.util.*;
import java.lang.*; 
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
    public static int USD;
    public UserActor() {
        this.USD = 0;
    }
    public static Props getProps() {
        return Props.create(UserActor.class);
    }
    @Override
	public Receive createReceive() {
        return receiveBuilder()
        .match(PlaceOffer.class, placeOffer-> {
            String reply = placeOffer.message;
            sender().tell(reply +  " " + String.valueOf(placeOffer.totalAmount) + " " + String.valueOf(placeOffer.totalCost), self());
        }).build();
    }
}