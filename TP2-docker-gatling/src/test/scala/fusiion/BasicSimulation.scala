package simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class BasicSimulation extends Simulation {

  val httpProtocol = http
    .baseUrl("http://localhost")
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
    .doNotTrackHeader("1")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .acceptEncodingHeader("gzip, deflate")
    .userAgentHeader("Mozilla/5.0 (Windows NT 5.1; rv:31.0) Gecko/20100101 Firefox/31.0")

  val scn = scenario("BasicSimulation")
    .exec(http("authentication")
      .post(":8080/login")
      .body(StringBody("{\"username\" : \"pgaultier\", \"password\" : \"password\"}"))
      .check(header("Authorization").saveAs("token"))
    ).pause(2)
    .exec(http("Collaborateur/pgaultier")
    .get(":8083/collaborateurs/pgaultier@sii.fr")
    .header("Authorization", "${token}")
    .check(status.is(session => 200))
  ).pause(2)
  .exec(http("Competence/all")
    .get(":8081/competences")
    .header("Authorization", "${token}")
    .check(status.is(session => 200))
  ).pause(2)
  .exec(http("Competence/pgaultier")
    .get(":8081/competences/collaborateur/pgaultier@sii.fr")
    .header("Authorization", "${token}")
    .check(status.is(session => 200))
  ).pause(2)
  .exec(http("Clients/all")
    .get(":8084/clients")
    .header("Authorization", "${token}")
    .check(status.is(session => 200))
  )

  setUp(
    scn.inject(
      rampUsers(300) during (180 seconds))).protocols(httpProtocol)
    .assertions(
      global.successfulRequests.percent.gt(80),
      forAll.failedRequests.percent.lt(5)
    )

}
