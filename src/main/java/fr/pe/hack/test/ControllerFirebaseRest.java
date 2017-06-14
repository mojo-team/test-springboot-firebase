package fr.pe.hack.test;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Created by christophe on 12/06/17.
 */
@RestController
@RequestMapping("firebase/rest")
public class ControllerFirebaseRest {

    @RequestMapping("litValeur")
    public String litUneValeurSimple() {

        RestTemplate client = new RestTemplate();
        String resultat = client.getForObject("https://test-3c860.firebaseio.com/test/users/1/full_name.json", String.class);
        return resultat;
    }

    @RequestMapping("litUser")
    public User litUnObjetComplet() {

        RestTemplate client = new RestTemplate();
        User resultat = client.getForObject("https://test-3c860.firebaseio.com/test/users/1.json", User.class);
        return resultat;
    }

    @RequestMapping("ecritUser")
    public String ecritUnObjetComplet() {

        RestTemplate client = new RestTemplate();
        User user = new User("01-01-2000", "Luke Skywalker", "Je suis ton fils");
        client.put("https://test-3c860.firebaseio.com/test/users/luke.json", user);
        return "OK";
    }


}
