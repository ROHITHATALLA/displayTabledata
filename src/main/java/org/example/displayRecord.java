package org.example;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

@WebServlet("/datadisplay")
public class displayRecord extends HttpServlet {

    private static final String URL =
            "jdbc:mysql://localhost:3306/db";

    private static final String USER = "root";

    private static final String PASSWORD =
            "Rohitha@2389";

    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("text/html;charset=UTF-8");

        PrintWriter out = resp.getWriter();

        out.println("<html>");
        out.println("<head>");
        out.println("<title>Student Records</title>");

        out.println("<style>");
        out.println("body { font-family: Arial; margin: 40px; }");
        out.println("h1 { text-align: center; }");
        out.println("table { border-collapse: collapse; margin: auto; }");
        out.println("th, td { border: 1px solid black; padding: 10px; }");
        out.println("th { background-color: #eeeeee; }");
        out.println("</style>");

        out.println("</head>");
        out.println("<body>");

        out.println("<h1>Student Records</h1>");

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");


            Connection con = DriverManager.getConnection(
                    URL,
                    USER,
                    PASSWORD
            );


            Statement st = con.createStatement();


            ResultSet rs = st.executeQuery("SELECT * FROM csef");


            ResultSetMetaData metaData = rs.getMetaData();

            int columnCount = metaData.getColumnCount();


            out.println("<table>");


            out.println("<tr>");

            for (int i = 1; i <= columnCount; i++) {
                out.println("<th>"
                        + metaData.getColumnName(i)
                        + "</th>");
            }

            out.println("</tr>");


            while (rs.next()) {

                out.println("<tr>");

                for (int i = 1; i <= columnCount; i++) {

                    out.println("<td>"
                            + rs.getString(i)
                            + "</td>");
                }

                out.println("</tr>");
            }

            out.println("</table>");


            rs.close();
            st.close();
            con.close();

        } catch (Exception e) {

            out.println("<h2>Error</h2>");
            out.println("<pre>");
            out.println(e.getMessage());
            out.println("</pre>");

            e.printStackTrace();
        }

        out.println("</body>");
        out.println("</html>");
    }
}