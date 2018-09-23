@file:JvmName("SparkApp")
package devcraftml

import spark.ModelAndView
import spark.Spark.*
import spark.template.velocity.VelocityTemplateEngine
import java.util.*

fun main(args: Array<String>) {
    port(8080)
    var latestImageData: ImageLookupResponse? = null

    post("/image", { req, res ->
        latestImageData = Parser().parseImageResult(req.body())
        System.out.println("Latest: ${latestImageData?.labels?.get(0)?.name}")
        "All ok"
    })

    get("/image", { req, res ->
        val model = HashMap<String, ImageLookupResponse?>()
        model.put("latestImageResult", latestImageData)
        VelocityTemplateEngine().render(ModelAndView(model, "image.vm"))
    })
}