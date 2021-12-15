package modules;

import models.Siswa;

public class Auth {

    public static int userId;

    public static int login(String nis) {
        int id = 0;
        if (nis == "admin") {
            id = -1;
            return id;
        }

        id = Siswa.getId(nis);

        return id;
    }
}
