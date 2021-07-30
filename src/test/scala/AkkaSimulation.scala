import io.gatling.core.Predef._
import io.gatling.http.Predef._
import ru.tinkoff.gatling.amqp.Predef._
import ru.tinkoff.gatling.amqp.protocol.AmqpProtocolBuilder

import scala.concurrent.duration._

class AkkaSimulation extends Simulation {
//  configurations here
  val httpConfiguration = http.baseUrl("http://www.amazon.com")
  
  val amqpConfiguration = amqp
  .connectionFactory(
      rabbitmq
        .host("localhost")
        .port(5672)
        .username("guest")
        .password("guest")
    )
    .useNonPersistentDeliveryMode

//  define the scenario
  val scenarioName = scenario("amazon test")
  .exec(
    http("homepage test")
      .get("").check(status is 200)
  )
    .exec(
      http("book page")
        .get("/music/unlimited?ref_=nav_em__dm_hf_0_2_2_2")
        .check(status is 200)
    )
  setUp(
    scenarioName.inject(atOnceUsers(3), constantUsersPerSec(10).during(10 seconds))
  ).protocols(httpConfiguration)
}