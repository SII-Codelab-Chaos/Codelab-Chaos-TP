package simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class BasicSimulation extends Simulation {

  // Protocole de communication

  val httpProtocol

  // Scenario Fonctionnel

  val scn

  // Setup du tir de charge

  setUp()

}
