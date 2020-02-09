<#import "template.ftl" as layout />
<@layout.mainLayout title="Ankieta - wyniki">
<body>
    <div class="wraper">

      <div class="card header nav">
        <div id="nav-content">
          <ul class="list-inline">
            <li class="list-inline-item">
                <img src="../static/img/logo.svg"/>
            </li>
            <li class="list-inline-item">
              <a href="/miasta">Miasta studenckie</a>
            </li>
            <li class="list-inline-item">
              <a href="/kierunki">Kierunki studiów</a>
            </li>
            <li class="list-inline-item active">
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
            <div class="card survey">
              <div class="card-header text-center question">
                Kierunki, które pasują do Ciebie:
              </div>
              <div class="card-body">
                  <table id="answers">
                    <tbody>
                    <#list courses as course>
                      <tr class="text-center">
                        <td>
                          <button class="btn btn-primary btn-lg">${course.name}</button>
                        </td>
                      </tr class="text-center">
                    </#list>
                    </tbody>
              </div>
            </div>
          </div>
    </div>
  </body>
</@layout.mainLayout>