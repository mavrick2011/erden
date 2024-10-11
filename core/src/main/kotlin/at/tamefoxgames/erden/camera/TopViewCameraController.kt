import at.tamefoxgames.erden.camera.CameraBehavior
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.Vector3

class TopViewCameraController(private val camera: OrthographicCamera) : CameraBehavior {

    private val moveSpeed = 0.1f

    override fun handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            camera.translate(Vector3(0f, 0f, -moveSpeed))
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            camera.translate(Vector3(0f, 0f, moveSpeed))
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            camera.translate(Vector3(-moveSpeed, 0f, 0f))
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            camera.translate(Vector3(moveSpeed, 0f, 0f))
        }

        camera.update()
    }

    override fun update() {
        // No update logic needed for top-down view
    }

    override fun getCamera(): OrthographicCamera {
        return camera
    }
}
