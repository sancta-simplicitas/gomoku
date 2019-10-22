import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics
import java.awt.Toolkit
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.JPanel

class ChessBoard : JPanel() {
    private val img = Toolkit.getDefaultToolkit().getImage("img/board.png")
    private var chessList = arrayListOf<Chess>()
    private var chessCount = 0
    private var isBlack = true

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        with(g) {
            drawImage(img, 0, 0, this@ChessBoard)
            (0..ROCO).forEach {
                drawLine(MARGIN, MARGIN + it * SPAN, MARGIN + ROCO * SPAN, MARGIN + it * SPAN)  //画横线
                drawLine(MARGIN + it * SPAN, MARGIN, MARGIN + it * SPAN, MARGIN + ROCO * SPAN)  //画竖线
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
            (0 until chessCount).forEach {
                chessList[it].draw(g)
                if (it == chessCount - 1) { // 如果是最后一个棋子
                    color = Color.red
                    drawRect( //下一行: xPos, 下下行: yPos //- DIAMETER / 2
                        (chessList[it].col * SPAN + MARGIN - DIAMETER / 2).toInt(),
                        (chessList[it].row * SPAN + MARGIN - DIAMETER / 2).toInt(),
                        DIAMETER.toInt(),
                        DIAMETER.toInt()
                    )
                }
            }
        }
    }

    override fun getPreferredSize() = Dimension(MARGIN * 2 + SPAN * ROCO, MARGIN * 2 + SPAN * ROCO)

    inner class MouseMonitor : MouseAdapter() {
        override fun mousePressed(e: MouseEvent) = with(e) {
            (x - MARGIN + SPAN / 2) / SPAN to (y - MARGIN + SPAN / 2) / SPAN
        }.run {
            // 将鼠标单击的像素坐标转换成网格索引
            val col = first
            val row = second
            // 落在棋盘外不能下棋
            if (col < 0 || col > ROCO || row < 0 || row > ROCO) return
            // 已经有棋子存在的x,y不能下棋
            fun hasChess(col: Int, row: Int) = run {
                for (i in 0 until chessCount) {
                    val ch = chessList[i]
                    if (ch.col == col && ch.row == row) return true
                }
                false
            }
            if (hasChess(col, row)) return
            chessCount += 1
            chessList.add(Chess(col, row, if (isBlack) Color.BLACK else Color.WHITE))
            repaint()
            isBlack = !isBlack
        }
    }
    init {
        addMouseListener(MouseMonitor())
    }
}