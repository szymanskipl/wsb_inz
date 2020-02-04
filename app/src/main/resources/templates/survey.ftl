<#import "template.ftl" as layout />
<@layout.mainLayout title="Ankieta">
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
            <li class="list-inline-item active">
              <a href="/ankieta">Znajdź swój kierunek (ankieta)</a>
            </li>
            </li>
          </ul>
        </div>
      </div>

      <div id="banner">
      <div class="carousel slide" data-ride="carousel">
        <div class="carousel-item active">
          <img class="d-block w-100" src="../static/img/baner1.png" alt="First slide">
        </div>
      </div>
    </div>

    <form action="/ankieta" method="put" enctype="application/x-www-form-urlencoded">
      <div class="main-content">
        <div class="card survey">
          <div class="card-header text-center question" name="question_id" value="${question.id}">
            ${question.text}
          </div>
          <div class="card-body">
              <table id="answers">
                <tbody>
                  <tr class="text-center">
                    <td>
                      <button type="submit" value="${answers[0].id}" class="btn btn-primary btn-lg" name="answer_id">${answers[0].text}</button>
                    </td>
                    <td>
                      <button type="submit" value="${answers[1].id}" class="btn btn-primary btn-lg" name="answer_id">${answers[1].text}</button>
                    </td>
                  </tr class="text-center">
                  <#if answers[2]??>
                  <tr class="text-center">
                    <td>
                      <button type="submit" value="${answers[2].id}" class="btn btn-primary btn-lg" name="answer_id">${answers[2].text}</button>
                    </td>
                    <#if answers[3]??>
                    <td>
                      <button type="submit" value="${answers[3].id}" class="btn btn-primary btn-lg" name="answer_id">${answers[3].text}</button>
                    </td>
                    </#if>
                  </tr class="text-center">
                  </#if>
                </tbody>
          </div>
        </div>
      </div>

    </div>
  </body>
</@layout.mainLayout>