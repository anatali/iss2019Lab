package experiment



var counter = 0 //type inferred

fun xxx() : Unit{
    val s1 = "a"
    var s2 = "a"
    println( "s1 === s2 : ${s1 === s2} ") //true
    println( "s1 == s2  : ${s1 == s2} ") //true
    s2 = "b"
    println( "s1 === s2 : ${s1 === s2} ") //false
    s2 = "a"
    println( "s1 === s2 : ${s1 === s2} ") //true
    val a = java.io.File("a")
    val b = java.io.File("a")
    println( "a === b : ${a === b}") //false
    println( "a == b  : ${a == b}") //true

}

fun main(){
    xxx()
}
