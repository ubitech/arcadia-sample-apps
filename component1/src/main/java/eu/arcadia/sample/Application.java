package eu.arcadia.sample;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

/**
 * Created by nikos on 5/5/2016.
 */
@Configuration
@ComponentScan(basePackages="eu.arcadia.sample")
@EnableAutoConfiguration
@RestController
public class Application extends SpringBootServletInitializer {
    private static final Logger logger = Logger.getLogger(Application.class.getName());
    public static final Integer defaultPort = 8080;
    public static final String getterURI = "/getInfo";


    // Only for Component 1 (Comment out)
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String info() {
        String serverEndpoint = System.getProperty("Component.connectedEndpoint");
        System.setProperty("Component.receivedRequests",String.valueOf(Integer.parseInt(System.getProperty("Component.sentRequests"))+1));
        System.setProperty("Component.sentRequests",String.valueOf(Integer.parseInt(System.getProperty("Component.sentRequests"))+2));
        try {
            HttpResponse<String> response = Unirest.get(serverEndpoint).asString();
            return "<b>Information of the nodes found in path:</b><br/><pre> "+response.getBody()+"</pre>";
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        return "Error communicating with connected node !";
    }

    //Just needed to compile the jar. Never gets called.
    public static void main(String[] args) {
    }
}
