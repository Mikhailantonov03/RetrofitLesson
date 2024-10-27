package com.hfad.retrofitlesson.retrofit

data class Product(
    val id: Int,
    val title: String,
    val category: String
)

data class ProductsResponse(
    val products: List<Product>,
    val total: Int,
    val skip: Int,
    val limit: Int
)
