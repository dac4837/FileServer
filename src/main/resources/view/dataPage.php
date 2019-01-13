<!DOCTYPE html>
<html>

  <head>
    <title>Data Page</title>
    <meta charset="utf-8" />
    <!--Style Sheets-->
    <link rel="stylesheet" href="../css/bootstrap.min.css">
    <link rel="stylesheet" href="../css/bootstrap-theme.min.css">

    <!--Java Script-->
    <script type="text/javascript" src="../js/jquery-2.2.3.min.js"></script>
    <script type="text/javascript" src="../js/bootstrap.min.js"></script>
    <script type="text/javascript" src="../js/handlebars.min.js"></script>

    <!--Import the data file based on the input-->
    <script type="text/javascript" src='<?php echo htmlspecialchars($_GET["source"]);?>'>

    </script>

    <script type="text/javascript">
	  //Double check that we have the file data.
	  var sourceValid = typeof pageFile !== 'undefined';
	  
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
		}
      });

    </script>

    <!--Templated main body-->
    <script id="body-template" type="text/x-handlebars-template">

      <!--navigation bar -->
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
          </div>
        </div>
      </div>

      <!--List of the data items -->
      <div class="container">
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
