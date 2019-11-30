import java.awt.BorderLayout
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JToolBar
import System.exit
import ExtFn.KtStd
import javax.swing.WindowConstants.EXIT_ON_CLOSE

case class Five() extends JFrame("五子棋") {
    private val startButton = new JButton("重新开始")
    private val backButton = new JButton("悔棋")
    private val exitButton = new JButton("退出")
    private val boardPanel = ChessBoard()
    new JToolBar().also(_.add(startButton)).also(_.add(backButton)).also(_.add(exitButton)).let(add(_, BorderLayout.NORTH))
    setBounds(200, 200, 300, 200)
    setDefaultCloseOperation(EXIT_ON_CLOSE)
    setVisible(true)
    add(boardPanel, BorderLayout.CENTER)
    setLocation(200, 200)
    pack()
    ActionMonitor().let { i =>
        startButton.addActionListener(i)
        backButton.addActionListener(i)
        exitButton.addActionListener(i)
    }

    case class ActionMonitor() extends ActionListener {
        override def actionPerformed(e: ActionEvent) {
            import boardPanel._
            val i = e.getSource
            if (i == startButton) restartGame()
            else if (i == backButton) goBack()
            else if (i == exitButton) exit(0)
        }
    }

}