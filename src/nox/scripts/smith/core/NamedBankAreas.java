package nox.scripts.smith.core;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.map.constants.Banks;

public final class NamedBankAreas {

    private NamedBankAreas() {}

    public static NamedBankArea[] get() {
        return new NamedBankArea[] {
                new NamedBankArea("Edgeville", Banks.EDGEVILLE, new Position(3107, 3499, 0), true),
                new NamedBankArea("Falador West", Banks.FALADOR_WEST, new Position(2974, 3369, 0), true),
                new NamedBankArea("Falador East", Banks.FALADOR_EAST, new Position(2974, 3369, 0), true),
                new NamedBankArea("Ardougne", Banks.ARDOUGNE_NORTH, new Position(2601, 3309, 0), true),
                new NamedBankArea("West Varrok", Banks.VARROCK_WEST, new Position(3187, 3426, 0), false),
                new NamedBankArea("Shilo Village", new Area(2850, 2951, 2856, 2957), new Position(2857, 2964, 0), true),
                new NamedBankArea("Neitizinot", new Area(2334, 3806, 2340, 3812), new Position(2344, 3812, 0), true),
        };
    }
}
