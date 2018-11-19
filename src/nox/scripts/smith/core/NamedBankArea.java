package nox.scripts.smith.core;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;

import java.util.Objects;

public class NamedBankArea {

    private final Position interactionLocation;
    private final boolean isSmelting;
    private final Area area;
    private final String name;

    public NamedBankArea(String name, Area area, Position interactionLocation, boolean isSmelting) {
        this.name = name;
        this.interactionLocation = interactionLocation;
        this.isSmelting = isSmelting;
        this.area = area;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NamedBankArea that = (NamedBankArea) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public String getName() {
        return name;
    }

    public Area getArea() {
        return area;
    }

    public Position getInteractionLocation() {
        return interactionLocation;
    }

    public boolean isSmelting() {
        return isSmelting;
    }

    public boolean isSmithing() { return !isSmelting; }
}
