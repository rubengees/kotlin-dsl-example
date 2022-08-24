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
        val project = Project(name).apply(init)
        projects.add(project)
        return project
    }
}

fun cloud(init: CloudDSL.() -> Unit): CloudDSL {
    return CloudDSL().apply(init)
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
        val database = Database(name, engine).apply(init)
        databases.add(database)
        return database
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
