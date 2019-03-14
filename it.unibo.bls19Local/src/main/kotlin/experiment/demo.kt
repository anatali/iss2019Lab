package experiment



var counter = 0 //type inferred
fun square(v: Int) = v * v

fun incCounter() : Unit{ counter++ }
fun decCounter() { counter-- }

val getCounter = {    counter }

val fl = { print( "Return last exp val: " ); 100 }

val sum = { x:Int, y:Int -> x+y }

fun mirror(v: Int) : Pair<Int,Int> {
    return Pair(v, -v)
}

val f = ::sum

fun sToN( s: String, base: Int=10 ) : Int{
    var v = 0
    for( i in 0..s.length-1 ) {
        val cifra =  s[i].toInt()-48
        v = ( cifra ) + v*base
        //println( "s[$i]= ${cifra}   v=$v " )
    }
    return v
}

fun exec23( op:(Int,Int) -> Int ) : Int { return op(2,3) }

fun p2( op: ( Int ) -> Int ) : Int { return op(2) }

fun counterCreate()  : ( cmd : String ) -> Int {
    var localCounter = 0
    return {
        when (it) {
            "inc" -> ++localCounter
            "dec" -> --localCounter
            "val" -> localCounter
             else -> throw Exception( "unknown" )
        }
    }
}

fun xxx() : Unit {
    val action: ()->Unit = fun() { println("Hello") }
    action() //hello

    val sum: (Int)->Int  = fun(x) = x * x
    println("sum=${sum(1,2)}")	      //sum=3

    val greet: (String)->()->Unit = fun(m:String) = fun() { println("Printing $m") }
            greet( "Hello World" )() //Printing Hello World



}

fun main(){
    xxx()
}
