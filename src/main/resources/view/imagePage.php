<!DOCTYPE html>
<html>
  <head>
    <title>Photo Page</title>
    <meta charset="utf-8" />
    <!--Style Sheets-->
    <link rel="stylesheet" href="../css/photoswipe.css">
    <link rel="stylesheet" href="../css/bootstrap.min.css">
    <link rel="stylesheet" href="../css/bootstrap-theme.min.css">
	<link rel="stylesheet" href="../css/default-skin/default-skin.css"> 

    <!--Java Script-->
    <script type="text/javascript" src="../js/jquery-2.2.3.min.js"></script>
    <script type="text/javascript" src="../js/bootstrap.min.js"></script>
    <script type="text/javascript" src="../js/handlebars.min.js"></script>
    <script type="text/javascript" src="../js/photoswipe.min.js"></script>
    <script type="text/javascript" src="../js/photoswipe-ui-default.min.js"></script>
	
	<!--Import the data file based on the input-->
	<script type="text/javascript" src='<?php echo htmlspecialchars($_GET["source"]);?>'></script>

    <script type="text/javascript">
	  //Double check that we have the file data.
	  var sourceValid = typeof pageFile !== 'undefined';
	
      //This variable keeps track of the current view
      var galleryView = true;

      //This function changes the picture view between a gallery view and a list view (and updates the button)
      function togglePictureView() {
        if (galleryView) {
          $("#toggleViewButton").html("Gallery View");
        } else {
          $("#toggleViewButton").html("List View");
        }

        $("#images-gallery").toggle();
        $("#images-list").toggle();
        galleryView = !galleryView;
      }

      //This handlebars function provides means of looping over a grid of a speficied width (the every variable)
      //For example if the total length is 20 and every is 4, it will produce an array with a length of 5 (rows) 
      //where each element is another array of 4 (columns)
      Handlebars.registerHelper('grouped_each', function(every, context, options) {
        var out = "",
          subcontext = [],
          i;
        if (context && context.length > 0) {
          for (i = 0; i < context.length; i++) {
            if (i > 0 && i % every === 0) {
              out += options.fn(subcontext);
              subcontext = [];
            }
            context[i].index = i;
            subcontext.push(context[i]);
          }
          out += options.fn(subcontext);
        }
        return out;
      });

      //This function kicks off the slide show at the specified index.
      function startSlideAt(startingPic) {
		if (!sourceValid) return 0;
        var pswpElement = document.querySelectorAll('.pswp')[0];

        // define options
        var options = {
          index: startingPic // start at the specified pic
        };

        // Initializes and opens PhotoSwipe
        var gallery = new PhotoSwipe(pswpElement, PhotoSwipeUI_Default, pageFile.items, options);
        gallery.init();
      }

      //startup functions
      $(document).ready(function() {
		if(sourceValid){
			
			//set the title
			if (pageFile.title) {
			  document.title = pageFile.title;
			}

			//set the body using handlebars
			var handlebarsSource = $("#body-template").html();
			var handlebarsTemplate = Handlebars.compile(handlebarsSource);
			$("#body-holder").html(handlebarsTemplate(pageFile));

			//update the first column to be offset (so it's center aligned).
			$(".image-col-0").addClass("col-md-offset-2");
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
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="index.html">Home</a>
          </div>
          <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
              <li>
                <a href="javascript:startSlideAt(0)">Start Slideshow</a>
              </li>
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

            <button type="button" class="btn btn-default" onclick="startSlideAt(0)">Start Slideshow</button>
            {{#if download_link}}
            <a href="{{download_link}}" class="btn btn-default" role="button" download>Download All</a> {{/if}}
            <button id="toggleViewButton" type="button" class="btn btn-default" onclick="togglePictureView()">List View</button>
          </div>
        </div>
      </div>

      <!--Image template in gallery form-->
      <div id="images-gallery">
        {{#grouped_each 4 items}}
        <div class="row">
          {{#each this }}
          <div class='col-md-2 image-col-{{@index}}'>
            <a href='javascript:startSlideAt({{index}})' class='thumbnail'>
										{{#if msrc}}
										<img src='{{msrc}}'
										{{else}}
										<img src='{{src}}' 
										{{/if}}
											alt='{{title}}' width="140" hight="140" />
											</a>
          </div>
          {{/each}}
        </div>
        {{/grouped_each}}
      </div>

      <!--Image template in list form-->
      <div id="images-list" class="container" hidden>
        <ul class="list-group">
          {{#items}}
          <li class="list-group-item">
            <a href='javascript:startSlideAt({{index}})'>
              <div class="media-body">
                <h4 class="list-group-item-heading">{{title}}</h4> {{#if description}}
                <p class="list-group-item-text">{{description}}</p>
                {{/if}}
              </div>
              <div class="media-right">

                {{#if msrc}}
                <img src='{{msrc}}' {{else}} <img src='{{src}}' {{/if}} alt='{{title}}' width="140" hight="140" />

              </div>
            </a>
          </li>

          {{/items}}
        </ul>
      </div>
    </script>
  </head>

  <body>

    <!-- placeholder for the handlebars body. Display an error message if invalid-->
    <div id="body-holder">
		 <?php include("includes/errorTemplate.php"); ?>
	</div>


    <!--The following div is required for the slideshow and is taken directly from the photoswipe documentation -->
    <div class="pswp" tabindex="-1" role="dialog" aria-hidden="true">

      <!-- Background of PhotoSwipe. It's a separate element as animating opacity is faster than rgba(). -->
      <div class="pswp__bg"></div>

      <!-- Slides wrapper with overflow:hidden. -->
      <div class="pswp__scroll-wrap">

        <!-- Container that holds slides. 
											PhotoSwipe keeps only 3 of them in the DOM to save memory.
											Don't modify these 3 pswp__item elements, data is added later on. -->
        <div class="pswp__container">
          <div class="pswp__item"></div>
          <div class="pswp__item"></div>
          <div class="pswp__item"></div>
        </div>

        <!-- Default (PhotoSwipeUI_Default) interface on top of sliding area. Can be changed. -->
        <div class="pswp__ui pswp__ui--hidden">

          <div class="pswp__top-bar">

            <!--  Controls are self-explanatory. Order can be changed. -->

            <div class="pswp__counter"></div>

            <button class="pswp__button pswp__button--close" title="Close (Esc)"></button>

            <button class="pswp__button pswp__button--share" title="Share"></button>

            <button class="pswp__button pswp__button--fs" title="Toggle fullscreen"></button>

            <button class="pswp__button pswp__button--zoom" title="Zoom in/out"></button>

            <!-- Preloader demo http://codepen.io/dimsemenov/pen/yyBWoR -->
            <!-- element will get class pswp__preloader ctive when preloader is running -->
            <div class="pswp__preloader">
              <div class="pswp__preloader__icn">
                <div class="pswp__preloader__cut">
                  <div class="pswp__preloader__donut"></div>
                </div>
              </div>
            </div>
          </div>

          <div class="pswp__share-modal pswp__share-modal--hidden pswp__single-tap">
            <div class="pswp__share-tooltip"></div>
          </div>

          <button class="pswp__button pswp__button--arrow--left" title="Previous (arrow left)">
          </button>

          <button class="pswp__button pswp__button--arrow--right" title="Next (arrow right)">
          </button>

          <div class="pswp__caption">
            <div class="pswp__caption__center"></div>
          </div>
        </div>
      </div>
    </div>
  </body>

</html>