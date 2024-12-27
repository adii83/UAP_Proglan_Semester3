package com.pustakadigital.api;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.json.JSONObject;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;

public class OpenLibraryAPI {

    private static final String BASE_URL = "https://openlibrary.org/api/books?bibkeys=ISBN:%s&format=json&jscmd=data";

    public JSONObject getBookInfoByISBN(String isbn) {
        String url = String.format(BASE_URL, isbn);
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                String jsonResponse = EntityUtils.toString(response.getEntity());
                JSONObject jsonObject = new JSONObject(jsonResponse);
                return jsonObject.optJSONObject("ISBN:" + isbn);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}