package net.shoreline.eventbus;

import net.shoreline.eventbus.event.Event;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class EventBus {
    public static final EventBus INSTANCE = new EventBus();

    private final Map<Class<? extends Event>, InvokerNode> event2InvokerMap = new ConcurrentHashMap<>();

    private EventBus() {}

    public void dispatch(Event event) {
        InvokerNode head = event2InvokerMap.get(event.getClass());
        if (head == null) return;

        InvokerNode current = head.next;
        while (current != null) {
            current.invoker.invoke(event);
            current = current.next;
        }
    }

    public void registerEventType(Class<? extends Event> eventType) {

        event2InvokerMap.putIfAbsent(eventType, new InvokerNode(null, null, 0));
    }

    public void subscribe(Object subscriber) {
        for (Method method : subscriber.getClass().getDeclaredMethods()) {
            if (method.getParameterCount() != 1) continue;
            if (!method.getReturnType().equals(void.class)) continue;

            Class<?> paramType = method.getParameterTypes()[0];
            if (!Event.class.isAssignableFrom(paramType)) continue;

            method.setAccessible(true);

            @SuppressWarnings("unchecked")
            Class<? extends Event> eventType = (Class<? extends Event>) paramType;

            registerEventType(eventType);

            Invoker invoker = event -> {
                try {
                    method.invoke(subscriber, event);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            };

            InvokerNode newNode = new InvokerNode(invoker, subscriber, 0);
            InvokerNode head = event2InvokerMap.get(eventType);

            synchronized (head) {
                newNode.next = head.next;
                head.next = newNode;
            }
        }
    }

    public static final class InvokerNode {
        public volatile InvokerNode next;
        public final Invoker invoker;
        public final Object subscriber;
        public final int priority;

        public InvokerNode(Invoker invoker, Object subscriber, int priority) {
            this.next = null;
            this.invoker = invoker;
            this.subscriber = subscriber;
            this.priority = priority;
        }
    }

    @FunctionalInterface
    public interface Invoker {
        void invoke(Event event);
    }
}