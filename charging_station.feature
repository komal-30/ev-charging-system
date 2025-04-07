Feature: Charging Station API

Scenario: Get all charging stations
  Given url 'http://localhost:8080/api/charging-stations'
  When method GET
  Then status 200
  And match response == [{id: 1, name: 'Station 1'}, {id: 2, name: 'Station 2'}]

Scenario: Create a charging station
  Given url 'http://localhost:8080/api/charging-stations'
  And request { name: 'New Station' }
  When method POST
  Then status 201
  And match response.id == '#notnull'
  And match response.name == 'New Station'
