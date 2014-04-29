package edu.wwu.classfinder2;

import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.HttpURLConnection;

import org.apache.commons.io.IOUtils;

public class FragmentTranscript extends Fragment {
	
	class TranscriptTask extends AsyncTask<URL, Integer, String> {

		@Override
		protected String doInBackground(URL... params) {
			
			try {
				URL url = params[0];
				HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
				InputStream in = new BufferedInputStream(urlConnection.getInputStream());
				return IOUtils.toString(in);
			} catch (Exception e){
				e.printStackTrace();
				return "Something went wrong in AsyncTask";
			}
		}
	}
	
	@Override
	public void onAttach(Activity activity){
		super.onAttach(activity);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup contrainer, Bundle savedInstanceState){
		View view = inflater.inflate(R.layout.fragment_main, null);
		
		URL url = null;
		try { url = new URL("https://admin.wwu.edu/pls/wwis/wwskahst.WWU_ViewTran"); }
		catch (MalformedURLException e) { e.printStackTrace(); }
		
		TranscriptTask mtt = new TranscriptTask();
		mtt.execute(url);
		
		String in = null;		
		try {
			in = mtt.get();
			Log.d("HI", in);
		} catch (Exception e){
			e.printStackTrace();
			Log.d("HI", "Something went wrong");
		}
		
		TextView page = (TextView) view.findViewById(R.id.page);
		page.setText(in);
		
		return view;
	}
}