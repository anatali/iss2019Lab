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


open class Person(val name: String, val nickname: String = "rambo") {
    var age : Int = 0
    var married = false
        set( value ){
            if( age < 14 ) println("WARNING: too joung for marriage")
            else field = true
        }
    val isAdult: Boolean
        get(){ return age >= 18} //custom getter

     lateinit var country  : Origin //visible from outside
     protected var  voter  : Boolean //not visible
        get(){ return isAdult }
    init{
        //country = Origin.europa
        voter  =  (age > 18)   //expression
    }
    //custom accessor
    fun voter():Boolean{ return voter }
}

class Student(name: String,nickname: String="nerd") : Person(name, nickname) {

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


fun main(){
    val v1 = Expr.Num(10)
    val v2 = Expr.Num(20)
    val sum = Expr.Add(v1,v2)
    println("${v1.eval()} + ${v2.eval()} = ${sum.eval()}")
}