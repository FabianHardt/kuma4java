package sh.kongme.work;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import io.micronaut.context.annotation.Value;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.uri.UriBuilder;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import static io.micronaut.http.MediaType.*;
import static java.lang.System.*;
import static java.net.http.HttpRequest.*;
import static java.net.http.HttpResponse.BodyHandlers.*;

@Controller
@Slf4j
public class RestController {

  @Value(("${meet.url:`http://localhost:8080`}"))
  String url;

  @SneakyThrows
  @Get(uri = "/work", produces = TEXT_PLAIN)
  public String work() {
    long start = currentTimeMillis();
    int count = 0;

    var httpClient = HttpClient.newHttpClient();
    var builder = newBuilder(URI.create(url + "/meet")).GET()
        .version(HttpClient.Version.HTTP_1_1)
        .setHeader("User-Agent", "Java/9");
    var request = builder.build();

    for (int i = 0; i < 4; i++) {
      var response = httpClient.send(request, discarding());
      if (response.statusCode() == 200) {
        log.info("\uD83D\uDEB6 Going to meeting: {}", i);
        count++;
      }
    }
    long end = currentTimeMillis();
    return "\uD83D\uDCC6 worked for " + (end - start) + "ms, and went to " + count + " meetings";
  }

}