package at.tamefoxgames.erden.ecs.world

import at.tamefoxgames.erden.ecs.Entity
import at.tamefoxgames.erden.ecs.factories.BlockFactory
import at.tamefoxgames.erden.ecs.systems.RenderSystem
import at.tamefoxgames.erden.ecs.templates.BlockTemplate
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.g3d.Environment
import com.badlogic.gdx.graphics.g3d.ModelBatch
import com.badlogic.gdx.math.Vector3

class World(val chunkSizeX: Int, val chunkSizeZ: Int, val chunkHeight: Int) {
    val entities = mutableListOf<Entity>()
    val chunks = mutableMapOf<Pair<Int, Int>, Chunk>()
    private val worldGenerator = WorldGenerator(chunkSizeX, chunkSizeZ, chunkHeight)

    /**
     * Generate the entire world by generating multiple chunks.
     */
    fun generateWorld(chunkRangeX: Int, chunkRangeZ: Int) {
        for (chunkX in 0 until chunkRangeX) {
            for (chunkZ in 0 until chunkRangeZ) {
                generateChunk(chunkX, chunkZ)
            }
        }
    }

    /**
     * Generate a chunk at a given position.
     * Calls the WorldGenerator to create terrain based on Perlin noise.
     */
    fun generateChunk(chunkX: Int, chunkZ: Int) {
        val chunk = Chunk(chunkSizeX, chunkHeight, chunkSizeZ)
        worldGenerator.generateChunk(chunkX, chunkZ, chunk)
        chunks[Pair(chunkX, chunkZ)] = chunk
    }

    /**
     * Add a block at the specified position in the world.
     * It determines which chunk the block belongs to and adds it there.
     */
    fun addBlock(x: Float, y: Float, z: Float, blockTemplate: BlockTemplate) {
        // Calculate which chunk the block belongs to
        val chunkX = (x / chunkSizeX).toInt()
        val chunkZ = (z / chunkSizeZ).toInt()

        // Get the chunk from the map
        val chunk = chunks[Pair(chunkX, chunkZ)]

        if (chunk != null) {
            // Set the block within the chunk
            val blockX = (x % chunkSizeX).toInt()
            val blockY = y.toInt()
            val blockZ = (z % chunkSizeZ).toInt()

            chunk.setBlock(blockX, blockY, blockZ, blockTemplate)

            // Optionally, you can also convert the block to an entity and add it to the entity list
            val position = Vector3(x, y, z)
            val block = BlockFactory.createBlock(position, blockTemplate)
            entities.add(block)
        } else {
            println("No chunk found for position: ($x, $y, $z)")
        }
    }

    /**
     * Render the world using the RenderSystem, but only render chunks within the render distance.
     */
    fun render(renderSystem: RenderSystem, deltaTime: Float, camera: Camera, modelBatch: ModelBatch, environment: Environment) {
        // Filter chunks and render only those within the render distance
        for ((chunkPos, chunk) in chunks) {
            val (chunkX, chunkZ) = chunkPos

            // Check if the chunk is within the render distance
            if (shouldRenderChunk(chunkX, chunkZ, camera)) {
                chunk.render(modelBatch, environment) // Render the chunk using ModelBatch
            }
        }
        renderSystem.update(entities, deltaTime) // Render components via RenderSystem
    }

    /**
     * Define the maximum render distance in chunks.
     */
    private val maxRenderDistance = 8

    /**
     * Check if a chunk is within the render distance of the camera.
     */
    private fun shouldRenderChunk(chunkX: Int, chunkZ: Int, camera: Camera): Boolean {
        // Get chunk center position
        val chunkCenterX = chunkX * chunkSizeX + chunkSizeX / 2
        val chunkCenterZ = chunkZ * chunkSizeZ + chunkSizeZ / 2

        // Calculate distance from camera to chunk center
        val distance = Vector3(chunkCenterX.toFloat(), 0f, chunkCenterZ.toFloat()).dst(camera.position)

        // Convert distance to chunk scale and compare with max render distance
        return (distance / Math.max(chunkSizeX, chunkSizeZ)) <= maxRenderDistance
    }
}
