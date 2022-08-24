package de.smartsquare

import de.smartsquare.DatabaseEngine.PostgreSQL
import de.smartsquare.DatabaseEngine.Redis

fun main() {
    val myCloud = cloud {
        project(name = "My project") {
            description = "Project with 2 databases"
            environment = ProjectEnvironment.Development

            database(name = "postgres", engine = PostgreSQL) {
                nodes = 3
                size = "2vcpu-20gb"
                region = "eu-west"
                tags = setOf("db", "postgres")
            }

            database(name = "redis", engine = Redis) {
                nodes = 2
                size = "2vcpu-40gb"
                tags = setOf("db", "redis")
            }
        }
    }

    provision(myCloud, token = "my-token")
}
