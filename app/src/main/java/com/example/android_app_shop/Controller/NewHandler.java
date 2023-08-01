package com.example.android_app_shop.Controller;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.android_app_shop.Model.News;
import com.example.android_app_shop.Model.Product;

import java.util.ArrayList;

public class NewHandler extends SQLiteOpenHelper {
    public static final String DB_NAME = "SMARTPHONE.db";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "NEWS";
    private static final String ID_COL = "ID";
    private static final String TITLE_COL = "Title";
    private static final String CONTENT_COL = "Content";
    private static final String DATE_COL = "Date";
    private static final String IMAGE_URL_COL = "ImageUrl";
    private static final String PRODUCT_ID_COL = "ProductID";
    public static String path = "/data/data/com.example.android_app_shop/database/smartphone.db";

    public NewHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, factory, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                ID_COL + " INTEGER PRIMARY KEY, " +
                TITLE_COL + " TEXT, " +
                CONTENT_COL + " TEXT, " +
                DATE_COL + " DATE, " +
                IMAGE_URL_COL + " TEXT, " +
                PRODUCT_ID_COL + " INTEGER, " +
                "FOREIGN KEY (" + PRODUCT_ID_COL + ") REFERENCES PRODUCT(ID)" +
                ")";
        db.execSQL(createTableQuery);
    }

    public void initData(){
        SQLiteDatabase db;
        db= SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        onCreate(db);
        String row1 = "INSERT OR IGNORE INTO " + TABLE_NAME + " (" + ID_COL + ", " + TITLE_COL + ", " + CONTENT_COL + ", " + DATE_COL + ", " + IMAGE_URL_COL + ", " + PRODUCT_ID_COL + " ) VALUES (1, 'IPHONE 15 Sắp ra mắt', 'Theo thông tin mới nhất từ trang MacRumors, iPhone 15 sẽ được trang bị pin có dung lượng cao hơn đáng kể so với thế hệ trước đó. Theo lời tiết lộ bởi một nhân viên của Foxconn, mẫu iPhone 15 sẽ có dung lượng pin lên đến 3.877 mAh, cao hơn hẳn so với mức 3.279 mAh của iPhone 14.', '2023-07-31', 'https://didongviet.vn/dchannel/wp-content/uploads/2022/09/iphone-15-co-gi-moi-didongviet-11.jpg', 1)";
        db.execSQL(row1);
        String row2 = "INSERT OR IGNORE INTO " + TABLE_NAME + " (" + ID_COL + ", " + TITLE_COL + ", " + CONTENT_COL + ", " + DATE_COL + ", " + IMAGE_URL_COL + ", " + PRODUCT_ID_COL + " ) VALUES (2, 'Apple phát triển case iPhone, iPad hoàn toàn mới', " +
                "'2\n" +
                "Số hóaSản phẩmThiết bịThứ năm, 13/7/2023, 19:00 (GMT+7)\n" +
                "Apple phát triển case iPhone, iPad hoàn toàn mới\n" +
                "Các mẫu case trong tương lai của iPhone và iPad sẽ có dạng vòng gắn quanh khung sườn, ngoài tính năng bảo vệ còn có thể gắn thêm linh kiện khác.\n" +
                "\n" +
                "Sau khi iPhone và iPad ra mắt, ngành công nghiệp phụ kiện, trong đó có các bộ case, phát triển nở rộ, giúp người dùng bảo vệ những chiếc điện thoại và máy tính bảng đắt tiền. Hiện nay, một số mẫu case còn có thêm tính năng như giá đỡ, ví tiền, pin sạc.\n" +
                "\n" +
                "Trong khi đó, Apple mới được cấp bằng sáng chế cho case bảo vệ iPhone, iPad mang tên \"vỏ ngoại vi cho thiết bị máy tính\", thay đổi hoàn toàn quan niệm về các mẫu case hiện có trên thị trường. Cụ thể, phần case có bộ phận chính là một vòng đệm giúp bao quanh thiết bị nhưng chỉ kết nối với khung sườn ở một cạnh duy nhất thay vì ôm sát vào toàn bộ khung như hiện tại.', " +
                "'2023-07-30', 'https://i1-sohoa.vnecdn.net/2023/07/12/magsafe-770x503-4082-1689099180.png?w=680&h=0&q=100&dpr=1&fit=crop&s=c8BDOljsikh345zb70daTg', 1)";
        db.execSQL(row2);
        String row3 = "INSERT OR IGNORE INTO " + TABLE_NAME + " (" + ID_COL + ", " + TITLE_COL + ", " + CONTENT_COL + ", " + DATE_COL + ", " + IMAGE_URL_COL + ", " + PRODUCT_ID_COL + " ) VALUES (3, 'Apple bắt đầu cho dùng thử iOS 17', " +
                "'Người dùng iPhone từ thế hệ XS và iPhone SE từ thế hệ hai có thể tải iOS 17 phiên bản Public Beta đầu tiên để trải nghiệm.\n" +
                "\n" +
                "Để sử dụng bản thử nghiệm iOS mới, người dùng các mẫu iPhone đủ điều kiện cần truy cập vào Settings -> General -> Software Update và chọn iOS 17 Public Beta.\n" +
                "\n" +
                "iOS 17 đánh dấu lần đầu Apple cho người dùng sử dụng thử rộng rãi phiên bản thử nghiệm, bao gồm bản chỉ dành cho nhà phát triển. Trước đây, mỗi máy cần có tài khoản của nhà phát triển Developer (giá 99 USD mỗi năm) mới có thể tải và sử dụng bản Developer Beta. Tuy nhiên, ngay sau sự kiện WWDC 2023 đầu tháng 6, người dùng đã có thể tải về mà không cần điều kiện đặc biệt nào.', " +
                "'2023-07-29', 'https://i1-sohoa.vnecdn.net/2023/07/13/Untitled-1-1598-1689205128.jpg?w=680&h=0&q=100&dpr=1&fit=crop&s=m0uSWNZLwQq_XQfMUN7TRQ', 1)";
        db.execSQL(row3);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public ArrayList<News> loadAllNews(){
        SQLiteDatabase db;
        ArrayList<News> lstNews = new ArrayList<>();
        db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        Cursor cursor = db.rawQuery("SELECT * FROM NEWS", null);
        cursor.moveToFirst();
        do{
            News news = new News();
            news.setID(cursor.getString(0));
            news.setTitle(cursor.getString(1));
            news.setContent(cursor.getString(2));
            news.setDate(cursor.getString(3));
            news.setImageURL(cursor.getString(4));
            news.setProductID(cursor.getString(5));
            lstNews.add(news);
        }while (cursor.moveToNext());
        cursor.close();
        db.close();
        return lstNews;
    }

}
