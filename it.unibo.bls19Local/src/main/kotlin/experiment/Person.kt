package experiment

class Person(val name: String) {
    var age : Int = 0
    var married = false
        set( value ){
            if( age < 14 ) println("WARNING: too joung for marriage")
            else field = true
        }
    val isAdult: Boolean
        get(){ return age >= 18} //custom getter
}

fun main(){
    val p = Person("Bob")
    p.age = 22
    //println( "name=${p.name}, age=${p.age}, married=${p.married} adult=${p.isAdult} ")
    //p.age=22
    p.married = true
    println( "name=${p.name}, age=${p.age}, married=${p.married} adult=${p.isAdult} ")
}