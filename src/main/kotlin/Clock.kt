import org.openrndr.application
import org.openrndr.color.ColorRGBa
import java.time.LocalDateTime

fun main() = application {
    configure {
        width = 800
        height = 800
    }

    program {
        extend {
            drawer.clear(ColorRGBa.WHITE)
            val centerX = width / 2.0
            val centerY = height / 2.0
            val clockRadius = 300.0

            // Draw clock face
            drawer.stroke = ColorRGBa.BLACK
            drawer.strokeWeight = 4.0
            drawer.circle(centerX, centerY, clockRadius)

            // Draw hour markers
            drawer.strokeWeight = 8.0
            for (i in 0 until 12) {
                val angle = Math.PI / 6 * i - Math.PI / 2
                val x = centerX + Math.cos(angle) * (clockRadius - 40)
                val y = centerY + Math.sin(angle) * (clockRadius - 40)
                drawer.lineSegment(x, y, x + Math.cos(angle) * 20, y + Math.sin(angle) * 20)
            }

            // Get current time
            val now = LocalDateTime.now()
            val hours = now.hour % 12 + now.minute / 60.0
            val minutes = now.minute + now.second / 60.0
            val seconds = now.second

            // Draw hour hand
            val hourAngle = Math.PI / 6 * hours - Math.PI / 2
            drawer.strokeWeight = 12.0
            drawer.lineSegment(
                centerX,
                centerY,
                centerX + Math.cos(hourAngle) * (clockRadius - 100),
                centerY + Math.sin(hourAngle) * (clockRadius - 100)
            )

            // Draw minute hand
            val minuteAngle = Math.PI / 30 * minutes - Math.PI / 2
            drawer.strokeWeight = 8.0
            drawer.lineSegment(
                centerX,
                centerY,
                centerX + Math.cos(minuteAngle) * (clockRadius - 60),
                centerY + Math.sin(minuteAngle) * (clockRadius - 60)
            )

            // Draw second hand
            val secondAngle = Math.PI / 30 * seconds - Math.PI / 2
            drawer.strokeWeight = 4.0
            drawer.stroke = ColorRGBa.RED
            drawer.lineSegment(
                centerX,
                centerY,
                centerX + Math.cos(secondAngle) * (clockRadius - 40),
                centerY + Math.sin(secondAngle) * (clockRadius - 40)
            )
        }
    }
}
