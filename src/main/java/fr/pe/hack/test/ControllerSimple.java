package fr.pe.hack.test;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by christophe on 12/06/17.
 */
@RestController
public class ControllerSimple {

    @RequestMapping("hello")
    public String retourneUnMessage(@RequestParam String prenom) {
        return String.format("%s, je suis ton père!", prenom);
    }
}
