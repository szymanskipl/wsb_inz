<#-- @ftlvariable name="error" type="java.lang.String" -->

<#import "template.ftl" as layout />
<@layout.mainLayout title="Login">
<form action="/admin/login" method="post" enctype="application/x-www-form-urlencoded">
    <#if error??>
        <p>${error}</p>
    </#if>
    <div>Email:</div>
    <div><input type="text" name="userEmail"/></div>
    <div>Hasło:</div>
    <div><input type="password" name="password"/></div>
    <div><input type="submit" value="Zaloguj" /></div>
</form>
</@layout.mainLayout>