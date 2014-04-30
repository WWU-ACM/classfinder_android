package edu.wwu.classfinder2;

import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class FragmentTranscript extends Fragment {

	class TranscriptTask extends AsyncTask<URL, Integer, String> {

		@Override
		protected String doInBackground(URL... params) {
			try {
				URL url = params[0];
				HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
				InputStream in = new BufferedInputStream(urlConnection.getInputStream());
				return IOUtils.toString(in);
			} catch (Exception e) {
				return "Something went wrong in AsyncTask";
			}
		}
	}

	class LoginTask extends AsyncTask<String, Integer, String> {

		String charset = "UTF-8";

		@Override
		protected String doInBackground(String... params) {

			/* Ensure that we are given 3 parameters - URL, username, password */
			if (params.length != 3) {
				return null;
			}

			String url_login = params[0];
			Map<String, String> post_params = new HashMap<String, String>();

			try {
				/* Get the hidden values that must be part of the POST request */
				Document doc = Jsoup.connect(url_login).get();
				Elements hidden = doc.select("input[type=hidden]");
				post_params = new HashMap<String, String>();
				post_params.put("username", params[1]);
				post_params.put("password", params[2]);
				for (Element e : hidden) {
					post_params.put(e.attr("name"), e.attr("value"));
				}
				
				Element jid = doc.select("form#login_form").first();
				String jsessionid = jid.attr("action");
				
				URL url = new URL(url_login + jsessionid + "?service=https://mywestern.wwu.edu/mywestern/Login");
				HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
				conn.setDoOutput(true);
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				conn.setRequestProperty("Host", "websso.wwu.edu");
				conn.setRequestProperty("Accept", "application/xhtml+xml, */*");
				conn.setRequestProperty("Cookie", jsessionid);

				OutputStream out = conn.getOutputStream();
				out.write(formatPost(post_params));
				out.close();

				String resp = IOUtils.toString(conn.getInputStream());
				return conn.getHeaderFields().toString() + "\n\n\n\n" + resp;
			} catch (IOException e) {
				e.printStackTrace();
				return "Something went wrong";
			}
		}

		private byte[] formatPost(Map<String, String> post_params) throws UnsupportedEncodingException {
			String post_string = "";
			for (Entry<String, String> e : post_params.entrySet()) {
				post_string += URLEncoder.encode(e.getKey(), charset) + '=' + URLEncoder.encode(e.getValue(), charset)
						+ '&';
			}
			if (post_params.size() > 0) {
				post_string = post_string.substring(0, post_string.length() - 1);
			}
			return post_string.getBytes(charset);
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup contrainer, Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.fragment_main, null);

		Button mLogin = (Button) view.findViewById(R.id.login);
		mLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String username = ((TextView) view.findViewById(R.id.username)).getText().toString();
				String password = ((TextView) view.findViewById(R.id.password)).getText().toString();

				TextView page = (TextView) view.findViewById(R.id.page);
				try {
					page.setText(login(username, password));
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		});

		return view;
	}

	private String login(String username, String password) throws Exception {
		String wwu = "https://websso.wwu.edu/cas/";
		LoginTask lt = new LoginTask();
		lt.execute(wwu, username, password);
		return lt.get();
	}

	private String getTranscript(String username, String password) throws Exception {
		URL url = new URL("https://admin.wwu.edu/pls/wwis/wwskahst.WWU_ViewTran");

		TranscriptTask mtt = new TranscriptTask();
		mtt.execute(url);
		return mtt.get();
	}
}