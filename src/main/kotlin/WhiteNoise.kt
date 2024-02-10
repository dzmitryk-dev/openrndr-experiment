import org.openrndr.application
import org.openrndr.draw.ColorType
import org.openrndr.draw.colorBuffer
import java.nio.ByteBuffer
import kotlin.random.Random

fun main() = application {
    configure {
        width = 800
        height = 600
    }

    program {
        val rnd = Random(System.currentTimeMillis())

        // -- create a color buffer that uses 8 bits per channel (the default)
        val cb = colorBuffer(800, 600, type = ColorType.UINT8)

        // -- create a buffer (on CPU) that matches size and layout of the color buffer
        val buffer = ByteBuffer.allocateDirect(cb.width * cb.height * cb.format.componentCount * cb.type.componentSize)

        extend {
            // -- fill buffer with random data
            for (y in 0 until cb.height) {
                for (x in 0 until cb.width) {
                    for (c in 0 until cb.format.componentCount) {
                        buffer.put((Math.random() * 255).toInt().toByte())
                    }
                }
            }

            // -- rewind the buffer, this is essential as upload will be from the position we left the buffer at
            buffer.rewind()
            // -- write into color buffer
            cb.write(buffer)

            drawer.image(cb)
        }
    }
}