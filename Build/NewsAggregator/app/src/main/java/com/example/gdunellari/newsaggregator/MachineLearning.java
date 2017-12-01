package com.example.gdunellari.newsaggregator;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by bbbbbfitzgerald on 11/30/17.
 */

public class MachineLearning extends AsyncTask<Integer, Void, JSONObject> {

    @Override
    protected JSONObject doInBackground(Integer... toRemove) {
        return decideNew(toRemove);
    }

    private JSONObject decideNew(Integer[] toRemove){
        JSONObject articles = getApiInput();
        Integer maxMatch = 0;
        JSONObject newArticle = new JSONObject();
        try {
            JSONArray articles_array = (JSONArray)articles.get("articles");
            for (int i = 0; i < articles_array.length(); i++) {
                JSONObject single_article = articles_array.getJSONObject(i);
                Integer interestMatch = analyzeArticle(single_article);
                if (interestMatch > maxMatch) {
                    maxMatch = interestMatch;
                    newArticle = single_article;
                }
            }

        }catch (JSONException e) {}

        manageFile(newArticle);

        return newArticle;

    }

    private void manageFile (JSONObject newArticle) {


        File storage = new File("file:///android_asset");
        File authorsFile = new File(storage,"authors.txt");
        File sourcesFile = new File(storage,"sources.txt");
        File subjectsFile = new File(storage,"subjects.txt");

        try {
            String authors = newArticle.getString("author")
                    + "\n" + fileToString(authorsFile);
            String sources = newArticle.getJSONObject("source").getString("id")
                    + "\n" + fileToString(sourcesFile);
            String subjects = findSubjects(newArticle.getString("title"))
                    + "\n" + fileToString(subjectsFile);

            stringToFile(authors, authorsFile);
            stringToFile(sources, sourcesFile);
            stringToFile(subjects, subjectsFile);

        }catch (JSONException e) {}

    }

    private String fileToString(File file){
        StringBuffer inputBuffer = new StringBuffer();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            int lineCount = 0;

            while ((line = br.readLine()) != null) {
                lineCount++;
                if (lineCount < 100) {
                    inputBuffer.append(line);
                    inputBuffer.append('\n');
                }
            }
        } catch (IOException e) {}

        return inputBuffer.toString();
    }

    private void stringToFile(String string, File file){
        try {
            OutputStreamWriter outputWriter = new OutputStreamWriter(new FileOutputStream(file));
            outputWriter.write(string);
            outputWriter.close();
        } catch(IOException e) {}
    }

    private int analyzeArticle (JSONObject article){

        File storage = new File("file:///android_asset");

        String authors = fileToString(new File(storage,"authors.txt"));
        String sources = fileToString(new File(storage,"sources.txt"));
        String subjects = fileToString(new File(storage,"subjects.txt"));
        String newAuthor;

        try {
            newAuthor = article.getString("author");
            String newSource = article.getJSONObject("source").getString("id");
            String newSubject = findSubjects(article.getString("title"));


            int fileLength = countNewline(authors);
            int authorCount = fileLength - countNewline(authors.replace("\n" + newAuthor + "\n", "\n"));
            int sourceCount = fileLength - countNewline(sources.replace("\n" + newSource + "\n", "\n"));
            int subjectCount = fileLength - countNewline(subjects.replace("\n" + newSubject + "\n", "\n"));

            return ((authorCount * 3) + (sourceCount * 3) + (subjectCount * 4)) / fileLength;
        } catch(JSONException e){}

        return 0;
    }

    private int countNewline (String string){
        return string.length() - string.replace("\n","").length();
    }

    private String findSubjects (String string){
        return "something about trump";
    }

    private JSONObject getApiInput(){

        JSONObject articles = new JSONObject();
        try {
            articles.put("status", "ok");

            JSONArray array = new JSONArray()
                    .put(new JSONObject()
                            .put("source",new JSONObject()
                                    .put("id","abc-news")
                                    .put("name","ABC News")
                            )
                            .put("author","ABC News")
                            .put("title","Thousands of kids could lose health insurance next month if Congress doesn't act fast")
                            .put("description","States plan to notify families soon they may not have insurance for their kids.")
                            .put("url","http://abcnews.go.com/Health/thousands-kids-lose-health-insurance-month-congress-act/story?id=51407468")
                            .put("urlToImage","http://a.abcnews.com/images/Politics/children-doctor-gty-hb-171129_16x9_992.jpg")
                            .put("publishedAt","2017-11-29T00:00:00Z")
                    )
                    .put(new JSONObject()
                            .put("source", new JSONObject()
                                    .put("id","abc-news")
                                    .put("name","ABC News")
                            )
                            .put("author","ABC News")
                            .put("title","Trump rallies support for tax bill as GOP tries to push it 'across the finish line'")
                            .put("description","With a Senate vote looming on Republicans' long-sought tax reform aspirations, President Donald Trump gave the party's plan one final pep rally in Missouri Wednesday as he implored Congress to deliver on the \"once in a lifetime opportunity.\"\nThe president's speech on the matter came one day after...")
                            .put("url","http://abcnews.go.com/Politics/trump-rallies-support-lifetime-tax-reform-opportunity/story?id=51457253")
                            .put("urlToImage","http://a.abcnews.com/images/Politics/donald-trump-missouri-01-ap-jc-171129_16x9_992.jpg")
                            .put("publishedAt","2017-11-29T00:00:00Z")
                    );
            articles.put("articles",array);
        }
        catch (JSONException e) {}

        return articles;
    }


}