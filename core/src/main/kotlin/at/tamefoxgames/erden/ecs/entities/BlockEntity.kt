package at.tamefoxgames.erden.ecs.entities

class BlockEntity {
    private val components = mutableMapOf<Class<*>, Any>()

    // Add a component to the entity
    fun <T : Any> addComponent(component: T) {
        components[component::class.java] = component
    }

    // Get a component from the entity
    fun <T : Any> getComponent(componentClass: Class<T>): T? {
        return components[componentClass] as? T
    }
}
