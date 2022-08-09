var ITALIC = "*"
var CODE = "code"
var BOLD = "**"
var HEADER1 = "#"
var HEADER2 = "##"
var HEADER3 = "###"
var HEADER4 = "####"
var HEADER5 = "#####"
var HEADER6 = "######"
var videoFileName = ""
var TABLE = "<table>\n" + 
"<tr>\n" + 
"<td style=\"padding-right:20px\"><b>Column 1<b></td>\n" +
"<td style=\"padding-right:20px\"><b>Column 2</b></td>\n" +
"<td style=\"padding-right:20px\"><b>Column 3</b></td>\n" +
"</tr>\n" +
"<tr><td>Row 1</td>\n" +
"<td> - </td>\n" +
"<td> - </td></tr>\n" +
"<tr><td>Row 2</td>\n" +
"<td> - </td>\n" +
"<td> - </td></tr>\n" +
"<tr><td>Row 3</td>\n" +
"<td> - </td>\n" +
"<td> - </td></tr>\n" +
"</table>"


const converter = new showdown.Converter({extensions: ['table']});
$("document").ready(function () {
  converter.setOption("simpleLineBreaks", true)
  converter.setOption("smoothPreview", true)
  converter.setOption("tasklists", true)
  converter.setOption("ghCompatibleHeaderId", true)
  converter.setOption("parseImgDimension", true)
document.querySelector('textarea').addEventListener("input", event => {
  previewMarkDown();
  /*
  $('#preview-content').empty();
  $('#mdcontent').children('div').each(function () {
    const text = $(this).text();
    $('#preview-content').append(converter.makeHtml(text))
    */
});
$(window).keydown(function (e){
  console.log("YEs");
  if ((e.metaKey || e.ctrlKey) && e.keyCode == 83) { /*ctrl+s or command+s*/
    $('#save-btn').click();
      e.preventDefault();
      return false;
  }
});

$("#boldText").click(function () {
  if ($("#boldText").hasClass("active-edit-btn")) {
    editSelection(BOLD, true);
  } else {
    editSelection(BOLD, false);
  }
});

$("#codeText").click(function () {
  textarea = document.getElementById("mdcontent");
    editSelection(CODE, false);
});

$("#tableBtn").click(function () {
  textarea = document.getElementById("mdcontent");
  insertAtCursor(textarea, TABLE);
  previewMarkDown();
});


$("#linkBtn").click(function () {
  textarea = document.getElementById("mdcontent");
    insertAtCursor(textarea, "[Linkname](https://url-to-site)");
    previewMarkDown();
});

$("#italicText").click(function () {
  if ($(this).hasClass("active-edit-btn")) {
    editSelection(ITALIC, true);
  } else {
    editSelection(ITALIC, false);
  }
  
});

$("#video").click(function () {
  $('#file-input').trigger('click');

});

$('#file-input').change(function(){
  videoFileName = $('#file-input').val().replace('C:\\fakepath\\', '')
  var section = $('#filedir').val();
  var VIDEO = "<video controls class='video' preload='metadata' height=250>" 
+ "<source src='videoReaderServlet?filedir=" + section + "&&filename=" + videoFileName+ "' type='video/mp4'></source>" + 
"</video>";
  textarea = document.getElementById("mdcontent");
  insertAtCursor(textarea, VIDEO);
  previewMarkDown();
  
});

$("#mdcontent").mouseup(function(e){
  var selection;
  
  if (window.getSelection) {
    selection = window.getSelection();
  } else if (document.selection) {
    selection = document.selection.createRange();
  }
  console.log(selection.toString());
  setEditButtonStatus(selection.toString());

});

$('body').on('mouseover','.tag',function(){
  console.log("mouse over");
  $(this).find("span").removeClass("hidden");
});

$('body').on('mouseleave','.tag',function(){
  console.log("mouseleave");
  $(this).find("span").addClass("hidden");
});

$('body').on('click','.tag-cross',function(){
  $(this).closest(".tag").remove();
});



  
}, false);



function updateHeader() {
  var select = document.getElementById('header');
  var option = select.options[select.selectedIndex];
  var headerSize = "";
  switch(option.value) {
    case "header1":
      headerSize = HEADER1;
      break;
    case "header2":
      headerSize = HEADER2;
      break;
    case "header3":
      headerSize = HEADER3;
      break;
    case "header4":
      headerSize = HEADER4;
      break;
    case "header5":
      headerSize = HEADER5;
      break;
    case "header6":
      headerSize = HEADER6;
      break;
  }
    console.log("header" + headerSize);
    select.selectedIndex = 0;
    textarea = document.getElementById("mdcontent");
    insertAtCursor(textarea, headerSize);
    previewMarkDown();
  
}

function editSelection(mdSyntax, remove) {
  textarea = document.getElementById("mdcontent");
  /*var selectionText = textarea.value.substr(textarea.selectionStart, textarea.selectionEnd);
  console.log(selectionText)
  textarea.value = mdSyntax + selectionText + mdSyntax;
  */
    var startPos = textarea.selectionStart;
    var endPos = textarea.selectionEnd;
  if (remove) {
    removeFromSelection(textarea, mdSyntax, startPos, endPos)
  } else {
    addToSelection(textarea, mdSyntax, startPos, endPos);
  }
}

function addToSelection(textarea, mdSyntax, startPos, endPos){
    beforeValue = mdSyntax;
    afterValue = mdSyntax;
    if (mdSyntax == "code"){
      beforeValue = "<" + mdSyntax + ">";
      afterValue = "</" + mdSyntax + ">";
    }
    textarea.value = textarea.value.substring(0, startPos)
    + beforeValue
    + textarea.value.substring(startPos, endPos)
    + afterValue
    + textarea.value.substring(endPos, textarea.value.length);
  previewMarkDown()
  textarea.focus();
  textarea.selectionStart = startPos;
  textarea.selectionEnd = endPos + mdSyntax.length + 2;
  setEditButtonStatus(textarea.value.substring(textarea.selectionStart, textarea.selectionEnd))
}

function removeFromSelection(textarea, mdSyntax, startPos, endPos) {
  var strippedWord = textarea.value.substring(startPos + mdSyntax.length, endPos - mdSyntax.length);
  textarea.value = textarea.value.substring(0, startPos)
      + strippedWord
      + textarea.value.substring(endPos, textarea.value.length);
  previewMarkDown()
  textarea.focus();
  textarea.selectionStart = startPos;
  textarea.selectionEnd = endPos - mdSyntax.length - 2;
  setEditButtonStatus(strippedWord)
}

function setEditButtonStatus(selectedWord) {
  setBoldButtonStatus(selectedWord);
  setItalicButtonStatus(selectedWord);
}

function setBoldButtonStatus(selectedWord) {
  boldTextId = "boldText";
  if (selectedWord.substring(0,2) == BOLD) {
    console.log("Will disable bold");
   setEditButtonClass(boldTextId, true)
  } else {
    console.log("Will enable bold");
    setEditButtonClass(boldTextId, false)
  }
}

function setItalicButtonStatus(selectedWord) {
  italicTextId = "italicText";
  if (selectedWord.substring(0,2) != BOLD && selectedWord.substring(0,1) == ITALIC) {
    console.log("Will disable italic");
   setEditButtonClass(italicTextId, true)
  } else {
    console.log("Will enable italic");
    setEditButtonClass(italicTextId, false)
  }
}

function setEditButtonClass(elementId, enable) {
  if (enable) {
    $("#" + elementId).removeClass("inactive-edit-btn");
    $("#" + elementId).addClass("active-edit-btn");
    } else {
      $("#" + elementId).addClass("inactive-edit-btn");
      $("#" + elementId).removeClass("active-edit-btn");
    }
}



function insertAtCursor(myField, myValue) {
  //IE support
  if (document.selection) {
      myField.focus();
      sel = document.selection.createRange();
      sel.text = myValue;
  }
  //MOZILLA and others
  else if (myField.selectionStart || myField.selectionStart == '0') {
      var startPos = myField.selectionStart;
      var endPos = myField.selectionEnd;
      myField.value = myField.value.substring(0, startPos)
          + myValue
          + myField.value.substring(endPos, myField.value.length);
      myField.selectionStart = startPos + myValue.length;
      myField.selectionEnd = startPos + myValue.length;
  } else {
      myField.value += myValue;
  }
}

function loadMdEditContent(data){
  $('#mdcontent').html(data)
  previewMarkDown();
// do something with the html here
}

function previewMarkDown(){
    textarea = document.getElementById("mdcontent");
    
    console.log("Editing")
    console.log($('#mdcontent').val());
    console.log(converter.makeHtml($(textarea).val()));
    const text = $(textarea).val();

      $('#preview-content').html(converter.makeHtml(text));
  }


