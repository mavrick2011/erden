package at.tamefoxgames.erden.ecs

class Entity {
    // A map to hold components, where the key is the component class
    private val components: MutableMap<Class<*>, Any> = mutableMapOf()

    // Function to add a component to the entity
    fun <T : Any> addComponent(component: T) {
        components[component::class.java] = component
    }

    // Function to get a component by its class
    fun <T : Any> getComponent(componentClass: Class<T>): T? {
        return components[componentClass] as? T
    }

    // Function to remove a component by its class
    fun <T : Any> removeComponent(componentClass: Class<T>) {
        components.remove(componentClass)
    }

    // Check if an entity has a specific component
    fun <T : Any> hasComponent(componentClass: Class<T>): Boolean {
        return components.containsKey(componentClass)
    }
}
