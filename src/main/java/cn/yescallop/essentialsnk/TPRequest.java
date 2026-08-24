package cn.yescallop.essentialsnk;

import org.powernukkitx.Player;
import org.powernukkitx.level.Location;

public class TPRequest implements Comparable<TPRequest> {

    private final long requestTime;
    private final Player sender;
    private final Player recipient;
    private final Location location;
    private final boolean to;

    public TPRequest(long requestTime, Player sender, Player recipient, Location location, boolean to) {
        this.requestTime = requestTime;
        this.sender = sender;
        this.recipient = recipient;
        this.location = location;
        this.to = to;
    }

    public long getRequestTime() {
        return requestTime;
    }

    public Player getSender() {
        return sender;
    }

    public Player getRecipient() {
        return recipient;
    }

    public Location getLocation() {
        return location;
    }

    public boolean isTo() {
        return to;
    }

    @Override
    public int compareTo(TPRequest that) {
        return Long.compare(this.requestTime, that.requestTime);
    }
}