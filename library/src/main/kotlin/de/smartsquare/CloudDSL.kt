package de.smartsquare

@DslMarker
annotation class CloudResourceMarker

@CloudResourceMarker
abstract class CloudResource

interface Taggable {
    var tags: Set<String>?
}

class CloudDSL : CloudResource() {
    internal val projects = mutableListOf<Project>()

    fun project(name: String, init: Project.() -> Unit): Project {
        val result = Project(name)
        result.init()
        projects.add(result)
        return result
    }
}

fun cloud(init: CloudDSL.() -> Unit): CloudDSL {
    val result = CloudDSL()
    result.init()
    return result
}

enum class ProjectEnvironment {
    Development, Staging, Production
}

class Project(internal val name: String) : CloudResource() {
    var description: String? = null
    var environment: ProjectEnvironment? = null

    internal val databases = mutableListOf<Database>()

    fun database(
        name: String,
        engine: DatabaseEngine,
        init: Database.() -> Unit
    ): Database {
        val result = Database(name, engine)
        result.init()
        databases.add(result)
        return result
    }
}

enum class DatabaseEngine(internal val engineName: String) {
    PostgreSQL("pg"), MySQL("mysql"), Redis("redis"), MongoDB("mongodb")
}

class Database(
    internal val name: String,
    internal val engine: DatabaseEngine,
) : CloudResource(), Taggable {
    var nodes: Int = 1
    var size: String = "2vpcu-20gb"
    var region: String = "nyc1"
    var version: String? = null
    override var tags: Set<String>? = null
}
