package nox.scripts.smith.nodes;

import nox.api.graphscript.Node;
import nox.scripts.smith.core.OSBotNode;
import nox.scripts.smith.core.ScriptContext;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.event.WalkingEvent;

public class WalkToEntity extends OSBotNode {

    public WalkToEntity(OSBotNode next, ScriptContext ctx, String message) {
        super(next, ctx, message);
    }

    @Override
    public boolean isValid() {
        int distanceToEntity = ctx.getScriptSettings().getBankArea().getInteractionLocation().distance(ctx.myPosition());

        String entityName = ctx.getScriptSettings().getBankArea().isSmelting() ? "Furnace" : "Anvil";

        return (distanceToEntity > 5 || !ctx.getObjects().closest(entityName).isVisible()) &&
                ctx.hasSmithingItems() && ctx.hasSmeltingItems();
    }

    @Override
    public int execute() {
        Position toWalk = ctx.getScriptSettings().getBankArea().getInteractionLocation();

        ctx.getWalking().walk(toWalk);
        return 5;
    }
}
