package nox.scripts.smith.ui;

import nox.scripts.smith.core.ScriptContext;
import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.api.util.ExperienceTracker;

import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Painter implements org.osbot.rs07.canvas.paint.Painter {
    private final ScriptContext ctx;

    private final long startTime;
    private long lastUpdateTime = 0;

    private String status;
    private String formattedTime;
    private int xpPerHour;
    private int gainedXp;
    private int gainedLevels;
    private int itemsMade;
    private String formattedTimeToLevel;

    private final String itemMadeText;

    private final Color color1 = new Color(0, 0, 0, 100);
    private final Color color2 = new Color(0, 0, 0);
    private final Color color3 = new Color(255, 255, 255);

    private final BasicStroke stroke1 = new BasicStroke(1);

    private final Font font1 = new Font("Arial", 0, 14);
    private final Font font2 = new Font("Arial", 0, 16);

    public Painter(ScriptContext ctx) {
        this.ctx = ctx;
        startTime = System.currentTimeMillis();
        ctx.getExperienceTracker().start(Skill.SMITHING);
        itemMadeText = String.format("%s %s", ctx.getScriptSettings().getMetal().getFriendlyName(), ctx.getScriptSettings().getItemToSmith() == null ? "bar" : ctx.getScriptSettings().getItemToSmith().getFriendlyName());
    }

    @Override
    public void onPaint(Graphics2D g) {
        if (System.currentTimeMillis() - lastUpdateTime > 1000)
            calculatePaintVariables();

        g.setColor(color1);
        g.fillRect(0, 203, 283, 133);
        g.setColor(color2);
        g.setStroke(stroke1);
        g.drawRect(0, 203, 283, 133);
        g.setFont(font1);
        g.setColor(color3);
        g.drawString("Status: " + status, 50, 333);
        g.setFont(font2);
        g.drawString("TTL: " + formattedTimeToLevel, 63, 307);
        g.drawString("XP/ Hour: " + xpPerHour, 27, 289);
        g.drawString("XP Gained: " + gainedXp + " (" + gainedLevels + ")", 13, 270);
        g.drawString(itemMadeText + "s Made: " + itemsMade, 9, 252);
        g.drawString("Runtime: " + formattedTime, 33, 223);

    }

    private String formatTime(long time) {
        Date date = new Date(time);
        DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        return formatter.format(date);
    }

    private void calculatePaintVariables() {
        ExperienceTracker tracker = ctx.getExperienceTracker();

        lastUpdateTime = System.currentTimeMillis();

        status = ctx.getCurrentNode().getMessage();
        formattedTime = formatTime(lastUpdateTime - startTime);
        formattedTimeToLevel = formatTime(tracker.getTimeToLevel(Skill.SMITHING));
        gainedXp = tracker.getGainedXP(Skill.SMITHING);
        xpPerHour = tracker.getGainedXPPerHour(Skill.SMITHING);
        gainedLevels = tracker.getGainedLevels(Skill.SMITHING);
        itemsMade = ctx.getTrackedItems().getItemsMade();
    }
}
