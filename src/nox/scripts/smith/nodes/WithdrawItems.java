package nox.scripts.smith.nodes;

import nox.scripts.smith.core.Constants;
import nox.scripts.smith.core.OSBotNode;
import nox.scripts.smith.core.ScriptContext;
import nox.scripts.smith.core.ScriptSettings;
import nox.scripts.smith.core.enums.Bar;
import org.osbot.rs07.api.Bank;

public class WithdrawItems extends OSBotNode {

    public WithdrawItems(OSBotNode next, ScriptContext ctx, String message) {
        super(next, ctx, message);
    }

    @Override
    public boolean isValid() {
        ScriptSettings s = ctx.getScriptSettings();
        boolean isInBank = s.getBankArea().getArea().contains(ctx.myPlayer());

        return isInBank && (!ctx.hasSmithingItems() || !ctx.hasSmeltingItems());
    }

    @Override
    public int execute() {

        Bank bank = ctx.getBank();
        Bar metal = ctx.getScriptSettings().getMetal();
        try {
            if (bank.open()) {
                if (ctx.getScriptSettings().getBankArea().isSmelting()) {
                    if (!ctx.getInventory().isEmpty())
                        bank.depositAll();
                    if (metal.getOre2() != null) {
                        if (!bank.contains(metal.getOre2().getId()) || bank.getAmount(metal.getOre2().getId()) < metal.getOre2Amount()) {
                            this.abort("We're all out of " + metal.getOre2().getFriendlyName());
                            return 5;
                        }
                        bank.withdraw(metal.getOre2().getId(), metal.getOre2WithdrawalAmount());
                    }
                    if (!bank.contains(metal.getOre1().getId()) || bank.getAmount(metal.getOre1().getId()) <  metal.getOre1Amount() ) {
                        this.abort("We're all out of " + metal.getOre1().getFriendlyName());
                        return 5;
                    }
                    bank.withdrawAll(metal.getOre1().getId());
                } else {
                    if (!ctx.getInventory().isEmptyExcept(Constants.HAMMER_ID)) {
                        bank.depositAllExcept(Constants.HAMMER_ID);
                    }
                    if (!ctx.getInventory().contains(Constants.HAMMER_ID)) {
                        if (!bank.contains(Constants.HAMMER_ID)) {
                            abort("Unable to locate a hammer in the bank or in the inventory.");
                            return 5;
                        }
                        bank.withdraw(Constants.HAMMER_ID, 1);
                    }
                    if (!bank.contains(metal.getId()) || bank.getAmount(metal.getId()) < ctx.getScriptSettings().getItemToSmith().getBarCount()) {
                        this.abort("We're all out of " + metal.getFriendlyName());
                        return 5;
                    }
                    bank.withdrawAll(metal.getId());
                }
            }
        } catch (InterruptedException e) {
            ctx.log("Error opening bank.");
            return 5;
        }
        return 5;
    }
}
