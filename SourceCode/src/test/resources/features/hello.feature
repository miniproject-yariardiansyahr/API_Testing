Feature: Hello Services AltaShopAPI

  @AltaStoreAPI @HelloAlta @GetIndex
  Scenario: user input hello
    Given User call an api "/hello" with method "GET"
    Then User verify response is match with json schema "Hello.json"