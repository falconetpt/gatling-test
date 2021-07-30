import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._

class AkkaSimulation extends Simulation {

//  configurations here
  val httpConfiguration = http.baseUrl("www.amazon.com")

//  define the scenario
  val scenarioName = scenario("amazon test")
  .exec(
    http("homepage test")
      .get("").check(status is 200)
  )

}
