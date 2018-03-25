package StepDefs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import gherkin.deps.com.google.gson.JsonArray;
import gherkin.deps.com.google.gson.JsonElement;
import gherkin.deps.com.google.gson.JsonObject;
import gherkin.deps.com.google.gson.JsonParser;
import gherkin.formatter.model.DataTableRow;
import org.apache.http.HttpHost;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.HttpResponse;
import org.junit.Assert;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.assertNull;

public class SongApiSD {


    static String baseurl ="http://turing.niallbunting.com:3003/api/video/";
    private HttpResponse httpResponse;
    private String response;
    static String videoID;



    @Given("^the song api is up and running$")
    public void the_apis_are_up_and_running_for_something() {
        this.baseurl = baseurl;
    }

    @When("^user performs post request with a body$")
    public void userPerformsARequest() throws IOException {
        String jsonBodyVideo = "{\n" +
                "\t\"artist\": \"Ed Sheeran\",\n" +
                "\t\"song\": \"Galway Girl\",\n" +
                "\t\"publishDate\": \"2017-10-02\"\n" +
                "}";
        System.out.println(baseurl);
        httpResponse = Request.Post(baseurl)
                .addHeader("content-type", "application/json")
                .addHeader("Accept", "application/json")
                .bodyString(jsonBodyVideo, ContentType.APPLICATION_JSON)
                .execute()
                .returnResponse();
        }

    @Then("^we get (\\d+) as a response$")
    public void weGetAsAResponse(int code) {
        int ActualCode = httpResponse.getStatusLine().getStatusCode();
        Assert.assertEquals(code, ActualCode);
    }


    @And("^verify with expected response body$")
    public void verifyWithExpectedResponseBody() throws Throwable {

        ResponseHandler < String > handler = new BasicResponseHandler();
        response = handler.handleResponse(httpResponse);
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(response).getAsJsonObject().get("_id");
        videoID= element.toString();
        videoID= videoID.replace("\"", "");
        System.out.println("video ID = " + videoID);



    }

    /*@When("^user creates a playlist \"([^\"]*)\" with following parameters$")
    public void userCreatesAPlaylistWithFollowingParameters(String postUrl) throws IOException {
       String id;
        String jsonBodyPlayList = "{\n" +
                "\t\"desc\": \"My first playlist.\",\n" +
                "\t\"title\": \"My List\"\n" +
                "}";
        this.baseurl = this.baseurl + postUrl;
        System.out.println(postUrl);
        System.out.println(baseurl);
        httpResponse = Request.Post(baseurl)
                .addHeader("content-type", "application/json")
                .addHeader("Accept", "application/json")
                .bodyString(jsonBodyPlayList, ContentType.APPLICATION_JSON)
                .execute()
                .returnResponse();

        System.out.println(httpResponse.toString());
        *//*ResponseHandler < String > handler = new BasicResponseHandler();
        response = handler.handleResponse(httpResponse);
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(response).getAsJsonObject().get("_id");
         this.newPlaylistID= element.toString();
        this.newPlaylistID= this.newPlaylistID.replace("\"", "");
        System.out.println("Playlist ID = " + this.newPlaylistID);
        this.newPlaylistID=id;
        System.out.println(id);*//*

    }*/



    @When("^user performs a get request")
    public void userPerformsAGetRequestTo() throws Throwable {

        System.out.println(baseurl);
        httpResponse = Request.Get(baseurl).connectTimeout(1000).socketTimeout(1000).execute().returnResponse();
        ResponseHandler<String> handler = new BasicResponseHandler();
        response = handler.handleResponse(httpResponse).toString();
        System.out.println(response);
        System.out.println(videoID);

    }

    @When("^user performs a delete request by id$")
    public void userperformsADeleteRequestTo() throws IOException {
        //this.baseurl = this.baseurl +deleteUrl;
       // System.out.println(deleteUrl);
        System.out.println(baseurl);
        httpResponse = Request.Delete(baseurl).connectTimeout(1000).socketTimeout(1000).execute().returnResponse();
    }


    @When("^user performs a patch request by id$")
    public void userPerformsAPatchRequestTo() throws IOException {

        System.out.println(baseurl);
        httpResponse = Request.Patch(baseurl).connectTimeout(1000).socketTimeout(1000).execute().returnResponse();


    }

    @When("^user performs a get Song request by ID$")
    public void userPerformsAGetSongRequestByID() throws IOException {

        baseurl = baseurl+videoID;
        System.out.println(baseurl);
        httpResponse = Request.Get(baseurl).connectTimeout(1000).socketTimeout(1000).execute().returnResponse();
        ResponseHandler<String> handler = new BasicResponseHandler();
        response = handler.handleResponse(httpResponse).toString();
        System.out.println(response);

    }


}


