package com.example.android.searchbook;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */
public final class QueryUtils {

    private QueryUtils(){

    }

    /**
     * Tag for the log messages
     */
    public static final String LOG_TAG = QueryUtils.class.getSimpleName();

    //Get the necessary information after parsing the JSON
    //This ties together the createUrl, makeHttpRequest and extractBooks methods

    public static List<Book> fetchBookData(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }

        // Extract relevant fields from the JSON response and create an {@link Event} object
        List<Book> books = extractBooks(jsonResponse);

        // Return the {@link Event}
        return books;
    }


    //Returns new URL object from the given string URL.

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }


     //Make an HTTP request to the given URL and return a String as the response.

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Return a list of {@link Book} objects that has been built up from
     * parsing a JSON response.
     */
    private static List<Book> extractBooks(String bookJSON) {
        //If the Json is empty  - return early
        if (TextUtils.isEmpty(bookJSON)) {
            return null;
        }

        //Create an empty ArrayList on which I will start adding the books
        List<Book> books = new ArrayList<>();

        try {
            //extract the root book object
            JSONObject root = new JSONObject(bookJSON);

            JSONArray itemsArray = root.getJSONArray("items");
            //extract the items array from inside the root JSONObject
            for(int i = 0; i < itemsArray.length(); i++) {
                //start adding the books
                JSONObject firstBook = itemsArray.getJSONObject(i);
                //get the volumeInfo of the book
                JSONObject volumeInfo = firstBook.getJSONObject("volumeInfo");
                //get the title
                String title = volumeInfo.getString("title");
                //get the authors
                JSONArray authorsArray = volumeInfo.getJSONArray("authors");
                String authors = authorsArray.toString();
                //get the preview link
                String previewLink = volumeInfo.getString("previewLink");

                //create a new object with the title, authors and the image from the JSON response
                Book book = new Book(title, authors, previewLink);

                //add it to the list of books
                books.add(book);
            }

        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the earthquake JSON results", e);
        }
        return books;
    }

}
