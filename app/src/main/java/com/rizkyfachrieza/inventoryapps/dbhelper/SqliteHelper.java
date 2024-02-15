package com.rizkyfachrieza.inventoryapps.dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.rizkyfachrieza.inventoryapps.model.ModelBarang;
import com.rizkyfachrieza.inventoryapps.model.ModelKategori;
import com.rizkyfachrieza.inventoryapps.model.ModelSatuan;
import com.rizkyfachrieza.inventoryapps.model.ModelSupplier;
import com.rizkyfachrieza.inventoryapps.model.ModelUser;

import java.util.ArrayList;
import java.util.List;

public class SqliteHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "inventoryapps";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_USERS = "tbl_user";
    public static final String KEY_ID = "id";
    public static final String KEY_USER_NAME = "username";
    public static final String KEY_NAMA = "nama";
    public static final String KEY_PASSWORD = "password";

    public static final String SQL_TABLE_USERS = " CREATE TABLE " + TABLE_USERS
            + " ( "
            + KEY_ID + " INTEGER PRIMARY KEY, "
            + KEY_USER_NAME + " TEXT, "
            + KEY_NAMA + " TEXT, "
            + KEY_PASSWORD + " TEXT"
            + " ) ";

    public static final String TABLE_MASTER_BARANG = "tbl_master_barang";
    public static final String KEY_KODE_BARANG = "kode_barang";
    public static final String KEY_NAMA_BARANG = "nama_barang";
    public static final String KEY_KATEGORI = "kategori";
    public static final String KEY_HARGA = "harga";
    public static final String KEY_STOK = "stok_barang";
    public static final String KEY_SATUAN = "satuan";

    public static final String TABLE_MASTER_KATEGORI = "tbl_master_kategori";
    public static final String KEY_NAMA_KATEGORI = "nama_kategori";

    public static final String TABLE_MASTER_SATUAN = "tbl_master_satuan";
    public static final String KEY_NAMA_SATUAN = "nama_satuan";

    public static final String TABLE_MASTER_SUPPLIER = "tbl_master_supplier";
    public static final String KEY_SUPPLIER = "supplier";

    public static final String SQL_TABLE_MASTER_SUPPLIER = " CREATE TABLE " + TABLE_MASTER_SUPPLIER
            + " ( "
            + KEY_ID + " INTEGER PRIMARY KEY, "
            + KEY_SUPPLIER + " TEXT"
            + " ) ";

    public static final String SQL_TABLE_MASTER_KATEGORI = " CREATE TABLE " + TABLE_MASTER_KATEGORI
            + " ( "
            + KEY_ID + " INTEGER PRIMARY KEY, "
            + KEY_NAMA_KATEGORI + " TEXT"
            + " ) ";

    public static final String SQL_TABLE_MASTER_SATUAN = " CREATE TABLE " + TABLE_MASTER_SATUAN
            + " ( "
            + KEY_ID + " INTEGER PRIMARY KEY, "
            + KEY_NAMA_SATUAN + " TEXT"
            + " ) ";

    public static final String SQL_TABLE_MASTER_BARANG = " CREATE TABLE " + TABLE_MASTER_BARANG
            + " ( "
            + KEY_ID + " INTEGER PRIMARY KEY, "
            + KEY_KODE_BARANG + " TEXT, "
            + KEY_NAMA_BARANG + " TEXT, "
            + KEY_KATEGORI + " TEXT, "
            + KEY_HARGA + " TEXT, "
            + KEY_STOK + " TEXT, "
            + KEY_SATUAN + " TEXT"
            + " ) ";

    public static final String TABLE_MASTER_BARANG_MASUK = "tbl_master_barang_masuk";
    public static final String TABLE_MASTER_BARANG_KELUAR = "tbl_master_barang_keluar";
    public static final String KEY_TGL_MASUK = "tgl_masuk";
    public static final String KEY_TGL_KELUAR = "tgl_keluar";
    public static final String KEY_ID_KODE_BARANG = "id_kode_barang";
    public static final String KEY_ID_SUPPLIER = "id_supplier";
    public static final String SQL_TABLE_MASTER_BARANG_MASUK = " CREATE TABLE " + TABLE_MASTER_BARANG_MASUK
            + " ( "
            + KEY_ID + " INTEGER PRIMARY KEY, "
            + KEY_ID_KODE_BARANG + " TEXT, "
            + KEY_ID_SUPPLIER + " TEXT, "
            + KEY_TGL_MASUK + " TEXT"
            + " ) ";

    public static final String SQL_TABLE_MASTER_BARANG_KELUAR = " CREATE TABLE " + TABLE_MASTER_BARANG_KELUAR
            + " ( "
            + KEY_ID + " INTEGER PRIMARY KEY, "
            + KEY_ID_KODE_BARANG + " TEXT, "
            + KEY_ID_SUPPLIER + " TEXT, "
            + KEY_TGL_KELUAR + " TEXT"
            + " ) ";

    public SqliteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_TABLE_USERS);
        sqLiteDatabase.execSQL(SQL_TABLE_MASTER_KATEGORI);
        long insertData = sqLiteDatabase.insert(TABLE_MASTER_KATEGORI, null, this.createValues(new String[]{KEY_NAMA_KATEGORI}, new String[]{"Makanan"}));
        long insertData2 = sqLiteDatabase.insert(TABLE_MASTER_KATEGORI, null, this.createValues(new String[]{KEY_NAMA_KATEGORI}, new String[]{"Minuman"}));

        sqLiteDatabase.execSQL(SQL_TABLE_MASTER_SATUAN);
        long insertData3  = sqLiteDatabase.insert(TABLE_MASTER_SATUAN, null, this.createValues(new String[]{KEY_NAMA_SATUAN}, new String[]{"pcs"}));
        long insertData4  = sqLiteDatabase.insert(TABLE_MASTER_SATUAN, null, this.createValues(new String[]{KEY_NAMA_SATUAN}, new String[]{"gram"}));
        long insertData45 = sqLiteDatabase.insert(TABLE_MASTER_SATUAN, null, this.createValues(new String[]{KEY_NAMA_SATUAN}, new String[]{"kg"}));


        sqLiteDatabase.execSQL(SQL_TABLE_MASTER_BARANG);
        sqLiteDatabase.execSQL(SQL_TABLE_MASTER_SUPPLIER);
        long insertData5 = sqLiteDatabase.insert(TABLE_MASTER_SUPPLIER, null, this.createValues(new String[]{KEY_SUPPLIER}, new String[]{"WIKA BETON"}));

        sqLiteDatabase.execSQL(SQL_TABLE_MASTER_BARANG_MASUK);
        sqLiteDatabase.execSQL(SQL_TABLE_MASTER_BARANG_KELUAR);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_USERS);
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_MASTER_BARANG);
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_MASTER_KATEGORI);
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_MASTER_SATUAN);
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_MASTER_SUPPLIER);
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_MASTER_BARANG_MASUK);
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_MASTER_BARANG_KELUAR);

    }

    public void addUser(ModelUser user) {
        SQLiteDatabase db = this.getWritableDatabase();
        long insertData = db.insert(TABLE_USERS, null, this.createValues(new String[]{KEY_USER_NAME,KEY_NAMA,KEY_PASSWORD}, new String[]{user.username, user.nama, user.password}));
    }

    public ContentValues createValues(String[] param, String[] value){
        ContentValues values = new ContentValues();
        for(int i = 0; i < param.length; i++){
            values.put(param[i],value[i]);
        }
        return values;
    }

    public ModelUser Authenticate(ModelUser user) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS,
                new String[]{KEY_ID, KEY_USER_NAME, KEY_NAMA, KEY_PASSWORD},
                KEY_USER_NAME + "=?",
                new String[]{user.username},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()&& cursor.getCount()>0) {
            ModelUser user1 = new ModelUser(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));

            if (user.password.equalsIgnoreCase(user1.password)) {
                return user1;
            }
        }
        return null;
    }

    public boolean isUsernameExist(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS,// Selecting Table
                new String[]{KEY_ID, KEY_USER_NAME, KEY_NAMA, KEY_PASSWORD},
                KEY_USER_NAME + "=?",
                new String[]{username},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()&& cursor.getCount()>0) {
            return true;
        }
        return false;
    }

    public ArrayList<ModelUser> readUser() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursorUser = db.rawQuery("SELECT * FROM " + TABLE_USERS, null);
        ArrayList<ModelUser> modelUserArrayList = new ArrayList<>();

        if (cursorUser.moveToFirst()) {
            do {
                modelUserArrayList.add(new ModelUser(cursorUser.getString(0),
                        cursorUser.getString(1),
                        cursorUser.getString(2),
                        cursorUser.getString(3)
                ));
            } while (cursorUser.moveToNext());
        }
        cursorUser.close();
        return modelUserArrayList;
    }

    public void addBarangKeluar(String id, String supplier, String tgl_masuk) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID_KODE_BARANG, id);
        values.put(KEY_ID_SUPPLIER, supplier);
        values.put(KEY_TGL_KELUAR, tgl_masuk);

        long insertData = db.insert(TABLE_MASTER_BARANG_KELUAR, null, values);
    }

    public Cursor searchBarangKeluar(String search) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_MASTER_BARANG_KELUAR
                        + " as msk INNER JOIN "+TABLE_MASTER_BARANG+" as brg ON "
                        + "brg."+KEY_ID+" = "
                        + "msk."+KEY_ID_KODE_BARANG
                        + " INNER JOIN "+TABLE_MASTER_SUPPLIER+" as sup ON "
                        + "sup."+KEY_ID+" = "
                        + "msk."+KEY_ID_SUPPLIER+
                        " where brg."+KEY_NAMA_BARANG+" LIKE '%"+search+"%'" +
                        " or brg."+KEY_KODE_BARANG+" LIKE '%"+search+"%'" +
                        " or msk."+KEY_TGL_KELUAR+" LIKE '%"+search+"%'" +
                        " or brg."+KEY_STOK+" LIKE '%"+search+"%'" +
                        " or sup."+KEY_SUPPLIER+" LIKE '%"+search+"%'"

                , null);
        return res;
    }

    public boolean deleteBarangKeluar(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long data = db.delete(TABLE_MASTER_BARANG_KELUAR, "id=?", new String[]{id});
        return true;
    }

    public boolean editBarangKeluar(String id, String kodeBarang, String tgl_keluar, String supplier) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID_KODE_BARANG, kodeBarang);
        values.put(KEY_TGL_KELUAR, tgl_keluar);
        values.put(KEY_ID_SUPPLIER, supplier);
        int i = db.update(TABLE_MASTER_BARANG_KELUAR, values,"id=?", new String[]{id});
        return i > 0;
    }

    public void addBarangMasuk(String id, String supplier, String tgl_masuk) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID_KODE_BARANG, id);
        values.put(KEY_ID_SUPPLIER, supplier);
        values.put(KEY_TGL_MASUK, tgl_masuk);

        long insertData = db.insert(TABLE_MASTER_BARANG_MASUK, null, values);
    }

    public Cursor searchBarangMasuk(String search) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_MASTER_BARANG_MASUK
                        + " as msk INNER JOIN "+TABLE_MASTER_BARANG+" as brg ON "
                        + "brg."+KEY_ID+" = "
                        + "msk."+KEY_ID_KODE_BARANG
                        + " INNER JOIN "+TABLE_MASTER_SUPPLIER+" as sup ON "
                        + "sup."+KEY_ID+" = "
                        + "msk."+KEY_ID_SUPPLIER+
                        " where brg."+KEY_NAMA_BARANG+" LIKE '%"+search+"%'" +
                        " or brg."+KEY_KODE_BARANG+" LIKE '%"+search+"%'" +
                        " or msk."+KEY_TGL_MASUK+" LIKE '%"+search+"%'" +
                        " or brg."+KEY_STOK+" LIKE '%"+search+"%'" +
                        " or sup."+KEY_SUPPLIER+" LIKE '%"+search+"%'"

                , null);
        return res;
    }

    public boolean deleteBarangMasuk(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long data = db.delete(TABLE_MASTER_BARANG_MASUK, "id=?", new String[]{id});
        return true;
    }

    public boolean editBarangMasuk(String id, String kodeBarang, String tgl_masuk, String supplier) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID_KODE_BARANG, kodeBarang);
        values.put(KEY_TGL_MASUK, tgl_masuk);
        values.put(KEY_ID_SUPPLIER, supplier);
        int i = db.update(TABLE_MASTER_BARANG_MASUK, values,"id=?", new String[]{id});
        return i > 0;
    }

    public void addBarang(ModelBarang barang) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_KODE_BARANG, barang.getKodeBarang());
        values.put(KEY_NAMA_BARANG, barang.getNamaBarang());
        values.put(KEY_KATEGORI, barang.getKategori());
        values.put(KEY_HARGA, barang.getHarga());
        values.put(KEY_STOK, barang.getStokBarang());
        values.put(KEY_SATUAN, barang.getSatuan());

        long insertData = db.insert(TABLE_MASTER_BARANG, null, values);
    }

    public Cursor searchBarang(String search) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_MASTER_BARANG +
                " where "+KEY_NAMA_BARANG+" LIKE '%"+search+"%'" +
                        " or "+KEY_KODE_BARANG+" LIKE '%"+search+"%'" +
                        " or "+KEY_KATEGORI+" LIKE '%"+search+"%'" +
                        " or "+KEY_HARGA+" LIKE '%"+search+"%'" +
                        " or "+KEY_SATUAN+" LIKE '%"+search+"%'"

                , null);
        return res;
    }

    public boolean deleteBarang(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long data = db.delete(TABLE_MASTER_BARANG, "id=?", new String[]{id});
        return true;
    }

    public boolean editBarang(ModelBarang barang) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_KODE_BARANG, barang.getKodeBarang());
        values.put(KEY_NAMA_BARANG, barang.getNamaBarang());
        values.put(KEY_KATEGORI, barang.getKategori());
        values.put(KEY_HARGA, barang.getHarga());
        values.put(KEY_STOK, barang.getStokBarang());
        values.put(KEY_SATUAN, barang.getSatuan());
        int i = db.update(TABLE_MASTER_BARANG, values,"id=?", new String[]{barang.getId()});
        return i > 0;
    }

    public void addSupplier(ModelSupplier supplier) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_SUPPLIER, supplier.getSupplier());

        long insertData = db.insert(TABLE_MASTER_SUPPLIER, null, values);
    }

    public Cursor searchSupplier(String search) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_MASTER_SUPPLIER +
                        " where "+KEY_SUPPLIER+" LIKE '%"+search+"%'"
                , null);
        return res;
    }

    public boolean deleteSupplier(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long data = db.delete(TABLE_MASTER_SUPPLIER, "id=?", new String[]{id});
        return true;
    }

    public boolean editSupplier(ModelSupplier supplier) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_SUPPLIER, supplier.getSupplier());
        int i = db.update(TABLE_MASTER_SUPPLIER, values,"id=?", new String[]{supplier.getId()});
        return i > 0;
    }

    public List<String> readKatogori() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_MASTER_KATEGORI, null);
        List<String> arrayList = new ArrayList<String>();
        arrayList.add("Pilih Kategori");
        if (cursor.moveToFirst()) {
            do {
                arrayList.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return arrayList;
    }

    public List<String> readSatuan() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_MASTER_SATUAN, null);
        List<String> arrayList = new ArrayList<String>();
        arrayList.add("Pilih Satuan");
        if (cursor.moveToFirst()) {
            do {
                arrayList.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return arrayList;
    }

    public ArrayList<ModelBarang> readBarang() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_MASTER_BARANG, null);
        ArrayList<ModelBarang> arrayList = new ArrayList<ModelBarang>();
        if (cursor.moveToFirst()) {
            do {
                arrayList.add(new ModelBarang(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return arrayList;
    }

    public ArrayList<ModelSupplier> readSupplier() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_MASTER_SUPPLIER, null);
        ArrayList<ModelSupplier> arrayList = new ArrayList<ModelSupplier>();
        if (cursor.moveToFirst()) {
            do {
                arrayList.add(new ModelSupplier(cursor.getString(0), cursor.getString(1)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return arrayList;
    }

    public Cursor getAllBarang() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_MASTER_BARANG, null);
        return res;
    }

    public Cursor getAllSupllier() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_MASTER_SUPPLIER, null);
        return res;
    }

    public Cursor getAllBarangMasuk() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_MASTER_BARANG_MASUK
                + " as msk INNER JOIN "+TABLE_MASTER_BARANG+" as brg ON "
                + "brg."+KEY_ID+" = "
                + "msk."+KEY_ID_KODE_BARANG
                + " INNER JOIN "+TABLE_MASTER_SUPPLIER+" as sup ON "
                + "sup."+KEY_ID+" = "
                + "msk."+KEY_ID_SUPPLIER, null);
        return res;
    }

    public Cursor getAllBarangKeluar() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_MASTER_BARANG_KELUAR
                + " as msk INNER JOIN "+TABLE_MASTER_BARANG+" as brg ON "
                + "brg."+KEY_ID+" = "
                + "msk."+KEY_ID_KODE_BARANG
                + " INNER JOIN "+TABLE_MASTER_SUPPLIER+" as sup ON "
                + "sup."+KEY_ID+" = "
                + "msk."+KEY_ID_SUPPLIER, null);
        return res;
    }
}