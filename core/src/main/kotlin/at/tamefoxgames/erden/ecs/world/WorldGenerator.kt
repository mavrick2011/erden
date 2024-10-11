package at.tamefoxgames.erden.ecs.world

import at.tamefoxgames.erden.ecs.templates.GrassTemplate
import at.tamefoxgames.erden.ecs.templates.StoneTemplate

class WorldGenerator(
    private val chunkSizeX: Int,
    private val chunkSizeZ: Int,
    private val maxHeight: Int,
    private val noiseScale: Float = 0.05f  // Scales Perlin noise for smoother terrain
) {
    private val perlinNoise = PerlinNoise()  // Create a Perlin noise generator

    /**
     * Calculates the height of a block at the coordinates (x, z) using Perlin noise.
     */
    fun getHeight(x: Int, z: Int): Int {
        val noiseValue = perlinNoise.noise(x * noiseScale, 0f, z * noiseScale)
        return ((noiseValue + 1) / 2 * maxHeight).toInt().coerceIn(0, maxHeight)
    }

    /**
     * Generates blocks for a chunk based on the Perlin noise height.
     * Fills the chunk with stone up to the height, then places grass on top.
     */
    fun generateChunk(chunkX: Int, chunkZ: Int, chunk: Chunk) {
        for (x in 0 until chunkSizeX) {
            for (z in 0 until chunkSizeZ) {
                val worldX = chunkX * chunkSizeX + x
                val worldZ = chunkZ * chunkSizeZ + z
                val height = getHeight(worldX, worldZ)

                // Fill the chunk with stone up to the calculated height
                for (y in 0 until height) {
                    chunk.setBlock(x, y, z, StoneTemplate)
                }

                // Place grass on the top layer
                if (height < maxHeight) {
                    chunk.setBlock(x, height, z, GrassTemplate)
                }
            }
        }
    }
}
