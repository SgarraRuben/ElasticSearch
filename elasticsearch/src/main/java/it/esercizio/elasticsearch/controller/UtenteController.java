package it.esercizio.elasticsearch.controller;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sun.el.parser.ParseException;

import it.esercizio.elasticsearch.entity.Utente;

@RestController
public class UtenteController {

	@GetMapping("/findbyid")
	public Utente test(@RequestParam("id") String id) throws ClientProtocolException, IOException {
		Utente utente = new Utente();
		CloseableHttpClient httpClient = HttpClients.createDefault();

		try {

			HttpGet request = new HttpGet("http://localhost:9200/utente/test/" + id);

			// add request headers
			request.addHeader("content-type", "application/json");
			request.addHeader(HttpHeaders.USER_AGENT, "Googlebot");

			CloseableHttpResponse response = httpClient.execute(request);

			try {

				// Get HttpResponse Status
				System.out.println(response.getProtocolVersion()); // HTTP/1.1
				System.out.println(response.getStatusLine().getStatusCode()); // 200
				System.out.println(response.getStatusLine().getReasonPhrase()); // OK
				System.out.println(response.getStatusLine().toString()); // HTTP/1.1 200 OK

				HttpEntity entity = response.getEntity();
				if (entity != null) {
					// return it as a String
					String result = EntityUtils.toString(entity);
					// System.out.println(result);
					JsonElement je = new Gson().fromJson(result, JsonElement.class);
					JsonObject jo = je.getAsJsonObject();
					// jo.add("_source");
					// System.out.println(jo.get("_source"));
					JsonObject j = (JsonObject) jo.get("_source");

					String name = j.get("name").getAsString();
					String lastname = j.get("lastname").getAsString();
					int eta = Integer.parseInt(j.get("eta").toString());
					utente.setId(id);
					utente.setName(name);
					utente.setLastname(lastname);
					utente.setEta(eta);

				}

			} finally {
				response.close();
			}
		} finally {
			httpClient.close();
		}

		return utente;
	}

	@PostMapping("/createUtente")
	public Utente postUtente(@RequestParam("name") String name, @RequestParam("lastname") String lastname,
			@RequestParam("eta") Integer eta) throws ParseException, IOException {
		Utente utente = new Utente();
		HttpPost post = new HttpPost("http://127.0.0.1:9200/utente/test");
		post.addHeader("content-type", "application/json");
		utente.setName(name);
		utente.setLastname(lastname);
		utente.setEta(eta);
		String json = new Gson().toJson(utente);
		post.setEntity(new StringEntity(json));
		try (CloseableHttpClient httpClient = HttpClients.createDefault();
				CloseableHttpResponse response = httpClient.execute(post)) {
			String result = EntityUtils.toString(response.getEntity());
			JsonElement je = new Gson().fromJson(result, JsonElement.class);
			JsonObject jo = je.getAsJsonObject();
			utente.setId(jo.get("_id").getAsString());
		}
		return utente;
	}

}
