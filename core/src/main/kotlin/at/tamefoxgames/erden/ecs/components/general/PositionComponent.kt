package at.tamefoxgames.erden.ecs.components.general

import com.badlogic.gdx.math.Vector3

/**
 * Component that holds the position of an entity in 3D space.
 */
class PositionComponent(val position: Vector3) {

    // Constructor to easily create the component with given x, y, z coordinates
    constructor(x: Float, y: Float, z: Float) : this(Vector3(x, y, z))

    /**
     * Sets a new position for this component.
     */
    fun setPosition(x: Float, y: Float, z: Float) {
        position.set(x, y, z)
    }
}
