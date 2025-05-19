package tacos.domain

data class Taco(
    var name: String = "",
    var ingredients: MutableList<Ingredient> = mutableListOf(),
)
