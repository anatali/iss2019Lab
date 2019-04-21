package it.unibo.kotlin.experiment

class Counter{
var v = 0
    fun inc(): Int{
        return ++v
    }
}
fun Counter.show() : String{
    return "counter($v)"
}
fun Counter.dec(): Int{
    this.v--
    return v
}

var Counter.value : Int
    get() = v
    set( x : Int ){ v = x }


fun String.lastChar() : Char = this.get(this.length-1)
val String.lastCharProp : Char
    get() = get( length-1 )

fun runCounter() {
    val c1 = Counter()
    println( c1.show() ) // counter(0)
    c1.inc();c1.inc();c1.dec()
    println( c1.show() ) // counter(1)

    c1.value = 10
    println( c1.value ) // counter(10)

    println( "hello!".lastChar() ) // !
    println( "hello!".lastCharProp ) // !

}


fun buildString0(
    builderAction: (StringBuilder) -> Unit ): String {
        val sb = StringBuilder()
        builderAction(sb)
        return sb.toString()
}

fun run0() {
    val s = buildString0 {
        it.append("Hello, ")
        it.append("World!")
    }
     println(s)     //Hello, World!
}
//------------------------------------------------
/*
    Use extension function type that describes a block of code
    that can be called as an extension function
*/
fun buildString1(
    builderAction: StringBuilder.() -> Unit ) : String {
        val sb = StringBuilder()
        sb.builderAction()
        return sb.toString()
}
fun run1() {
    val s = buildString1 {
        this.append("Hello, ")
        append("World!")
    }
    println(s)  //Hello, World!
}

//------------------------------------------------
val appendExcl : StringBuilder.() -> Unit =
    { this.append("!") }

fun run2(){
    val stringBuilder = StringBuilder("Hi")
    stringBuilder.appendExcl()
    println(stringBuilder)                  //hi!
    println(buildString1(appendExcl))       //!
}
//------------------------------------------------
fun run4(){
    val map = mutableMapOf(1 to "one")
    map.apply { this[2] = "two"}
    with (map) { this[3] = "three" }
    println(map)    //{1=one, 2=two, 3=three}
}
//------------------------------------------------

fun mybuildString (  //Use extension function type
        builderAction: StringBuilder.( s : String  ) -> Unit,
        v : String )
    : String {
        val sb = StringBuilder()
        sb.append(v)
        sb.builderAction(v)
        sb.append(v)
        return sb.toString()
    }


fun myrun() {
    val s = mybuildString({
        append("Hello, ")
        append("World")
    }, "|")
    println(s)  //Hello, World!
}

fun main(args: Array<String>) {
    runCounter()
    /*
    run0()
    run1()
    run2()
    run4()
    myrun()
    */
}
