package twitchviewer.assist;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class TwitchConnector {
	public static List<String> getFollowedChannels(final String username) {
		final List<String> channels = new ArrayList<String>();
		
		try {
			final HttpResponse<JsonNode> jsonResponse = Unirest.get("https://api.twitch.tv/kraken/users/" + username + "/follows/channels")
					.header("accept","application/vnd.twitchtv.v3+json")
					.queryString("direction","ASC")
					.queryString("limit",1000)
					.asJson();
			
			final JSONObject jsonObject = jsonResponse.getBody().getObject();
			final JSONArray jsonArray = jsonObject.getJSONArray("follows");
			for(int i = 0; i < jsonArray.length(); i++) {
				JSONObject jo = jsonArray.getJSONObject(i);
				JSONObject jo2 = jo.getJSONObject("channel");
				channels.add(jo2.getString("url"));
				/*System.out.println(jo2.get("url"));
				System.out.println(jo2.get("logo"));*/
				
			}
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return channels;
	}
}
