package at.tamefoxgames.erden

import at.tamefoxgames.erden.camera.CameraBehavior
import at.tamefoxgames.erden.camera.CameraType
import at.tamefoxgames.erden.ecs.systems.RenderSystem
import at.tamefoxgames.erden.ecs.templates.GrassTemplate
import at.tamefoxgames.erden.ecs.world.World
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g3d.Environment
import com.badlogic.gdx.graphics.g3d.ModelBatch
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute
import ktx.app.KtxApplicationAdapter
import ktx.app.clearScreen
import ktx.log.info

class Erden : KtxApplicationAdapter {
    private lateinit var modelBatch: ModelBatch
    private lateinit var environment: Environment
    private lateinit var cameraController: CameraBehavior
    private lateinit var world: World
    private lateinit var renderSystem: RenderSystem

    override fun create() {
        // Initialize model batch for rendering
        modelBatch = ModelBatch()

        // Set up the environment with ambient light and directional light
        environment = Environment().apply {
            set(ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f))
            add(DirectionalLight().set(1f, 1f, 1f, -1f, -0.8f, -0.2f))
        }


        // Create the world
        world = World(16, 16, 16)  // Define chunk size
        world.generateWorld(chunkRangeX = 4, chunkRangeZ = 4) // Generate the initial chunk
        info { "World generated." }
        // Create the camera through CameraFactory using CameraType
        cameraController = CameraFactory.createCamera(CameraType.FIRST_PERSON, world)
        info { "Camera initialized." }
        // Add a debug block in the world to ensure something is rendered
        world.addBlock(0f, 0f, 0f, GrassTemplate)

        info { "Number of entities in the world: ${world.entities.size}" }

        // Initialize the render system
        renderSystem = RenderSystem(modelBatch, environment, cameraController)
        info { "World and RenderSystem initialized." }
        info { "Camera position: ${cameraController.getCamera().position}" }
        info { "Camera direction: ${cameraController.getCamera().direction}" }
    }

    override fun render() {
        // Clear the screen for rendering
        clearScreen(0.1f, 0.1f, 0.1f)

        // Handle camera input and update
        cameraController.handleInput()
        cameraController.update()

        // Render the world
        world.render(renderSystem, Gdx.graphics.deltaTime, cameraController.getCamera(), modelBatch, environment)
    }

    override fun dispose() {
        // Dispose resources to avoid memory leaks
        modelBatch.dispose()
    }
}
