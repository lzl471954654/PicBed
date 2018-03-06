import java.awt.Toolkit
import java.awt.datatransfer.Clipboard
import java.awt.datatransfer.DataFlavor
import java.awt.datatransfer.StringSelection
import java.awt.datatransfer.Transferable
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.util.*
import javax.imageio.ImageIO
import kotlin.system.exitProcess


var DEBUG = false
lateinit var username: String
lateinit var token: String
lateinit var repo: String


fun main(args: Array<String>) {
    args.forEach {
        if (it == "-d")
            DEBUG = true
    }
    checkConfig()
    log("start!")
    val clipboard : Clipboard = Toolkit.getDefaultToolkit().systemClipboard
    val contents = clipboard.getContents(null)
    if (contents.isDataFlavorSupported(DataFlavor.stringFlavor))
        dealFile(contents.getTransferData(DataFlavor.stringFlavor) as String)
    else
        dealImage(contents.getTransferData(DataFlavor.imageFlavor) as BufferedImage)
}

fun checkConfig(){
    val proFile = File("picbed.properties")
    val properties = Properties()
    if (!proFile.exists()){
        println("Please config the PicBed properties\n" +
                "username=<username>\n" +
                "token=<token>\n" +
                "repo=<repo>\n" +
                "This is optional parameter\n" +
                "format=<jpg/png/bmp/gif>  default = png")
        proFile.createNewFile()
        exitProcess(-1)
    }
    properties.load(proFile.inputStream())
    try{
        properties.load(proFile.inputStream())
    }catch (e : IOException){
        e.printStackTrace()
        println("Read PicBed properties failed , please check permission !")
        exitProcess(-1)
    }
    username = properties.getProperty("username",null)
    token = properties.getProperty("token",null)
    repo = properties.getProperty("repo",null)
    if ( username == null || token == null){
        println("PicBed properties is not complete , please check the username and token! And please set like this\n" +
                "username=<username>\n" +
                "token=<token>\n" +
                "repo=<repo>")
        exitProcess(-1)
    }
}

fun dealImage(bufferedImage: BufferedImage){
    val baseData = getBase64ByImageObject(bufferedImage)
    log("This is image base64:\n$baseData")
    sendToGitHub(baseData)
}

fun dealFile(path: String){
    if (checkImage(path) === null){
        log("This is not a image file!")
        return
    }
    val baseData = getBase64ByFile(File(path))
    log("This is image base64:\n$baseData")
    sendToGitHub(baseData)
}

fun getBase64ByImageObject(bufferedImage: BufferedImage):String{
    val byteArrayOutputStream = ByteArrayOutputStream()
    ImageIO.write(bufferedImage,"png",byteArrayOutputStream)
    return Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray())
}

fun getBase64ByFile(file: File):String{
    return Base64.getEncoder().encodeToString(file.readBytes())
}

fun checkImage(path:String):File?{
    val suffixArray = arrayOf(".jpg",".png",".bmp",".gif")
    val file = File(path)
    if (file.isFile){
        suffixArray.forEach {
            if (path.endsWith(it))
                return file
        }
    }
    return null
}

fun log(logData :String){
    if (DEBUG)
        println("${Date(System.currentTimeMillis())}:$logData")
}