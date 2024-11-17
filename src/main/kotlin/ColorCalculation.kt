import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.math.Vector3

@OptIn(ExperimentalStdlibApi::class)
fun main() = application {
    configure {
        width = 1024
        height = 768
    }
    program {
        val steps = 30
        val step = 0xFFFFFF / steps

        val rectangleHeight = height.toDouble() / 4
        val rectangleWidth = (width / steps).toDouble()

        println("step: ${step.toHexString()}")
        val colorMapHex = (0xFFFFFF downTo 0x000000 step step)
            .onEach { println("hexString = ${it.toHexString()}") }
            .map { ColorRGBa.fromHex(it) }
            .onEach { println("color hex $it") }
            .toTypedArray()

        val colorMapDouble = buildList {
            val stepDouble = 1.0 / steps
            repeat(steps) { iteration ->
                add(1.0 - iteration * stepDouble)
            }
        }.onEach { println("Double $it") }
            .map { ColorRGBa.fromVector(Vector3(it, it, it)) }
            .onEach { println("color double $it") }
            .toTypedArray()

        val colorMapIter = buildList {
            val colorSteps = buildList {
                val colorSteps = steps / 3
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
        }.onEach { println("color iter $it") }
            .toTypedArray()

        val rainbowGradient = buildList {
            val colorSteps = steps / 3
            val colorStep = 1.0 / colorSteps
            repeat(colorSteps) { iteration ->
                add(ColorRGBa(1.0, iteration * colorStep, 0.0))
            }
            repeat(colorSteps) { iteration ->
                add(ColorRGBa(1.0 - iteration * colorStep, 1.0, 0.0))
            }
            repeat(colorSteps) { iteration ->
                add(ColorRGBa(0.0, 1.0, iteration * colorStep))
            }
        }.onEach { println("color rainbow $it") }
            .toTypedArray()

        extend {
            repeat(steps) { i ->
                drawer.stroke = colorMapHex[i]
                drawer.fill = colorMapHex[i]
                drawer.rectangle(rectangleWidth * i, 0.0, rectangleWidth, rectangleHeight)

                drawer.stroke = colorMapDouble[i]
                drawer.fill = colorMapDouble[i]
                drawer.rectangle(rectangleWidth * i, rectangleHeight, rectangleWidth, rectangleHeight)

                drawer.stroke = colorMapIter[i]
                drawer.fill = colorMapIter[i]
                drawer.rectangle(rectangleWidth * i, rectangleHeight * 2, rectangleWidth, rectangleHeight)

                drawer.stroke = rainbowGradient[i]
                drawer.fill = rainbowGradient[i]
                drawer.rectangle(rectangleWidth * i, rectangleHeight * 3, rectangleWidth, rectangleHeight)
            }
        }
    }
}