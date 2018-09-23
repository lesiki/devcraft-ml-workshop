@file:JvmName("SparkApp")
package devcraftml

import spark.Spark.*

fun main(args: Array<String>) {
    port(8080)
    var latestImageData: String = "No image uploaded yet"

    post("/image", { req, res ->
        latestImageData = req.body()
        "All ok"
    })

    get("/image", { req, res ->
        latestImageData
    })
}