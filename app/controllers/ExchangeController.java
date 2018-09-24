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
    private static final ALogger logger = Logger.of(ExchangeController.class);
	// public ExchangeController(ActorSystem system, )
}
