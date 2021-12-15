package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import modules.Model;

import java.time.LocalDate;

public class Peminjaman extends Model {
    private int rowNum;
    private String namaSiswa;
    private String nisSiswa;
    private String judulBuku;
    private LocalDate tanggalPengembalian;
    private LocalDate tanggalPeminjaman;

    public Peminjaman(int rowNum, String namaSiswa, String nisSiswa, String judulBuku, LocalDate tanggalPengembalian,
            LocalDate tanggalPeminjaman) {
        this.namaSiswa = namaSiswa;
        this.nisSiswa = nisSiswa;
        this.judulBuku = judulBuku;
        this.tanggalPengembalian = tanggalPengembalian;
        this.tanggalPeminjaman = tanggalPeminjaman;
        this.rowNum = rowNum;
    }

    public String getNamaSiswa() {
        return namaSiswa;
    }

    public String getNisSiswa() {
        return nisSiswa;
    }

    public String getJudulBuku() {
        return judulBuku;
    }

    public LocalDate getTanggalPengembalian() {
        return tanggalPengembalian;
    }

    public LocalDate getTanggalPeminjaman() {
        return tanggalPeminjaman;
    }

    public int getRowNum() {
        return rowNum;
    }

    public static boolean pinjam(int idBuku, int idSiswa, String tanggalPeminjaman, String tanggalPengembalian) {
        try {
            PreparedStatement stmt = prepare(
                    "INSERT INTO peminjaman(id_buku, id_siswa, tanggal_peminjaman," +
                            "tanggal_pengembalian) VALUES(?,?,?,?)");
            stmt.setInt(1, idBuku);
            stmt.setInt(2, idSiswa);
            stmt.setObject(3, LocalDate.parse(tanggalPeminjaman));
            stmt.setObject(4, LocalDate.parse(tanggalPengembalian));
            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static ArrayList<Peminjaman> getAll(int idSiswa) {
        ArrayList<Peminjaman> peminjaman = new ArrayList<Peminjaman>();
        try {
            PreparedStatement stmt = prepare(
                    "SELECT ROW_NUMBER () OVER (ORDER BY id_peminjaman) AS row_num, siswa.nama AS nama_siswa, siswa.nis AS nis, buku.judul_buku AS judul_buku, tanggal_peminjaman, tanggal_pengembalian"
                            +
                            " FROM peminjaman" +
                            " INNER JOIN siswa ON peminjaman.id_siswa = siswa.id_siswa" +
                            " INNER JOIN buku ON peminjaman.id_buku = buku.id_buku" +
                            " WHERE siswa.id_siswa = ?");

            stmt.setInt(1, idSiswa);

            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                peminjaman.add(new Peminjaman(
                        result.getInt("row_num"),
                        result.getString("nama_siswa"), result.getString("nis"),
                        result.getString("judul_buku"),
                        result.getObject("tanggal_pengembalian", LocalDate.class),
                        result.getObject("tanggal_peminjaman", LocalDate.class)));
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return peminjaman;
    }

    public static ArrayList<Peminjaman> getByNIS(String nis) {
        ArrayList<Peminjaman> peminjaman = new ArrayList<Peminjaman>();
        try {
            PreparedStatement stmt = prepare(
                    "SELECT ROW_NUMBER () OVER (ORDER BY id_peminjaman) AS row_num, siswa.nama AS nama_siswa, siswa.nis AS nis, buku.judul_buku AS judul_buku, tanggal_peminjaman, tanggal_pengembalian"
                            +
                            " FROM peminjaman" +
                            " INNER JOIN siswa ON peminjaman.id_siswa = siswa.id_siswa" +
                            " INNER JOIN buku ON peminjaman.id_buku = buku.id_buku" +
                            " WHERE siswa.nis = ?");

            stmt.setString(1, nis);

            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                peminjaman.add(new Peminjaman(
                        result.getInt("row_num"),
                        result.getString("nama_siswa"), result.getString("nis"),
                        result.getString("judul_buku"),
                        result.getObject("tanggal_pengembalian", LocalDate.class),
                        result.getObject("tanggal_peminjaman", LocalDate.class)));
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return peminjaman;
    }
}