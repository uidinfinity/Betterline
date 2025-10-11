package net.shoreline.client.api.event;

/**
 * @author linus
 * @see Event
 * @see EventStage
 * @since 1.0
 */
public class StageEvent extends Event {
    public static EventStage EventStage;
    // The current event stage which determines which segment of the event is
    // currently running.
    private EventStage stage;

    /**
     * Returns the current {@link EventStage} of the {@link Event}.
     *
     * @return The current event stage
     */
    public EventStage getStage() {
        return stage;
    }

    /**
     * @param stage
     */
    public void setStage(EventStage stage) {
        this.stage = stage;
    }
}
