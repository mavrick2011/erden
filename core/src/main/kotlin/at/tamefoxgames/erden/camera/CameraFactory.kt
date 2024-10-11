import at.tamefoxgames.erden.camera.CameraBehavior
import at.tamefoxgames.erden.camera.CameraType
import at.tamefoxgames.erden.camera.IsoOrthoCameraController
import at.tamefoxgames.erden.camera.ThirdPersonCameraController
import at.tamefoxgames.erden.ecs.world.World
import com.badlogic.gdx.graphics.PerspectiveCamera
import com.badlogic.gdx.graphics.OrthographicCamera

object CameraFactory {
    fun createCamera(type: CameraType, world: World): CameraBehavior {
        return when (type) {
            CameraType.FIRST_PERSON -> FirstPersonCameraController(
                PerspectiveCamera(67f, 16f, 9f).apply {
                    position.set(0f, 2f, 6f)  // Start behind and slightly above the origin
                    lookAt(0f, 0f, 0f)  // Look at the cube in the center
                    near = 0.1f
                    far = 100f
                    update()  // Ensure camera is updated after setting the properties
                },
                world
            )
            CameraType.THIRD_PERSON -> ThirdPersonCameraController(
                PerspectiveCamera(67f, 16f, 9f).apply {
                    position.set(0f, 10f, 10f)  // Start behind and slightly above the origin
                    lookAt(0f, 0f, 0f)  // Look at the cube in the center
                    near = 0.1f
                    far = 100f
                    update()
                }
            )
            CameraType.TOP_VIEW -> TopViewCameraController(
                OrthographicCamera(16f, 9f).apply {
                    position.set(0f, 20f, 0f)  // Directly above the cube
                    lookAt(0f, 0f, 0f)
                    near = 0.1f
                    far = 100f
                    update()
                }
            )
            CameraType.ORTHO_ISO -> IsoOrthoCameraController(
                OrthographicCamera(16f, 9f).apply {
                    position.set(10f, 10f, 10f)  // Isometric position
                    lookAt(0f, 0f, 0f)  // Focus on the cube
                    near = 0.1f
                    far = 100f
                    update()
                }
            )
        }
    }
}


