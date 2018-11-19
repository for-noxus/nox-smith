package nox.scripts.smith.core.enums;

import sun.plugin.viewer.frame.AxBridgeEmbeddedFrame;

public enum SmithItem {
    DAGGER("Dagger", 1),
    AXE("Axe", 1),
    MEDIUM_HELMET("Med Helm", 1),
    CROSSBOW_BOLTS("Crossbow Bolts", 1),
    SWORD("Sword", 1),
    DART_TIPS("Dart tips", 1),
    NAILS("Nails", 1),
    ARROWTIPS("Arrowtips", 1),
    JAVELIN_HEADS("Javelin Heads", 1),
    THROWING_KNIVES("Throwing Knives", 1),
    CROSSBOW_LIMBS("Crossbow Limbs", 1),
    SCIMITAR("Scimitar", 2),
    LONGSWORD("Longsword", 2),
    FULL_HELMET("Full Helm", 2),
    SQUARE_SHIELD("Sq Shield", 2),
    CLAWS("Claws", 2),
    WARHAMMER("Warhammer", 3),
    BATTLEAXE("Battleaxe", 3),
    CHAINBODY("Chainbody", 3),
    KITESHIELD("Kiteshield", 3),
    TWO_HANDED_SWORD("2h Sword", 3),
    PLATELEGS("Platelegs", 3),
    PLATESKIRT("Plateskirt", 3),
    PLATEBODY("Platebody", 5);


    private final String friendlyName;
    private final int barCount;

    public String getFriendlyName() {
        return friendlyName;
    }

    public int getBarCount() {
        return barCount;
    }

    @Override
    public String toString() {
        return friendlyName;
    }

    SmithItem(String friendlyName, int barCount) {
        this.friendlyName = friendlyName;
        this.barCount = barCount;
    }
}
