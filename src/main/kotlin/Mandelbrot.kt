import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.draw.ColorType
import org.openrndr.draw.colorBuffer
import org.openrndr.math.Vector2
import org.openrndr.math.map

fun main() = application {
    configure {
        width = 800
        height = 600
    }

    program {
        val maxIterations = 1000
        val bounds = Vector2(-2.5, -1.0) to Vector2(1.5, 1.0)

        // -- create a color buffer that uses 8 bits per channel (the default)
        val cb = colorBuffer(width, height, type = ColorType.UINT8)

        println("cb.format.componentCount: ${cb.format.componentCount}")
        println("cb.type.componentSize: ${cb.type.componentSize}")
        println("Pixel size: ${cb.format.componentCount * cb.type.componentSize}")
        println("Buffer size: ${cb.width * cb.height * cb.format.componentCount * cb.type.componentSize}")

        val shadow = cb.shadow

        val calculated = IntArray(height * width)

        for (x in 0 until width) {
            for (y in 0 until height) {
                val zx = map(0.0, width.toDouble(), bounds.first.x, bounds.second.x, x.toDouble())
                val zy = map(0.0, height.toDouble(), bounds.first.y, bounds.second.y, y.toDouble())

                var cx = zx
                var cy = zy

                var iteration = 0

                while (iteration < maxIterations) {
                    val xtemp = cx * cx - cy * cy + zx
                    cy = 2 * cx * cy + zy
                    cx = xtemp

                    if (cx * cx + cy * cy > 4) {
                        break
                    }

                    iteration++
                }

                val hue = 0xFFFFFFFF.toUInt() - map(0.0, maxIterations.toDouble(), 0.0, 0xFFFFFFFF.toDouble(), iteration.toDouble()).toUInt()

                calculated[x * height + y] = hue.toInt()
            }
        }



        extend {
            val diff = (seconds * 100 % 0xFFFFFFFF).toInt()
            for (x in 0 until width) {
                for (y in 0 until height) {
                    shadow[x, y] = ColorRGBa.fromHex(calculated[x * height + y] + diff)
                }
            }

            shadow.upload()

            drawer.image(cb)
        }
    }
}
