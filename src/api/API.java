package api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

public class API {

    public static void main(String[] args) {
        URL url;
        try {

            url = new URL("https://api.lomadee.com/v2/1501065109550272d9f6a/offer/_product/673955?sourceId=35796574&size=36");

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");
            BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));

            JsonReader jsonReader = Json.createReader(connection.getInputStream());
            JsonObject jsonObject = jsonReader.readObject();

            JsonArray ofertas = jsonObject.getJsonArray("offers");

            float menorPreco = 0;
            String nomeMenorPreco = "";
            float maiorPreco = 0;
            String nomeMaiorPreco = "";
            float media = 0;
            for (int i = 0; i < ofertas.size(); i++) {
                JsonObject oferta = ofertas.getJsonObject(i);

                float precoAtual = oferta.getJsonNumber("price").intValue();
                JsonObject store = oferta.getJsonObject("store");
                String nameStore = store.getJsonString("name").getString();
                
                if (menorPreco == 0) {
                    menorPreco = precoAtual;
                    maiorPreco = precoAtual;
                }
                if (precoAtual < menorPreco) {
                    menorPreco = precoAtual;
                    nomeMenorPreco = nameStore;
                } else if (maiorPreco < precoAtual) {
                    maiorPreco = precoAtual;
                    nomeMaiorPreco = nameStore;
                }
                media = media + precoAtual;
            }
            media = media/ofertas.size();
            
            System.out.println("O Menor preço é da " + nomeMenorPreco + " e é : " + menorPreco);
            System.out.println("O Maior preço é da " + nomeMaiorPreco + " e é : " + maiorPreco);
            System.out.println("A Media de preços é: "+media);

        } catch (IOException e) {
            System.out.println("Ocorreu algum erro ao processar a requisição, ERROR:" + e);
        }
    }

}
