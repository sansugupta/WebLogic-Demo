<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>WebLogic Demo App</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            max-width: 800px;
            margin: 50px auto;
            padding: 20px;
            background-color: #f5f5f5;
        }
        .container {
            background-color: white;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        h1 {
            color: #333;
        }
        .endpoint {
            background-color: #f0f0f0;
            padding: 10px;
            border-radius: 4px;
            margin: 20px 0;
            font-family: monospace;
        }
        a {
            color: #0066cc;
            text-decoration: none;
        }
        a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>🚀 WebLogic Demo Application</h1>
        <p>Welcome to the WebLogic Demo App!</p>
        
        <h2>Available Endpoints:</h2>
        <div class="endpoint">
            <strong>GET</strong> <a href="<%= request.getContextPath() %>/api/hello">/api/hello</a>
        </div>
        
        <p>Click the link above to test the REST endpoint.</p>
        
        <hr>
        <p><small>Deployed on: <%= new java.util.Date() %></small></p>
    </div>
</body>
</html>
