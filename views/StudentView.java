package views;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.github.freva.asciitable.AsciiTable;
import com.github.freva.asciitable.Column;
import com.github.freva.asciitable.HorizontalAlign;

import models.Booking;
import models.Buku;
import models.JenisBuku;
import models.Peminjaman;
import modules.Auth;
import modules.View;
import utils.Console;

public class StudentView extends View {

    public void book() {

        // List Jenis
        ArrayList<JenisBuku> listJenis = JenisBuku.getAll();
        listJenis.forEach((jenis) -> {
            System.out.println(jenis.getId_jenis() + ". " + jenis.getJenis_buku());
        });

        System.out.print("Masukkan jenis yang diinginkan : ");
        int indexJenis = this.input.getNumber(1, listJenis.size());

        // List Buku
        List<Buku> listBuku = Buku.getByJenis(listJenis.get(indexJenis - 1).getId_jenis());
        System.out.println(AsciiTable.getTable(listBuku, Arrays.asList(
                new Column().header("No.")
                        .dataAlign(HorizontalAlign.CENTER)
                        .with(buku -> String.valueOf(buku.getRowNumber())),
                new Column().header("Judul")
                        .headerAlign(HorizontalAlign.CENTER)
                        .dataAlign(HorizontalAlign.LEFT)
                        .with(buku -> buku.getJudulBuku()))));

        System.out.print("Masukkan judul yang diinginkan : ");
        int indexJudul = this.input.getNumber(1, listBuku.size());

        int idBuku = listBuku.get(indexJudul - 1).getIdBuku();

        System.out.print("Masukkan tanggal pengambilan : ");
        String tanggal = this.input.inputDate();

        Booking.book(tanggal, idBuku, Auth.userId);
        Console.clear();
        System.out.println("Data booking berhasil ditambahkan!");

    }

    public void listPeminjaman() {
        List<Peminjaman> listPeminjaman = Peminjaman.getAll(Auth.userId);

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
                            .dataAlign(HorizontalAlign.CENTER)
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

            )));
        } else {
            System.out.println("Tidak ada data peminjaman");
        }
        System.out.println();
    }
}
