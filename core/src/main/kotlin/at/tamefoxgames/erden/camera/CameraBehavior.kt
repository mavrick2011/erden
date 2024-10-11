package at.tamefoxgames.erden.camera

import com.badlogic.gdx.graphics.Camera

interface CameraBehavior {
    fun handleInput()  // Handle user input for camera movement
    fun update()       // Update the camera position and direction
    fun getCamera(): Camera
}
