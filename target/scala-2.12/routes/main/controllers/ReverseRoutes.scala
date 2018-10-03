// @GENERATOR:play-routes-compiler
// @SOURCE:/home/jamesl/Desktop/csc750proj2/cryto-exchange/conf/routes
// @DATE:Wed Oct 03 18:33:43 EDT 2018

import play.api.mvc.Call


import _root_.controllers.Assets.Asset
import _root_.play.libs.F

// @LINE:6
package controllers {

  // @LINE:11
  class ReverseExchangeController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:16
    def getsellofferbyid(offerid:String): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "selloffers/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[String]].unbind("offerid", offerid)))
    }
  
    // @LINE:18
    def setdebugconfirmfail(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "debug/confirm_fail")
    }
  
    // @LINE:13
    def gettranactions(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "transactions")
    }
  
    // @LINE:11
    def addbalance(amount:Integer): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "addbalance/usd/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[Integer]].unbind("amount", amount)))
    }
  
    // @LINE:15
    def getselloffers(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "selloffers")
    }
  
    // @LINE:14
    def gettranactionbyid(transactionID:Integer): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "transactions/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[Integer]].unbind("transactionID", transactionID)))
    }
  
    // @LINE:19
    def setdebugconfirmno_response(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "debug/confirm_no_response")
    }
  
    // @LINE:17
    def buy(maxrate:Integer, amount:Integer): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "buy/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[Integer]].unbind("maxrate", maxrate)) + "/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[Integer]].unbind("amount", amount)))
    }
  
    // @LINE:12
    def getbalance(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "getbalance")
    }
  
    // @LINE:20
    def reset(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "debug/reset")
    }
  
  }

  // @LINE:6
  class ReverseHomeController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:6
    def index(): Call = {
      
      Call("GET", _prefix)
    }
  
  }

  // @LINE:8
  class ReverseAssets(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:8
    def versioned(file:Asset): Call = {
      implicit lazy val _rrc = new play.core.routing.ReverseRouteContext(Map(("path", "/public"))); _rrc
      Call("GET", _prefix + { _defaultPrefix } + "assets/" + implicitly[play.api.mvc.PathBindable[Asset]].unbind("file", file))
    }
  
  }


}
