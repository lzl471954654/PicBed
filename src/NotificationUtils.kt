import java.awt.*
import java.awt.datatransfer.StringSelection
import java.awt.datatransfer.Transferable
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.JTextField

lateinit var frame: JFrame
lateinit var panel: JPanel
lateinit var dimension: Dimension
lateinit var textField: JTextField

fun uploadSuccess(realURL:String){
    createNotificationWindow()
    frame.title = "Upload Success"
    textField.text = realURL
}

fun uploadFailed(httpCode:Int){
    createNotificationWindow()
    frame.title = "Upload Failed"
    textField.text = "upload failed , the http status code is :$httpCode"
}

fun createNotificationWindow(){
    frame = JFrame()
    frame.setSize(500,50)
    panel = JPanel()
    dimension = frame.size
    panel.minimumSize = dimension
    frame.add(panel)
    textField = JTextField()
    textField.minimumSize = dimension.clone() as Dimension
    panel.add(textField)
    frame.isVisible = true
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
}
