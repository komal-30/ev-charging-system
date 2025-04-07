//package com.ev.charging.system.test;
//
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import org.apache.hc.client5.http.fluent.Request;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//
//import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
//import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
//import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
//import au.com.dius.pact.consumer.junit5.PactTestFor;
//import au.com.dius.pact.core.model.RequestResponsePact;
//
//@ExtendWith(PactConsumerTestExt.class)
//@PactTestFor(providerName = "ChargingStationProvider")
//public class ChargingStationPactTest {
//
//	@au.com.dius.pact.consumer.dsl.Pact(consumer = "ChargingStationConsumer")
//	public RequestResponsePact createPact(PactDslWithProvider builder) {
//		Map<String, String> headers = new HashMap<>();
//		headers.put("Content-Type", "application/json");
//
//		return builder.given("Charging station with ID 1 exists").uponReceiving("A request to GET charging station 1")
//				.path("/api/charging-stations/1").method("GET").willRespondWith().status(200).headers(headers)
//				.body(new PactDslJsonBody().integerType("id", 1).stringType("name", "EV Station 1")
//						.stringType("location", "Dublin"))
//				.toPact();
//	}
//
//	@Test
//	void testPactConsumer(MockServer mockServer) throws Exception {
//		String response = Request.get(mockServer.getUrl() + "/api/charging-stations/1").execute().returnContent()
//				.asString();
//
//		assertTrue(response.contains("EV Station 1"));
//	}
//}
//	