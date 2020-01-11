<#import "template.ftl" as layout />
<@layout.mainLayout title="Uczelnie - Panel administracyjny">
<body>
    <div class="wrapper">
      <!-- Sidebar -->
      <nav id="sidebar">
        <div class="sidebar-header">
          <img src="/static/img/logo.svg" width="200px" />
        </div>

        <ul class="list-unstyled components">
          <li>
            <a href="/admin/kierunki"><img src="/static/img/icon_kierunki.svg" />Kierunki</a>
          </li>
          <li class="active">
            <a href="/admin/uczelnie"><img src="/static/img/icon_uczelnie.svg" />Uczelnie</a>
          </li>
          <li>
            <a href="/admin/pytania"><img src="/static/img/icon_pytania.svg" />Pytania</a>
          </li>
          <li>
            <a href="/admin/wyloguj"><img src="/static/img/icon_wyloguj.svg" />Wyloguj</a>
          </li>
        </ul>
      </nav>

      <!-- Page Content -->
      <div id="content">
        <div class="card header">
          Uczelnie
          <a href="/admin/uczelnie/nowa"><span class="btn btn-primary add-btn">+ DODAJ NOWĄ</span></a>
        </div>
        <table class="table table-condensed" id="myTable">
          <tbody>
          <#assign lastCity = "">
          <#list universities?sort_by("city") as university>
           <#if lastCity != university.city>
            <tr>
              <td class="col-sm-10" id="row-head">
                <div data-toggle="collapse" data-target="#row${university.city}" data-parent="#myTable">
                  <span class="head1">${university.city}</span>
                </div>
              </td>
            </tr>
            </#if>

            <#assign lastCity = university.city>
            <tr id="row${university.city}" class="collapse">
              <td>
                <span class="university-item">${university.name}</span>
              </td>

              <td class="col-sm-1 action-icons">
                <a href="/admin/uczelnie/${university.id}/edycja">
                    <img src="/static/img/icon_edit.svg"/>
                </a>
              </td>
              <td class="col-sm-1 action-icons">
                <a href="/admin/uczelnie/${university.id}/usun">
                   <img src="/static/img/icon_delete.svg"/>
                </a>
              </td>
            </tr>
            </#list>
          </tbody>
        </table>
      </div>
    </div>
  </body>
</@layout.mainLayout>