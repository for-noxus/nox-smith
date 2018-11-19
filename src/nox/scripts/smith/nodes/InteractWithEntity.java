package nox.scripts.smith.nodes;

import jdk.nashorn.internal.runtime.regexp.joni.ast.ConsAltNode;
import nox.api.graphscript.Node;
import nox.scripts.smith.core.Constants;
import nox.scripts.smith.core.OSBotNode;
import nox.scripts.smith.core.ScriptContext;
import nox.scripts.smith.core.Sleep;
import org.osbot.rs07.api.Widgets;
import org.osbot.rs07.api.filter.Filter;
import org.osbot.rs07.api.model.Item;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.utility.ConditionalSleep;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class InteractWithEntity extends OSBotNode {

    private boolean isOnAll = false;
    private long lastAnimationTime = 0;

    public InteractWithEntity(OSBotNode next, ScriptContext ctx, String message) {
        super(next, ctx, message);
    }

    @Override
    public boolean isValid() {
        int distanceToEntity = ctx.getScriptSettings().getBankArea().getInteractionLocation().distance(ctx.myPosition());

        String entityName = ctx.getScriptSettings().getBankArea().isSmelting() ? "Furnace" : "Anvil";
        RS2Object entity = ctx.getObjects().closest(entityName);
        return (distanceToEntity <= 5 && entity != null && entity.isVisible()) &&
                ctx.hasSmithingItems() && ctx.hasSmeltingItems();
    }

    @Override
    public void execute() {
        String entityName = ctx.getScriptSettings().getBankArea().isSmelting() ? "Furnace" : "Anvil";

        if (ctx.getDialogues().inDialogue()) {
            ctx.getDialogues().clickContinue();
        }

        if (ctx.myPlayer().isAnimating())
            lastAnimationTime = System.currentTimeMillis();

        if (System.currentTimeMillis() - lastAnimationTime < 5000)
            return;

        if (ctx.getScriptSettings().getBankArea().isSmelting()) {
            if (ctx.getObjects().closest(entityName).interact("Smelt")) {
                new Sleep(() -> ctx.getWidgets().get(Constants.WIDGET_SMELT_ROOT, Constants.WIDGET_SMELT_CHILD) != null, 5000, 200).sleep();

                ensureAllIsSelected();

                RS2Widget[] widgets = ctx.getWidgets().getWidgets(Constants.WIDGET_SMELT_ROOT);
                RS2Widget smeltWidget = Arrays.stream(widgets).filter(f -> Arrays.stream(f.getItems()).anyMatch(item -> item.getId() == ctx.getScriptSettings().getMetal().getId())).findFirst().get();

                if (!smeltWidget.interact("Smelt")) {
                    ctx.log("Error smelting.");
                }
            }
        } else {
            if (ctx.getObjects().closest(entityName).interact("Smith")) {
                new Sleep(() -> ctx.getWidgets().getWidgets(Constants.WIDGET_SMITH_ROOT) != null, 5000, 200).sleep();

                RS2Widget smithWidget = Arrays.stream(ctx.getWidgets().getWidgets(Constants.WIDGET_SMITH_ROOT))
                        .filter(f -> Arrays.stream(f.getItems()).anyMatch(a -> a.getName().toLowerCase().contains(ctx.getScriptSettings().getItemToSmith().getFriendlyName().toLowerCase())))
                        .findFirst().orElse(null);

                if (smithWidget == null) {
                    abort("Unable to locate item to smith, tried to find: " + ctx.getScriptSettings().getItemToSmith().getFriendlyName());
                }

                if (!smithWidget.interact("Smith all")) {
                    ctx.log("Error smithing.");
                }
            }
        }
    }

    private void ensureAllIsSelected() {
        if (!isOnAll) {
            RS2Widget widget = ctx.getWidgets().get(Constants.WIDGET_QUANTITY_ALL_ROOT, Constants.WIDGET_QUANTITY_ALL_CHILD, Constants.WIDGET_QUANTITY_ALL_SUBCHILD);
            if (widget.getMessage().contains("col=")) {
                isOnAll = true;
            } else {
                if (widget.interact()) {
                    isOnAll = true;
                }
            }
        }
    }
}
