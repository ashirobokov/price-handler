package ru.ashirobokov.microservices.receiver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import ru.ashirobokov.microservices.model.InstrumentPrice;

import java.time.LocalTime;
import java.util.List;

@Component
public class PriceReceiver {
    public final static Logger LOG = LoggerFactory.getLogger(PriceReceiver.class);
    private WebClient webClient;
    private int port = 8080;

    public PriceReceiver() {
        this.webClient = WebClient.create("http://localhost:" + port);
    }

    public void testGenerator() {

/*
// Rerurn body content from Mono
        Mono<ResponseEntity<String>> test = webClient.get()
                .uri("/")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(String.class);

        test.doOnNext(s -> LOG.info("PriceReceiver : " + s.getBody()))
                .block();
*/


        String message = webClient.get()
                .uri("/")
                .retrieve()
                .bodyToMono(String.class)
                .block(); // return <T> body's content

        LOG.info("PriceReceiver : message : {}", message);
    }


    public void getPrices() {
        ParameterizedTypeReference<ServerSentEvent<List<InstrumentPrice>>> type
                = new ParameterizedTypeReference<ServerSentEvent<List<InstrumentPrice>>>() {
        };

        Flux<ServerSentEvent<List<InstrumentPrice>>> eventStream =
                webClient.get()
                        .uri("/allprices")
                        .retrieve()
                        .bodyToFlux(type);

        LOG.info("Test info : getting list of prices from flux");

        eventStream.subscribe(
                content -> LOG.info("Time: {} - event: name[{}], id [{}], content[{}] ",
                        LocalTime.now(), content.event(), content.id(), content.data()),
                error -> LOG.error("Error receiving SSE: {}", error),
                () -> LOG.info("Completed!!!"));
    }

    public void getStrings() {
        ParameterizedTypeReference<ServerSentEvent<String>> type
                = new ParameterizedTypeReference<ServerSentEvent<String>>() {
        };

        Flux<ServerSentEvent<String>> eventStream =
                webClient.get()
                        .uri("/strings")
                        .retrieve()
                        .bodyToFlux(type);

        LOG.info("Test info : getting STRINGS from flux");

        eventStream.subscribe(
                content -> LOG.info("Time: {} - event: name[{}], id [{}], content[{}] ",
                        LocalTime.now(), content.event(), content.id(), content.data()),
                error -> LOG.error("Error receiving SSE: {}", error),
                () -> LOG.info("Completed!!!"));
    }

}
