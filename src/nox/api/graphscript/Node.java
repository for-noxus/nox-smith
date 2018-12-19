package nox.api.graphscript;

public abstract class Node<k extends Node> {

    private k next;
    private String message;

    protected boolean aborted = false;
    protected String abortedReason = "Node has been aborted.";

    public Node(k next, String message) { this.next = next;
        this.message = message;
    }

    public void setNext(k next) {
        this.next = next;
    }

    public k getNext() { return next; }

    public String getMessage() {
        return message;
    }

    public abstract boolean isValid();

    public abstract int execute();

    public boolean isAborted() { return aborted; }

    public String getAbortedReason() { return abortedReason; }

    protected void abort(String reason) {
        this.abortedReason = reason;
        this.aborted = true;
    }
}
