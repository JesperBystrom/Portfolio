@charset "UTF-8";
* {
  margin: 0;
  padding: 0; }

body {
  background: #010101;
  overflow-x: hidden; }

header {
  width: 100%;
  height: 100%;
  padding: 2em;
  background: yellow; }

header nav .logo {
  background: url(../Bilder/logo.png);
  width: 4em;
  height: 3em;
  background-size: cover;
  position: absolute;
  top: 2.7em; }

header nav #pc_search {
  background: url(../Bilder/search.png);
  width: 2em;
  height: 2em;
  background-size: cover;
  margin-left: 3em;
  margin-top: 1.2em; }

header nav #pc_searchHidden {
  display: none;
  background: #914803;
  width: 1500%;
  height: 3em;
  position: relative;
  right: 27em;
  top: 3em;
  border-radius: 0.5em; }

header nav #pc_search #pc_searchHidden p {
  text-align: center;
  height: 100%;
  width: 100%;
  line-height: 1.7em;
  color: #fff;
  font-family: main;
  background: color-background-text; }

header nav #pc_searchHidden .arrowUp {
  background: url(../Bilder/arrow.png);
  width: 15%;
  height: 20%;
  background-size: contain;
  background-repeat: no-repeat;
  background-position: center;
  position: relative;
  left: 85%;
  bottom: 20%; }

header nav #pc_search:hover {
  /*
  	Byt ut till en SVG och gö fill:
  */ }

header nav ul li {
  color: #fff; }

header nav ul {
  list-style-type: none;
  display: flex;
  justify-content: flex-end;
  padding-right: 5em; }

header nav ul li {
  width: 10em;
  height: 100%;
  line-height: 5em;
  text-align: center; }

header nav ul a li p {
  color: #fff;
  text-decoration: none;
  font-family: main; }

.header_phone {
  width: 100%;
  height: 5em;
  background: blue;
  display: none; }

.header_phone nav .logo {
  background: url(../Bilder/logo.png);
  width: 4em;
  height: 3em;
  background-size: cover;
  position: absolute;
  top: 2.7em;
  left: 2em; }

.header_phone nav #mobile_search {
  background: url(../Bilder/Hamburger.png);
  width: 4em;
  height: 4em;
  background-size: contain;
  background-repeat: no-repeat;
  background-position: center;
  margin-left: 3em;
  margin-top: 1em;
  color: #fff;
  z-index: 2;
  position: fixed;
  right: 5%;
  background: color-background-text; }

.header_phone nav {
  padding-right: 5em; }

.header_phone nav #mobile_searchHidden {
  width: 100vw;
  height: 100vh;
  background: #914803;
  z-index: 1;
  position: fixed;
  top: 0;
  left: 0;
  display: none;
  transition: ease 0.2s; }

.header_phone nav #mobile_searchHidden ul {
  margin-top: 10%; }

.header_phone nav #mobile_searchHidden ul li {
  width: 100%;
  background: #5f2f02;
  font-family: main;
  text-align: center;
  line-height: 10em; }

.header_phone nav #mobile_searchHidden ul li:hover {
  background: white; }

.header_phone nav #mobile_searchHidden ul a {
  list-style-type: none;
  color: #fff; }

#loginScreen {
  display: none;
  background: #fff;
  width: 40vw;
  height: 75%;
  position: fixed;
  top: 0;
  left: 0;
  bottom: 0;
  right: 0;
  margin: auto;
  border-radius: 1em;
  padding: 2.5em;
  box-sizing: border-box;
  z-index: 999; }

#loginScreen form input {
  width: 100%;
  height: 3em;
  margin-top: 8%;
  border: none;
  border-bottom: 0.2em solid lightgray;
  background: none;
  padding-left: 3em;
  background: url("../Bilder/user.png");
  background-repeat: no-repeat;
  background-size: 2em;
  background-position: left 0.8em; }

#loginScreen form .icoUser {
  width: 5%;
  height: 5%;
  background: url("../Bilder/user.png");
  position: absolute;
  top: 23%;
  left: 6%;
  background-repeat: no-repeat;
  background-size: cover; }

#loginScreen form .icoPass {
  width: 5%;
  height: 5%;
  background: url("../Bilder/user.png");
  position: absolute;
  top: 38%;
  left: 6%;
  background-repeat: no-repeat;
  background-size: cover; }

#loginScreen p {
  color: #914803;
  width: 100%;
  font-family: main;
  font-size: 32px;
  text-align: center; }

#loginScreen form p {
  color: #914803;
  width: 100%;
  font-family: main;
  font-size: 18px;
  text-align: left;
  margin-top: 3em;
  margin-bottom: 1em;
  font-family: main; }

#loginScreen button {
  background: #914803;
  width: 100%;
  height: 3.2em;
  font-size: 18px;
  color: #fff;
  border: none;
  font-family: main;
  margin-top: 4em; }

#loginScreen button:last-child {
  margin-top: 0em; }

#main {
  width: 100vw;
  background: #fff;
  margin-top: 4em;
  display: flex; }

#main h1 {
  width: 100%;
  height: 3em;
  font-family: main;
  text-align: left;
  line-height: 3em;
  font-size: 2em;
  display: inline-block;
  text-align: center;
  color: #555555; }

#mainContent {
  flex: 1; }

#mainContent #produkterFlexWrapper {
  width: 100%; }

#mainContent #produkterFlexWrapper .produkt {
  width: 25%;
  height: 15em;
  margin-left: 2.95em;
  margin-right: 2.95em;
  margin-top: 4em;
  display: inline-block; }

#mainContent #produkterFlexWrapper .produkt .image {
  background: url(file:///C|/Users/elev/Google%20Drive/trean/%C3%84mnen/WU2%20TE15%20Jesper%20Bystr%C3%B6m/Projekt/Hemsida/Bilder/ssd.png);
  background-size: contain;
  background-repeat: no-repeat;
  background-position: 50% 50%;
  width: 100%;
  height: 50%;
  margin-top: 10%;
  margin-bottom: 2em; }

#mainContent #produkterFlexWrapper .produkt p {
  width: 100%;
  text-align: center;
  font-family: main;
  margin-top: 1em;
  color: #555555; }

#mainContent #produkterFlexWrapper .produkt h3 {
  width: 100%;
  text-align: center;
  font-family: main;
  color: #555555; }

#mainContent #produkterFlexWrapper .produkt .textWrapper {
  width: 100%;
  margin-bottom: 1em; }

#contentMenu {
  width: 10%;
  padding: 3.5em;
  order: -1;
  background: #f5f5f5;
  flex: 0; }

#contentMenu ul {
  list-style-type: none; }

#contentMenu ul li p {
  font-family: main;
  margin-bottom: 1em;
  color: #555555; }

#contentMenu h2 {
  font-family: main;
  margin-bottom: 1em;
  color: #555555; }

#contentMenu ul li .arrow {
  width: 1em;
  height: 1em;
  background: orange;
  display: inline-block;
  margin-right: 0.5em; }

#contentMenu ul li p {
  display: inline-block;
  color: #555555; }

footer {
  background: #914803;
  width: 100%;
  height: 11em;
  padding-top: 1em; }

footer .logo {
  background: url(../Bilder/logo.png);
  width: 4em;
  height: 3em;
  background-size: cover;
  margin-left: 3em;
  display: block; }

footer .flexWrapper {
  display: flex;
  justify-content: space-between;
  padding-right: 2em;
  padding-left: 2em; }

footer .flexWrapper #textWrapper {
  padding-top: 1.5em; }

footer .flexWrapper #textWrapper p {
  color: #fff;
  font-family: main; }

footer .flexWrapper #socialMediaWrapper .socialMedia_1 {
  background: url("../Bilder/twitter.png");
  width: 4em;
  height: 4em;
  display: inline-block;
  margin-left: 1em;
  background-size: cover;
  margin-bottom: 1em; }

footer .flexWrapper #socialMediaWrapper .socialMedia_2 {
  background: url("../Bilder/facebook.png");
  width: 4em;
  height: 4em;
  display: inline-block;
  margin-left: 1em;
  background-size: cover;
  margin-bottom: 1em; }

footer .flexWrapper #socialMediaWrapper .socialMedia_3 {
  background: url("../Bilder/facebook.png");
  width: 4em;
  height: 4em;
  display: inline-block;
  margin-left: 1em;
  background-size: cover;
  margin-bottom: 1em; }

footer .flexWrapper #socialMediaWrapper .socialMedia_4 {
  background: url("../Bilder/facebook.png");
  width: 4em;
  height: 4em;
  display: inline-block;
  margin-left: 1em;
  background-size: cover;
  margin-bottom: 1em; }

@media screen and (min-width: 950px) {
  body {
    overflow-y: visible; } }
@media screen and (max-width: 950px) {
  #loginScreen {
    width: 90vw; }

  #loginScreen button {
    margin-top: 2em; } }
@media screen and (max-width: 880px) {
  header {
    display: none; }

  .header_phone {
    display: block; }

  #mainContent #produkterFlexWrapper .produkt {
    width: 100%;
    margin: auto;
    display: block; } }
@media screen and (max-width: 680px) {
  footer .logo {
    display: none; } }
@font-face {
  font-family: 'main';
  src: url(../Fonts/Berlin%20Sans%20FB%20Regular.ttf); }
@font-face {
  font-family: 'main_bold';
  src: url(../Fonts/Berlin_Sans_FB_Demi_Bold.ttf); }

/*# sourceMappingURL=sortiment.cs.map */
