package com.student;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@WebServlet("/api/students/*")
public class StudentServlet extends HttpServlet {

    private String filePath;
    private List<Map<String, Object>> students = new ArrayList<>();
    private final Gson gson = new Gson();

    @Override
    public void init() throws ServletException {
        filePath = getServletContext().getRealPath("/WEB-INF/students.json");

        File file = new File(filePath);
        if (file.exists()) {
            try (FileReader reader = new FileReader(file)) {
                Type type = new TypeToken<List<Map<String, Object>>>() {
                }.getType();
                students = gson.fromJson(reader, type);
                if (students == null) {
                    students = new ArrayList<>();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void saveToFile() {
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(students, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int getId(Object val) {
        if (val instanceof Number) {
            return ((Number) val).intValue();
        }
        return Integer.parseInt(val.toString());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.getWriter().write(gson.toJson(students));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        BufferedReader reader = req.getReader();
        Map<String, Object> student = gson.fromJson(reader, Map.class);

        int newId = students.isEmpty() ? 1 : getId(students.get(students.size() - 1).get("id")) + 1;
        student.put("id", newId);

        students.add(student);
        saveToFile();

        resp.setContentType("application/json");
        resp.getWriter().write(gson.toJson(student));
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String path = req.getPathInfo();
        if (path == null || path.length() <= 1) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID is required");
            return;
        }
        int id = Integer.parseInt(path.substring(1));

        Map<String, Object> updated = gson.fromJson(req.getReader(), Map.class);

        boolean found = false;
        for (Map<String, Object> s : students) {
            if (getId(s.get("id")) == id) {
                s.putAll(updated);
                s.put("id", id); // keep ID unchanged
                found = true;
                break;
            }
        }

        if (!found) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Student not found");
            return;
        }

        saveToFile();
        resp.setContentType("application/json");
        resp.getWriter().write("{\"status\":\"updated\"}");
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String path = req.getPathInfo();
        if (path == null || path.length() <= 1) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID is required");
            return;
        }
        int id = Integer.parseInt(path.substring(1));

        boolean removed = students.removeIf(s -> getId(s.get("id")) == id);

        if (!removed) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Student not found");
            return;
        }

        saveToFile();
        resp.setContentType("application/json");
        resp.getWriter().write("{\"status\":\"deleted\"}");
    }
}
