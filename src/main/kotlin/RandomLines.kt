import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.math.Vector2
import kotlin.random.Random

fun main() = application {
    configure {
        width = 1024
        height = 768
    }

    program {

        val rnd = Random(System.currentTimeMillis())

        extend {
            drawer.fill = ColorRGBa.WHITE
            drawer.stroke = ColorRGBa.WHITE

            repeat(rnd.nextInt(1000)) {
                drawer.point(Vector2(rnd.nextInt(0, width).toDouble(), rnd.nextInt(0, height).toDouble()))
            }

            repeat(rnd.nextInt(1000)) {
                drawer.lineSegment(
                    Vector2(rnd.nextInt(0, width).toDouble(), rnd.nextInt(0, height).toDouble()),
                    Vector2(rnd.nextInt(0, width).toDouble(), rnd.nextInt(0, height).toDouble())
                )
            }
        }
    }
}