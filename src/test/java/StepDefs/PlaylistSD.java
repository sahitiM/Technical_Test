package StepDefs;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import gherkin.deps.com.google.gson.JsonElement;
import gherkin.deps.com.google.gson.JsonParser;
import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.BasicResponseHandler;
import org.junit.Assert;

import java.io.IOException;
import java.util.Map;

public class PlaylistSD {



    static String baseurl ="http://turing.niallbunting.com:3003/api/playlist/";
    private HttpResponse httpResponse;
    private String response;
    static String newPlaylistID;



    @Given("^the playlist api is up and running$")
    public void thePlaylistApiIsUpAndRunning(){
        this.baseurl = baseurl;
    }

    @When("^user performs post request for a playlist with a body$")
    public void userPerformsPostRequestForAPlaylistWithABody() throws IOException {
        String jsonBodyPlayList = "{\n" +
                "\t\"desc\": \"My first playlist.\",\n" +
                "\t\"title\": \"My List\"\n" +
                "}";
        System.out.println(baseurl);
        httpResponse = Request.Post(baseurl)
                .addHeader("content-type", "application/json")
                .addHeader("Accept", "application/json")
                .bodyString(jsonBodyPlayList, ContentType.APPLICATION_JSON)
                .execute()
                .returnResponse();
        System.out.println(httpResponse.toString());

    }

    @And("^verify with expected playlist response body$")
    public void verifyWithExpectedPlaylistResponseBody() throws IOException {
        ResponseHandler< String > handler = new BasicResponseHandler();
        response = handler.handleResponse(httpResponse);
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(response).getAsJsonObject().get("_id");
        newPlaylistID= element.toString();
        newPlaylistID= newPlaylistID.replace("\"", "");
        System.out.println("playlist ID = " +newPlaylistID);
    }

    @When("^user performs get request for playlist$")
    public void userPerformsAGetRequestForPlaylist() throws IOException {
        System.out.println(baseurl);
        httpResponse = Request.Get(baseurl).connectTimeout(1000).socketTimeout(1000).execute().returnResponse();
       /* ResponseHandler<String> handler = new BasicResponseHandler();
        response = handler.handleResponse(httpResponse).toString();
        System.out.println(response);
        System.out.println(newPlaylistID);*/

    }




    @When("^user performs a patch request for a playlist by id$")
    public void userPerformsAPatchRequestForAPlaylistById() throws IOException {
        System.out.println(baseurl);
        httpResponse = Request.Patch(baseurl).connectTimeout(1000).socketTimeout(1000).execute().returnResponse();

    }

    @When("^user performs a delete request for a playlist by id$")
    public void userPerformsADeleteRequestForAPlaylistById() throws IOException {
        System.out.println(baseurl);
        httpResponse = Request.Delete(baseurl).connectTimeout(1000).socketTimeout(1000).execute().returnResponse();

    }

    @Then("^we get (\\d+) as a response for playlist$")
    public void weGetAsAResponseForPlaylist(int code){
        int ActualCode = httpResponse.getStatusLine().getStatusCode();
        Assert.assertEquals(code, ActualCode);

    }

    @When("^user gets single playlist by id$")
    public void userGetsSinglePlaylistById() throws IOException {
        baseurl = baseurl+newPlaylistID;
        System.out.println(baseurl);
        httpResponse = Request.Get(baseurl).connectTimeout(1000).socketTimeout(1000).execute().returnResponse();
        ResponseHandler<String> handler = new BasicResponseHandler();
        response = handler.handleResponse(httpResponse).toString();
        System.out.println(response);

    }
    @When("^user performs a delete request for a song by id$")
    public void userPerformsADeleteRequestForASongById() throws IOException {
        System.out.println(baseurl);
        httpResponse = Request.Delete(baseurl).connectTimeout(1000).socketTimeout(1000).execute().returnResponse();
    }
}
