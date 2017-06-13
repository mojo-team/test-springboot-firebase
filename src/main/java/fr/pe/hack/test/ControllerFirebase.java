package fr.pe.hack.test;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseCredentials;
import com.google.firebase.database.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("firebase")
public class ControllerFirebase {

    @Value(value = "classpath:test-8a7fd6d9e7b0.json")
    private Resource monTokenFirebase;

    private static FirebaseApp maBaseFirebase = null;

    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerFirebase.class);

    @RequestMapping("ping")
    public String testDeConnexionAFirebase() throws IOException {

        creeLaConnexionAFirebase();

        return "OK";

    }

    @RequestMapping("ecrit")
    public String testDEcritureDansFirebase(@RequestParam String idUser) throws IOException {

        creeLaConnexionAFirebase();

        DatabaseReference usersRef = pointeVersLaBaseDesUsers();

        Map<String, Object> users = new HashMap<String, Object>();
        // String id = UUID.randomUUID().toString();
        String id = idUser;
        users.put(id, new User("June 23, 1912", "Alan Turing", "id-" + id));

        usersRef.updateChildren(users);

        return "OK - " + id;

    }

    @RequestMapping("lit")
    public String testDeLectureDansFirebase(@RequestParam String idUser) throws IOException {
        creeLaConnexionAFirebase();

        DatabaseReference usersRef = pointeVersLaBaseDesUsers();

        // Pour retrouver juste la valeur d'un noeud
        DatabaseReference unUser = usersRef.child(idUser).child("nickname");
        unUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String nickname = dataSnapshot.getValue(String.class);
                LOGGER.error("Nickname : " + nickname);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });

        // Retrouve toutes les propriétés d'un noeud (si celui-ci n'a que des feuilles)
        usersRef.child(idUser).addChildEventListener(new ChildEventListener() {
            @Override
            // Un callback à cette méthode par propriété
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                LOGGER.error("Propriété : " + dataSnapshot.getValue());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) { }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) { }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) { }
            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });

        // Retrouve tous les sous-noeuds d'un noeud
        usersRef.addChildEventListener(new ChildEventListener() {
            @Override
            // Un callback à cette méthode par noeud (objet)
            // Chaque "value" retournée est une HashMap contenant toutes les "feuilles" du noeud
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                LOGGER.error("User : " + dataSnapshot.getValue() + " - classe : " + dataSnapshot.getValue().getClass().getName());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) { }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) { }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) { }
            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });

        return"OK";
    }

    private DatabaseReference pointeVersLaBaseDesUsers() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("test");
        return ref.child("users");
    }


    private void creeLaConnexionAFirebase() throws IOException {
        if (maBaseFirebase == null) {
            InputStream serviceAccount = monTokenFirebase.getInputStream();

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredential(FirebaseCredentials.fromCertificate(serviceAccount))
                    .setDatabaseUrl("https://test-3c860.firebaseio.com/")
                    .build();

            maBaseFirebase = FirebaseApp.initializeApp(options);
        }
    }


}
