package nox.scripts.smith.core.enums;

public enum Ore {
    COPPER("Copper", 436),
    TIN("Tin", 438),
    IRON("Iron", 440),
    SILVER("Silver", 442),
    COAL("Coal", 453),
    GOLD("Gold", 444),
    MITHRIL("Mithril", 447),
    ADAMANTITE("Adadmantite", 449),
    RUNITE("Runite", 451);

    private final String friendlyName;

    private final int id;

    public String getFriendlyName() {
        return friendlyName;
    }

    public int getId() {
        return id;
    }

    Ore(String friendlyName, int id) {
        this.friendlyName = friendlyName;
        this.id = id;
    }
}
