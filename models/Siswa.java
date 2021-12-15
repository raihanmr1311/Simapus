package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import modules.Model;


public class Siswa extends Model {
    private int rowNumber;
    private int idSiswa;
    private String nama;
    private String nis;

    public Siswa(int rowNumber, int idSiswa, String nama, String nis) {
        this.idSiswa = idSiswa;
        this.nama = nama;
        this.nis = nis;
        this.rowNumber = rowNumber;
    }

    public int getIdSiswa() {
        return idSiswa;
    }

    public String getNama() {
        return nama;
    }

    public String getNis() {
        return nis;
    }
    public int getRowNumber(){
        return rowNumber;
    }

    public static ArrayList<Siswa> getAll() {
        ArrayList<Siswa> siswaList = new ArrayList<Siswa>();
        try {
            PreparedStatement stmt = prepare("SELECT ROW_NUMBER () OVER (ORDER BY id_siswa) AS row_num, SELECT * FROM siswa");
            ResultSet result = stmt.executeQuery();

            while(result.next()){
            Siswa inc = new Siswa(result.getInt("row_num"), result.getInt("id_siswa"), result.getString("nama"), result.getString("nis"));
            siswaList.add(inc);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return siswaList;
    }

    public static int getId(String nis) {
        int userId = 0;
        try {
            PreparedStatement stmt = prepare("SELECT id_siswa FROM siswa WHERE nis = ?");
            stmt.setString(1, nis);
            ResultSet result = stmt.executeQuery();
            while (result.next()) {
                userId = result.getInt("id_siswa");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return userId;
    }
}
