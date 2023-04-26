Feature: Authentication AltaStoreAPI

  @AltaStoreAPI @AuthAlta @Register
  Scenario: User register with randomize identity
    Given User is call an api "/auth/register" with method "POST" with payload below
      | email | password | fullname |
      | randomEmail | randomPassword | randomFullname |
    Then User verify status code is 200
    Then User verify response is match with json schema "auth.json"

  @AltaStoreAPI @AuthAlta @Login
  Scenario: User login with given identity
    Given User is call an api "/auth/login" with method "POST" with payload below
      | email | password |
      | userEmail | userPassword |
    Then User verify status code is 200
    Then User verify response is match with json schema "login.json"

  @AltaStoreAPI @AuthAlta @Getinformations
  Scenario: User get informations
    Given User get other users informations
    Then User verify status code is 200
      Then User verify response is match with json schema "auth.json"