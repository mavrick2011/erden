package at.tamefoxgames.erden.ecs.components.block

import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute
import com.badlogic.gdx.graphics.Color

/**
 * Component that holds the color attribute of a block, using ColorAttribute for rendering.
 */
class BlockColorComponent(private val colorAttribute: ColorAttribute) {

    /**
     * Changes the color of this block by modifying the internal Color of the ColorAttribute.
     */
    fun setColor(r: Float, g: Float, b: Float, a: Float = 1f) {
        colorAttribute.color.set(r, g, b, a)
    }

    /**
     * Retrieves the current color of the block as a Color object.
     */
    fun getColor(): Color {
        return colorAttribute.color
    }

    /**
     * Sets a new color attribute.
     */
    fun setColorAttribute(newColorAttribute: ColorAttribute) {
        colorAttribute.color.set(newColorAttribute.color)
    }
}
