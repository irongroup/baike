<%@page import="com.sina.sae.fetchurl.SaeFetchurl"%>
<%@page import="com.sina.sae.memcached.SaeMemcache"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
hello world.<%=new Date() %>

<%
SaeMemcache mc=new SaeMemcache("127.0.0.1", 11211);
mc.init();

SaeFetchurl fetchUrl = new SaeFetchurl();
String content = fetchUrl.fetch("http://api.lkong.net/top250.json");
out.println(content.substring(0, 10));
try{
	out.println(""+(content.getBytes().length/1024));
mc.set("top250", "test");
out.println("set successfully");
}catch(Exception e){
	out.print(e.getMessage());
}
out.print(mc.get("top250"));
String encoding = System.getProperty("file.encoding");
out.println(encoding);
%>
</body>
</html>