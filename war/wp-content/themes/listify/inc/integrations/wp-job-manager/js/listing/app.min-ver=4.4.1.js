(function(){var a,b,c=function(a,b){return function(){return a.apply(b,arguments)}};a=function(){function a(){this.setMarker=c(this.setMarker,this),this.setupMap=c(this.setupMap,this),this.setOptions=c(this.setOptions,this),this.canvas="listing-contact-map",document.getElementById(this.canvas)&&(this.setOptions(),this.setupMap(),this.setMarker())}return a.prototype.setOptions=function(){return this.options=listifySingleMap,this.latlng=new google.maps.LatLng(this.options.lat,this.options.lng),this.zoom=parseInt(this.options.mapOptions.zoom),this.styles=this.options.mapOptions.styles,this.mapOptions={zoom:this.zoom,center:this.latlng,scrollwheel:!1,draggable:!1,styles:this.styles,streetViewControl:!1}},a.prototype.setupMap=function(){return this.map=new google.maps.Map(document.getElementById(this.canvas),this.mapOptions)},a.prototype.setMarker=function(){return this.marker=new RichMarker({position:this.latlng,flat:!0,draggable:!1,content:'<div class="map-marker type-'+this.options.term+'"><i class="'+this.options.icon+'"></i></div>'}),this.marker.setMap(this.map)},a}(),b=function(){return new a},google.maps.event.addDomListener(window,"load",b),jQuery(function(a){var b;return new(b=function(){function b(){this.toggleStars=c(this.toggleStars,this),this.bindActions=c(this.bindActions,this),this.bindActions()}return b.prototype.bindActions=function(){return a(".comment-sorting-filter").on("change",function(b){return a(this).closest("form").submit()}),a("#respond .stars-rating .star").on("click",function(a){return function(b){return b.preventDefault(),a.toggleStars(b.target)}}(this))},b.prototype.toggleStars=function(b){var c;return a("#respond .stars-rating .star").removeClass("active"),b=a(b),b.addClass("active"),c=b.data("rating"),0===a("#comment_rating").length?a(".form-submit").append(a("<input />").attr({type:"hidden",id:"comment_rating",name:"comment_rating",value:c})):a("#comment_rating").val(c)},b}())}),jQuery(function(a){var b;return new(b=function(){function b(){this.slick=c(this.slick,this),this.gallery=c(this.gallery,this),this.slick(),this.gallery()}return b.prototype.gallery=function(){var b,c;return c=a("#job_preview").length||a(".no-gallery-comments").length,b={gallery:{enabled:!0,preload:[1,1]}},c?b.type="image":(b.type="ajax",b.ajax={settings:{type:"GET",data:{view:"singular"}}},b.callbacks={open:function(){return a("body").addClass("gallery-overlay")},close:function(){return a("body").removeClass("gallery-overlay")},lazyLoad:function(b){var c;return c=a(b.el).data("src")},parseAjax:function(b){return b.data=a(b.data).find("#main")}}),a(".listing-gallery__item-trigger").magnificPopup(b)},b.prototype.slick=function(){return a(".listing-gallery").slick({slidesToShow:1,slidesToScroll:1,arrows:!1,fade:!0,adaptiveHeight:!0,asNavFor:".listing-gallery-nav"}),a(".listing-gallery-nav").slick({slidesToShow:7,slidesToScroll:4,asNavFor:".listing-gallery",dots:!0,arrows:!1,focusOnSelect:!0,infininte:!0,responsive:[{breakpoint:1200,settings:{slidesToShow:5}}]})},b}())})}).call(this);
//# sourceMappingURL=app.min.map