import kotlin.browser.*
import kotlin.dom.*

fun main(args: Array<String>) {
    document.getElementById("tooltip")?.appendText("The first step")
}