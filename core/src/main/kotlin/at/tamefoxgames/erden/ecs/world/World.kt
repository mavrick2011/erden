package at.tamefoxgames.erden.ecs.world

import at.tamefoxgames.erden.ecs.Entity
import at.tamefoxgames.erden.ecs.factories.BlockFactory
import at.tamefoxgames.erden.ecs.systems.RenderSystem
import at.tamefoxgames.erden.ecs.templates.BlockTemplate
import com.badlogic.gdx.math.Vector3

class World(private val chunkSizeX: Int, private val chunkSizeZ: Int, private val chunkHeight: Int) {
    val entities = mutableListOf<Entity>()
    private val chunks = mutableMapOf<Pair<Int, Int>, Chunk>()
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

        // Convert chunk blocks into entities and add to the world
        for (x in 0 until chunkSizeX) {
            for (y in 0 until chunkHeight) {
                for (z in 0 until chunkSizeZ) {
                    val blockTemplate = chunk.getBlock(x, y, z)
                    if (blockTemplate != null) {
                        addBlock(
                            x.toFloat() + chunkX * chunkSizeX,
                            y.toFloat(),
                            z.toFloat() + chunkZ * chunkSizeZ,
                            blockTemplate
                        )
                    }
                }
            }
        }
    }

    /**
     * Add a block at a specific position using a BlockTemplate.
     */
    fun addBlock(x: Float, y: Float, z: Float, blockTemplate: BlockTemplate) {
        val position = Vector3(x, y, z)
        val block = BlockFactory.createBlock(position, blockTemplate)
        entities.add(block)
    }

    /**
     * Render the entire world using the RenderSystem.
     */
    fun render(renderSystem: RenderSystem, deltaTime: Float) {
        renderSystem.update(entities, deltaTime)
    }
}
