package at.tamefoxgames.erden.camera

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.OrthographicCamera

class IsoOrthoCameraController(private val camera: OrthographicCamera) : CameraBehavior {

    private val moveSpeed = 0.1f  // Speed for camera movement
    private var zoomSpeed = 0.02f  // Speed for camera zooming

    override fun handleInput() {
        // Move with WASD keys along X and Y axes
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            camera.translate(0f, moveSpeed)  // Move up
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            camera.translate(0f, -moveSpeed)  // Move down
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            camera.translate(-moveSpeed, 0f)  // Move left
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            camera.translate(moveSpeed, 0f)  // Move right
        }

        // Zoom in and out with mouse wheel or keyboard keys
        if (Gdx.input.isKeyJustPressed(Input.Keys.PLUS)) {
            camera.zoom = (camera.zoom - zoomSpeed).coerceAtLeast(0.1f)  // Minimum zoom of 0.1 to prevent being too close
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.MINUS)) {
            camera.zoom = (camera.zoom + zoomSpeed).coerceAtMost(10f)  // Maximum zoom of 10 to prevent too far zoom
        }

        // Mouse movement for panning (moving the camera without rotation)
        if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
            val deltaX = -Gdx.input.deltaX * moveSpeed
            val deltaY = Gdx.input.deltaY * moveSpeed
            camera.translate(deltaX, deltaY)
        }

        camera.update()
    }

    override fun update() {
        // No additional logic required for update
    }

    override fun getCamera(): Camera {
        return camera
    }
}




