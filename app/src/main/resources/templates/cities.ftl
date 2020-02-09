<#import "template.ftl" as layout />
<@layout.mainLayout title="Miasta studenckie">
<body>
    <div class="wraper">

      <div class="card header nav">
              <div id="nav-content">
                <ul class="list-inline">
                  <li class="list-inline-item">
                      <img src="../static/img/logo.svg"/>
                  </li>
                  <li class="list-inline-item active">
                    <a href="/miasta">Miasta studenckie</a>
                  </li>
                  <li class="list-inline-item">
                    <a href="/kierunki">Kierunki studiów</a>
                  </li>
                  <li class="list-inline-item">
                    <a href="/ankieta">Znajdź swój kierunek (ankieta)</a>
                  </li>
                </ul>
              </div>
            </div>

      <div id="banner">
                <div class="carousel slide" data-ride="carousel">
                    <div class="carousel-item active">
                        <img class="d-block w-100" src="../static/img/baner1.png" alt="First slide">
                    </div>
                    <div class="carousel-item">
                        <img class="d-block w-100" src="../static/img/baner2.png" alt="Second slide">
                    </div>
                    <div class="carousel-item">
                        <img class="d-block w-100" src="../static/img/baner3.png" alt="Third slide">
                    </div>
                    <div class="carousel-item">
                        <img class="d-block w-100" src="../static/img/baner4.png" alt="Fourth slide">
                    </div>
                </div>
            </div>

      <div class="main-content">
        <h2>Poznań</h2>
        <ul>
        <#list poznan as poznanUniversity>
          <li>${poznanUniversity.name}</li>
        </#list>
        </ul>
        <h2>Warszawa</h2>
        <ul>
        <#list warszawa as warszawaUniversity>
          <li>${warszawaUniversity.name}</li>
        </#list>
        </ul>
        <h2>Wrocław</h2>
        <ul>
        <#list wroclaw as wroclawUniversity>
          <li>${wroclawUniversity.name}</li>
        </#list>
        </ul>
      </div>

    </div>
  </body>
</@layout.mainLayout>