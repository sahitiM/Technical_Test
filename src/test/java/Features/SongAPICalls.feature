@SongApiCalls

Feature:Happy path tests for GET,POST,PATCH and DELETE

  Background:
    Given the song api is up and running

  @postSong
  Scenario: create single video

    When user performs post request with a body
    Then we get 201 as a response
    And verify with expected response body

  @getSong
  Scenario: Get list of videos

    When user performs a get request
    Then we get 200 as a response


  @getSongById
  Scenario: Get a single video

    When user performs a get Song request by ID
    Then we get 200 as a response


  @patchSong
  Scenario: update videos
    When user performs a patch request by id
    Then we get 501 as a response


  @deleteSong @delete
  Scenario: delete video
    When user performs a delete request by id
    Then we get 204 as a response

