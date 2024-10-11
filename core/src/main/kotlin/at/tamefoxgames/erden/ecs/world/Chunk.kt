package at.tamefoxgames.erden.ecs.world

import at.tamefoxgames.erden.ecs.templates.AirTemplate
import at.tamefoxgames.erden.ecs.factories.ModelFactory
import at.tamefoxgames.erden.ecs.templates.BlockTemplate
import com.badlogic.gdx.graphics.g3d.Environment
import com.badlogic.gdx.graphics.g3d.ModelBatch
import com.badlogic.gdx.graphics.g3d.ModelInstance
import com.badlogic.gdx.math.Vector3

class Chunk(
    val chunkSizeX: Int,
    val chunkSizeY: Int,
    val chunkSizeZ: Int
) {
    // 3D Array to hold block templates
    private val blocks: Array<Array<Array<BlockTemplate>>> = Array(chunkSizeX) {
        Array(chunkSizeY) {
            Array(chunkSizeZ) { AirTemplate }
        }
    }

    // Store instances for each block position
    private val blockInstances: MutableMap<Vector3, ModelInstance> = mutableMapOf()

    /**
     * Renders all visible blocks in the chunk.
     */
    fun render(modelBatch: ModelBatch, environment: Environment) {
        blockInstances.values.forEach { instance ->
            modelBatch.render(instance, environment)
        }
    }

    /**
     * Sets a block at the specified position.
     */
    fun setBlock(x: Int, y: Int, z: Int, blockTemplate: BlockTemplate) {
        blocks[x][y][z] = blockTemplate

        // If the block is not AIR, create or update the instance
        if (blockTemplate != AirTemplate) {
            val position = Vector3(x.toFloat(), y.toFloat(), z.toFloat())
            val model = ModelFactory.getModelForBlock(blockTemplate)

            val instance = blockInstances.getOrPut(position) {
                ModelInstance(model).apply {
                    transform.setToTranslation(position)
                }
            }

            // Update the model instance if needed (e.g. color or texture change)
            instance.materials.first().set(blockTemplate.color)
        } else {
            // If the block is AirTemplate, remove the instance
            blockInstances.remove(Vector3(x.toFloat(), y.toFloat(), z.toFloat()))
        }
    }

    /**
     * Gets the block template at the specified position.
     */
    fun getBlock(x: Int, y: Int, z: Int): BlockTemplate? {
        return if (x in 0 until chunkSizeX && y in 0 until chunkSizeY && z in 0 until chunkSizeZ) {
            blocks[x][y][z]
        } else {
            null  // out of bounds
        }
    }
}
