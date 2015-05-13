@ViewImagesAndVideosForAModel

Feature:  CWID8 - View images and videos for a model
  As a user I would like to visit the gallery of a model from header and share it on social network

 @ready
 Scenario: View images and videos for a model
   Given user is on home pAge
   And   click a model from the Global Header
   Then  ensure page is redirected to overview page for the model
   And   Navigate to the gallery page for that model
   When  the user clicks on the asset in the gallery
   Then  gallery overlay is displayed
   And   Ensure user is able to browse through the assets
   Then  Share an asset on social network
   And   Download an asset to the user's machine.

