package servlets;


import databse.Database;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "NewGroupToDBServlet", value = "/servlets/NewGroupToDBServlet")
public class NewGroupToDBServlet extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //Input lesen
        String groupname = req.getParameter("group_name_input");
        String members = req.getParameter("group_members_input");

        boolean groupAlreadyExists = false;
        try {
            ArrayList<String> existingGroupNames = Database.getAllGroupNames();

            for (String s : existingGroupNames) {
                if (groupname.equals(s)) {
                    groupAlreadyExists = true;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        //Gruppennamen Validieren
        if (groupname.length() > 0 && members.length() > 1 && !groupAlreadyExists) {

            //Mitglieder auf richtigkeit überprüfen
            List<String> memberList = new ArrayList<String>(Arrays.asList(members.split("\\s*,\\s*")));
            ArrayList<String> allUsers;
            try {
                allUsers = Database.getAllUsers();
                boolean validGroupMembers = true;
                for (int i = 0; i < memberList.size(); i++) {
                    if(!allUsers.contains(memberList.get(i))) {
                        validGroupMembers = false;
                        System.out.println("Folgender Nutzer konnte nicht gefunden werden: " + memberList.get(i));
                    }
                }


                if(validGroupMembers) {
                    //String wieder zusammensetzen
                    StringBuilder stringBuilder = new StringBuilder("");

                    for (String singleString : memberList) {
                        stringBuilder.append(singleString).append(",");
                    }

                    String membersWithComma = stringBuilder.toString();

                    //In DB schreiben
                    boolean successfulTransaction = Database.addGroup(groupname, membersWithComma);

                    if (successfulTransaction) {
                        System.out.println("Gruppe erfolgreich erstellt!");
                    } else {
                        System.out.println("Fehler beim Gruppe in die Datenbank einfuegen!");
                    }

                } else {
                    System.out.println("Gruppe konnte nicht erstellt werden.");
                }


            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }




        } else {
            System.out.println("Ungueltiger Gruppenname oder Mitgliederzahl.");
        }




        resp.sendRedirect(req.getContextPath() + "/chat.jsp");

    }
}
