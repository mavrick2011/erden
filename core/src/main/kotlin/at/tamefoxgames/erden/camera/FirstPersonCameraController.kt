import at.tamefoxgames.erden.camera.CameraBehavior
import at.tamefoxgames.erden.ecs.world.World
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.PerspectiveCamera
import com.badlogic.gdx.math.Vector3
import kotlin.math.cos
import kotlin.math.sin

class FirstPersonCameraController(private val camera: PerspectiveCamera, private val world: World) : CameraBehavior {

    private val moveSpeed = 0.1f
    private val gravity = 0.02f // Adjust gravity as needed
    private val cameraHeight = 1.7f // Approximate height for the camera (like a person)
    private val mouseSensitivity = 0.1f
    private var yaw = 0f
    private var pitch = 0f

    init {
        // Lock the cursor (fix the mouse in the center)
        Gdx.input.isCursorCatched = true

        // Initialize the camera position at a safe height above the terrain
        positionCameraOnGround()
    }

    override fun handleInput() {

        // Check if ESC is pressed to unlock the cursor and exit the program
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.input.isCursorCatched = false  // Unlock the cursor
            Gdx.app.exit()  // Exit the application
        }
        // Handle camera movement
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            moveCamera(camera.direction.cpy().scl(moveSpeed))
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            moveCamera(camera.direction.cpy().scl(-moveSpeed))
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            moveCamera(camera.direction.cpy().crs(camera.up).nor().scl(-moveSpeed))
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            moveCamera(camera.direction.cpy().crs(camera.up).nor().scl(moveSpeed))
        }

        // Mouse look
        val deltaX = -Gdx.input.deltaX * mouseSensitivity
        val deltaY = -Gdx.input.deltaY * mouseSensitivity
        yaw += deltaX
        pitch += deltaY
        pitch = pitch.coerceIn(-89f, 89f) // Limit pitch to prevent camera flipping

        // Update camera direction based on yaw and pitch
        camera.direction.set(
            -sin(Math.toRadians(yaw.toDouble())).toFloat(),
            sin(Math.toRadians(pitch.toDouble())).toFloat(),
            -cos(Math.toRadians(yaw.toDouble())).toFloat()
        ).nor()

        camera.update()

        // Apply gravity if needed
        applyGravity()
    }

    override fun update() {
        // Update logic for camera, if necessary
    }

    override fun getCamera(): Camera {
        return camera
    }

    // Moves the camera while checking for collisions
    private fun moveCamera(delta: Vector3) {
        val oldPosition = camera.position.cpy()
        camera.position.add(delta)

        // Check for collisions and adjust position if necessary
        if (isColliding(camera.position)) {
            camera.position.set(oldPosition) // Reset to previous position if colliding
        }

        // Ensure the camera stays above the blocks
        applyGravity()
        camera.update()
    }

    // Simple gravity check to keep the camera on the ground
    private fun applyGravity() {
        val currentPosition = camera.position
        val groundHeight = getGroundHeight(currentPosition.x, currentPosition.z)

        if (currentPosition.y > groundHeight + cameraHeight) {
            camera.position.y -= gravity // Apply gravity if above the ground
        } else {
            camera.position.y = groundHeight + cameraHeight // Keep the camera standing on the block
        }
    }

    // Get the height of the ground at the specified X and Z positions
    private fun getGroundHeight(x: Float, z: Float): Float {
        val chunkX = (x / world.chunkSizeX).toInt()
        val chunkZ = (z / world.chunkSizeZ).toInt()

        val chunk = world.chunks[Pair(chunkX, chunkZ)] ?: return 0f // Return 0 if no chunk found

        for (y in world.chunkHeight downTo 0) {
            if (chunk.getBlock(x.toInt() % world.chunkSizeX, y, z.toInt() % world.chunkSizeZ) != null) {
                return y.toFloat()
            }
        }
        return 0f // Default to 0 if no block found
    }

    // Check if the camera is colliding with any blocks
    private fun isColliding(position: Vector3): Boolean {
        val groundHeight = getGroundHeight(position.x, position.z)
        return position.y <= groundHeight + cameraHeight // Check if camera is below block height
    }

    // Position the camera initially on the ground based on the terrain height
    private fun positionCameraOnGround() {
        val groundHeight = getGroundHeight(camera.position.x, camera.position.z)
        camera.position.y = groundHeight + cameraHeight // Start above the ground
        camera.update()
    }
}
