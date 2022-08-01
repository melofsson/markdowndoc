
function convertToHtml(data){
    const converter = new showdown.Converter();
    var html = converter.makeHtml(data);
    $('#innermain').append(html)
    // do something with the html here
}

/* When the user clicks on the button, 
toggle between hiding and showing the dropdown content */
function openSettingsDialog() {
    document.getElementById("settingsDropDown").classList.toggle("show");
  }
  
  // Close the dropdown if the user clicks outside of it
  window.onclick = function(event) {
    if (!event.target.matches('#settings-btn')) {
      var dropdowns = document.getElementsByClassName("dropdown-content");
      var i;
      for (i = 0; i < dropdowns.length; i++) {
        var openDropdown = dropdowns[i];
        if (openDropdown.classList.contains('show')) {
          openDropdown.classList.remove('show');
        }
      }
    }
  }
