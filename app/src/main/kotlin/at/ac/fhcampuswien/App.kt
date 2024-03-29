/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package at.ac.fhcampuswien

class App {
    // Game logic for a number guessing game
    fun playNumberGame(digitsToGuess: Int = 4) {
        val generatedNumber = generateRandomNonRepeatingNumber(digitsToGuess)
        println("A random non-repeating number has been generated. Start guessing!")

        // Initialize `result` with a default value that guarantees the loop will execute at least once.
        var result = CompareResult(0, 0)

        do {
            println("Enter your guess:")
            val userInput = readlnOrNull()?.toIntOrNull()
            if (userInput == null || userInput.toString().length != digitsToGuess) {
                println("Please enter a valid $digitsToGuess-digit number with non-repeating digits.")
                continue // This will skip the rest of the loop iteration and go back to the beginning.
            }

            // Update `result` with the actual comparison result.
            result = checkUserInputAgainstGeneratedNumber(userInput, generatedNumber)
            println(result)

        } while (result.m != digitsToGuess) // This condition now correctly references `result`.

        println("Congratulations! You've guessed the correct number: $generatedNumber")
    }

    /**
     * Generates a non-repeating number of a specified length between 1-9.
     *
     * Note: The function is designed to generate a number where each digit is unique and does not repeat.
     * It is important to ensure that the length parameter does not exceed the maximum possible length
     * for non-repeating digits (which is 9 excluding 0 for base-10 numbers).
     *
     * @param length The length of the non-repeating number to be generated.
     *               This dictates how many digits the generated number will have.
     * @return An integer of generated non-repeating number.
     *         The generated number will have a number of digits equal to the specified length and will
     *         contain unique, non-repeating digits.
     * @throws IllegalArgumentException if the length is more than 9 or less than 1.
     */
    val generateRandomNonRepeatingNumber: (Int) -> Int = { length ->
        if (length < 1 || length > 9) throw IllegalArgumentException("Length must be between 1 and 9.")
        val digits = (1..9).shuffled().take(length).joinToString("").toInt()
        digits   // return value is a placeholder
    }

    /**
     * Compares the user's input integer against a generated number for a guessing game.
     * This function evaluates how many digits the user guessed correctly and how many of those
     * are in the correct position. The game generates number with non-repeating digits.
     *
     * Note: The input and the generated number must both be numbers.
     * If the inputs do not meet these criteria, an IllegalArgumentException is thrown.
     *
     * @param input The user's input integer. It should be a number with non-repeating digits.
     * @param generatedNumber The generated number with non-repeating digits to compare against.
     * @return [CompareResult] with two properties:
     *         1. `n`: The number of digits guessed correctly (regardless of their position).
     *         2. `m`: The number of digits guessed correctly and in the correct position.
     *         The result is formatted as "Output: m:n", where "m" and "n" represent the above values, respectively.
     * @throws IllegalArgumentException if the inputs do not have the same number of digits.
     */
    val checkUserInputAgainstGeneratedNumber: (Int, Int) -> CompareResult = { input, generatedNumber ->
        val inputStr = input.toString()
        val generatedStr = generatedNumber.toString()

        if (inputStr.length != generatedStr.length) throw IllegalArgumentException("Input and generated number must have the same number of digits.")

        var correctPositions = 0 // Digits that are in the exact correct position
        var correctDigits = 0 // Digits that match regardless of their position

        val seenInGenerated = BooleanArray(10) // Tracking digits seen in the generated number
        generatedStr.forEach { digit ->
            seenInGenerated[digit - '0'] = true
        }

        inputStr.forEachIndexed { index, c ->
            val digit = c - '0'
            if (seenInGenerated[digit]) {
                correctDigits++
                seenInGenerated[digit] = false // Avoid double counting
            }
            if (c == generatedStr[index]) {
                correctPositions++
            }
        }

        CompareResult(correctPositions, correctDigits) // Note the swapped order
    }
}

fun main() {
    val app = App()
    app.playNumberGame()
}
