package at.tamefoxgames.erden.ecs.systems

import at.tamefoxgames.erden.camera.CameraBehavior
import at.tamefoxgames.erden.ecs.Entity
import at.tamefoxgames.erden.ecs.components.general.PositionComponent
import at.tamefoxgames.erden.ecs.components.rendering.ModelComponent
import com.badlogic.gdx.graphics.g3d.Environment
import com.badlogic.gdx.graphics.g3d.ModelBatch
import ktx.log.info

class RenderSystem(
    private val modelBatch: ModelBatch,
    private val environment: Environment,
    private val cameraBehavior: CameraBehavior
) {

    /**
     * Render all entities with a ModelComponent.
     */
    fun update(entities: List<Entity>, deltaTime: Float) {
        // Get the camera from the CameraBehavior
        val camera = cameraBehavior.getCamera()

        // Log the camera position and direction for debugging
        info { "Rendering with camera at position: ${camera.position}" }
        info { "Camera direction: ${camera.direction}" }

        // Begin rendering with the camera
        modelBatch.begin(camera)

        // Render all entities that have a ModelComponent
        for (entity in entities) {
            val modelComponent = entity.getComponent(ModelComponent::class.java)
            val positionComponent = entity.getComponent(PositionComponent::class.java)

            if (modelComponent != null && positionComponent != null) {
                // Check if the model instance is positioned correctly

                // Ensure that the transformation of the model is set correctly
                modelComponent.modelInstance.transform.setToTranslation(positionComponent.position)

                // Render the model with the environment
                modelBatch.render(modelComponent.modelInstance, environment)
            } else {
                // Log missing components for debugging
            }
        }

        // End rendering
        modelBatch.end()
    }
}
