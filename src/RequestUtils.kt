import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import jdk.nashorn.internal.ir.debug.JSONWriter
import jdk.nashorn.internal.parser.JSONParser
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import java.util.*
import java.util.concurrent.TimeUnit


const val ROOT_URL = "https://api.github.com"

fun getAuthencationString(username: String, token: String): String {
    return Base64.getEncoder().encodeToString("$username:$token".toByteArray(charset("UTF-8")))
}

fun sendToGitHub(base64Data : String){
    val pathName = "${System.currentTimeMillis()}.png"
    val url = createFileAPIURL(username,repo,pathName)
    val uploadData = UploadData()
    uploadData.message = "upload a pic named $pathName"
    uploadData.path = pathName
    uploadData.content = base64Data
    val bodyData = Gson().toJson(uploadData)
    val requestBody = RequestBody.create(MediaType.parse("application/json"),bodyData)
    val request = Request.Builder()
            .url(url)
            .addHeader("Authorization"," Basic ${getAuthencationString(username,token)}")
            .put(requestBody)
            .build()
    val call = client.newCall(request)
    val response = call.execute()
    val responseData = response.body()?.string()
    println("code is ${response.code()}")
    println("reponseData is :\n$responseData")
    if (response.code() == 201)
        uploadSuccess(getDownloadURL(responseData!!))
    else
        uploadFailed(response.code())
}

fun createFileAPIURL(owner: String, repo: String, path: String): String {
    return "$ROOT_URL/repos/$owner/$repo/contents/$path"
}

fun getDownloadURL( data : String) = JsonParser()
        .parse(data).asJsonObject.get("content")
        .asJsonObject.get("download_url").asString



val client = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(20, TimeUnit.SECONDS)
        .build()
