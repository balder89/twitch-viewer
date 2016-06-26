package twitchviewer.assist;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class TwitchConnector {
	public static List<Map<String,String>> getFollowedChannels(final String username) {
		final List<Map<String,String>> channels = new ArrayList<Map<String,String>>();
		
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
				Map<String,String> map = new HashMap<String,String>();
				
				Thumbnailer.getBufferedImageFromUrl((jo2.get("logo").equals(null)) ? "" : jo2.getString("logo"));
				
				map.put("logo","");
				map.put("url",jo2.getString("url"));
				channels.add(map);
				
			}
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return channels;
	}
}
