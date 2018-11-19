package nox.scripts.smith;

import nox.scripts.smith.core.*;
import nox.scripts.smith.nodes.InteractWithEntity;
import nox.scripts.smith.nodes.WalkToBank;
import nox.scripts.smith.nodes.WalkToEntity;
import nox.scripts.smith.nodes.WithdrawItems;
import nox.scripts.smith.ui.NoxSmithUI;
import nox.scripts.smith.ui.Painter;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.ui.Message;
import org.osbot.rs07.event.WebWalkEvent;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Arrays;

@ScriptManifest(name = "NoxSmith", author = "Nox", version = 1.0, info = "", logo = "")
public class NoxSmith extends Script {

    private ScriptContext ctx;
    private NoxSmithUI ui;

    private ArrayList<OSBotNode> nodes;

    @Override
    public void onStart() {
        try {
            SwingUtilities.invokeLater(this::initializeGui);
        } catch (Exception e) {
            log("Script failed to start.");
            Arrays.stream(e.getStackTrace()).forEach(f -> log(f.toString()));
        }
    }

    @Override
    public void onExit() {
        if (ui != null) {
            SwingUtilities.invokeLater(ui::dispose);
        }
    }

    @Override
    public int onLoop() throws InterruptedException {
        if (ui != null && ui.isVisible()) {
            log("Awaiting GUI configuration..");
            NamedBankArea location = getUserLocationSettings();
            SwingUtilities.invokeLater(() -> ui.setLocationValidationText(location, myPlayer().getPosition()));
            return 2001;
        } else if (ctx != null){
            if (ctx.getCurrentNode().isAborted()) {
                log(String.format("Node %s requested script abortion.\nReason: %s", ctx.getCurrentNode().getClass().getSimpleName(), ctx.getCurrentNode().getAbortedReason()));
                stop();
            }
            int tryCount = 0;
            while (!ctx.getCurrentNode().isValid()) {
                tryCount++;
                ctx.setCurrentNode(ctx.getCurrentNode().getNext());

                if (tryCount > nodes.size()) {
                    log("Script was in a state where no actions were valid. Exiting.");
                    stop();
                }
            }
            ctx.getCurrentNode().execute();
            log(ctx.getCurrentNode().getMessage());
        }
        return 1001;
    }

    @Override
    public void onMessage(Message msg) throws InterruptedException {
        super.onMessage(msg);
        if (msg.getType() == Message.MessageType.GAME &&
                (msg.getMessage().toLowerCase().contains("you retrieve a bar") ||
                 msg.getMessage().toLowerCase().contains("you hammer the"))) {
            ctx.getTrackedItems().addItemMade();
        }
    }

    @Override
    public void onPaint(Graphics2D g) {
        //This is where you will put your code for paint(s)
    }

    private void initializeGui() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }

            ui = new NoxSmithUI(NamedBankAreas.get(), getBot().getCanvas());
            ui.startButton.addActionListener(a -> {
                ui.setVisible(false);
                ui.dispose();
                ctx = new ScriptContext(this, ui.extractSettings());
                getBot().addPainter(new Painter(ctx));
                initializeNodes();
            });

            ui.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    super.windowClosing(e);
                    stop(false);
                }
            });

            ui.launch();

        } catch (Exception e) {
            log("Script failed to start.");
            Arrays.stream(e.getStackTrace()).forEach(f -> log(f.toString()));
        }
    }

    private void initializeNodes() {
        OSBotNode interactWithEntity = new InteractWithEntity(null, ctx, "Interacting with entity");
        OSBotNode walkToEntity = new WalkToEntity(interactWithEntity, ctx, "Walking to entity");
        OSBotNode withdrawItems = new WithdrawItems(walkToEntity, ctx, "Withdrawing items");
        OSBotNode walkToBank = new WalkToBank(withdrawItems, ctx, "Walking to bank");
        interactWithEntity.setNext(walkToBank);
        nodes = new ArrayList<OSBotNode>(Arrays.asList(withdrawItems, walkToBank, walkToEntity, interactWithEntity));
        ctx.setCurrentNode(nodes.stream().filter(OSBotNode::isValid).findFirst().orElse(null));
        if (ctx.getCurrentNode() == null) {
            log("Unable to find a valid entrypoint for the script..");
            stop();
        }
    }

    private NamedBankArea getUserLocationSettings() {

        NamedBankArea[] areas = NamedBankAreas.get();

        WebWalkEvent webWalkEvent = new WebWalkEvent(Arrays.stream(areas).map(NamedBankArea::getArea).toArray(Area[]::new));
        webWalkEvent.prefetchRequirements(this);

        NamedBankArea chosenArea = Arrays.stream(areas).filter(f -> f.getArea().contains(webWalkEvent.getDestination())).findFirst().get();

        return chosenArea;
    }
}