import java.awt.BorderLayout
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JToolBar

class Five : JFrame("五子棋") {
    init {
        JToolBar().apply {
            add(JButton("重新开始"))
            add(JButton("悔棋"))
            add(JButton("退出"))
        }.let {
            add(it, BorderLayout.NORTH)
            setBounds(200, 200, 300, 200)
            defaultCloseOperation = EXIT_ON_CLOSE
            isVisible = true
            add(ChessBoard(), BorderLayout.CENTER)
            setLocation(200, 200)
            pack()
            isResizable = false
        }
    }
}