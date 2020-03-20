package simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class BasicSimulation extends Simulation {

  // Protocole de communication

  val httpProtocol

  // Scenario Fonctionnel

  val scn = scenario("BasicSimulation")
    .exec(http("authentication") // Nom de l'appel dans le rapport gatling
      .post(":8080/login") // appel HTTP POST sur la ressource REST localhost:8080/login
      .body(StringBody("{\"username\" : \"pgaultier\", \"password\" : \"password\"}")) // body du POST avec user/password
      .check(header("Authorization").saveAs("token")) // stockage du token JWT dans une variable token
    )
    .pause(2) // pause de 2 seconde pour simuler un vrai utilisateur
    .exec(http({{Nom_Sequence}})
      .get({{Adresse_Service_Web}})
      .header("Authorization", "${token}") // reutilisation du token JWT pour s'authentifier auprès d'un autre service
      .check(status.is(session => 200)) // Test du code de retour HTTP : 200 OK
    )
    .pause(2)

  // Setup du tir de charge

  setUp()

}
