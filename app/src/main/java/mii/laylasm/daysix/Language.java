package mii.laylasm.daysix;

/**
 * Created by Layla Siti Mardhiyah on 26/09/2017.
 */

public class Language {
    public static final int ENGLISH = 0;
    public static final int BAHASA = 1;
    private int lang;
    private static final String COMPANY = "M1";

    public Language(int Lang) {
        this.lang = Lang;
    }

    public void setLanguage(int Lang) {
        this.lang = Lang;
    }

    public int activeLang() {
        return this.lang;
    }

    public static class text {
        public static final String[] WELCOME = {"Welcome to " + COMPANY, "Selamat Datang di " + COMPANY};
        public static final String[] PLS_ACTV_ACC = {"Please activate your account", "Silahkan aktivasi akun anda"};
        public static final String[] CARD_NUMBER = {"Card Number", "No Kartu"};
        public static final String[] MOBILE_PIN = {"Mobile PIN", "Mobile PIN"};
        public static final String[] VERIFY = {"VERIFY", "VERIFIKASI"};
        public static final String[] LOGIN = {"LOGIN","LOGIN"};
        public static final String[] PLS_SET_ACCESS_CODE = {"Please set your access code", "Masukkan kode akses anda"};
        public static final String[] ACCESS_CODE = {"Access code", "Kode Akses"};
        public static final String[] TYPE_YOUR_ACCESS_CODE = {"Type your access code","Masukkan kode akses anda"};
        public static final String[] CONFIRM_ACCESS_CODE = {"Confirm Access Code", "Konfirmasi Kode Akses"};
        public static final String[] SUBMIT = {"Submit", "Lanjut"};
        public static final String[] FAILED_BACK = {"Back", "Kembali"};
        public static final String[] SUCCESS_OK = {"OK", "OK"};
    }

}
