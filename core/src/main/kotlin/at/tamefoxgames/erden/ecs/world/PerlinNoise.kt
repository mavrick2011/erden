package at.tamefoxgames.erden.ecs.world

import kotlin.math.floor
import kotlin.random.Random

class PerlinNoise(private val seed: Int = Random.nextInt()) {

    private val grad3 = arrayOf(
        intArrayOf(1, 1, 0), intArrayOf(-1, 1, 0), intArrayOf(1, -1, 0), intArrayOf(-1, -1, 0),
        intArrayOf(1, 0, 1), intArrayOf(-1, 0, 1), intArrayOf(1, 0, -1), intArrayOf(-1, 0, -1),
        intArrayOf(0, 1, 1), intArrayOf(0, -1, 1), intArrayOf(0, 1, -1), intArrayOf(0, -1, -1)
    )

    private val p: IntArray = IntArray(512)

    init {
        val perm = IntArray(256) { it }
        perm.shuffle(Random(seed))
        for (i in 0 until 512) {
            p[i] = perm[i and 255]
        }
    }

    private fun fade(t: Float): Float {
        return t * t * t * (t * (t * 6 - 15) + 10)
    }

    private fun lerp(t: Float, a: Float, b: Float): Float {
        return a + t * (b - a)
    }

    private fun grad(hash: Int, x: Float, y: Float, z: Float): Float {
        val h = hash and 15
        val u = if (h < 8) x else y
        val v = if (h < 4) y else if (h == 12 || h == 14) x else z
        return (if (h and 1 == 0) u else -u) + (if (h and 2 == 0) v else -v)
    }

    fun noise(x: Float, y: Float, z: Float): Float {
        val X = floor(x).toInt() and 255
        val Y = floor(y).toInt() and 255
        val Z = floor(z).toInt() and 255

        val xf = x - floor(x)
        val yf = y - floor(y)
        val zf = z - floor(z)

        val u = fade(xf)
        val v = fade(yf)
        val w = fade(zf)

        val a = p[X] + Y
        val aa = p[a] + Z
        val ab = p[a + 1] + Z
        val b = p[X + 1] + Y
        val ba = p[b] + Z
        val bb = p[b + 1] + Z

        return lerp(w,
            lerp(v, lerp(u, grad(p[aa], xf, yf, zf), grad(p[ba], xf - 1, yf, zf)),
                lerp(u, grad(p[ab], xf, yf - 1, zf), grad(p[bb], xf - 1, yf - 1, zf))),
            lerp(v, lerp(u, grad(p[aa + 1], xf, yf, zf - 1), grad(p[ba + 1], xf - 1, yf, zf - 1)),
                lerp(u, grad(p[ab + 1], xf, yf - 1, zf - 1), grad(p[bb + 1], xf - 1, yf - 1, zf - 1)))
        )
    }
}
