import at.tamefoxgames.erden.camera.CameraBehavior
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.PerspectiveCamera
import kotlin.math.cos
import kotlin.math.sin

class FirstPersonCameraController(private val camera: PerspectiveCamera) : CameraBehavior {

    private val moveSpeed = 0.1f
    private var yaw = 0f
    private var pitch = 0f

    override fun handleInput() {
        // Movement keys (W, A, S, D)
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            camera.position.add(camera.direction.cpy().scl(moveSpeed))
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            camera.position.add(camera.direction.cpy().scl(-moveSpeed))
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            camera.position.add(camera.direction.cpy().crs(camera.up).nor().scl(-moveSpeed))
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            camera.position.add(camera.direction.cpy().crs(camera.up).nor().scl(moveSpeed))
        }

        val deltaX = -Gdx.input.deltaX * 0.1f
        val deltaY = -Gdx.input.deltaY * 0.1f
        yaw += deltaX
        pitch += deltaY
        pitch = pitch.coerceIn(-89f, 89f)

        camera.direction.set(
            -sin(Math.toRadians(yaw.toDouble())).toFloat(),
            sin(Math.toRadians(pitch.toDouble())).toFloat(),
            -cos(Math.toRadians(yaw.toDouble())).toFloat()
        ).nor()

        camera.update()
    }

    override fun update() {
        // No update logic needed for first-person view
    }

    override fun getCamera(): Camera {
        return camera
    }
}
