package at.tamefoxgames.erden.camera

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.PerspectiveCamera
import com.badlogic.gdx.math.Vector3

/**
 * Base class for camera controllers, handling basic movement logic.
 */
open class CameraController(protected val camera: PerspectiveCamera) {

    protected val moveSpeed = 0.1f  // Basic movement speed

    /**
     * Handles basic movement input. Specialized classes should override or extend this.
     */
    open fun handleInput() {
        val forward = Vector3(camera.direction.x, 0f, camera.direction.z).nor()
        val right = Vector3(camera.direction.z, 0f, -camera.direction.x).nor()

        // Movement keys (W, A, S, D)
        if (Gdx.input.isKeyPressed(Input.Keys.W)) camera.position.add(forward.scl(moveSpeed))
        if (Gdx.input.isKeyPressed(Input.Keys.S)) camera.position.add(forward.scl(-moveSpeed))
        if (Gdx.input.isKeyPressed(Input.Keys.A)) camera.position.add(right.scl(-moveSpeed))
        if (Gdx.input.isKeyPressed(Input.Keys.D)) camera.position.add(right.scl(moveSpeed))

        // Update the camera after movement
        camera.update()
    }
}
