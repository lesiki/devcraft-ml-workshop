package devcraftml

import org.json.simple.JSONArray
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import java.util.*

class Parser {
    fun parseImageResult(input : String) : ImageLookupResponse {
        val jsonObject = JSONParser().parse(input) as JSONObject
        val labels  = ArrayList<Label>()
        for(label in jsonObject.get("labels") as JSONArray) {
            labels.add(Label(
                    name = (label as JSONObject).get("Name") as String,
                    confidence = (label as JSONObject).get("Confidence") as Double
                ))
        }
        return ImageLookupResponse(s3key = jsonObject.get("s3key") as String,
                labels = labels)
    }
}