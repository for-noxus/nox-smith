package nox.scripts.smith.ui;

import nox.scripts.smith.core.ScriptContext;
import org.osbot.rs07.canvas.paint.Painter;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class NoxSmithMousePaint implements Painter {
    int angle = 0;
    private BasicStroke cursorStroke = new BasicStroke(2);
    private Color cursorColor = Color.WHITE;

    private ScriptContext ctx;

    public NoxSmithMousePaint(ScriptContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public void onPaint(Graphics2D g) {
        g.setStroke(cursorStroke);
        g.setColor(cursorColor);

        AffineTransform old = g.getTransform();
        Point pt = ctx.getMouse().getPosition();

        angle = (angle >= 360) ? 0 : angle + 2;

        g.rotate(Math.toRadians(angle), pt.x, pt.y);

        g.drawLine(pt.x - 5, pt.y + 5, pt.x + 5, pt.y - 5);
        g.drawLine(pt.x + 5, pt.y + 5, pt.x - 5, pt.y - 5);

        g.setTransform(old);
    }
}
