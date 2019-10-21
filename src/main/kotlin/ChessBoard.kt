import java.awt.Dimension
import java.awt.Graphics
import java.awt.Image
import java.awt.Toolkit
import javax.swing.JPanel

const val MARGIN = 15   //边距
const val SPAN = 20     //网格宽度
const val ROCO = 18     //棋盘行列数

class ChessBoard() : JPanel() {

    private val img = Toolkit.getDefaultToolkit().getImage("img/board.png")

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
        }
    }

    override fun getPreferredSize() = Dimension(MARGIN * 2 + SPAN * ROCO, MARGIN * 2 + SPAN * ROCO)
}