package com.example.android_app_shop.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android_app_shop.Controller.ImageProductHandler;
import com.example.android_app_shop.Controller.InPutPayToBillHandler;
import com.example.android_app_shop.Controller.PayHandler;
import com.example.android_app_shop.Controller.ProductHandlder;
import com.example.android_app_shop.Model.Bill;
import com.example.android_app_shop.Model.Cart;
import com.example.android_app_shop.Model.CartManager;
import com.example.android_app_shop.Model.CustomAdapterPay;
import com.example.android_app_shop.Model.ImageProduct;
import com.example.android_app_shop.Model.Pay;
import com.example.android_app_shop.Model.Product;
import com.example.android_app_shop.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class PayActivity extends AppCompatActivity {
    TextView tvTenIphone, tvDungLuong, tvSoLuong, tvMauSac, tvToTal;
    EditText edtHoTen,edtSDT,edtEmail;

    ImageView imgKq;
    Spinner spnTinhTp, spnQuanHuyen;
    ListView lvKq;
    Button btnHoanThanh;

    ImageProductHandler imageProductHandler;
    ProductHandlder productHandlder;
    PayHandler cart_in_payHandler;
    RadioButton rdoTaiCH, rdoTaiNha,rdoMoMo,rdoVNpay,rdoTienmat;
    String idProduct;
    CartManager cartManager;

    InPutPayToBillHandler inPutPaytoBill;

    ArrayList<Product> productArrayList = new ArrayList<>();
    ArrayList<Integer> checkedProductIds;
    ArrayList<Pay> payList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        productHandlder = new ProductHandlder(getApplication(), "SMARTPHONE.db", null, 1);

        inPutPaytoBill = new InPutPayToBillHandler(getApplication(), "SMARTPHONE.db", null, 1);
        inPutPaytoBill.initData();
        productArrayList = productHandlder.loadProduct();
        cartManager = new CartManager(this);
        addControls();
        loadCartItems();
        spnClicks();
        addEvents();
        Intent intent = getIntent();
        checkedProductIds = getIntent().getIntegerArrayListExtra("checkedProductIds");
        idProduct = String.valueOf(checkedProductIds.get(0));

        double totalAmount = intent.getDoubleExtra("totalAmount", 0.0);
        NumberFormat formatter = DecimalFormat.getInstance(new Locale("vi", "VN"));
        String formattedTotalAmount = formatter.format(totalAmount);
        tvToTal.setText( "$" + formattedTotalAmount );

    }
    private void addEvents() {
        rdoTaiNha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rdoTaiNha.isChecked()){
                    rdoTaiCH.setChecked(false);

                }
            }
        });
        rdoTaiCH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rdoTaiCH.isChecked()) {
                    rdoTaiNha.setChecked(false);
                }
            }
        });

        rdoMoMo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rdoMoMo.isChecked()) {
                    rdoTienmat.setChecked(false);
                    rdoVNpay.setChecked(false);
                    showDialogMomo();
                }
            }
        });

        rdoVNpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rdoVNpay.isChecked()) {
                    rdoTienmat.setChecked(false);
                    rdoMoMo.setChecked(false);
                    showDialogVNPAY();
                }
            }
        });

        rdoTienmat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rdoTienmat.isChecked()) {
                    rdoVNpay.setChecked(false);
                    rdoMoMo.setChecked(false);
                }
            }
        });
        btnHoanThanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve data from EditText fields
                String fullname = edtHoTen.getText().toString();
                String email = edtEmail.getText().toString();
                String phoneNumber = edtSDT.getText().toString();

                Bill bill;

                try {
                    for (int i = 0; i < checkedProductIds.size(); i++){
                        bill = new Bill(1,2,1,fullname,email,phoneNumber,"18/05/2003");
                        inPutPaytoBill.addBill(bill);
                        clearCart();
                        finish();


                    }
                    Toast.makeText(PayActivity.this, "Thanh toán thành công", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(PayActivity.this, "Thanh toán thất bại!", Toast.LENGTH_SHORT).show();
                }
            }


        });


    }
    private void showDialogMomo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(R.layout.custom_dialog_momo) // Sử dụng layout custom_dialog.xml cho giao diện hộp thoại
                .setTitle("")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Xử lý sự kiện khi người dùng nhấn nút "OK"
                        // Ví dụ: lấy dữ liệu từ EditText trong hộp thoại
//                        String userInput = editText.getText().toString();
                        // Do something with the user input
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Xử lý sự kiện khi người dùng nhấn nút "Cancel"
                        dialog.dismiss(); // Đóng hộp thoại
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void showDialogVNPAY() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(R.layout.custom_dialog_vnpay) // Sử dụng layout custom_dialog.xml cho giao diện hộp thoại
                .setTitle("")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Xử lý sự kiện khi người dùng nhấn nút "OK"
                        // Ví dụ: lấy dữ liệu từ EditText trong hộp thoại
//                        String userInput = editText.getText().toString();
                        // Do something with the user input
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Xử lý sự kiện khi người dùng nhấn nút "Cancel"
                        dialog.dismiss(); // Đóng hộp thoại
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void addControls() {
        tvMauSac = (findViewById(R.id.tvMauSac));
        tvDungLuong = (findViewById(R.id.tvDungLuong));
        tvToTal = (findViewById(R.id.tvToTal));
        spnQuanHuyen = findViewById(R.id.spnQuanHuyen);
        spnTinhTp = findViewById(R.id.spnTinhTp);
        lvKq = (ListView) findViewById(R.id.lvKq);
        rdoTaiCH = (RadioButton) findViewById(R.id.rdoNhanTaiCuaHang);
        rdoTaiNha = (RadioButton) findViewById(R.id.rdoNhanTaiNha);
        rdoMoMo = (RadioButton) findViewById(R.id.rdoMoMo);
        rdoVNpay = (RadioButton) findViewById(R.id.rdoVNpay);
        rdoTienmat = (RadioButton) findViewById(R.id.rdoTienmat);
        edtHoTen = (EditText) findViewById(R.id.edtHoTen);
        edtSDT = (EditText) findViewById(R.id.edtSDT);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        btnHoanThanh = (Button) findViewById(R.id.btnHoanThanh);
    }
    public void clearCart(){
        cartManager.clearCart();
        loadCartItems();
        Toast.makeText(PayActivity.this, "Thanh toán thành công", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(PayActivity.this, CartActivity.class);
        startActivity(intent);
    }
    private void spnClicks() {


        ArrayList<String> danhSachTinh = new ArrayList<>();
        danhSachTinh.add("Thành Phố Hồ Chí Minh"); // Thay thế bằng danh sách tỉnh thực tế
        danhSachTinh.add("Đồng Nai");

        HashMap<String, ArrayList<String>> danhSachHuyen = new HashMap<>();
        ArrayList<String> danhSachQuanHCM = new ArrayList<>();
        danhSachQuanHCM.add("Quận 1"); // Thay thế bằng danh sách huyện thực tế cho tỉnh A
        danhSachQuanHCM.add("Quận 2");
        danhSachQuanHCM.add("Quận 3");
        danhSachQuanHCM.add("Quận 4");
        danhSachQuanHCM.add("Gò Vấp");
        danhSachQuanHCM.add("Bình Thạnh");

        ArrayList<String> danhSachHuyenDN = new ArrayList<>();
        danhSachHuyenDN.add("Long Thành"); // Thay thế bằng danh sách huyện thực tế cho tỉnh B
        danhSachHuyenDN.add("Nhơn Trạch");
        danhSachHuyenDN.add("Tân Phú");
        danhSachHuyenDN.add("Long Khánh");

        danhSachHuyen.put("Thành Phố Hồ Chí Minh", danhSachQuanHCM);
        danhSachHuyen.put("Đồng Nai", danhSachHuyenDN);

        ArrayAdapter<String> tinhAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, danhSachTinh);
        tinhAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnTinhTp.setAdapter(tinhAdapter);


        danhSachTinh.add(0, "Chọn danh sách Tỉnh/Thành Phố");
        spnTinhTp.setSelection(0);


        // Xử lý sự kiện chọn tỉnh
        spnTinhTp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                // Check if the initial item is selected
                if (position == 0) {
                    spnQuanHuyen.setAdapter(null); // Clear the district spinner
                } else {
                    String selectedTinh = danhSachTinh.get(position);

                    ArrayList<String> danhSachHuyenTinh = danhSachHuyen.get(selectedTinh);

                    ArrayAdapter<String> huyenAdapter = new ArrayAdapter<>(PayActivity.this, android.R.layout.simple_spinner_dropdown_item, danhSachHuyenTinh);
                    danhSachHuyenTinh.add(0, "Chọn danh sách Quận/Huyện");
                    spnQuanHuyen.setSelection(0);
                    spnQuanHuyen.setAdapter(huyenAdapter);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Không làm gì khi không có tỉnh nào được chọn
            }
        });
    }
    private void loadCartItems() {
        cart_in_payHandler = new PayHandler(this, "SMARTPHONE.db", null, 1);
        if (imageProductHandler == null) {
            imageProductHandler = new ImageProductHandler(this, "SMARTPHONE.db", null, 1);
        }
        CartManager cartManager = new CartManager(this);
        payList = (ArrayList<Pay>) cartManager.getCart_in_PayItems();
        // Tải các hình ảnh cho từng sản pfm trong giỏ hàng
        for (Pay pay : payList) {
            int productId = pay.getId();

            // Lấy danh sách URL hình ảnh từ ImageProductHandler dựa vào ID sản phẩm
            List<ImageProduct> imageURLs = imageProductHandler.getListImagesByProductId(productId);
            // Cập nhật danh sách URL hình ảnh cho mỗi sản phẩm trong giỏ hàng
            for (ImageProduct img : imageURLs) {
                pay.addImageURL(img.getURL());
            }
        }
        // Hiển thị danh sách sản phẩm trong ListView bằng CustomAdapterCart
        CustomAdapterPay customCart_in_pay = new CustomAdapterPay(this, payList);
        lvKq.setAdapter(customCart_in_pay);

        // Hiển thị thông báo hoặc ẩn dựa trên danh sách sản phẩm trong giỏ hàng

    }
}