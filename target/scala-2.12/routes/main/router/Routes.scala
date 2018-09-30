// @GENERATOR:play-routes-compiler
// @SOURCE:/home/jamesl/Desktop/csc750proj2/cryto-exchange/conf/routes
// @DATE:Sun Sep 30 15:19:26 EDT 2018

package router

import play.core.routing._
import play.core.routing.HandlerInvokerFactory._

import play.api.mvc._

import _root_.controllers.Assets.Asset
import _root_.play.libs.F

class Routes(
  override val errorHandler: play.api.http.HttpErrorHandler, 
  // @LINE:6
  HomeController_0: controllers.HomeController,
  // @LINE:8
  Assets_1: controllers.Assets,
  // @LINE:11
  ExchangeController_2: controllers.ExchangeController,
  val prefix: String
) extends GeneratedRouter {

   @javax.inject.Inject()
   def this(errorHandler: play.api.http.HttpErrorHandler,
    // @LINE:6
    HomeController_0: controllers.HomeController,
    // @LINE:8
    Assets_1: controllers.Assets,
    // @LINE:11
    ExchangeController_2: controllers.ExchangeController
  ) = this(errorHandler, HomeController_0, Assets_1, ExchangeController_2, "/")

  def withPrefix(prefix: String): Routes = {
    router.RoutesPrefix.setPrefix(prefix)
    new Routes(errorHandler, HomeController_0, Assets_1, ExchangeController_2, prefix)
  }

  private[this] val defaultPrefix: String = {
    if (this.prefix.endsWith("/")) "" else "/"
  }

  def documentation = List(
    ("""GET""", this.prefix, """controllers.HomeController.index"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """assets/""" + "$" + """file<.+>""", """controllers.Assets.versioned(path:String = "/public", file:Asset)"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """addbalance/usd/""" + "$" + """amount<[^/]+>""", """controllers.ExchangeController.addbalance(amount:Integer)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """getbalance""", """controllers.ExchangeController.getbalance"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """transactions""", """controllers.ExchangeController.gettranactions"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """transactions/""" + "$" + """transactionID<[^/]+>""", """controllers.ExchangeController.gettranactionbyid(transactionID:Integer)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """selloffers""", """controllers.ExchangeController.getselloffers"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """selloffers/""" + "$" + """offerid<[^/]+>""", """controllers.ExchangeController.getsellofferbyid(offerid:String)"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """buy/""" + "$" + """maxrate<[^/]+>/""" + "$" + """amount<[^/]+>""", """controllers.ExchangeController.buy(maxrate:Integer, amount:Integer)"""),
    Nil
  ).foldLeft(List.empty[(String,String,String)]) { (s,e) => e.asInstanceOf[Any] match {
    case r @ (_,_,_) => s :+ r.asInstanceOf[(String,String,String)]
    case l => s ++ l.asInstanceOf[List[(String,String,String)]]
  }}


  // @LINE:6
  private[this] lazy val controllers_HomeController_index0_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix)))
  )
  private[this] lazy val controllers_HomeController_index0_invoker = createInvoker(
    HomeController_0.index,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.HomeController",
      "index",
      Nil,
      "GET",
      this.prefix + """""",
      """ An example controller showing a sample home page""",
      Seq()
    )
  )

  // @LINE:8
  private[this] lazy val controllers_Assets_versioned1_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("assets/"), DynamicPart("file", """.+""",false)))
  )
  private[this] lazy val controllers_Assets_versioned1_invoker = createInvoker(
    Assets_1.versioned(fakeValue[String], fakeValue[Asset]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Assets",
      "versioned",
      Seq(classOf[String], classOf[Asset]),
      "GET",
      this.prefix + """assets/""" + "$" + """file<.+>""",
      """ Map static resources from the /public folder to the /assets URL path""",
      Seq()
    )
  )

  // @LINE:11
  private[this] lazy val controllers_ExchangeController_addbalance2_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("addbalance/usd/"), DynamicPart("amount", """[^/]+""",true)))
  )
  private[this] lazy val controllers_ExchangeController_addbalance2_invoker = createInvoker(
    ExchangeController_2.addbalance(fakeValue[Integer]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.ExchangeController",
      "addbalance",
      Seq(classOf[Integer]),
      "POST",
      this.prefix + """addbalance/usd/""" + "$" + """amount<[^/]+>""",
      """ RESTful APIs""",
      Seq()
    )
  )

  // @LINE:12
  private[this] lazy val controllers_ExchangeController_getbalance3_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("getbalance")))
  )
  private[this] lazy val controllers_ExchangeController_getbalance3_invoker = createInvoker(
    ExchangeController_2.getbalance,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.ExchangeController",
      "getbalance",
      Nil,
      "GET",
      this.prefix + """getbalance""",
      """""",
      Seq()
    )
  )

  // @LINE:13
  private[this] lazy val controllers_ExchangeController_gettranactions4_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("transactions")))
  )
  private[this] lazy val controllers_ExchangeController_gettranactions4_invoker = createInvoker(
    ExchangeController_2.gettranactions,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.ExchangeController",
      "gettranactions",
      Nil,
      "GET",
      this.prefix + """transactions""",
      """""",
      Seq()
    )
  )

  // @LINE:14
  private[this] lazy val controllers_ExchangeController_gettranactionbyid5_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("transactions/"), DynamicPart("transactionID", """[^/]+""",true)))
  )
  private[this] lazy val controllers_ExchangeController_gettranactionbyid5_invoker = createInvoker(
    ExchangeController_2.gettranactionbyid(fakeValue[Integer]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.ExchangeController",
      "gettranactionbyid",
      Seq(classOf[Integer]),
      "GET",
      this.prefix + """transactions/""" + "$" + """transactionID<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:15
  private[this] lazy val controllers_ExchangeController_getselloffers6_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("selloffers")))
  )
  private[this] lazy val controllers_ExchangeController_getselloffers6_invoker = createInvoker(
    ExchangeController_2.getselloffers,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.ExchangeController",
      "getselloffers",
      Nil,
      "GET",
      this.prefix + """selloffers""",
      """""",
      Seq()
    )
  )

  // @LINE:16
  private[this] lazy val controllers_ExchangeController_getsellofferbyid7_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("selloffers/"), DynamicPart("offerid", """[^/]+""",true)))
  )
  private[this] lazy val controllers_ExchangeController_getsellofferbyid7_invoker = createInvoker(
    ExchangeController_2.getsellofferbyid(fakeValue[String]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.ExchangeController",
      "getsellofferbyid",
      Seq(classOf[String]),
      "GET",
      this.prefix + """selloffers/""" + "$" + """offerid<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:17
  private[this] lazy val controllers_ExchangeController_buy8_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("buy/"), DynamicPart("maxrate", """[^/]+""",true), StaticPart("/"), DynamicPart("amount", """[^/]+""",true)))
  )
  private[this] lazy val controllers_ExchangeController_buy8_invoker = createInvoker(
    ExchangeController_2.buy(fakeValue[Integer], fakeValue[Integer]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.ExchangeController",
      "buy",
      Seq(classOf[Integer], classOf[Integer]),
      "POST",
      this.prefix + """buy/""" + "$" + """maxrate<[^/]+>/""" + "$" + """amount<[^/]+>""",
      """""",
      Seq()
    )
  )


  def routes: PartialFunction[RequestHeader, Handler] = {
  
    // @LINE:6
    case controllers_HomeController_index0_route(params@_) =>
      call { 
        controllers_HomeController_index0_invoker.call(HomeController_0.index)
      }
  
    // @LINE:8
    case controllers_Assets_versioned1_route(params@_) =>
      call(Param[String]("path", Right("/public")), params.fromPath[Asset]("file", None)) { (path, file) =>
        controllers_Assets_versioned1_invoker.call(Assets_1.versioned(path, file))
      }
  
    // @LINE:11
    case controllers_ExchangeController_addbalance2_route(params@_) =>
      call(params.fromPath[Integer]("amount", None)) { (amount) =>
        controllers_ExchangeController_addbalance2_invoker.call(ExchangeController_2.addbalance(amount))
      }
  
    // @LINE:12
    case controllers_ExchangeController_getbalance3_route(params@_) =>
      call { 
        controllers_ExchangeController_getbalance3_invoker.call(ExchangeController_2.getbalance)
      }
  
    // @LINE:13
    case controllers_ExchangeController_gettranactions4_route(params@_) =>
      call { 
        controllers_ExchangeController_gettranactions4_invoker.call(ExchangeController_2.gettranactions)
      }
  
    // @LINE:14
    case controllers_ExchangeController_gettranactionbyid5_route(params@_) =>
      call(params.fromPath[Integer]("transactionID", None)) { (transactionID) =>
        controllers_ExchangeController_gettranactionbyid5_invoker.call(ExchangeController_2.gettranactionbyid(transactionID))
      }
  
    // @LINE:15
    case controllers_ExchangeController_getselloffers6_route(params@_) =>
      call { 
        controllers_ExchangeController_getselloffers6_invoker.call(ExchangeController_2.getselloffers)
      }
  
    // @LINE:16
    case controllers_ExchangeController_getsellofferbyid7_route(params@_) =>
      call(params.fromPath[String]("offerid", None)) { (offerid) =>
        controllers_ExchangeController_getsellofferbyid7_invoker.call(ExchangeController_2.getsellofferbyid(offerid))
      }
  
    // @LINE:17
    case controllers_ExchangeController_buy8_route(params@_) =>
      call(params.fromPath[Integer]("maxrate", None), params.fromPath[Integer]("amount", None)) { (maxrate, amount) =>
        controllers_ExchangeController_buy8_invoker.call(ExchangeController_2.buy(maxrate, amount))
      }
  }
}
