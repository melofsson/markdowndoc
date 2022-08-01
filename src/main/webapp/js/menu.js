

 

  $( ".section-header" ).on( "click", function( event ) {
    pagesDivElement = $(this).next("div");
    if (pagesDivElement.hasClass('pages-hidden')) {
      console.log(pagesDivElement.attr("class") + pagesDivElement.hasClass('pages-hidden'));
      $(this).addClass("section-opened");
      $(this).find("i").removeClass("fa-caret-right");
      pagesDivElement.removeClass('pages-hidden');
      $(this).find("i").addClass("fa-caret-down");
    } else {
      pagesDivElement.addClass('pages-hidden');
      $(this).find("i").removeClass("fa-caret-down");
      $(this).find("i").addClass("fa-caret-right");
      $(this).removeClass("section-opened");
    }
    

  });

  $( ".menu-page" ).on( "click", function( event ) {
   $(this).next("a").click();
  });

  $("document").ready(function () {
  $(".section-header").each(function( index ) {
    if (!$(this).next(".pages-div").hasClass('pages-hidden')) {
      $(this).find("i").removeClass("fa-caret-right");
      $(this).find("i").addClass("fa-caret-down");
      $(this).addClass("section-opened");
    }
  });
  
});




