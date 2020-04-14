package org.kafka;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;
import org.pojo.Event;

import java.util.Map;

public class EventSerde implements Serde<Event> {

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public void close() {

    }

    @Override
    public Serializer<Event> serializer() {
        return new EventSerializer();
    }

    @Override
    public Deserializer<Event> deserializer() {
        return new EventDeserializer();
    }

}
