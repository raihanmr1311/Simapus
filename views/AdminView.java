package views;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.github.freva.asciitable.AsciiTable;
import com.github.freva.asciitable.Column;
import com.github.freva.asciitable.HorizontalAlign;

import models.Booking;
import models.Buku;
import models.Peminjaman;
import modules.View;
import utils.Console;

public class AdminView extends View {
    public void showBooking() {

        System.out.println("List booking siswa");

        List<Booking> listBooking = Booking.getAll();

        if (listBooking.size() > 0) {

            System.out.println(AsciiTable.getTable(listBooking, Arrays.asList(
                    new Column().header("No.")
                            .headerAlign(HorizontalAlign.CENTER)
                            .dataAlign(HorizontalAlign.CENTER)
                            .with(booking -> String.valueOf(booking.getRowNum())),
                    new Column().header("Judul Buku")
                            .headerAlign(HorizontalAlign.CENTER)
                            .dataAlign(HorizontalAlign.LEFT)
                            .with(booking -> booking.getJudulBuku()),
                    new Column().header("Nama")
                            .headerAlign(HorizontalAlign.CENTER)
                            .dataAlign(HorizontalAlign.LEFT)
                            .with(booking -> booking.getNamaSiswa()),
                    new Column().header("NIS")
                            .headerAlign(HorizontalAlign.CENTER)
                            .dataAlign(HorizontalAlign.CENTER)
                            .with(booking -> booking.getNisSiswa()),
                    new Column().header("Tanggal Ambil")
                            .headerAlign(HorizontalAlign.CENTER)
                            .dataAlign(HorizontalAlign.CENTER)
                            .with(booking -> booking.getTanggalAmbil().toString())

            )));
        } else {
            System.out.println("Tidak ada data booking");
        }System.out.println();
    }

    public void acceptBooking() {
        this.showBooking();
        ArrayList<Booking> listBooking = Booking.getAll();
        if (listBooking.size() > 0) {
            String tanggalPengembalian;
            int idBooking;

            System.out.print("Masukkan nomor booking => ");
            int index = this.input.getNumber(1, listBooking.size());
            idBooking = listBooking.get(index - 1).getIdBooking();

            System.out.println();
            System.out.print("Masukkan tanggal pengembalian => ");
            tanggalPengembalian = this.input.inputAndCompareDate(listBooking.get(index-1).getTanggalAmbil());

            Booking.accept(idBooking, tanggalPengembalian);
            Console.clear();
            System.out.println("Permintaan berhasil dikonfirmasi!");
        }
    }

    public void inpBook() {
        System.out.println("Menambah Buku");
        System.out.println(
                "Jenis Buku : \n1. Sejarah \n2. Biologi \n3. Agama \n4. Cerita Rakyat \n5. Novel Remaja \n6. Komik");
        System.out.print("Masukkan id jenis buku yang ingin ditambahkan : ");
        int idJenis = this.scan.nextInt();
        System.out.print("Masukkan judul buku : ");
        this.scan.nextLine();
        String judul = this.scan.nextLine();
        Buku.insBook(judul, idJenis);
        Console.clear();
        System.out.println("Data berhasil ditambahkan!");

    }

    public void listPeminjamanByNIS() {
        System.out.print("Masukkan nis => ");
        String nis = this.scan.next();

        ArrayList<Peminjaman> listPeminjaman = Peminjaman.getByNIS(nis);

        if (listPeminjaman.size() > 0) {

            System.out.println(AsciiTable.getTable(listPeminjaman, Arrays.asList(
                    new Column().header("No.")
                            .headerAlign(HorizontalAlign.CENTER)
                            .dataAlign(HorizontalAlign.CENTER)
                            .with(peminjaman -> String.valueOf(peminjaman.getRowNum())),
                    new Column().header("Judul Buku")
                            .headerAlign(HorizontalAlign.CENTER)
                            .dataAlign(HorizontalAlign.LEFT)
                            .with(peminjaman -> peminjaman.getJudulBuku()),
                    new Column().header("Nama")
                            .headerAlign(HorizontalAlign.CENTER)
                            .dataAlign(HorizontalAlign.LEFT)
                            .with(peminjaman -> peminjaman.getNamaSiswa()),
                    new Column().header("NIS")
                            .headerAlign(HorizontalAlign.CENTER)
                            .dataAlign(HorizontalAlign.CENTER)
                            .with(peminjaman -> peminjaman.getNisSiswa()),
                    new Column().header("Tanggal Peminjaman")
                            .headerAlign(HorizontalAlign.CENTER)
                            .dataAlign(HorizontalAlign.CENTER)
                            .with(peminjaman -> peminjaman.getTanggalPeminjaman()
                                    .toString()),
                    new Column().header("Tanggal Pengembalian")
                            .headerAlign(HorizontalAlign.CENTER)
                            .dataAlign(HorizontalAlign.CENTER)
                            .with(peminjaman -> peminjaman.getTanggalPengembalian()
                                    .toString())
                
            )));System.out.println();
        } else {
            System.out.println("Tidak ada data peminjaman");
        }
        }
}
