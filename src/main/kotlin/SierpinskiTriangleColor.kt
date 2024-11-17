import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.draw.Drawer
import org.openrndr.math.Vector2

fun main() = application {
    configure {
        width = 1024
        height = 768
    }

    program {
        val p1 = Vector2(width / 2.0, 50.0)
        val p2 = Vector2(50.0, height - 50.0)
        val p3 = Vector2(width - 50.0, height - 50.0)

        val depth = 6

        val colorMap = buildList {
            val colorSteps = buildList {
                val colorSteps = depth / 3
                val colorStep = 1.0 / colorSteps
                repeat(colorSteps) { iteration ->
                    add(1.0 - iteration * colorStep)
                }
            }
            colorSteps.forEach { r ->
                colorSteps.forEach { g ->
                    colorSteps.forEach { b ->
                        println("Color iter double $r $g $b")
                        add(ColorRGBa(r, g, b))
                    }
                }
            }
        }.reversed()
            .toTypedArray()

        extend {
            (depth downTo 1).forEach {
                drawer.stroke = colorMap[it]
                drawer.fill = null
                drawer.strokeWeight = 2.0

                drawSierpinskiTriangle(drawer, p1, p2, p3, it)
            }
        }
    }
}

private fun drawSierpinskiTriangle(drawer: Drawer, p1: Vector2, p2: Vector2, p3: Vector2, depth: Int) {
    if (depth == 0) {
        // Draw a triangle for the base case
        drawer.lineSegment(p1, p2)
        drawer.lineSegment(p2, p3)
        drawer.lineSegment(p3, p1)
    } else {
        // Calculate the midpoints of each side
        val mid1 = (p1 + p2) / 2.0
        val mid2 = (p2 + p3) / 2.0
        val mid3 = (p3 + p1) / 2.0

        // Recursively draw smaller triangles
        drawSierpinskiTriangle(drawer, p1, mid1, mid3, depth - 1)
        drawSierpinskiTriangle(drawer, mid1, p2, mid2, depth - 1)
        drawSierpinskiTriangle(drawer, mid3, mid2, p3, depth - 1)
    }
}
