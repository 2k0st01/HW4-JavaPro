package ua.kiev.prog.case2;

import ua.kiev.prog.shared.Client;
import ua.kiev.prog.shared.ConnectionFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        try (Connection conn = ConnectionFactory.getConnection())
        {
            // remove this
            try {
                try (Statement st = conn.createStatement()) {
                    st.execute("DROP TABLE IF EXISTS Clients");
                    //st.execute("CREATE TABLE Clients (id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, name VARCHAR(20) NOT NULL, age INT)");
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

            ClientDAOImpl2 dao = new ClientDAOImpl2(conn, "Clients");
            dao.createTable(Client.class);

            Client c = new Client("test", 11);
            Client c2 = new Client("test2", 23);
            dao.add(c);
            dao.add(c2);
            // int id = c.getId(); // DZ1
            int id = c2.getId();
            System.out.println(id);

            List<Client> list = dao.getAll(Client.class);
            List<Client> list1 = dao.getAll(Client.class, "name, age");
            List<Client> list2 = dao.getAll(Client.class, "id, name");
            List<Client> list3 = dao.getAll(Client.class, "name");
            for (Client cli : list)
                System.out.println("List:" + cli);
            for (Client cli : list1)
                System.out.println("List 1: " + cli);
            for (Client cli : list2)
                System.out.println("List 2: " + cli);
            for (Client cli : list3)
                System.out.println("List 3: " + cli);

            list.get(0).setAge(55);
            dao.update(list.get(0));

            /* Dz2

            List<Client> list = dao.getAll(Client.class, "name", "age");
            List<Client> list = dao.getAll(Client.class, "age");
            for (Client cli : list)
                System.out.println(cli);

             */

            dao.delete(list.get(0));
        }
    }
}
