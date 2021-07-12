package info.ognibeni.memberservice

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.containsString
import org.junit.jupiter.api.Test

@QuarkusTest
class MemberResourceTest {

    @Test
    fun testHelloEndpoint() {
        given()
          .`when`().get("/members")
          .then()
             .statusCode(200)
             .body(containsString("Thilo"))
    }
}
