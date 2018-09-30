// @GENERATOR:play-routes-compiler
// @SOURCE:/home/jamesl/Desktop/csc750proj2/cryto-exchange/conf/routes
// @DATE:Sun Sep 30 15:19:26 EDT 2018

import play.api.routing.JavaScriptReverseRoute


import _root_.controllers.Assets.Asset
import _root_.play.libs.F

// @LINE:6
package controllers.javascript {

  // @LINE:11
  class ReverseExchangeController(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:16
    def getsellofferbyid: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.ExchangeController.getsellofferbyid",
      """
        function(offerid0) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "selloffers/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[String]].javascriptUnbind + """)("offerid", offerid0))})
        }
      """
    )
  
    // @LINE:13
    def gettranactions: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.ExchangeController.gettranactions",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "transactions"})
        }
      """
    )
  
    // @LINE:11
    def addbalance: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.ExchangeController.addbalance",
      """
        function(amount0) {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "addbalance/usd/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[Integer]].javascriptUnbind + """)("amount", amount0))})
        }
      """
    )
  
    // @LINE:15
    def getselloffers: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.ExchangeController.getselloffers",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "selloffers"})
        }
      """
    )
  
    // @LINE:14
    def gettranactionbyid: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.ExchangeController.gettranactionbyid",
      """
        function(transactionID0) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "transactions/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[Integer]].javascriptUnbind + """)("transactionID", transactionID0))})
        }
      """
    )
  
    // @LINE:17
    def buy: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.ExchangeController.buy",
      """
        function(maxrate0,amount1) {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "buy/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[Integer]].javascriptUnbind + """)("maxrate", maxrate0)) + "/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[Integer]].javascriptUnbind + """)("amount", amount1))})
        }
      """
    )
  
    // @LINE:12
    def getbalance: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.ExchangeController.getbalance",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "getbalance"})
        }
      """
    )
  
  }

  // @LINE:6
  class ReverseHomeController(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:6
    def index: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.HomeController.index",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + """"})
        }
      """
    )
  
  }

  // @LINE:8
  class ReverseAssets(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:8
    def versioned: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.Assets.versioned",
      """
        function(file1) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "assets/" + (""" + implicitly[play.api.mvc.PathBindable[Asset]].javascriptUnbind + """)("file", file1)})
        }
      """
    )
  
  }


}
