package nox.scripts.smith.core.enums;

public enum Bar {
    BRONZE("Bronze", 2349, Ore.COPPER, Ore.TIN, 1, 1),
    IRON("Iron", 2351, Ore.IRON, null, 1, 0),
    SILVER("Silver", 2355, Ore.SILVER, null, 1, 0),
    STEEL("Steel", 2353, Ore.IRON, Ore.COAL, 1, 2),
    GOLD("Gold", 2357, Ore.GOLD, null, 1, 0),
    MITHRIL("Mithril", 2359, Ore.MITHRIL, Ore.COAL, 1, 4),
    ADAMANTITE("Adamantite", 2361, Ore.ADAMANTITE, Ore.COAL, 1, 6),
    RUNITE("Runite", 2363, Ore.RUNITE, Ore.COAL, 1, 8);

    private String friendlyName;

    private Ore   ore1;
    private Ore ore2;

    private int id;
    private int ore1Amount;
    private int ore2Amount;

    public String getFriendlyName() {
        return friendlyName;
    }

    public int getId() {
        return id;
    }

    public Ore getOre1() {
        return ore1;
    }

    public Ore getOre2() {
        return ore2;
    }

    public int getOre1Amount() {
        return ore1Amount;
    }

    public int getOre2Amount() {
        return ore2Amount;
    }

    public int getOre2WithdrawalAmount() {
        if (ore2Amount == 0 || ore2 == null) return 0;
        return (int) Math.floor(28.0 / (1 + 1.0 / ore2Amount));
    }

    Bar(String friendlyName, int itemId, Ore ore1, Ore ore2, int ore1Amount, int ore2Amount) {
        this.friendlyName = friendlyName;
        this.id = itemId;
        this.ore1 = ore1;
        this.ore2 = ore2;
        this.ore1Amount = ore1Amount;
        this.ore2Amount = ore2Amount;
    }

    @Override
    public String toString() {
        return friendlyName;
    }
}
