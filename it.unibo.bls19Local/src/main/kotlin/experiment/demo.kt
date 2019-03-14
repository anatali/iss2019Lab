package experiment



var counter = 0 //type inferred
fun square(v: Int) = v * v

fun incCounter() : Unit{ counter++ }
fun decCounter() { counter-- }

val getCounter = {    counter }

val fl = { print( "Return last exp val: " ); 100 }

fun sum(a:Int, b:Int) : Int  {
    return a+b
}

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

fun exec23( op: (first: Int, second: Int) -> Int ) : Int{
    return op(2,3)
}

fun p2( op: ( Int ) -> Int ) : Int { return op(2) }

fun xxx() : Unit {


    val v1 = p2( { x:Int-> x*x } )
    val v2 = p2( )  { x:Int-> x*x }
    val v3 =  p2 { x:Int-> x*x }
    val v4 =  p2 { x -> x*x }
    val v5 =  p2 { it -> it*it }
    println("v5=$v5")	      //v45=4


}
fun main(){
    xxx()
}
