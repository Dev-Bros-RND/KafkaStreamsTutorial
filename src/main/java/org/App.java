package org;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.kafka.EventSerde;
import org.pojo.Event;
import org.pojo.Priority;

import java.util.Properties;

public class App {

    public static void main(String ...args) {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "event-stream-example");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, EventSerde.class);

        StreamsBuilder builder = new StreamsBuilder();
        KStream<String, Event> stream = builder.stream("main_topic", Consumed.with(Serdes.String(), new EventSerde()));

        KStream<String, Event> highPriorityStream = stream.filter((id, event) -> event.getPriority().equals(Priority.HIGH));
        KStream<String, Event> mediumPriorityStream = stream.filter((id, event) -> event.getPriority().equals(Priority.MEDIUM));
        KStream<String, Event> lowPriorityStream = stream.filter((id, event) -> event.getPriority().equals(Priority.LOW));

        highPriorityStream.to("high_topic", Produced.with(Serdes.String(), new EventSerde()));
        mediumPriorityStream.to("medium_topic", Produced.with(Serdes.String(), new EventSerde()));
        lowPriorityStream.to("low_topic", Produced.with(Serdes.String(), new EventSerde()));

        KafkaStreams streams = new KafkaStreams(builder.build(), props);
        streams.start();
    }

}
