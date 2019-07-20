import java.util.*
// For information on how this works, check out the SoloLearn Machine Learning lessons

// This simple single-neuron brain can be taught to learn **very** simple patterns
// that take 2 integer inputs and returns a single integer answer.
class Neuron {
var state = false	
	
	fun activate(){
		state = true
	}	
	fun disable(){
		state = false
	}
	
	
	
 	fun identity(input1: Int, input2: Int): Int{
		val output: Int =  input1 * input2
		return output 	
	}

	fun testIdentity(){
//		var i1 = 0
//		var i2 = 0
		println(" 0,0 -> ${identity(0,0) }") 
		println(" 0,1 -> ${identity(0,1) }") 
		println(" 1,0 -> ${identity(1,0) }") 
		println(" 1,1 -> ${identity(1,1) }") 
	}
 
}
fun main(args: Array<String>){
	val sys = Neuron()
	println("Brain that adds--\n")
	sys.testIdentity()
}