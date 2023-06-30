package org.example;
import com.google.gson.Gson;
import io.github.cdimascio.dotenv.Dotenv;

public class BodyAuth {

    Dotenv dotenv = Dotenv.load();
    private final String client_id = dotenv.get("CLIENT_ID");
    private final String client_secret = dotenv.get("CLIENT_SECRET");

    @Override
    public String toString() {
        return (new Gson()).toJson(this);
    }
    public String getId(){
        return client_id;
    }
}
