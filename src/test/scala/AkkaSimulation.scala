import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._

class AkkaSimulation extends Simulation {
  val httpProtocol = http
    .baseUrl("https://manga4all-akka.herokuapp.com")
    .acceptHeader("text/html,text/json.application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
    .basicAuth("", "")

  val scn = scenario("Manga 4 all Latest Scenario")
    .exec(http("Latest Manga Request")
      .get("/latest/1"))
    .exec(http("Popular manga request")
      .get("/popular/1"))


  setUp(
    scn.inject(
      nothingFor(4 seconds), // 1
      atOnceUsers(10), // 2
      rampUsers(10) during (5 seconds), // 3
      constantUsersPerSec(20) during (15 seconds), // 4
      constantUsersPerSec(20) during (15 seconds) randomized, // 5
//      rampUsersPerSec(10) to 20 during (10 minutes), // 6
//      rampUsersPerSec(10) to 20 during (10 minutes) randomized, // 7
      heavisideUsers(1000) during (20 seconds) // 8
    )
    .protocols(httpProtocol))
//    .assertions(global.responseTime.max.lt(2000))
}
