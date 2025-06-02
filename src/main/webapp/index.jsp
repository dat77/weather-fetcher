<%@ page import="java.io.*, java.net.*, jakarta.ws.rs.client.*, jakarta.ws.rs.core.Response, com.fasterxml.jackson.databind.*" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Weather Request</title>
</head>
<body>
    <h2>Get Weather in the city</h2>

    <%
        String defaultValue = request.getParameter("city");
        if (defaultValue == null || defaultValue.isEmpty()) {
            defaultValue = "Hamburg, Germany";
        }
    %>

    <form method="POST">
        <label for="city">Enter City:</label>
        <input type="text" id="city" name="city" value="<%= defaultValue %>" required>
        <button type="submit">Submit</button>
    </form>

    <%
        // Handle form submission inside JSP
        String city = request.getParameter("city");
        if (city != null && !city.isEmpty()) {
            try {
                // Call REST API using JAX-RS Client
                Client client = ClientBuilder.newClient();
                WebTarget target = client.target("http://localhost:8080/weather-fetcher/api/v1/weather/city");
                Response apiResponse = target.request().post(Entity.text(city));
                String jsonResponse = apiResponse.readEntity(String.class);
                client.close();
            if (apiResponse.getStatus() >= 400) {
                out.println("<p style='color:red'>" + jsonResponse + "</p>");
            } else {
                // Parse JSON response for weather data
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(jsonResponse);
    %>
                <h3>Weather for <%= jsonNode.get("city").asText() %></h3>
                <p><strong>Temperature:</strong> <%= jsonNode.get("temperature").asText() %></p>
                <p><strong>Condition:</strong> <%= jsonNode.get("weatherCondition").asText() %></p>
                <p><strong>Wind Speed:</strong> <%= jsonNode.get("windSpeed").asText() %></p>
    <%
            }
            } catch (Exception e) {
                out.println("<p>"+e.getMessage()+"</p>");
            }
        }
    %>
</body>
</html>

