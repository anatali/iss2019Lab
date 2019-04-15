package experiment
enum class Origin{
    asia, africa, europa, america, australia
}


enum class Color( //properties
    var r: Int, val g: Int, val b: Int){
//Property values for each constant
    RED(255,0,0),
    YELLOW(255,255,0) //declares its own anonymous class
    { override fun toString():String{return "YELLOW_COLOR"} },
    GREEN(0,255,0), BLUE(0,0, 255)
    ; //semicolon is is mandatory if define methods

    fun rgb() = (r * 256 + g) * 256 + b
    override fun toString() : String {
        return "${super.toString()}($r,$g,$b)" }
}




sealed class Expr{
    class Num( val value:Int):Expr()
    class Add( val left:Expr, val right:Expr):Expr()
    fun eval():Int{
        when( this ){
            is Num -> return value
            is Add -> return left.eval() + right.eval()
            //no deafult branch
        }
    }
}


/*
data class Person(val name: String) {
    var age : Int = 0     //public
    var married = false   //public
    val isAdult: Boolean
        get(){ return age >= 18} //custom getter
}

fun main(){
    val p1 = Person("Bob")
    p1.age=20
    val p3 = Person("Bob")
    p3.age= p1.age
    println( "p1=${p1}, p3=${p3} ")
    println( "equals:  ${p1.equals(p3)}" )
}
*/
    /*
    Person.showAllPersons()
    Person.About.showAllAdults()
    Person.About.showOrderedByName()
    */
//    val persons = listOf( p1,p2 )
//    println( persons.sortedWith( Person.NameComparator ))
