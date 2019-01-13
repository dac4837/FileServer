<!DOCTYPE html>
<html>

  <head>
    <title>Video Page</title>
    <meta charset="utf-8" />
    <!--Style Sheets-->
    <link rel="stylesheet" href="../css/bootstrap.min.css">
    <link rel="stylesheet" href="../css/bootstrap-theme.min.css">

    <!--Java Script-->
    <script type="text/javascript" src="../js/jquery-2.2.3.min.js"></script>
    <script type="text/javascript" src="../js/bootstrap.min.js"></script>
    <script type="text/javascript" src="../js/handlebars.min.js"></script>

    <!--Import the data file based on the input-->
    <script type="text/javascript" src='<?php echo htmlspecialchars($_GET["source"]);?>'></script>

    <script type="text/javascript">
	  //Double check that we have the file data.
	  var sourceValid = typeof pageFile !== 'undefined';
	  
      //This variable keeps track of whether the preview is visible or not
      var previewVisible = true;

      //This function helps determine if the video type is compatible for in-browser viewing
      function isSupportedVideo(type) {
        return (type.toString().toLowerCase() === "mp4" || type.toString().toLowerCase() === "ogg" || type.toString().toLowerCase() === "webm");
      }

      //This handlebars function is a wrapper for the isSupportedVideo function but for use with handlbars.
      Handlebars.registerHelper('ifSupportedVideo', function(type, options) {
        if (isSupportedVideo(type)) {
          return options.fn(this);
        } else {
          return "";
        }
      });

      Handlebars.registerHelper('toLowerCase', function(str) {
        return str.toLowerCase();
      });

      //startup functions
      $(document).ready(function() {
		if(sourceValid){
			//set the title
			if (pageFile.title) {
			  document.title = pageFile.title;
			}

			//set the body
			var handlebarsSource = $("#body-template").html();
			var handlebarsTemplate = Handlebars.compile(handlebarsSource);
			$("#body-holder").html(handlebarsTemplate(pageFile));

			//hide the preview button if there's nothing to preview
			var shouldHide = true;
			for (index = 0; index < pageFile.items.length; index++) {
			  if (isSupportedVideo(pageFile.items[index].type)) {
				shouldHide = false;
				break;
			  }
			}

			//If determined that there are no videos to display, hide the 'hide video preview' button
			if (shouldHide) {
			  $("#toggleViewButton").hide();
			}
		}
      });

      //This function toggles the hide/show button. It will hide all of the previews or redisplay them.
      function toggleVideoPreview() {
        if (previewVisible) {
          $("#toggleViewButton").html("Show Previews");
        } else {
          $("#toggleViewButton").html("Hide Previews");
        }

        $('.video-preview').toggle();
        previewVisible = !previewVisible;
      }

    </script>

    <!--Templated main body-->
    <script id="body-template" type="text/x-handlebars-template">
      <!-- navigation bar -->
      <nav class="navbar navbar-default">
        <div class="container">
          <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false">
              <span class="sr-only">Toggle navigation</span>
              <span class="icon-bar" />
              <span class="icon-bar" />
              <span class="icon-bar" />
            </button>
            <a class="navbar-brand" href="index.html">Home</a>
          </div>
          <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
              {{#if download_link }}
              <li>
                <a href="{{download_link}}" download>Download All</a>
              </li>
              {{/if}}

            </ul>
          </div>
        </div>
      </nav>

      <!--Title and description-->
      <div class="container">
        <div class="page-header">
		  <!--Include a banner if provided -->
		  {{#if banner}}
		  <div class="container">
			<img src="{{banner}}" alt="banner photo" class="img-responsive center-block" />
		  </div>
		  {{/if}}
          {{#if title}}
          <h1>{{title}}</h1> {{/if}} {{#if description}}
          <h4>{{description}}</h4> {{/if}}


          <!--Button controls -->
          <div class="btn-group" role="group">
            {{#if download_link}}
            <a href="{{download_link}}" class="btn btn-default" role="button" download>Download All</a> {{/if}}
            <!--<button id="toggleViewButton" type="button" class="btn btn-default" onclick="toggleVideoPreview()">Hide Previews</button>-->
          </div>
        </div>
      </div>

      <!--list of videos -->
      <div id="videos" class="container">

        <ul class="list-group">
          {{#items}}
          <li class="list-group-item">
            <div class="media-body">
              <h4 class="list-group-item-heading">{{title}}</h4> {{#if description}}
              <p class="list-group-item-text">{{description}}</p>
              {{/if}}

            </div>
            <div class="media-left">
              <a href="{{src}}" download="{{title}}" class="btn btn-default" role="button">Download</a>
            </div>

            <div class="media-right">
              {{#ifSupportedVideo type}}
			  <!--
              <video class="media-object video-preview" width="320" height="180" controls>
                <source src="{{src}}" alt="{{title}}" type="video/{{toLowerCase type}}" />
              </video>-->
              {{/ifSupportedVideo}}
            </div>
          </li>
          {{/items}}
        </ul>
      </div>
    </script>
  </head>

  <body>

    <!-- placeholder for the handlebars body-->
    <div id="body-holder"><?php include("includes/errorTemplate.php"); ?></div>

  </body>

</html>
