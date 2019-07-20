import java.util.*
// For information on how this works, check out the SoloLearn Machine Learning lessons

// This simple single-neuron brain can be taught to learn **very** simple patterns
// that take 2 integer inputs and returns a single integer answer.
class NeuralNetwork {
	private val random = Random()

	private var weight1: Float = random.nextFloat() % 1
	private var weight2: Float = random.nextFloat() % 1

	fun think(input1: Int, input2: Int): Int {
		val output: Float = weight1*input1 + weight2 * input2
		return output.toInt()
	}

	fun train(input1: Int, input2: Int, output: Int){
		val out = think(input1, input2)
		val error = output - out
		weight1 += (0.01f*error*input1)
		weight2 += (0.01f*error*input2)
	}

}
fun main(args: Array<String>){
	// going to make two different brains
	val nn_add_pairs = NeuralNetwork() // this one can add two single-digit numbers
	val nn_add_and_double = NeuralNetwork() // this one can add two numbers and multiply the result by 2


	// this training set teaches the brain to add two single digit numbers
	(0..1000).forEach {
		for(i in 1..10){
			val b = i+1
			nn_add_pairs.train(i, b, i+b)
		}
	}

	println("Brain that adds--\n")
	println("6 + 6 = " + nn_add_pairs.think(6, 6)) // should output 12 if training was sufficient
	println("3 + 4 = " + nn_add_pairs.think(3, 4)) // should output 7
	println("2 + 6 = " + nn_add_pairs.think(2, 6))
	println()


	// this training set teaches the brain to recognize the pattern (x + y) * 2
	// for example:
	// 4 and 6 = (4 + 6) * 2 = 20
	(0..1000).forEach {
		for ( i in 1..10 ){
			val b = i+1
			nn_add_and_double.train(i, b, (i+b)*2)
		}
	}

	println("Brain that adds then doubles--\n")
	println("(4 + 7)*2 = " + nn_add_and_double.think(4, 7)) // should output 22
	println("(6 + 8)*2 = " + nn_add_and_double.think(6, 8)) // should output 28
	println("(1 + 2)*2 = " + nn_add_and_double.think(1, 2)) // should be 6

}