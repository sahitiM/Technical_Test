@PlaylistApiCalls

Feature:Happy path tests for GET,POST,PATCH and DELETE

  Background:
    Given the playlist api is up and running

  @postPlaylist
  Scenario: create single playlist

    When user performs post request for a playlist with a body
    Then we get 201 as a response for playlist
    And verify with expected playlist response body

  @getPlaylist
  Scenario: Get list of playlists

    When user performs get request for playlist
    Then we get 200 as a response for playlist


  @getPlaylistById
  Scenario: Get a single playlist

    When user gets single playlist by id
    Then we get 200 as a response for playlist


  @patchPlaylistByID
  Scenario: update playlist
    When user performs a patch request for a playlist by id
    Then we get 501 as a response for playlist


  @deletePlaylistById
  Scenario: delete playlist
    When user performs a delete request for a playlist by id
    Then we get 204 as a response for playlist





