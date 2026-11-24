// Stretch Challenge: Data class to represent recipe information cleanly
data class Recipe(
    val id: Int,
    var name: String,
    var category: String,
    var ingredients: MutableList<String>,
    var instructions: String,
    var prepTimeMinutes: Int
)

class RecipeOrganizer {
    // Stretch Challenge: Mutable collection to store and modify recipes
    private val recipes = mutableListOf<Recipe>()
    private var nextId = 1

    fun addRecipe() {
        print("Enter recipe name: ")
        val name = readln()

        print("Enter category (e.g., Dessert, Dinner): ")
        val category = readln()

        print("Enter ingredients (comma-separated): ")
        val ingredientsInput = readln()
        val ingredients = ingredientsInput.split(",").map { it.trim() }.toMutableList()

        print("Enter cooking instructions: ")
        val instructions = readln()

        print("Enter preparation time (in minutes): ")
        val prepTime = readln().toIntOrNull() ?: 0

        val newRecipe = Recipe(nextId++, name, category, ingredients, instructions, prepTime)
        recipes.add(newRecipe)
        println("✔ Recipe '${newRecipe.name}' added successfully!\n")
    }

    fun viewAllRecipes() {
        if (recipes.isEmpty()) {
            println("ℹ No recipes found in the organizer.\n")
            return
        }
        println("\n=== All Recipes ===")
        for (recipe in recipes) {
            printRecipeDetails(recipe)
        }
    }

    fun searchRecipe() {
        print("Enter recipe name to search: ")
        val query = readln().lowercase()
        val foundRecipes = recipes.filter { it.name.lowercase().contains(query) }

        if (foundRecipes.isEmpty()) {
            println("ℹ No recipes found matching '$query'.\n")
        } else {
            println("\n=== Search Results ===")
            for (recipe in foundRecipes) {
                printRecipeDetails(recipe)
            }
        }
    }

    fun editRecipe() {
        print("Enter the ID of the recipe to edit: ")
        val id = readln().toIntOrNull()
        val recipe = recipes.find { it.id == id }

        if (recipe == null) {
            println("❌ Recipe with ID $id not found.\n")
            return
        }

        print("Enter new name (leave blank to keep '${recipe.name}'): ")
        val nameInput = readln()
        if (nameInput.isNotBlank()) recipe.name = nameInput

        print("Enter new category (leave blank to keep '${recipe.category}'): ")
        val catInput = readln()
        if (catInput.isNotBlank()) recipe.category = catInput

        print("Enter new prep time in minutes (leave blank to keep ${recipe.prepTimeMinutes}): ")
        val timeInputStr = readln()
        if (timeInputStr.isNotBlank()) {
            val timeInput = timeInputStr.toIntOrNull()
            if (timeInput != null) recipe.prepTimeMinutes = timeInput
        }

        println("✔ Recipe updated successfully!\n")
    }

    fun deleteRecipe() {
        print("Enter the ID of the recipe to delete: ")
        val id = readln().toIntOrNull()
        val removed = recipes.removeIf { it.id == id }

        if (removed) {
            println("✔ Recipe deleted successfully!\n")
        } else {
            println("❌ Recipe with ID $id not found.\n")
        }
    }

    private fun printRecipeDetails(recipe: Recipe) {
        println("ID: ${recipe.id}")
        println("Name: ${recipe.name}")
        println("Category: ${recipe.category}")
        println("Prep Time: ${recipe.prepTimeMinutes} mins")
        println("Ingredients: ${recipe.ingredients.joinToString(", ")}")
        println("Instructions: ${recipe.instructions}")
        println("-----------------------------------")
    }
}

fun main() {
    val organizer = RecipeOrganizer()
    var running = true

    // Loop for continuous menu interaction
    while (running) {
        println("=== Recipe Organizer Menu ===")
        println("1. Add Recipe")
        println("2. View All Recipes")
        println("3. Search Recipe")
        println("4. Edit Recipe")
        println("5. Delete Recipe")
        println("6. Exit")
        print("Choose an option (1-6): ")

        // Stretch Challenge: varied uses of the 'when' keyword for flow control
        when (readln().trim()) {
            "1" -> organizer.addRecipe()
            "2" -> organizer.viewAllRecipes()
            "3" -> organizer.searchRecipe()
            "4" -> organizer.editRecipe()
            "5" -> organizer.deleteRecipe()
            "6" -> {
                running = false
                println("Goodbye!")
            }
            else -> println("❌ Invalid option. Please choose between 1 and 6.\n")
        }
    }
}