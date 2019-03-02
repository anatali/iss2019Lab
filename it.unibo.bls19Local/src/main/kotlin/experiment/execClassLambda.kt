package experiment

var vglobal : Int = 100
fun silly() :  Unit  { print(" %SILLY TRACE% ") }

class useful(){
    fun inc(x:Int):Int = x+vglobal++  //action with side-effect
}

class aClass( logo: String,         //primary constructor with parameter logo
              val name: String ){   //and property name
    val myLogo = logo               //property myLogo initialized with parameter
    //Execute a 'procedure' with no args given  in input to method exec
    fun exec( f:()->Unit ){ print("$myLogo-$name exec| "); f() ; println(" | $myLogo done") }
    fun run(  f:(arg : useful)-> Unit ){  //SIGNATURE
        //val u=useful(); print("$u");
        print(" | $myLogo-$name run | "); f( useful() ) ; println(" | $myLogo done")
    }
}
fun classThatExecutesLamda() {
    val a = aClass("EXAMPLE", "Bob")
    a.exec( { print("hello") } )
    println("vglobal= $vglobal")    //100
    a.run(  { print("hello") } )
    a.run(  { print("${it.inc(10)}") } )
    println("vglobal= $vglobal")    //101
    a.exec( ::silly  )
}


fun main(args: Array<String>) {
    println("--------------- classThatExecutesALamda ----------")
    classThatExecutesLamda()
}