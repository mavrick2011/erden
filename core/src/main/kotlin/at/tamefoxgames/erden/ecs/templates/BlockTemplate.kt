package at.tamefoxgames.erden.ecs.templates

import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute
import com.badlogic.gdx.math.Vector3

// Blueprint/Template for block types
// Base block template with default values
open class BlockTemplate(
    val color: ColorAttribute = ColorAttribute.createDiffuse(1f, 1f, 1f, 1f),  // Default white color
    val size: Vector3 = Vector3(1f, 1f, 1f),  // Default size 1x1x1
    val isSolid: Boolean = true,  // Default solid
    val hardness: Float = 1.0f  // Default hardness
)

val HalfBlockTemplate = BlockTemplate(
    size = Vector3(1f, 0.5f, 1f)  // Half block size (1x0.5x1)
)

// Define some block templates
val GrassTemplate = BlockTemplate(
    color = ColorAttribute.createDiffuse(0f, 1f, 0f, 1f),
)

val StoneTemplate = BlockTemplate(
    color = ColorAttribute.createDiffuse(0.5f, 0.5f, 0.5f, 1f),
    hardness = 1.5f
)

val WaterTemplate = BlockTemplate(
    color = ColorAttribute.createDiffuse(0f, 0f, 1f, 0.5f),
    isSolid = false,
    hardness = 0f
)

val AirTemplate = BlockTemplate(
    color = ColorAttribute.createDiffuse(0f, 0f, 0f, 0f),
    isSolid = false,
    hardness = 0f
)
