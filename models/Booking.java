package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;

import modules.Model;

public class Booking extends Model {
    private int rowNum;
    private int idBooking;
    private LocalDate tanggalAmbil;
    private String namaSiswa;
    private String nisSiswa;
    private String judulBuku;

    public Booking(int rowNum, int idBooking, LocalDate tanggalAmbil, String namaSiswa, String nisSiswa,
            String judulBuku) {
        this.idBooking = idBooking;
        this.tanggalAmbil = tanggalAmbil;
        this.namaSiswa = namaSiswa;
        this.nisSiswa = nisSiswa;
        this.judulBuku = judulBuku;
        this.rowNum = rowNum;
    }

    public int getRowNum() {
        return rowNum;
    }

    public int getIdBooking() {
        return idBooking;
    }

    public LocalDate getTanggalAmbil() {
        return tanggalAmbil;
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

    public static boolean book(String tanggalAmbil, int idBuku, int idSiswa) {
        try {
            PreparedStatement stmt = prepare("INSERT INTO booking(tanggal_ambil, id_buku, id_siswa) VALUES(?,?,?)");
            stmt.setObject(1, LocalDate.parse(tanggalAmbil));
            stmt.setInt(2, idBuku);
            stmt.setInt(3, idSiswa);
            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static ArrayList<Booking> getAll() {
        ArrayList<Booking> booking = new ArrayList<Booking>();
        try {
            PreparedStatement stmt = prepare(
                            "SELECT ROW_NUMBER () OVER (ORDER BY id_booking) AS row_num,id_booking,buku.judul_buku AS judul_buku, siswa.nama AS nama, siswa.nis AS nis, tanggal_ambil"
                                    +
                                    " FROM booking INNER JOIN buku ON buku.id_buku = booking.id_buku" +
                                    " INNER JOIN siswa ON siswa.id_siswa = booking.id_siswa");
            ResultSet result = stmt.executeQuery();
            while (result.next()) {
                booking.add(new Booking(
                        result.getInt("row_num"),
                        result.getInt("id_booking"),
                        result.getObject("tanggal_ambil", LocalDate.class),
                        result.getString("nama"),
                        result.getString("nis"),
                        result.getString("judul_buku")));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return booking;
    }

    public static boolean accept(int idBooking, String tanggalPengembalian) {
        try {
            int idBuku = 0;
            int idSiswa = 0;
            LocalDate tanggalPeminjaman = null;

            PreparedStatement stmt = prepare("SELECT id_buku, id_siswa, tanggal_ambil FROM booking WHERE id_booking=?");
            stmt.setInt(1, idBooking);
            ResultSet resultBooking = stmt.executeQuery();
            while (resultBooking.next()) {
                idBuku = resultBooking.getInt("id_buku");
                idSiswa = resultBooking.getInt("id_siswa");
                tanggalPeminjaman = resultBooking.getObject("tanggal_ambil", LocalDate.class);
            }

            PreparedStatement stmtPeminjaman = prepare("INSERT INTO peminjaman"
                    + " (id_buku, id_siswa, tanggal_peminjaman, tanggal_pengembalian) VALUES(?,?,?,?)");

            stmtPeminjaman.setInt(1, idBuku);
            stmtPeminjaman.setInt(2, idSiswa);
            stmtPeminjaman.setObject(3, tanggalPeminjaman);
            stmtPeminjaman.setObject(4, LocalDate.parse(tanggalPengembalian));
            stmtPeminjaman.executeUpdate();

            PreparedStatement stmtDeleteBooking = prepare("DELETE FROM booking WHERE id_booking=?");
            stmtDeleteBooking.setInt(1, idBooking);
            stmtDeleteBooking.executeUpdate();

            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

}