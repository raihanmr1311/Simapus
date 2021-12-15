package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import modules.Model; 


public class JenisBuku extends Model {
    int rowNum;
    int id_jenis;
    String jenis_buku;

    public JenisBuku(int rowNum, int id_jenis, String jenis_buku) {
        this.id_jenis = id_jenis;
        this.jenis_buku = jenis_buku;
        this.rowNum = rowNum;
    }

    public int getId_jenis() {
        return id_jenis;
    }

    public String getJenis_buku() {
        return jenis_buku;
    }

    public int getRoNum() {
        return rowNum;
    }


    public static ArrayList<JenisBuku> getAll() {
        ArrayList<JenisBuku> jenisBuku = new ArrayList<JenisBuku>();
        try {
            PreparedStatement stmt = prepare(
                "SELECT ROW_NUMBER () OVER (ORDER BY id_jenis) AS row_num, * FROM jenis_buku");
            ResultSet result = stmt.executeQuery();

            while(result.next()) {
                JenisBuku inc = new JenisBuku(result.getInt("row_num"), result.getInt("id_jenis"), result.getString("jenis"));
                jenisBuku.add(inc);
            }
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return jenisBuku;
    }

}
