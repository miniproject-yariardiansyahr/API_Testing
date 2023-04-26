Feature: Products service at AltaShop

  @AltaStoreAPI @ProductsAlta @GetAllProducts
  Scenario: User get all products
    Given User call an api "/products" with method "GET"
    Then User verify response is match with json schema "Allproducts.json"

  @AltaStoreAPI @ProductsAlta @GetByIdProducts
  Scenario: User get products by ID
    Given User call an api "/products/11277" with method "GET"
    Then User verify response is match with json schema "GetByIdproducts.json"

  @AltaStoreAPI @ProductsAlta @GetRatingProducts
  Scenario: User get product rating
    Given User call an api "/products/11277/ratings" with method "GET"
    Then User verify response is match with json schema "GetRatingproducts.json"

  @AltaStoreAPI @ProductsAlta @GetCommentsProducts
  Scenario: User get product comments
    Given User call an api "/products/11277/comments" with method "GET"
    Then User verify response is match with json schema "GetCommentproducts.json"

  @AltaStoreAPI @ProductsAlta @CreateProducts
  Scenario:  User create a new product
    Given User is create a new product
    Then User verify status code is 200
    Then User verify response is match with json schema "Createproducts.json"

  @AltaStoreAPI @ProductsAlta @PostRatingProducts
  Scenario: User assign a product rating
    Given User input a product rating
    Then User verify status code is 200
    Then User verify response is match with json schema "Postratingproducts.json"

  @AltaStoreAPI @ProductsAlta @PostCommentProducts
  Scenario: User create a comment
    Given User create a comment on product
    Then User verify status code is 200
    Then User verify response is match with json schema "Postcommentproducts.json"

  @AltaStoreAPI @ProductsAlta @DeleteProducts
  Scenario: User delete a product
    Given User call an api "/products/11272" with method "DELETE"
    Then User verify response is match with json schema "Deleteproducts.json"