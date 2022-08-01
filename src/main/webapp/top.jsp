<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="header-container container-fluid">
    <div class="top">
        <div class="header-div">
        <span class="logo">
            <img class="company-logo" src="imageReaderServlet?img=logo"/>
            </span>
            <p class="header-title">Markdown Doc</p></div>
        <div class="search-div">
            <form action="search.jsp" method="GET" class="searchfield input-group input-group-sm">
                <div class="searchicon">
                    <svg width="16" height="16" fill="currentColor" color="#d1d5db" focusable="false"><path fill-rule="evenodd" clip-rule="evenodd" d="M7 0c3.87 0 7 3.134 7 7a6.97 6.97 0 01-1.394 4.192l3.101 3.1a1 1 0 01-1.414 1.415l-3.1-3.1A6.97 6.97 0 017 14c-3.866 0-7-3.13-7-7s3.13-7 7-7zM2 7c0 2.76 2.24 5 5 5s5-2.24 5-5-2.24-5-5-5-5 2.24-5 5z"></path></svg>
                </div>
                <input name="search" aria-label="search" id="searchForm" spellcheck="false" autocomplete="off" size="1" placeholder="Search" tabindex="0" type="text" class="searchinput">
            </form>
        </div>
    </div>
</div>