<#import "template.ftl" as layout />
<@layout.mainLayout title="Kierunki - Panel administracyjny">
<body>
    <div class="wrapper">
      <!-- Sidebar -->
      <nav id="sidebar">
        <div class="sidebar-header">
          <img src="/static/img/logo.svg" width="200px" />
        </div>

        <ul class="list-unstyled components">
          <li class="active">
            <a href="./kierunki"><img src="/static/img/icon_kierunki.svg" />Kierunki</a>
          </li>
          <li>
            <a href="./uczelnie"><img src="/static/img/icon_uczelnie.svg" />Uczelnie</a>
          </li>
          <li>
            <a href="./pytania"><img src="/static/img/icon_pytania.svg" />Pytania</a>
          </li>
          <li>
            <a href="./wyloguj"><img src="/static/img/icon_wyloguj.svg" />Wyloguj</a>
          </li>
        </ul>
      </nav>

      <!-- Page Content -->
      <div id="content">
        <div class="card header">
          Kierunki
          <a href="./kierunki/nowy"><span class="btn btn-primary add-btn">+ DODAJ NOWY</span></a>
        </div>
        <table class="table table-condensed" id="myTable">
          <tbody>
          <#list courses as courses>
            <tr>
              <td class="col-sm-10" id="row-head">
                <div data-toggle="collapse" data-target="#row${courses.id}" data-parent="#myTable">
                  <span class="head1">${courses.name}</span>
                </div>
                <div id="row${courses.id}" class="collapse">
                    <span class="head2">Opis kierunku:</span>
                    <p>${courses.description}</p>
                </div>
              </td>
              <td class="col-sm-1 action-icons">
                    <a href="./kierunki/${courses.id}/edycja">
                        <img src="/static/img/icon_edit.svg"/>
                    </a>
              </td>
              <td class="col-sm-1 action-icons">
                <img src="/static/img/icon_delete.svg" />
              </td>
            </tr>
            </#list>
          </tbody>
        </table>
      </div>
    </div>
  </body>
</@layout.mainLayout>