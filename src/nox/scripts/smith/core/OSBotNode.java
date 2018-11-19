package nox.scripts.smith.core;

import nox.api.graphscript.Node;

public abstract class OSBotNode extends Node<OSBotNode> {

    protected final ScriptContext ctx;

    public OSBotNode(OSBotNode next, ScriptContext ctx, String message) {
        super(next, message);
        this.ctx = ctx;
    }
}
