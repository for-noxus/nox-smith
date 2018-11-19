package nox.scripts.smith.core;

import nox.scripts.smith.core.enums.Bar;
import org.osbot.rs07.api.Inventory;
import org.osbot.rs07.script.MethodProvider;

public class ScriptContext extends MethodProvider {

    private ScriptSettings scriptSettings;

    private OSBotNode currentNode;

    private TrackedItems trackedItems;

    public ScriptSettings getScriptSettings() {
        return scriptSettings;
    }

    public OSBotNode getCurrentNode() {
        return currentNode;
    }

    public void setCurrentNode(OSBotNode currentNode) {
        this.currentNode = currentNode;
    }

    public ScriptContext(MethodProvider api, ScriptSettings scriptSettings) {
        exchangeContext(api.getBot());
        this.scriptSettings = scriptSettings;
        trackedItems = new TrackedItems();
    }

    public boolean hasSmithingItems() {
        Bar metal = scriptSettings.getMetal();
        Inventory inventory = getInventory();
        return scriptSettings.getBankArea().isSmelting() ||
                (inventory.getAmount(metal.getId()) >= scriptSettings.getItemToSmith().getBarCount() &&
                inventory.contains(Constants.HAMMER_ID));
    }

    public boolean hasSmeltingItems() {
        Bar metal = scriptSettings.getMetal();
        Inventory inventory = getInventory();
        return scriptSettings.getBankArea().isSmithing() ||
                (inventory.getAmount(metal.getOre1().getId()) >= metal.getOre1Amount()) &&
                        (metal.getOre2() == null || inventory.getAmount(metal.getOre2().getId()) >= metal.getOre2Amount());
    }

    public TrackedItems getTrackedItems() {
        return trackedItems;
    }

    public class TrackedItems {

        private int itemsMade = 0;

        public int getItemsMade() {
            return itemsMade;
        }

        public void addItemMade() { itemsMade++; }
    }
}
