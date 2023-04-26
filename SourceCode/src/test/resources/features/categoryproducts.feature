Feature: Category Products service at AltaShop

  @AltaStoreAPI @CategoryProductsAlta @GetAllCategoryProducts
  Scenario: User get all category products
    Given User call an api "/categories" with method "GET"
    Then User verify response is match with json schema "Allcategory.json"

  @AltaStoreAPI @CategoryProductsAlta @GetCategoryProductsById
  Scenario: User get category products by id
    Given User call an api "/categories/11293" with method "GET"
    Then User verify response is match with json schema "CategorybyId.json"

  @AltaStoreAPI @CategoryProductsAlta @PostCreateCategory
  Scenario: User create category products
    Given User is create new category products
    Then User verify status code is 200
    Then User verify response is match with json schema "Createcategory.json"

  @AltaStoreAPI @CategoryProductsAlta @DeleteCategoryProducts
  Scenario: User get delete category products
    Given User call an api "/categories/11292" with method "DELETE"
    Then User verify response is match with json schema "Deletecategory.json"