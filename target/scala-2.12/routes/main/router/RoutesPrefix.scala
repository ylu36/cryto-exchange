// @GENERATOR:play-routes-compiler
// @SOURCE:/home/jamesl/Desktop/csc750proj2/cryto-exchange/conf/routes
// @DATE:Sun Sep 23 13:01:13 EDT 2018


package router {
  object RoutesPrefix {
    private var _prefix: String = "/"
    def setPrefix(p: String): Unit = {
      _prefix = p
    }
    def prefix: String = _prefix
    val byNamePrefix: Function0[String] = { () => prefix }
  }
}
