package nox.scripts.smith.nodes;

import nox.api.graphscript.Node;
import nox.scripts.smith.core.Constants;
import nox.scripts.smith.core.OSBotNode;
import nox.scripts.smith.core.ScriptContext;
import nox.scripts.smith.core.enums.Bar;
import org.osbot.rs07.api.Inventory;
import org.osbot.rs07.api.filter.Filter;
import org.osbot.rs07.api.model.Item;

import java.util.Arrays;

public class WalkToBank extends OSBotNode {

    public WalkToBank(OSBotNode next, ScriptContext ctx, String message) {
        super(next, ctx, message);
    }

    @Override
    public boolean isValid() {
        boolean isInBank = ctx.getScriptSettings().getBankArea().getArea().contains(ctx.myPosition());

        return !isInBank && (!ctx.hasSmithingItems() || !ctx.hasSmeltingItems());
    }

    @Override
    public void execute() {
        ctx.getWalking().walk(ctx.getScriptSettings().getBankArea().getArea());
    }
}
