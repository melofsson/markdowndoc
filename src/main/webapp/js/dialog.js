$("document").ready(function () {

  $("#newSectionLink").click(function(){ 
    $("#inputSectionName").css("display", "inline")
  $("#sections").css("display", "none")
  $("#newSectionLink").css("display", "none")
    return false; });

  $("#close-dialog").click(function(){ 
    $("#dialog").removeClass("show-tag-dialog");
    $("#dialog").removeClass("show-new-page-dialog");
    $("#dialog").removeClass("show-new-subpage-dialog");
    return false; });

  $("#openNewPageForm").click(function(){
    $("#inputSectionName").css("display", "none")
    $("#sections").css("display", "inline")
    $("#newSectionLink").css("display", "inline")
    $("#dialog-title").css("display", "inline");
    $("#dialog").removeClass("show-tag-dialog");
    $("#dialog").removeClass("show-new-subpage-dialog");
    $("#dialog").addClass("show-new-page-dialog");
    $("#new-page-form").css("display", "block");
    $("#new-subpage-form").css("display", "none");
    $("#new-tag-form").css("display", "none");
    return false; });

  $("#openNewSubPageForm").click(function(){
    $("#dialog-title").css("display", "inline");
    $("#dialog-title").text("Add subpage");
    $("#dialog").removeClass("show-tag-dialog");
    $("#dialog").removeClass("show-new-page-dialog");
    $("#dialog").addClass("show-new-subpage-dialog");
    $("#new-subpage-form").css("display", "block");
    $("#new-tag-form").css("display", "none");
    $("#new-page-form").css("display", "none");
    return false; });
  

  $("#add-tag-btn").click(function(){ 
    $("#dialog").removeClass("show-new-page-dialog");
    $("#dialog").removeClass("show-new-subpage-dialog");
    $("#dialog").addClass("show-tag-dialog");
    $("#dialog-title").css("display", "none");
    $("#new-page-form").css("display", "none");
    $("#new-subpage-form").css("display", "none");
    $("#new-tag-form").css("display", "block");
    return false; });



}, false);



