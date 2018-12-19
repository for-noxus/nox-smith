package nox.scripts.smith.core;

import nox.scripts.smith.core.enums.SleepDuration;
import org.osbot.rs07.utility.ConditionalSleep;

import java.util.function.BooleanSupplier;

public final class Sleep extends ConditionalSleep {

    private final BooleanSupplier condition;

    public Sleep(final BooleanSupplier condition, final int timeout, final int interval) {
        super(timeout, interval);
        this.condition = condition;
    }

    @Override
    public final boolean condition() throws InterruptedException {
        return condition.getAsBoolean();
    }

    public static boolean until(final BooleanSupplier condition, final int timeout, final int interval) {
        return new Sleep(condition, timeout, interval).sleep();
    }

}