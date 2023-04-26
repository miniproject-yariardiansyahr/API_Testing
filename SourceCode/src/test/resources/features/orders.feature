Feature: Orders service at AltaShop

  @AltaStoreAPI @OrdersAlta @GetAllOrders
  Scenario: User get all orders
    Given User get all orders
    Then User verify status code is 200
    Then User verify response is match with json schema "Allorders.json"

  @AltaStoreAPI @OrdersAlta @GetOrdersbyid
  Scenario: User get orders by id
    Given User get all orders by id
    Then User verify status code is 200
    Then User verify response is match with json schema "Byidorders.json"

  @AltaStoreAPI @OrdersAlta @CreateOrders
  Scenario: User create new orders
    Given User create orders
    Then User verify status code is 200
    Then User verify response is match with json schema "Createorders.json"