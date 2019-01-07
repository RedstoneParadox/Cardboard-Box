package redstoneparadox.cardboardbox.misc

/**
 * Created by RedstoneParadox on 1/6/2019.
 */

/**
 * A helper class for creating RGBA colors. Values greater than 255 will be read as 255 and values less than 0
 * will be read as 0.
 *
 * @param red the value of red.
 * @param blue the value of blue.
 * @param green the value of green.
 * @param alpha the opacity value.
 */
class RGBAColor(red: Short, green: Short, blue: Short, alpha: Short) {


    /**
     * An alternate constructor with no transparency.
     */
    constructor(red: Short, green: Short, blue: Short) : this(red, green, blue, 255)

    private var red : Byte
    private var blue : Byte
    private var green : Byte
    private var alpha : Byte

    init {
        this.red = valueToChannel(red)
        this.blue = valueToChannel(blue)
        this.green = valueToChannel(green)
        this.alpha = valueToChannel(alpha)
    }

    fun getRedChannel() : Short {
        return channelToValue(red)
    }

    fun getBlueChannel() : Short {
        return channelToValue(blue)
    }

    fun getGreenChannel() : Short {
        return channelToValue(green)
    }

    fun getAlphaChannel() : Short {
        return channelToValue(alpha)
    }

    fun setRedChannel(value: Short) {
        red = valueToChannel(value)
    }

    fun setBlueChannel(value: Short) {
        blue = valueToChannel(value)
    }

    fun setGreenChannel(value: Short) {
        green = valueToChannel(value)
    }

    fun setAlphaChannel(value: Short) {
        alpha = valueToChannel(value)
    }

    fun percentRed() : Float {
        return channelToPercent(red)
    }

    fun percentBlue() : Float {
        return channelToPercent(blue)
    }

    fun percentGreen() : Float {
        return channelToPercent(green)
    }

    fun percentAlpha() : Float {
        return channelToPercent(alpha)
    }

    private fun valueToChannel(value : Short) : Byte {
        return when {
            value > 255 -> {127}
            value < 0 -> {-128}
            else -> {
                (value - 128).toByte()
            }
        }
    }

    private fun channelToValue(byte: Byte) : Short {
        return (byte + 128).toShort()
    }

    private fun channelToPercent(byte : Byte) : Float {
        return ((byte + 128)/255).toFloat()
    }

    override fun toString(): String {
        return "RGBAColor: {Red ${getRedChannel()}, Green ${getGreenChannel()}, Blue ${getBlueChannel()}, Alpha ${getAlphaChannel()}}"
    }

    /**
     * Helper enum for generating some common colors.
     */
    enum class Presets(private val red: Short, private val green: Short, private val blue: Short) {
        WHITE(255, 255, 255),
        BLACK(0, 0, 0),
        RED(255, 0, 0),
        GREEN(0, 255, 0),
        BLUE(0,0,255);

        fun pick() : RGBAColor {
            return RGBAColor(red, green, blue)
        }

        fun pick(alpha: Short) : RGBAColor {
            return RGBAColor(red, green, blue, alpha)
        }
    }
}