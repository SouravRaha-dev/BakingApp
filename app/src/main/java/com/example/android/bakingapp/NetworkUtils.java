package com.example.android.bakingapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class NetworkUtils {
    private static final String URL =  "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
    private static URL getURLFromUri(Uri uri) {
        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }
    private static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();
            Scanner sc = new Scanner(in);
            sc.useDelimiter("\\A");
            boolean hasInput = sc.hasNext();
            if (hasInput)
                return sc.next();
            else
                return null;
        } finally {
            urlConnection.disconnect();
        }
    }
    public static List<Recipe> getListRecipesFromURL() {
        List<Recipe> listRecipes = new ArrayList<>();
        URL url = getURLFromUri(Uri.parse(URL));
        AsyncTask<URL, Void, List<Recipe>> async = new GetRecipeTask().execute(url);
        try {
            listRecipes = async.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return listRecipes;
    }
    private static class GetRecipeTask extends AsyncTask<URL, Void, List<Recipe>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<Recipe> doInBackground(URL... params) {
            URL urlQuery = params[0];
            String resultQuery;
            List<Recipe> recipes = new ArrayList<>();
            try {
                resultQuery = getResponseFromHttpUrl(urlQuery);
                recipes = JsonUtils.parseJson(resultQuery);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return recipes;
        }

        @Override
        protected void onPostExecute(List<Recipe> recipes) {
            super.onPostExecute(recipes);
        }
    }
    public static boolean isNetworkAvailable(Context context) {
        int[] networkTypes = { ConnectivityManager.TYPE_MOBILE, ConnectivityManager.TYPE_WIFI };
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            for (int networkType : networkTypes) {
                NetworkInfo networkInfo = cm.getNetworkInfo(networkType);
                if (networkInfo != null && networkInfo.getState() == NetworkInfo.State.CONNECTED)
                    return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }
    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            return BitmapFactory.decodeStream(input);
        } catch (IOException e) {
            return null;
        }
    }
    public static boolean isAnImage(String url) {
        Boolean answer = null;
        AsyncTask<String, Void, Boolean> async = new CheckIsAnImageTask().execute(url);
        try {
            answer = async.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return answer;
    }
    private static class CheckIsAnImageTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(String... params) {
            Boolean answer = null;
            try {
                URL url = new URL(params[0]);
                URLConnection connection = url.openConnection();
                String contentType = connection.getHeaderField("Content-Type");
                answer = contentType.startsWith("image/");
            } catch (Exception e){
                e.printStackTrace();
            }
            return answer;
        }

        @Override
        protected void onPostExecute(Boolean answer) {
            super.onPostExecute(answer);
        }
    }
}
