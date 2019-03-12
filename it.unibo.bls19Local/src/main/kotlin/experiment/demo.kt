package experiment

var counter = 0 //type inferred

fun incCounter() : Unit{ counter++ } //EXPRESSION BLOCK

fun main(){
    for( i in 1..5 ) incCounter()
    println( "counter=$counter")
}
