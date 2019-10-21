import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics
import java.awt.Toolkit
import javax.swing.JPanel

class ChessBoard : JPanel() {
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
            //画棋子:
            Chess(2,1, Color.BLACK).draw(this)
            Chess(5,2, Color.WHITE).draw(this)
        }
    }

    override fun getPreferredSize() = Dimension(MARGIN * 2 + SPAN * ROCO, MARGIN * 2 + SPAN * ROCO)
}