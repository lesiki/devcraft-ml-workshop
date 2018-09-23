package devcraftml

data class ImageLookupResponse(val s3key: String, val labels: List<Label>)
