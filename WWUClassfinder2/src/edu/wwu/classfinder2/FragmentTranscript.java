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
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

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

		@Override
		protected String doInBackground(String... params) {

			try {				
				Document doc = Jsoup.connect(params[0]).get();
				// <!-- The following hidden fields must be part of the submitted Form -->
				Elements hidden = doc.select("input[type=hidden]");
				
				URL url = new URL(params[0]);
				HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
				conn.setRequestMethod("POST");
				conn.setConnectTimeout(5000);
				conn.setReadTimeout(5000);
				conn.setDoOutput(true);
				conn.setDoInput(true);
				
				OutputStream out = conn.getOutputStream();
				
				return null;
			} catch (IOException e) {
				return "Something went wrong";
			}
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
		URL url = new URL("https://websso.wwu.edu/cas/login");
		LoginTask lt = new LoginTask();
		lt.execute("https://websso.wwu.edu/cas/login");
		return lt.get();
	}

	private String getTranscript(String username, String password) throws Exception {
		URL url = new URL("https://admin.wwu.edu/pls/wwis/wwskahst.WWU_ViewTran");

		TranscriptTask mtt = new TranscriptTask();
		mtt.execute(url);
		return mtt.get();
	}
}