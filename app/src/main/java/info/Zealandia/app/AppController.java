package info.Zealandia.app;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import info.Zealandia.model.SanctuaryView;
import info.Zealandia.util.LruBitmapCache;

public class AppController extends Application {
	public static final String TAG = AppController.class.getSimpleName();

	private RequestQueue mRequestQueue;
	private ImageLoader mImageLoader;

	// For our caching Default maximum disk usage in bytes
	private static final int DEFAULT_DISK_USAGE_BYTES = 25 * 1024 * 1024;

	//cache folder name
	private static final String DEFAULT_CACHE_DIR = "photos2";//"photos";

	private static AppController mInstance;

	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;
	}

	public static synchronized AppController getInstance() {
		return mInstance;
	}
	public RequestQueue getRequestQueue() {
		if (mRequestQueue == null) {
			mRequestQueue = this.newRequestQueue(getApplicationContext());
		}

		return mRequestQueue;
	}
	private static RequestQueue newRequestQueue(Context context) {
		// define cache folder
		File rootCache = context.getExternalCacheDir();
		if (rootCache == null) {

			rootCache = context.getCacheDir();
		}

		File cacheDir = new File(rootCache, DEFAULT_CACHE_DIR);
		cacheDir.mkdirs();

		HttpStack stack = new HurlStack();
		Network network = new BasicNetwork(stack);
		DiskBasedCache diskBasedCache = new DiskBasedCache(cacheDir, DEFAULT_DISK_USAGE_BYTES);
		RequestQueue queue = new RequestQueue(diskBasedCache, network);
		queue.start();

		return queue;
	}
	public RequestQueue getRequestQueue2() {
		//check cache before we make request
		//get external cache dir
		File rootCache = getExternalFilesDir(null);
		/*
		if (rootCache == null) {
			//need to log here think yar using volley.log
			rootCache = getExternalFilesDir(DEFAULT_CACHE_DIR);
		}*/
		// File file = new File(getExternalFilesDir(null), "DemoFile.jpg");
		//lets check
		File cacheDir = new File(rootCache, DEFAULT_CACHE_DIR);
		//create dir if not exists
		cacheDir.mkdirs();

		//singleton check  needs to comment code :)
		//if (mRequestQueue == null) {
		HttpStack stack = new HurlStack();
		Network network = new BasicNetwork(stack);
		DiskBasedCache diskBasedCache = new DiskBasedCache(cacheDir, DEFAULT_DISK_USAGE_BYTES);
		RequestQueue mRequestQueue = new RequestQueue(diskBasedCache, network);
		mRequestQueue.start();
		//mRequestQueue = Volley.newRequestQueue(getApplicationContext());
		//}

		return mRequestQueue;
	}

	public ImageLoader getImageLoader() {
		getRequestQueue();
		ImageLoader.ImageCache imageCache = new LruBitmapCache();
		if (mImageLoader == null) {
			mImageLoader = new ImageLoader(this.mRequestQueue,
					imageCache);
		}
		return this.mImageLoader;
	}

	public <T> void addToRequestQueue(Request<T> req, String tag) {
		// set the default tag if tag is empty
		req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
		getRequestQueue().add(req);
	}

	public <T> void addToRequestQueue(Request<T> req) {
		req.setTag(TAG);
		getRequestQueue().add(req);
	}

	public void cancelPendingRequests(Object tag) {
		if (mRequestQueue != null) {
			mRequestQueue.cancelAll(tag);
		}
	}

	//Our own storage
	public String readFromCache(String theStoreFileName) {

		String ret = "";

		try {
			InputStream inputStream = openFileInput(theStoreFileName + ".txt");

			if ( inputStream != null ) {
				InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
				BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
				String receiveString = "";
				StringBuilder stringBuilder = new StringBuilder();

				while ( (receiveString = bufferedReader.readLine()) != null ) {
					stringBuilder.append(receiveString);
				}

				inputStream.close();
				ret = stringBuilder.toString();
			}
		}
		catch (FileNotFoundException e) {


		} catch (IOException e) {
			e.printStackTrace();
		}

		return ret;
	}

	/*
		Writes to file requires the filename and data
	 */
	public void writeToFile(String theStoreFileName , String data) {
		try {

			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput(theStoreFileName + ".txt", Context.MODE_PRIVATE));
			outputStreamWriter.write(data);
			outputStreamWriter.close();
		}
		catch (IOException e) {

		}
	}


}