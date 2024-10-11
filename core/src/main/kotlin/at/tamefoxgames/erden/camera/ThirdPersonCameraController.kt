package at.tamefoxgames.erden.camera

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.PerspectiveCamera
import com.badlogic.gdx.math.Vector3

class ThirdPersonCameraController(private val camera: PerspectiveCamera) : CameraBehavior {

    private val moveSpeed = 0.1f
    private var yaw = 0f
    private var pitch = 0f
    private val distanceToTarget = 5f  // Distance from the target (the character or object)

    // Position of the target the camera will follow (e.g., the player)
    private val targetPosition = Vector3(0f, 0f, 0f)

    override fun handleInput() {
        // Handle target movement with WASD
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            targetPosition.add(camera.direction.cpy().nor().scl(moveSpeed)) // Move target forward
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            targetPosition.add(camera.direction.cpy().nor().scl(-moveSpeed)) // Move target backward
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            targetPosition.add(camera.direction.cpy().crs(camera.up).nor().scl(-moveSpeed)) // Move target left
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            targetPosition.add(camera.direction.cpy().crs(camera.up).nor().scl(moveSpeed)) // Move target right
        }

        // Mouse movement for rotating the camera around the target
        val deltaX = -Gdx.input.deltaX * 0.5f
        val deltaY = -Gdx.input.deltaY * 0.5f
        yaw += deltaX
        pitch = (pitch + deltaY).coerceIn(-30f, 30f)  // Restrict pitch to avoid too much vertical rotation

        // Calculate camera's new position based on yaw and pitch
        val offsetX = Math.cos(Math.toRadians(yaw.toDouble())).toFloat() * distanceToTarget
        val offsetZ = Math.sin(Math.toRadians(yaw.toDouble())).toFloat() * distanceToTarget
        val offsetY = Math.sin(Math.toRadians(pitch.toDouble())).toFloat() * distanceToTarget

        camera.position.set(
            targetPosition.x + offsetX,
            targetPosition.y + offsetY,
            targetPosition.z + offsetZ
        )

        // Make the camera look at the target
        camera.lookAt(targetPosition)
        camera.update()
    }

    override fun update() {
        // No additional update logic needed for third-person camera
    }

    override fun getCamera(): Camera {
        return camera
    }
}



