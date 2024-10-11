package at.tamefoxgames.erden.ecs.factories

import at.tamefoxgames.erden.ecs.templates.BlockTemplate
import com.badlogic.gdx.graphics.g3d.Model
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder
import com.badlogic.gdx.graphics.VertexAttributes
import com.badlogic.gdx.graphics.g3d.Material

object ModelFactory {

    private val modelCache: MutableMap<BlockTemplate, Model> = mutableMapOf()

    /**
     * Returns the model for a given block template.
     * It either retrieves the model from the cache or creates a new one if it doesn't exist.
     */
    fun getModelForBlock(blockTemplate: BlockTemplate): Model {
        // Check if the model is already in the cache
        return modelCache.getOrPut(blockTemplate) {
            // If not, create a new model using ModelBuilder with the size from BlockTemplate
            val modelBuilder = ModelBuilder()
            modelBuilder.createBox(
                blockTemplate.size.x,  // Width
                blockTemplate.size.y,  // Height
                blockTemplate.size.z,  // Depth
                Material(blockTemplate.color),  // Apply the block's color from the template
                (VertexAttributes.Usage.Position or VertexAttributes.Usage.Normal).toLong()
            )
        }
    }

    /**
     * Clears the model cache when no longer needed to free up memory.
     */
    fun clearCache() {
        modelCache.values.forEach { it.dispose() }
        modelCache.clear()
    }
}
