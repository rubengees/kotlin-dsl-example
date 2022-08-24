package de.smartsquare

/**
 * Function responsible for actually creating the resources. The [dsl] is passed in as configuration for what should be
 * created. Additionally, a [token] is required to authorize the requests.
 */
fun provision(dsl: CloudDSL, token: String) {
    println("Token: $token")

    for (project in dsl.projects) {
        println("Creating project ${project.name}.")

        for (database in project.databases) {
            println("Creating database ${database.name} with engine ${database.engine}.")
        }
    }
}
