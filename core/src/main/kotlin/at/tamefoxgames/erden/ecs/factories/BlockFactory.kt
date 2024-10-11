package at.tamefoxgames.erden.ecs.factories

import at.tamefoxgames.erden.ecs.Entity
import at.tamefoxgames.erden.ecs.components.block.BlockColorComponent
import at.tamefoxgames.erden.ecs.components.general.PositionComponent
import at.tamefoxgames.erden.ecs.components.block.SolidComponent
import at.tamefoxgames.erden.ecs.components.block.HardnessComponent
import at.tamefoxgames.erden.ecs.components.rendering.ModelComponent
import at.tamefoxgames.erden.ecs.templates.AirTemplate
import at.tamefoxgames.erden.ecs.templates.BlockTemplate
import com.badlogic.gdx.graphics.g3d.ModelInstance
import com.badlogic.gdx.math.Vector3

object BlockFactory {
    fun createBlock(position: Vector3, template: BlockTemplate): Entity {
        val block = Entity()

        // Add position, color, solid, and hardness components
        block.addComponent(PositionComponent(position))
        block.addComponent(BlockColorComponent(template.color))
        block.addComponent(SolidComponent(template.isSolid))
        block.addComponent(HardnessComponent(template.hardness))

        // Check if the block is not AIR, then add a ModelComponent
        if (template != AirTemplate) {
            val model = ModelFactory.getModelForBlock(template)
            val modelInstance = ModelInstance(model).apply {
                transform.setToTranslation(position)
            }
            block.addComponent(ModelComponent(modelInstance))
        }

        return block
    }
}

