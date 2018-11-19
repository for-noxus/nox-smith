package nox.scripts.smith.core;

import nox.scripts.smith.core.enums.Bar;
import nox.scripts.smith.core.enums.SmithItem;

public class ScriptSettings {

    private NamedBankArea bankArea;

    private SmithItem itemToSmith;

    private boolean useSuperheat;

    private Bar metal;

    public NamedBankArea getBankArea() {
        return bankArea;
    }

    public void setBankArea(NamedBankArea bankArea) {
        this.bankArea = bankArea;
    }

    public ScriptSettings(Bar metal, SmithItem itemToSmith, boolean useSuperheat) {
        this.metal = metal;
        this.itemToSmith = itemToSmith;
        this.useSuperheat = useSuperheat;
    }

    public Bar getMetal() {
        return metal;
    }

    public SmithItem getItemToSmith() {
        return itemToSmith;
    }

    public boolean isUseSuperheat() {
        return useSuperheat;
    }

}
