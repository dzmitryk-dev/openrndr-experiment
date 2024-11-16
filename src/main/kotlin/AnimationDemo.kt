import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.draw.loadFont
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

fun main() = application {
    configure {
        width = 800
        height = 600
    }

    program {
        val font = loadFont("data/fonts/default.otf", 15.0)
        var frame: ULong = 0u

        drawer.fontMap = font

        val orbitRadius = min(width / 2.0, height / 2.0)

        extend {
            drawer.fill = ColorRGBa.WHITE
            drawer.text("Frame: $frame Seconds: ${String.format("%.2f", seconds)}", 5.0, 25.0)

            // Draw a white circle that would represent the trajectory of the pink circle
            drawer.fill = ColorRGBa.BLACK
            drawer.stroke = ColorRGBa.WHITE
            drawer.circle( width / 2.0, height / 2.0, orbitRadius)

            // Draw a small red circle in the center of the screen
            drawer.fill = ColorRGBa.RED
            drawer.stroke = ColorRGBa.WHITE
            drawer.circle(width / 2.0, height / 2.0, 5.0)

            // Draw a circle that moves in a circle around the center of the screen
            drawer.fill = ColorRGBa.YELLOW
            drawer.circle(cos(seconds) * orbitRadius + width / 2.0, sin(seconds) * orbitRadius + height / 2.0, 25.0)

            // Draw a circle that moves in a circle around the center of the screen
            drawer.fill = ColorRGBa.PINK
            drawer.circle(cos(seconds) * width / 2.0 + width / 2.0, sin(seconds) * height / 2.0 + height / 2.0, 25.0)

            frame++
        }
    }
}