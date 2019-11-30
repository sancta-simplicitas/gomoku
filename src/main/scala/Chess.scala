import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.RenderingHints
import java.awt.RadialGradientPaint
import java.awt.geom.Ellipse2D
import Const._

case class Chess(col: Int, row: Int, color: Color) {
    def draw(g: Graphics) {
        val xPos = col * SPAN + MARGIN
        val yPos = row * SPAN + MARGIN
        val g2d = g.asInstanceOf[Graphics2D]
        var paint: RadialGradientPaint = null
        val x = xPos + DIAMETER / 4
        val y = yPos + DIAMETER / 4
        val f = Array[Float](0f, 1f)
        val c = Array(Color.WHITE, Color.BLACK)
        color match {
            case Color.BLACK => paint = new RadialGradientPaint(x, y, DIAMETER, f, c)
            case Color.WHITE => paint = new RadialGradientPaint(x, y, DIAMETER * 2, f, c)
        }
        import g2d._
        setPaint(paint)     //以下两行使边界更均匀
        setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_DEFAULT)
        fill(new Ellipse2D.Float(xPos - DIAMETER / 2, yPos - DIAMETER / 2, DIAMETER, DIAMETER))
    }
}