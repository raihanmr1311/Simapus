package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import modules.Model;

public class Buku extends Model {

    private int rowNumber;
    private int idBuku;
    private String judulBuku;
    private int idJenis;

    public Buku(int rowNumber,String judulBuku, int idJenis, int idBuku) {
        this.judulBuku = judulBuku;
        this.idJenis = idJenis;
        this.idBuku = idBuku;
        this.rowNumber = rowNumber;
    }

    public int getIdBuku() {
        return idBuku;
    }

    public String getJudulBuku() {
        return judulBuku;
    }

    public int getIdJenis() {
        return idJenis;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public static boolean insBook(String judulBuku, int idJenis) {
        try {
            PreparedStatement stmt = prepare("INSERT INTO buku(judul_buku, id_jenis) VALUES (?,?) ");
            stmt.setString(1, judulBuku);
            stmt.setInt(2, idJenis);
            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        }
        
    }

    public static ArrayList<Buku> getByJenis(int idJenis) {
        ArrayList<Buku> listBuku = new ArrayList<Buku>();
        try {
            PreparedStatement stmt = prepare("SELECT ROW_NUMBER () OVER (ORDER BY id_buku) AS row_num ,* FROM buku WHERE id_jenis=?");
            stmt.setInt(1, idJenis);
            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                Buku buku = new Buku(result.getInt("row_num"),result.getString("judul_buku"), result.getInt("id_jenis"),
                        result.getInt("id_buku"));
                listBuku.add(buku);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return listBuku;
    }

}