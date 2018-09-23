@file:JvmName("SparkApp")
package devcraftml

import spark.ModelAndView
import spark.Spark.*
import spark.template.velocity.VelocityTemplateEngine
import java.util.*

fun main(args: Array<String>) {
    port(8080)
    var latestImageData: String = "No image uploaded yet"

    post("/image", { req, res ->
        latestImageData = req.body()
        "All ok"
    })

    get("/image", { req, res ->
        val model = HashMap<String, Any?>()
        VelocityTemplateEngine().render(ModelAndView(model, "image.vm"))
    })
}