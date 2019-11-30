import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics
import java.awt.Toolkit
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.JOptionPane
import javax.swing.JPanel
import Const._
import ExtFn.KtStd
import scala.collection.mutable.ArrayBuffer

case class ChessBoard() extends JPanel() {
    private val img = Toolkit.getDefaultToolkit.getImage("img/board.png")
    private var chessList = ArrayBuffer[Chess]()
    private var chessCount = 0
    private var isBlack = true

    override def paintComponent(g: Graphics) = {
        super.paintComponent(g)
        import g._
        drawImage(img, 0, 0, this)
        (0 to ROCO).foreach { i =>
            drawLine(MARGIN, MARGIN + i * SPAN, MARGIN + ROCO * SPAN, MARGIN + i * SPAN)  //画横线
            drawLine(MARGIN + i * SPAN, MARGIN, MARGIN + i * SPAN, MARGIN + ROCO * SPAN)  //画竖线
        } //画星位:
        fillRect(MARGIN + 3 * SPAN - 2, MARGIN + 3 * SPAN - 2, 5, 5)
        fillRect(MARGIN + (ROCO / 2) * SPAN - 2, MARGIN + 3 * SPAN - 2, 5, 5)
        fillRect(MARGIN + (ROCO - 3) * SPAN - 2, MARGIN + 3 * SPAN - 2, 5, 5)
        fillRect(MARGIN + 3 * SPAN - 2, MARGIN + (ROCO / 2) * SPAN - 2, 5, 5)
        fillRect(MARGIN + (ROCO / 2) * SPAN - 2, MARGIN + (ROCO / 2) * SPAN - 2, 5, 5)
        fillRect(MARGIN + (ROCO - 3) * SPAN - 2, MARGIN + (ROCO / 2) * SPAN - 2, 5, 5)
        fillRect(MARGIN + 3 * SPAN - 2, MARGIN + (ROCO - 3) * SPAN - 2, 5, 5)
        fillRect(MARGIN + (ROCO / 2) * SPAN - 2, MARGIN + (ROCO - 3) * SPAN - 2, 5, 5)
        fillRect(MARGIN + (ROCO - 3) * SPAN - 2, MARGIN + (ROCO - 3) * SPAN - 2, 5, 5)
        //画棋子:
        (0 until chessCount).foreach { i =>
            chessList(i).draw(g)
            if (i == chessCount - 1) { // 如果是最后一个棋子
                setColor(Color.red)
                drawRect( //下一行: xPos, 下下行: yPos //- DIAMETER / 2
                    (chessList(i).col * SPAN + MARGIN - DIAMETER / 2).toInt,
                    (chessList(i).row * SPAN + MARGIN - DIAMETER / 2).toInt,
                    DIAMETER.toInt,
                    DIAMETER.toInt
                )
            }
        }
    }

    override def getPreferredSize = new Dimension(MARGIN * 2 + SPAN * ROCO, MARGIN * 2 + SPAN * ROCO)

    var isGamming = true

    case class MouseMonitor() extends MouseAdapter() {
        override def mousePressed(e: MouseEvent): Unit = {
            if (!isGamming) return
            // 将鼠标单击的像素坐标转换成网格索引 -> first => col, second => row
            val n = ((e.getX - MARGIN + SPAN / 2) / SPAN, (e.getY - MARGIN + SPAN / 2) / SPAN)
            // 落在棋盘外不能下棋
            if (n._1 < 0 || n._1 > ROCO || n._2 < 0 || n._2 > ROCO) return
            // 已经有棋子存在的x,y不能下棋
            def hasChess(col: Int, row: Int): Boolean = {
                (0 until chessCount).foreach { i =>
                    val e = chessList(i)
                    if (e.col == col && e.row == row) return true
                }
                false
            }
            if (hasChess(n._1, n._2)) return
            chessCount += 1
            chessList += Chess(n._1, n._2, if (isBlack) Color.BLACK else Color.WHITE)
            repaint()
            if (isWin(n._1, n._2)) {
                (if (isBlack) "黑棋" else "白棋").let {
                    String.format("恭喜，%s赢了!", _)
                }.let {
                    JOptionPane.showMessageDialog(ChessBoard(), _)
                }
                isGamming = false
            }
            isBlack = !isBlack
        }
    }

    addMouseListener(MouseMonitor())

    def isWin(cols: Int, rows: Int): Boolean = {
        var continueCount = 1   //连续棋子个数
        val c = if (isBlack) Color.black else Color.white
        def hasChess(col: Int, row: Int, color: Color): Boolean = {
            (0 until chessCount).foreach { i =>
                val e = chessList(i)
                if (e.col == col && e.row == row && e.color == color) return true
            }
            false
        }
        //横向向左寻找
        (0 until cols).reverse.foreach { i => if (hasChess(i, rows, c)) continueCount += 1 }
        //横向向右寻找
        (cols + 1 to ROCO).foreach { i => if (hasChess(i, rows, c)) continueCount += 1 }
        if (continueCount >= 5) return true else continueCount = 1
        //纵向向上搜索
        (0 until rows).reverse.foreach { i => if (hasChess(cols, i, c)) continueCount += 1 }
        //纵向向下搜索
        (rows + 1 to ROCO).foreach { i => if (hasChess(cols, i, c)) continueCount += 1 }
        if (continueCount >= 5) return true else continueCount = 1
        //继续另一种情况的搜索：右上到左下
        //向右上寻找
        for (x <- cols + 1 to ROCO; y <- (0 until rows).reverse) {
            if (hasChess(x, y, c)) continueCount += 1
        }
        //向左下寻找
        for (x <- (0 until cols).reverse; y <- rows + 1 to ROCO) {
            if (hasChess(x, y, c)) continueCount += 1
        }
        if (continueCount >= 5) return true else continueCount = 1
        //继续另一种情况的搜索：左上到右下
        //向左上寻找
        for (x <- (0 until cols).reverse; y <- (0 until rows).reverse) {
            if (hasChess(x, y, c)) continueCount += 1
        }
        //向右下寻找
        for (x <- cols + 1 to ROCO; y <- rows + 1 to ROCO) {
            if (hasChess(x, y, c)) continueCount += 1
        }
        continueCount >= 5
    }

    def restartGame() {
        chessList = ArrayBuffer[Chess]()
        isBlack = true
        isGamming = true
        chessCount = 0
        repaint()
    }

    def goBack() {
        if (chessCount == 0) return
        chessList.let(i => i.remove(i.size - 1))
        chessCount -= 1
        isBlack = !isBlack
        repaint()
    }
}