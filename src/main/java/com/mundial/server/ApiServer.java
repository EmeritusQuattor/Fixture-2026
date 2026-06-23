package com.mundial.server;

import com.mundial.dao.GroupDAO;
import com.mundial.dao.MatchDAO;
import com.mundial.dao.TeamDAO;
import com.mundial.model.Group;
import com.mundial.model.Match;
import com.mundial.model.Team;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class ApiServer {

    private static final GroupDAO groupDAO = new GroupDAO();
    private static final TeamDAO teamDAO = new TeamDAO();
    private static final MatchDAO matchDAO = new MatchDAO();

    public static void start(int port) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/api/groups", new GroupsHandler());
        server.createContext("/api/teams", new TeamsHandler());
        server.createContext("/api/matches", new MatchesHandler());
        server.createContext("/", new StaticHandler());
        server.setExecutor(null);
        server.start();
        System.out.println("Servidor iniciado en http://localhost:" + port);
    }

    static class GroupsHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            try {
                List<Group> groups = groupDAO.findAll();
                List<Team> allTeams = teamDAO.findAll();
                StringBuilder json = new StringBuilder("[");
                for (int i = 0; i < groups.size(); i++) {
                    Group g = groups.get(i);
                    json.append("{");
                    json.append("\"id\":").append(g.getId()).append(",");
                    json.append("\"name\":").append(jsonStr(g.getName())).append(",");
                    json.append("\"teams\":[");
                    List<Team> groupTeams = allTeams.stream()
                            .filter(t -> t.getGroupId() == g.getId())
                            .collect(Collectors.toList());
                    for (int j = 0; j < groupTeams.size(); j++) {
                        Team t = groupTeams.get(j);
                        json.append("{");
                        json.append("\"id\":").append(t.getId()).append(",");
                        json.append("\"name\":").append(jsonStr(t.getName())).append(",");
                        json.append("\"country\":").append(jsonStr(t.getCountry()));
                        json.append("}");
                        if (j < groupTeams.size() - 1) json.append(",");
                    }
                    json.append("]");
                    json.append("}");
                    if (i < groups.size() - 1) json.append(",");
                }
                json.append("]");
                sendJson(exchange, json.toString());
            } catch (SQLException e) {
                sendError(exchange, e.getMessage());
            }
        }
    }

    static class TeamsHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            try {
                List<Team> teams = teamDAO.findAll();
                StringBuilder json = new StringBuilder("[");
                for (int i = 0; i < teams.size(); i++) {
                    Team t = teams.get(i);
                    json.append("{");
                    json.append("\"id\":").append(t.getId()).append(",");
                    json.append("\"name\":").append(jsonStr(t.getName())).append(",");
                    json.append("\"country\":").append(jsonStr(t.getCountry())).append(",");
                    json.append("\"groupId\":").append(t.getGroupId());
                    json.append("}");
                    if (i < teams.size() - 1) json.append(",");
                }
                json.append("]");
                sendJson(exchange, json.toString());
            } catch (SQLException e) {
                sendError(exchange, e.getMessage());
            }
        }
    }

    static class MatchesHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("GET".equalsIgnoreCase(exchange.getRequestMethod())) {
                handleGet(exchange);
            } else if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                handlePost(exchange);
            } else {
                sendError(exchange, "Método no soportado");
            }
        }

        private void handleGet(HttpExchange exchange) throws IOException {
            try {
                List<Match> matches = matchDAO.findAll();
                List<Team> allTeams = teamDAO.findAll();
                StringBuilder json = new StringBuilder("[");
                for (int i = 0; i < matches.size(); i++) {
                    Match m = matches.get(i);
                    String localName = allTeams.stream()
                            .filter(t -> t.getId() == m.getLocalId())
                            .findFirst().map(Team::getName).orElse("?");
                    String visitorName = allTeams.stream()
                            .filter(t -> t.getId() == m.getVisitorId())
                            .findFirst().map(Team::getName).orElse("?");
                    json.append("{");
                    json.append("\"id\":").append(m.getId()).append(",");
                    json.append("\"localName\":").append(jsonStr(localName)).append(",");
                    json.append("\"visitorName\":").append(jsonStr(visitorName)).append(",");
                    json.append("\"localGoals\":").append(m.getLocalGoals()).append(",");
                    json.append("\"visitorGoals\":").append(m.getVisitorGoals()).append(",");
                    json.append("\"phase\":").append(jsonStr(m.getPhase())).append(",");
                    json.append("\"date\":").append(jsonStr(m.getDate())).append(",");
                    json.append("\"status\":").append(jsonStr(m.getStatus()));
                    json.append("}");
                    if (i < matches.size() - 1) json.append(",");
                }
                json.append("]");
                sendJson(exchange, json.toString());
            } catch (SQLException e) {
                sendError(exchange, e.getMessage());
            }
        }

        private void handlePost(HttpExchange exchange) throws IOException {
            String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            Map<String, String> params = parseFormData(body);
            try {
                int localId = Integer.parseInt(params.get("localId"));
                int visitorId = Integer.parseInt(params.get("visitorId"));
                int groupId = Integer.parseInt(params.get("groupId"));
                String phase = params.get("phase");
                String date = params.get("date");
                int gl = Integer.parseInt(params.get("localGoals"));
                int gv = Integer.parseInt(params.get("visitorGoals"));
                String status = gl > gv ? "Local" : gl < gv ? "Visitante" : "Empate";

                matchDAO.save(new Match(0, localId, visitorId, groupId, phase, date, gl, gv, status));
                sendJson(exchange, "{\"ok\":true}");
            } catch (Exception e) {
                sendError(exchange, e.getMessage());
            }
        }
    }

    static class StaticHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String path = exchange.getRequestURI().getPath();
            if (path.equals("/")) path = "/index.html";
            File file = new File("webapp" + path);
            if (!file.exists() || file.isDirectory()) {
                String response = "404 Not Found";
                exchange.sendResponseHeaders(404, response.length());
                exchange.getResponseBody().write(response.getBytes());
                exchange.getResponseBody().close();
                return;
            }
            String contentType = getContentType(path);
            exchange.getResponseHeaders().set("Content-Type", contentType);
            exchange.sendResponseHeaders(200, file.length());
            try (OutputStream os = exchange.getResponseBody(); FileInputStream fs = new FileInputStream(file)) {
                fs.transferTo(os);
            }
        }

        private String getContentType(String path) {
            if (path.endsWith(".html")) return "text/html; charset=utf-8";
            if (path.endsWith(".css")) return "text/css; charset=utf-8";
            if (path.endsWith(".js")) return "application/javascript; charset=utf-8";
            if (path.endsWith(".png")) return "image/png";
            if (path.endsWith(".jpg") || path.endsWith(".jpeg")) return "image/jpeg";
            if (path.endsWith(".svg")) return "image/svg+xml";
            return "application/octet-stream";
        }
    }

    private static void sendJson(HttpExchange exchange, String json) throws IOException {
        byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=utf-8");
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
        exchange.sendResponseHeaders(200, bytes.length);
        exchange.getResponseBody().write(bytes);
        exchange.getResponseBody().close();
    }

    private static void sendError(HttpExchange exchange, String message) throws IOException {
        String json = "{\"error\":" + jsonStr(message) + "}";
        byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=utf-8");
        exchange.sendResponseHeaders(500, bytes.length);
        exchange.getResponseBody().write(bytes);
        exchange.getResponseBody().close();
    }

    private static String jsonStr(String s) {
        if (s == null) return "null";
        return "\"" + s.replace("\\", "\\\\").replace("\"", "\\\"") + "\"";
    }

    private static Map<String, String> parseFormData(String body) {
        Map<String, String> map = new HashMap<>();
        if (body == null || body.isEmpty()) return map;
        String[] pairs = body.split("&");
        for (String pair : pairs) {
            String[] kv = pair.split("=", 2);
            if (kv.length == 2) {
                map.put(URLDecoder.decode(kv[0], StandardCharsets.UTF_8),
                        URLDecoder.decode(kv[1], StandardCharsets.UTF_8));
            }
        }
        return map;
    }
}
